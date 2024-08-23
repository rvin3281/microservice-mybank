package com.arvend.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountTypeException extends Exception {

    public AccountTypeException (String message)
    {
        super(message);
    }

}
