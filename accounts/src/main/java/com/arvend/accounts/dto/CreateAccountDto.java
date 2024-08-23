package com.arvend.accounts.dto;

import com.arvend.accounts.constant.AccountType;
import com.arvend.accounts.customvalidation.SixteenDigits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDto {

    @NotNull(message = "Account Number cannot be null or empty")
    @SixteenDigits(message = "Account Number must be exactly 16 digits")
    private Long accountNumber;

    @NotNull(message = "Account Type cannot be null")
    private AccountType accountType;

    @NotEmpty(message = "Branch Address cannot be null or empty")
    private String branchAddress;

    private String createdBy;

    @NotNull(message = "Customer ID cannot be null")
    public int customer_id;

}
