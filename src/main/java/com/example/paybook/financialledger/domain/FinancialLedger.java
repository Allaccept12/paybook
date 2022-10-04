package com.example.paybook.financialledger.domain;


import com.example.paybook.common.converter.FlagConverter;
import com.example.paybook.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "delete_flag = 'F'")
public class FinancialLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "financial_ledger_id")
    private Long id;

    @Embedded
    private Memo memo;

    @Embedded
    private Money amount;


    @Convert(converter = FlagConverter.class)
    @Column(name = "delete_flag")
    private boolean deleteFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected FinancialLedger(Memo memo, Money amount,Member member) {
        this.memo = memo;
        this.amount = amount;
        this.member = member;
        this.deleteFlag = false;
    }

    public static FinancialLedger createFinancialLedger(Memo memo, Money money, Member member) {
        return new FinancialLedger(memo,money,member);
    }

    public void editRecord(Memo memo, Money amount) {
        this.memo = memo;
        this.amount = amount;
    }

    public void updateDeleteFlag() {
        this.deleteFlag = !this.isDeleteFlag();
    }
}
