package com.arvend.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name="NewCustomer",
        description = "Schema to create new Customer"
)
public class CreateCustomerDto {

    @Schema(
            description ="Name of the customer",
            example = "Arvend"
    )
    @NotEmpty(message="Name cannot be null or empty")
    @Size(min=5, max=30, message="The length of customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email of the customer",
            example = "arvend@cc.com"
    )
    @NotEmpty(message="Email cannot be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Mobile Number of the customer",
            example = "01251177171"
    )
    @NotEmpty(message="Mobile Number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 Digit")
    private String mobileNumber;

    private String createdBy;

}
