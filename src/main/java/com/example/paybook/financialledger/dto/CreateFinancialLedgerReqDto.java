package com.example.paybook.financialledger.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFinancialLedgerReqDto {

    private String memo;
    private int amount;

    public CreateFinancialLedgerReqDto() {
    }
}
