package com.arvend.accounts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    public String statusCode;

    public String statusMsg;
}
