package com.example.paybook.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class TokenDto {

    private String accessToken;
    private Long accessTokenExpiresIn;

    public TokenDto() {
    }
}
