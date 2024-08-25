package com.arvend.accounts.controller;

import com.arvend.accounts.constant.AccountConstant;
import com.arvend.accounts.dto.CustomerDetailsDto;
import com.arvend.accounts.dto.response.ErrorResponseDto;
import com.arvend.accounts.dto.response.ResponseDto;
import com.arvend.accounts.dto.response.ResponseWithDataDto;
import com.arvend.accounts.exception.InternalException;
import com.arvend.accounts.exception.NotFoundException;
import com.arvend.accounts.service.impl.CustomServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST API for Customer in Eazybank",
        description = "REST APIs in EazyBank to Fetch customer details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class ExternalController {

    @Autowired
    private CustomServiceImpl customService;

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch customer details based on mobile number"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description =  "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error",
                            content=@Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<ResponseWithDataDto<CustomerDetailsDto>> fetchCustomerDetails(@RequestParam
                                                                   @Pattern(regexp = "(^$|[0-9]{10})",message="Mobile number must be 10 digit")
                                                                   String mobileNumner) throws NotFoundException, InternalException {

        CustomerDetailsDto customerDetailsDto = customService.fetchFeignCustomerDetails(mobileNumner);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWithDataDto<>(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200, customerDetailsDto));
    }

}
