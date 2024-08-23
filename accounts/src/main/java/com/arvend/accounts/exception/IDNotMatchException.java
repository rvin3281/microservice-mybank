package com.arvend.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IDNotMatchException extends Exception{

    public IDNotMatchException(String message)
    {
        super(message);
    }

}
