package com.example.paybook.common.error;


import lombok.Getter;

@Getter
public enum ErrorCode {
    ACCOUNT_NOT_FOUND("유저를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND("상품을 찾을 수 없습니다."),
    PASSWORD_IS_REQUIRED_VALUE("비밀번호는 필수 값 입니다."),
    EMAIL_IS_REQUIRED_VALUE("이메일은 필수 값 입니다."),
    EMAIL_DUPLICATION("중복된 이메일 입니다."),
    PASSWORD_WRONG("패스워드가 올바르지 않습니다."),
    TOKEN_NOT_FOUND("토큰이 없거나 이미 로그아웃 하셨습니다."),
    FINANCIAL_LEDGER_NOT_FOUND("가계부가 존재 하지 않습니다."),
    INPUT_VALUE_INVALID("입력값이 올바르지 않습니다.");


    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }


}
