package com.example.paybook.financialledger.application;

import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.financialledger.domain.FinancialLedger;
import com.example.paybook.financialledger.domain.FinancialLedgerRepository;
import com.example.paybook.financialledger.domain.Memo;
import com.example.paybook.financialledger.domain.Money;
import com.example.paybook.financialledger.dto.CreateFinancialLedgerReqDto;
import com.example.paybook.financialledger.dto.EditFinancialLedgerReqDto;
import com.example.paybook.member.domain.Email;
import com.example.paybook.member.domain.Member;
import com.example.paybook.member.domain.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@MockitoSettings(strictness = Strictness.WARN)
class CommandFinancialLedgerServiceImplTest {

    @InjectMocks
    CommandFinancialLedgerServiceImpl commandFinancialLedgerService;

    @Mock
    FinancialLedgerRepository financialLedgerRepository;

    @Test
    @DisplayName("가계부 작성")
    void createFinancialLedger() {
        // given
        CreateFinancialLedgerReqDto reqDto = new CreateFinancialLedgerReqDto("첫 가계부",1000);
        Member member = Member.createMember(Email.of("email"), Password.of("1234"));
        FinancialLedger financialLedger
                = FinancialLedger.createFinancialLedger(Memo.of(reqDto.getMemo()), Money.of(reqDto.getAmount()),member);
        given(financialLedgerRepository.save(any())).willReturn(financialLedger);
        // when
        ResponseDto<?> result = commandFinancialLedgerService.createFinancialLedger(reqDto, member);
        // then
        assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("가계부 수정")
    void editFinancialLedger() {
        // given
        EditFinancialLedgerReqDto reqDto
                = new EditFinancialLedgerReqDto(1L,1000,"첫 가계부");
        Member member = Member.createMember(Email.of("email"), Password.of("1234"));
        FinancialLedger financialLedger
                = FinancialLedger.createFinancialLedger(Memo.of(reqDto.getMemo()), Money.of(reqDto.getAmount()),member);
        given(financialLedgerRepository.findById(anyLong())).willReturn(Optional.of(financialLedger));
        // when
        ResponseDto<?> result = commandFinancialLedgerService.editFinancialLedger(reqDto, member);
        // then
        assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("가계부 삭제플래그")
    void updateFinancialLedgerFlag() {
        // given
        Member member = Member.createMember(Email.of("email"), Password.of("1234"));
        FinancialLedger financialLedger
                = FinancialLedger.createFinancialLedger(Memo.of("가계부 1"), Money.of(1000),member);
        given(financialLedgerRepository.findByMemberIdAtNativeQuery(anyLong()))
                .willReturn(Optional.of(financialLedger));
        // when
        ResponseDto<?> result = commandFinancialLedgerService.updateFinancialLedgerFlag(anyLong());
        // then
        assertTrue(result.isSuccess());
    }

}