package com.example.paybook.member.Presentation;


import com.example.paybook.BaseIntegrationTest;
import com.example.paybook.jwt.TokenProvider;
import com.example.paybook.member.domain.*;
import com.example.paybook.member.dto.LoginReqDto;
import com.example.paybook.member.dto.SignUpReqDto;
import com.example.paybook.member.dto.TokenDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class MemberIT extends BaseIntegrationTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TokenProvider tokenProvider;

    @BeforeEach
    void init() {
        memberRepository.deleteAll();
        String TEST_EMAIL = "email@naver.com";
        String TEST_PASSWORD = "1234";
        Member member = Member.createMember(Email.of(TEST_EMAIL), Password.of(TEST_PASSWORD));
        memberRepository.save(member);
    }

    @Test
    @DisplayName("회원가입 통합테스트")
    void signupIT() throws Exception {
        // given
        String TEST_EMAIL = "email";
        String TEST_PASSWORD = "1234";
        SignUpReqDto reqDto = new SignUpReqDto(TEST_EMAIL, TEST_PASSWORD);
        // when
        ResultActions resultActions = mvc.perform(
                        post("/api/member/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reqDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",is(notNullValue())));
    }

    @Test
    @DisplayName("로그인 통합테스트")
    void loginIT() throws Exception {
        // given
        String TEST_EMAIL = "email@naver.com";
        String TEST_PASSWORD = "1234";
        LoginReqDto reqDto = new LoginReqDto(TEST_EMAIL, TEST_PASSWORD);

        // when
        ResultActions resultActions = mvc.perform(
                        post("/api/member/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reqDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",is(notNullValue())))
                .andExpect(header().string("Authorization",is(notNullValue())));
    }

    @Test
    @DisplayName("로그아웃 통합테스트")
    void logoutIT() throws Exception {
        // given
        String TEST_EMAIL = "email@naver.com";
        Optional<Member> member = memberRepository.findByEmail(Email.of(TEST_EMAIL));
        UserDetails userDetails = new UserDetailsImpl(member.get());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
        TokenDto tokenDto = tokenProvider.generateTokenDto(member.get());
        // when
        ResultActions resultActions = mvc.perform(
                        post("/api/member/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization",tokenDto.getAccessToken())
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",is(notNullValue())));

    }
}
