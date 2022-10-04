package com.example.paybook.member.domain;


import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Password {

    @Column(nullable = false)
    private String password;

    protected Password() {
    }

    protected Password(String password) {
        this.password = encodePassword(password);
    }

    public static Password of(String password) {
        return new Password(password);
    }

    public boolean isMatched(String password) {
        return new BCryptPasswordEncoder().matches(password,this.password);
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
