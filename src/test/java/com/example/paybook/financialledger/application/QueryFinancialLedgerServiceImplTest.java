package com.example.paybook.financialledger.application;

import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.financialledger.domain.FinancialLedger;
import com.example.paybook.financialledger.domain.FinancialLedgerRepository;
import com.example.paybook.financialledger.domain.Memo;
import com.example.paybook.financialledger.domain.Money;
import com.example.paybook.member.domain.Email;
import com.example.paybook.member.domain.Member;
import com.example.paybook.member.domain.Password;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@MockitoSettings(strictness = Strictness.WARN)
class QueryFinancialLedgerServiceImplTest {

    @InjectMocks
    QueryFinancialLedgerServiceImpl queryFinancialLedgerService;

    @Mock
    FinancialLedgerRepository financialLedgerRepository;

    @Test
    @DisplayName("전체 가계부 리스트 조회")
    void getAllFinancialLedger() {
        // given
        Member member = Member.createMember(Email.of("email"), Password.of("1234"));
        FinancialLedger financialLedger
                = FinancialLedger.createFinancialLedger(Memo.of("memo"), Money.of(1000),member);
        given(financialLedgerRepository.findByMemberId(anyLong()))
                .willReturn(List.of(financialLedger));
        // when
        ResponseDto<?> result = queryFinancialLedgerService.getAllFinancialLedger(anyLong());
        // then
        assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName(" 가계부 단건 조회")
    void getOneFinancialLedger() {
        // given
        Member member = Member.createMember(Email.of("email"), Password.of("1234"));
        FinancialLedger financialLedger
                = FinancialLedger.createFinancialLedger(Memo.of("memo"), Money.of(1000),member);
        given(financialLedgerRepository.findById(anyLong()))
                .willReturn(Optional.of(financialLedger));
        // when
        ResponseDto<?> result = queryFinancialLedgerService.getOneFinancialLedger(anyLong());
        // then
        assertTrue(result.isSuccess());
    }

}