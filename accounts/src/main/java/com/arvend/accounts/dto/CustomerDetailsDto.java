package com.arvend.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(
        name="CustomDetails",
        description = "Schema to hold Customer and Account Information"
)
public class CustomerDetailsDto {

    private ReadCustomerAccountDto readCustomerAccountDto;

    // CARDS DTO
    private ReadCardsDto readCardsDto;

    // LOANS DTO
    private ReadLoanDto readLoanDto;
}
