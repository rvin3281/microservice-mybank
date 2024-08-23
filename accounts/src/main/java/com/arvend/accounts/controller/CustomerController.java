package com.arvend.accounts.controller;

import com.arvend.accounts.constant.AccountConstant;
import com.arvend.accounts.dto.CreateCustomerDto;
import com.arvend.accounts.dto.ReadCustomerDto;
import com.arvend.accounts.dto.UpdateCustomerDto;
import com.arvend.accounts.dto.response.ResponseDto;
import com.arvend.accounts.dto.response.ResponseWithDataDto;
import com.arvend.accounts.exception.IDNotMatchException;
import com.arvend.accounts.exception.InternalException;
import com.arvend.accounts.exception.NotFoundException;
import com.arvend.accounts.exception.SaveException;
import com.arvend.accounts.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/accounts/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CustomerController {

//    @Value("${test.variable1}")
//    String variable1;
//
//    @Value("${test.variable2}")
//    String variable2;
//
//    @Value("${spring.profiles.active}")
//    String profile;

    @Autowired
    private CustomerService service;

    @PostMapping
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) throws SaveException, InternalException {

         service.createCustomer(createCustomerDto);

         return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(new ResponseDto(AccountConstant.STATUS_201, AccountConstant.MESSAGE_201));
    }

    @GetMapping
    public ResponseEntity<ResponseWithDataDto<List<ReadCustomerDto>>> getAllCustomer() throws InternalException {

//        System.out.println(profile);
//
//        if(profile.equals("dev")){
//            System.out.println(variable1);
//        }else{
//            System.out.println(variable2);
//        }

        List<ReadCustomerDto> customers = service.getAllCustomers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWithDataDto<>(
                        AccountConstant.STATUS_200, AccountConstant.MESSAGE_200, customers
                ));
    }

    @GetMapping("/mobile-number")
    public ResponseEntity<ResponseWithDataDto<ReadCustomerDto>> findCustomerByMobileNumber(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 Digit") String mobileNumber)
            throws NotFoundException, InternalException {

        ReadCustomerDto readCustomerDto = service.getCustomerByMobileNumber(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWithDataDto<>(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200,readCustomerDto));
    }

    @PatchMapping
    public ResponseEntity<ResponseWithDataDto<ReadCustomerDto>> updateCustomerByMobileNumber(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 Digit")  String mobileNumber,
            @RequestParam String updateBy,
            @Valid @RequestBody UpdateCustomerDto updateCustomerDto)
            throws IDNotMatchException, NotFoundException, InternalException {

        ReadCustomerDto readCustomerDto = service.updateCustomerByMobileNumber(mobileNumber, updateBy, updateCustomerDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWithDataDto<>(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200, readCustomerDto));

    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteCustomerByMobileNumber(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 Digit") String mobileNumber,
            @RequestParam String updateBy)
            throws NotFoundException, InternalException {

            service.deleteCustomerByMobileNumber(mobileNumber, updateBy);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
    }

}
