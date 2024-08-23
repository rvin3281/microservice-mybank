package com.arvend.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadCustomerAccountDto {

    private Integer customer_id;

    private String name;

    private String email;

    private String mobileNumber;

    private List<ReadAccountDto> accounts;


}
