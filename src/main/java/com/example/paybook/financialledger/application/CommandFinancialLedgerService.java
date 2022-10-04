package com.example.paybook.financialledger.application;

import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.financialledger.dto.CreateFinancialLedgerReqDto;
import com.example.paybook.financialledger.dto.EditFinancialLedgerReqDto;
import com.example.paybook.member.domain.Member;

public interface CommandFinancialLedgerService {

    ResponseDto<?> createFinancialLedger(CreateFinancialLedgerReqDto createLedgerReqDto, Member member);
    ResponseDto<?> editFinancialLedger(EditFinancialLedgerReqDto editFinancialLedgerReqDto, Member member);
    ResponseDto<?> updateFinancialLedgerFlag(Long financialLedgerId);



}
