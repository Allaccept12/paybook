package com.example.paybook.financialledger.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditFinancialLedgerReqDto {

    private Long financialLedgerId;
    private int amount;
    private String memo;

    public EditFinancialLedgerReqDto() {
    }
}
