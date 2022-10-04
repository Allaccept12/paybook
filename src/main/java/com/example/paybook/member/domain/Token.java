package com.example.paybook.member.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id",nullable = false)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "token_value",nullable = false)
    private String value;

    @Column(name = "token_expire",nullable = false)
    private Long expire;


    protected Token(Member member, String value, Long expire) {
        this.member = member;
        this.value = value;
        this.expire = expire;
    }

    public static Token of(Member member, String value, Long expire) {
        return new Token(member,value,expire);
    }
}
