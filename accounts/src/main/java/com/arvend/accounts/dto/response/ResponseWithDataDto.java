package com.arvend.accounts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWithDataDto<T> {

    public String statusCode;

    public String statusMsg;

    public T data;

}
