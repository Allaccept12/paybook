package com.example.paybook.financialledger.application;

import com.example.paybook.common.model.ResponseDto;
import com.example.paybook.financialledger.domain.FinancialLedger;
import com.example.paybook.financialledger.domain.FinancialLedgerRepository;
import com.example.paybook.financialledger.domain.Memo;
import com.example.paybook.financialledger.domain.Money;
import com.example.paybook.financialledger.dto.CreateFinancialLedgerReqDto;
import com.example.paybook.financialledger.dto.EditFinancialLedgerReqDto;
import com.example.paybook.member.domain.Member;
import com.example.paybook.member.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommandFinancialLedgerServiceImpl implements CommandFinancialLedgerService {

    private final FinancialLedgerRepository financialLedgerRepository;


    @Transactional
    public ResponseDto<?> createFinancialLedger(CreateFinancialLedgerReqDto reqDto, Member member) {
        FinancialLedger financialLedger = FinancialLedger.createFinancialLedger(
                Memo.of(reqDto.getMemo()), Money.of(reqDto.getAmount()), member
        );
        financialLedgerRepository.save(financialLedger);
        return ResponseDto.success(financialLedger.getId());
    }

    @Transactional
    public ResponseDto<?> editFinancialLedger(EditFinancialLedgerReqDto reqDto, Member member) {
        FinancialLedger financialLedger = getFinancialLedger(reqDto.getFinancialLedgerId());
        financialLedger.editRecord(Memo.of(reqDto.getMemo()),Money.of(reqDto.getAmount()));
        return ResponseDto.success("success");
    }

    @Transactional
    public ResponseDto<?> updateFinancialLedgerFlag(Long financialLedgerId) {
        FinancialLedger financialLedger = financialLedgerRepository.findByMemberIdAtNativeQuery(financialLedgerId)
                .orElseThrow(NotFoundFinancialLedgerException::new);
        financialLedger.updateDeleteFlag();
        return ResponseDto.success("success");
    }

    private FinancialLedger getFinancialLedger(Long financialLedgerId) {
        return financialLedgerRepository.findById(financialLedgerId)
                .orElseThrow(NotFoundFinancialLedgerException::new);
    }
}
