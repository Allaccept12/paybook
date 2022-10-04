package com.example.paybook.member.application;

import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.member.domain.UserDetailsImpl;
import com.example.paybook.member.dto.LoginReqDto;
import com.example.paybook.member.dto.SignUpReqDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MemberService {

    ResponseDto<?> login(LoginReqDto loginReqDto, HttpServletResponse response, HttpServletRequest request);
    ResponseDto<?> logout(UserDetailsImpl userDetails);
    ResponseDto<?> signUp(SignUpReqDto signUpReqDto);
}
