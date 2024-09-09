package com.arvend.accounts.service.client;

import com.arvend.accounts.dto.APIResponseWithDataDTO;
import com.arvend.accounts.dto.ReadCardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient {

    @Override
    public ResponseEntity<APIResponseWithDataDTO<ReadCardsDto>> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
