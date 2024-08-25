package com.arvend.accounts.service.client;

import com.arvend.accounts.dto.APIResponseWithDataDTO;
import com.arvend.accounts.dto.ReadCardsDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient("cards")
public interface CardsFeignClient {

    @GetMapping(value = "/api/v1/cards", consumes = "application/json")
    public ResponseEntity<APIResponseWithDataDTO<ReadCardsDto>> fetchCardDetails(@RequestParam String mobileNumber);

}
