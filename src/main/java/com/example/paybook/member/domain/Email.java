package com.example.paybook.member.domain;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Email {

    @Column(nullable = false,unique = true)
    private String email;

    protected Email() {
    }
    protected Email(String email) {
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }


}
