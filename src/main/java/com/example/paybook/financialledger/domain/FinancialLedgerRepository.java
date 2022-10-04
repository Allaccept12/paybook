package com.example.paybook.financialledger.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FinancialLedgerRepository extends JpaRepository<FinancialLedger,Long> {

    @Query("select f from FinancialLedger f where f.member.id =:memberId")
    List<FinancialLedger> findByMemberId(@Param("memberId") Long memberId);

    @Query(value = "select * from financial_ledger as fl where fl.financial_ledger_id =:financialLedgerId",nativeQuery = true)
    Optional<FinancialLedger> findByMemberIdAtNativeQuery(@Param("financialLedgerId") Long financialLedgerId);
}
