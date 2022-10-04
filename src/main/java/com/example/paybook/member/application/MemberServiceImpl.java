package com.example.paybook.member.application;

import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.jwt.TokenProvider;
import com.example.paybook.member.domain.*;
import com.example.paybook.member.dto.LoginReqDto;
import com.example.paybook.member.dto.LoginResDto;
import com.example.paybook.member.dto.SignUpReqDto;
import com.example.paybook.member.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> signUp(SignUpReqDto signUpReqDto) {
        if (checkDuplicatedEmail(signUpReqDto.getEmail())) {
            throw new EmailDuplicationException(Email.of(signUpReqDto.getEmail()));
        }
        Member member = Member.createMember(Email.of(signUpReqDto.getEmail()), Password.of(signUpReqDto.getPassword()));
        memberRepository.save(member);
        return ResponseDto.success(member.getId());
    }

    @Transactional
    public ResponseDto<?> login(LoginReqDto loginReqDto,
                                HttpServletResponse response, HttpServletRequest request) {

        Member member = getMember(loginReqDto.getEmail());
        if (!member.checkPassword(loginReqDto.getPassword())) {
            throw new WrongPasswordException();
        }

        TokenDto tokenDto = generateToken(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(new LoginResDto(member.getId(),member.getEmail().getEmail()));
    }

    @Transactional
    public ResponseDto<?> logout(UserDetailsImpl userDetails) {
        if (userDetails.getMember() == null) {
            throw new MemberNotFoundException();
        }
        return tokenProvider.deleteToken(userDetails.getMember());
    }

    private boolean checkDuplicatedEmail(String email) {
        return memberRepository.existByEmail(Email.of(email)) != null;
    }

    private void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    private TokenDto generateToken(Member member) {
        Optional<Token> optToken = tokenRepository.findByMember(member);
        if (optToken.isEmpty() || !tokenProvider.validateToken(optToken.get().getValue())) {
            return tokenProvider.generateTokenDto(member);
        }
        return new TokenDto(optToken.get().getValue(), optToken.get().getExpire());
    }


    public Member getMember(String email) {
        return memberRepository.findByEmail(Email.of(email))
                .orElseThrow(MemberNotFoundException::new);
    }

}
