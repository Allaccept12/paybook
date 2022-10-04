package com.example.paybook.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class LoginResDto {

    private Long memberId;
    private String email;

    public LoginResDto() {
    }
}
