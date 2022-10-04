package com.example.paybook.member.presentation;


import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.member.application.MemberService;
import com.example.paybook.member.domain.UserDetailsImpl;
import com.example.paybook.member.dto.LoginReqDto;
import com.example.paybook.member.dto.SignUpReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/signup")
    public ResponseDto<?> signUp(@RequestBody SignUpReqDto ReqDto) {
        return memberService.signUp(ReqDto);
    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody LoginReqDto ReqDto,
                                HttpServletResponse response,
                                HttpServletRequest request) {
        return memberService.login(ReqDto,response,request);
    }

    @PostMapping("/logout")
    public ResponseDto<?> logout(@AuthenticationPrincipal UserDetailsImpl member) {
        return memberService.logout(member);
    }
}
