package com.example.paybook.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class SignUpReqDto {

    @NotBlank(message = "이메일은 필수 값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    private String password;

    public SignUpReqDto() {
    }

}
