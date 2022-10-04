package com.example.paybook.member.application;

import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.jwt.TokenProvider;
import com.example.paybook.member.domain.*;
import com.example.paybook.member.dto.LoginReqDto;
import com.example.paybook.member.dto.SignUpReqDto;
import com.example.paybook.member.dto.TokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@MockitoSettings(strictness = Strictness.WARN)
class MemberServiceImplTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    TokenProvider tokenProvider;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 성공")
    void signupTest() {
        // given
        SignUpReqDto reqDto = new SignUpReqDto("email","1234");
        //Member member = Member.createMember(Email.of("email"), Password.of("123123"));
        given(memberRepository.save(any())).willReturn(any());
        given(memberRepository.existByEmail(Email.of(reqDto.getEmail()))).willReturn(null);
        // when
        ResponseDto<?> result = memberService.signUp(reqDto);
        // then
        assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("로그인 성공")
    void loginTest() {
        //given
        HttpServletResponse response = new MockHttpServletResponse();
        HttpServletRequest request = new MockHttpServletRequest();
        LoginReqDto loginReqDto = new LoginReqDto("email", "123123");
        Member member = Member.createMember(Email.of("email"), Password.of("123123"));
        TokenDto token = new TokenDto("token", 1234L);

        given(tokenProvider.generateTokenDto(any())).willReturn(token);
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));
        //when
        ResponseDto<?> result = memberService.login(loginReqDto, response, request);
        // then
        assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logoutTest() {
        // given
        Member member = Member.createMember(Email.of("email"), Password.of("123123"));
        UserDetailsImpl userDetails = new UserDetailsImpl(member);

        given(tokenProvider.deleteToken(any())).willReturn(ResponseDto.success(any()));
        // when
        ResponseDto<?> result = memberService.logout(userDetails);
        // then
        assertTrue(result.isSuccess());
    }

}