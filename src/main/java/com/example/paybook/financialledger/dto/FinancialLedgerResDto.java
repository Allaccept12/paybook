package com.example.paybook.financialledger.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FinancialLedgerResDto {

    private Long financialLedgerId;
    private String memo;
    private int amount;

    public FinancialLedgerResDto() {
    }

}
