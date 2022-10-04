package com.example.paybook.member.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    protected Member(Email email, Password password) {
        this.email = email;
        this.password = password;
    }

    public static Member createMember(Email email, Password password) {
        return new Member(email, password);
    }

    public boolean checkPassword(String password) {
        return this.password.isMatched(password);
    }
}
