package com.example.paybook.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResDto {

    private Long memberId;
    private String email;

    public LoginResDto() {
    }
}
