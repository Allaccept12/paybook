package com.example.paybook.financialledger.presentation;

import com.example.paybook.BaseIntegrationTest;
import com.example.paybook.financialledger.domain.FinancialLedger;
import com.example.paybook.financialledger.domain.FinancialLedgerRepository;
import com.example.paybook.financialledger.domain.Memo;
import com.example.paybook.financialledger.domain.Money;
import com.example.paybook.financialledger.dto.CreateFinancialLedgerReqDto;
import com.example.paybook.financialledger.dto.EditFinancialLedgerReqDto;
import com.example.paybook.jwt.TokenProvider;
import com.example.paybook.member.domain.*;
import com.example.paybook.member.dto.TokenDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



class FinancialLedgerIT extends BaseIntegrationTest {

    @Autowired
    FinancialLedgerRepository financialLedgerRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TokenProvider tokenProvider;

    @BeforeEach
    void init() {
        financialLedgerRepository.deleteAll();
        memberRepository.deleteAll();
        Member saveMember = saveAndGetMember();

    }

    @Test
    @DisplayName("가계부 작성 통합테스트")
    void createIT() throws Exception {
        // given
        Member saveMember = saveAndGetMember();
        CreateFinancialLedgerReqDto reqDto
                = new CreateFinancialLedgerReqDto("memo",10000);
        Optional<Member> member = memberRepository.findById(saveMember.getId());
        UserDetails userDetails = new UserDetailsImpl(member.get());
        settingSecurity(userDetails);
        TokenDto tokenDto = getTokenDto(member.get());
        // when
        ResultActions resultActions = mvc.perform(
                        post("/api/financial-ledger")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reqDto))
                                .header("Authorization",tokenDto.getAccessToken())
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",is(notNullValue())));
    }


    @Test
    @DisplayName("가계부 수정 통합테스트")
    void editIT() throws Exception {
        // given
        Member saveMember = saveAndGetMember();
        FinancialLedger financialLedger = saveAndGetFinancialLedger(saveMember);
        EditFinancialLedgerReqDto reqDto
                = new EditFinancialLedgerReqDto(financialLedger.getId(),10000,"memo");

        Optional<Member> member = memberRepository.findById(saveMember.getId());
        UserDetails userDetails = new UserDetailsImpl(member.get());
        settingSecurity(userDetails);
        TokenDto tokenDto = getTokenDto(member.get());
        // when
        ResultActions resultActions = mvc.perform(
                        patch("/api/financial-ledger")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reqDto))
                                .header("Authorization",tokenDto.getAccessToken())
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",is(notNullValue())));
    }

    @Test
    @DisplayName("가계부 삭제 플래그 통합테스트")
    void updateFlagIT() throws Exception {
        // given
        Member saveMember = saveAndGetMember();
        FinancialLedger financialLedger = saveAndGetFinancialLedger(saveMember);
        Optional<Member> member = memberRepository.findById(saveMember.getId());
        UserDetails userDetails = new UserDetailsImpl(member.get());
        settingSecurity(userDetails);
        TokenDto tokenDto = getTokenDto(member.get());
        // when
        ResultActions resultActions = mvc.perform(
                        put("/api/financial-ledger/{financialLedgerId}",financialLedger.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization",tokenDto.getAccessToken())
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",is(notNullValue())));
    }

    @Test
    @DisplayName("가계부 전체 리스트 조회 통합테스트")
    void getAllFinancialLedgerIT() throws Exception {
        // given
        Member saveMember = saveAndGetMember();
        Optional<Member> member = memberRepository.findById(saveMember.getId());
        UserDetails userDetails = new UserDetailsImpl(member.get());
        settingSecurity(userDetails);
        TokenDto tokenDto = getTokenDto(member.get());
        // when
        ResultActions resultActions = mvc.perform(
                        get("/api/financial-ledger")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization",tokenDto.getAccessToken())
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",is(notNullValue())));
    }

    @Test
    @DisplayName("가계부 단건 조회 통합테스트")
    void getOneFinancialLedgerIT() throws Exception {
        // given
        Member saveMember = saveAndGetMember();
        FinancialLedger financialLedger = saveAndGetFinancialLedger(saveMember);
        Optional<Member> member = memberRepository.findById(saveMember.getId());
        UserDetails userDetails = new UserDetailsImpl(member.get());
        settingSecurity(userDetails);
        TokenDto tokenDto = getTokenDto(member.get());
        // when
        ResultActions resultActions = mvc.perform(
                        get("/api/financial-ledger/{financialLedgerId}",financialLedger.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization",tokenDto.getAccessToken())
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",is(notNullValue())));
    }

    private Member saveAndGetMember() {
        Member member = Member.createMember(Email.of("email"), Password.of("1234"));
        return memberRepository.save(member);
    }

    private FinancialLedger saveAndGetFinancialLedger(Member member) {
        FinancialLedger financialLedger
                = FinancialLedger.createFinancialLedger(Memo.of("memo"), Money.of(1000),member);
        return financialLedgerRepository.save(financialLedger);
    }


    private void settingSecurity(UserDetails userDetails) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }

    private TokenDto getTokenDto(Member member) {
        return tokenProvider.generateTokenDto(member);
    }

}