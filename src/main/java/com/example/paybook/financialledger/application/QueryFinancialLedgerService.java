package com.example.paybook.financialledger.application;

import com.example.paybook.common.model.ResponseDto;

public interface QueryFinancialLedgerService {

    ResponseDto<?> getAllFinancialLedger(Long memberId);
    ResponseDto<?> getOneFinancialLedger(Long financialLedgerId);
}
