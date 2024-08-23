package com.mybank.cardsservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Read Cards",
        description = "Schema to Fetch Card information"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadCardsDto {

    private Long cardId;
    private String mobileNumber;
    private String cardNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;

}
