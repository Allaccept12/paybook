package com.example.paybook.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {

    private String accessToken;
    private Long accessTokenExpiresIn;

    public TokenDto() {
    }
}
