package com.example.paybook.financialledger.application;

import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.financialledger.domain.FinancialLedger;
import com.example.paybook.financialledger.domain.FinancialLedgerRepository;
import com.example.paybook.financialledger.dto.FinancialLedgerResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryFinancialLedgerServiceImpl implements QueryFinancialLedgerService{

    private final FinancialLedgerRepository financialLedgerRepository;

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllFinancialLedger(Long memberId) {
        List<FinancialLedger> financialLedgerList = financialLedgerRepository.findByMemberId(memberId);
        return ResponseDto.success(financialLedgerList.stream()
                .map(d -> new FinancialLedgerResDto(d.getId(),d.getMemo().getMemo(),d.getAmount().getAmount()))
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getOneFinancialLedger(Long financialLedgerId) {
        FinancialLedger financialLedger = financialLedgerRepository.findById(financialLedgerId)
                .orElseThrow(NotFoundFinancialLedgerException::new);
        return ResponseDto.success(
                new FinancialLedgerResDto(
                        financialLedger.getId(),
                        financialLedger.getMemo().getMemo(),
                        financialLedger.getAmount().getAmount()));
    }
}
