package com.eazybyte.loanservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "Loan Data Return",
        description = "Schema to hold the return loan information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadLoanDto {

    private Long loanId;

    @Schema(
            description = "Mobile number of customer",
            example = "8163093928"
    )
    private String mobileNumber;

    @Schema(
            description = "Loan number of customer",
            example = "231234897034"
    )
    private String loanNumber;

    @Schema(
            description = "Type of the loan",
            example = "Home Loan"
    )
    private String loanType;

    @Schema(
            description = "Total loan amount",
            example = "100000"
    )
    private int totalLoan;

    @Schema(
            description = "Total loan amount paid",
            example = "1000"
    )
    private int amountPaid;

    @Schema(
            description = "Total outstanding amount against a loan",
            example = "99000"
    )
    private int outstandingAmount;

}
