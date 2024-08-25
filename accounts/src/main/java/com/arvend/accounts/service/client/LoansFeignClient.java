package com.arvend.accounts.service.client;

import com.arvend.accounts.dto.APIResponseWithDataDTO;
import com.arvend.accounts.dto.ReadLoanDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping(value = "api/v1/loans", consumes = "application/json")
    public ResponseEntity<APIResponseWithDataDTO<ReadLoanDto>> fetchLoanDetails(@RequestParam String mobileNumber);



}
