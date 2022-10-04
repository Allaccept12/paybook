package com.example.paybook.financialledger.domain;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Money {

    @Column(nullable = false)
    private int amount;

    protected Money() {
    }

    protected Money(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public static Money of(int amount) {
        return new Money(amount);
    }

    @Override
    public String toString() {
        return Integer.toString(amount);
    }
}
