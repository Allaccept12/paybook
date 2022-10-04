package com.example.paybook.common.error;


import com.example.paybook.financialledger.application.NotFoundFinancialLedgerException;
import com.example.paybook.jwt.NotFoundTokenException;
import com.example.paybook.member.application.EmailDuplicationException;
import com.example.paybook.member.application.MemberNotFoundException;
import com.example.paybook.member.application.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = {MemberNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleAccountNotFoundException(MemberNotFoundException e) {
        final ErrorCode errorCode = ErrorCode.ACCOUNT_NOT_FOUND;
        return ErrorResponse.builder().message(errorCode.getValue()).build();
    }

    @ExceptionHandler(value = {NotFoundTokenException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleTokenNotFoundException(NotFoundTokenException e) {
        final ErrorCode errorCode = ErrorCode.TOKEN_NOT_FOUND;
        return ErrorResponse.builder().message(errorCode.getValue()).build();
    }
    @ExceptionHandler(value = {NotFoundFinancialLedgerException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleFinancialLedgerNotFoundException(NotFoundFinancialLedgerException e) {
        final ErrorCode errorCode = ErrorCode.FINANCIAL_LEDGER_NOT_FOUND;
        return ErrorResponse.builder().message(errorCode.getValue()).build();
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleIllegalStateException(IllegalStateException e) {
        return ErrorResponse.builder().message(e.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return ErrorResponse.builder()
                .message(ErrorCode.INPUT_VALUE_INVALID.getValue())
                .errors(fieldErrors)
                .build();
    }

    @ExceptionHandler(EmailDuplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleConstraintViolationException(EmailDuplicationException e) {
        final ErrorCode errorCode = ErrorCode.EMAIL_DUPLICATION;
        return ErrorResponse.builder().message(errorCode.getValue()).build();
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleWrongPasswordException(WrongPasswordException e) {
        final ErrorCode errorCode = ErrorCode.PASSWORD_WRONG;
        return ErrorResponse.builder().message(errorCode.getValue()).build();
    }

    private List<ErrorResponse.FieldError> getFieldErrors(BindingResult bindingResult) {
        final List<FieldError> errors = bindingResult.getFieldErrors();
        return errors.stream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value((String) error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }
}
