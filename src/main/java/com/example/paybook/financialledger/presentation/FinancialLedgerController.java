package com.example.paybook.financialledger.presentation;

import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.financialledger.application.CommandFinancialLedgerService;
import com.example.paybook.financialledger.application.QueryFinancialLedgerService;
import com.example.paybook.financialledger.dto.CreateFinancialLedgerReqDto;
import com.example.paybook.financialledger.dto.EditFinancialLedgerReqDto;
import com.example.paybook.member.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/financial-ledger")
public class FinancialLedgerController {

    private final QueryFinancialLedgerService queryFinancialLedgerService;
    private final CommandFinancialLedgerService commandFinancialLedgerService;

    @PostMapping("")
    public ResponseDto<?> create(@RequestBody CreateFinancialLedgerReqDto reqDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commandFinancialLedgerService.createFinancialLedger(reqDto,userDetails.getMember());
    }

    @PatchMapping("")
    public ResponseDto<?> edit(@RequestBody EditFinancialLedgerReqDto reqDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commandFinancialLedgerService.editFinancialLedger(reqDto,userDetails.getMember());
    }

    @PutMapping("/{financialLedgerId}")
    public ResponseDto<?> updateFlag(@PathVariable("financialLedgerId") Long financialLedgerId) {
        return commandFinancialLedgerService.updateFinancialLedgerFlag(financialLedgerId);
    }

    @GetMapping("")
    public ResponseDto<?> getAllFinancialLedger(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return queryFinancialLedgerService.getAllFinancialLedger(userDetails.getMember().getId());
    }

    @GetMapping("/{financialLedgerId}")
    public ResponseDto<?> getOneFinancialLedger(@PathVariable("financialLedgerId") Long financialLedgerId) {
        return queryFinancialLedgerService.getOneFinancialLedger(financialLedgerId);
    }
}
