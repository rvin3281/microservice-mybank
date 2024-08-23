package com.arvend.accounts.mapper;

import com.arvend.accounts.dto.CreateCustomerDto;
import com.arvend.accounts.dto.ReadCustomerDto;
import com.arvend.accounts.entity.Customers;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customers createCustomerDtoToCustomer (CreateCustomerDto createCustomerDt);

    ReadCustomerDto customerToReadCustomerDto(Customers customers);

}
