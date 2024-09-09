package com.arvend.accounts.service.client;

import com.arvend.accounts.dto.APIResponseWithDataDTO;
import com.arvend.accounts.dto.ReadLoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {

    @Override
    public ResponseEntity<APIResponseWithDataDTO<ReadLoanDto>> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }

}
