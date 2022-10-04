package com.example.paybook.financialledger.domain;


import javax.persistence.Embeddable;

@Embeddable
public class Memo {

    private String memo;

    protected Memo() {
    }

    protected Memo(String memo) {
        this.memo = memo;
    }

    public static Memo of(String memo) {
        return new Memo(memo);
    }

    public String getMemo() {
        return memo;
    }


}
