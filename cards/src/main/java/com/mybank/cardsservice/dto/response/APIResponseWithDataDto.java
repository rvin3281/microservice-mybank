package com.mybank.cardsservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponseWithDataDto<T> {

    private String statusMsg;

    private String statusCode;

    private LocalDateTime timeStamp;

    private T data;

}
