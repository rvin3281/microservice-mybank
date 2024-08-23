package com.arvend.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerDto {

    private int customer_id;

    private String name;

    private String email;

    private String mobileNumber;


}
