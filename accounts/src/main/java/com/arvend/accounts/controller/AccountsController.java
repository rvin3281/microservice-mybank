package com.arvend.accounts.controller;

import com.arvend.accounts.constant.AccountConstant;
import com.arvend.accounts.dto.AccountContactInfoDto;
import com.arvend.accounts.dto.CreateAccountDto;
import com.arvend.accounts.dto.ReadCustomerAccountDto;
import com.arvend.accounts.dto.response.ErrorResponseDto;
import com.arvend.accounts.dto.response.ResponseDto;
import com.arvend.accounts.dto.response.ResponseWithDataDto;
import com.arvend.accounts.exception.AccountTypeException;
import com.arvend.accounts.exception.InternalException;
import com.arvend.accounts.exception.NotFoundException;
import com.arvend.accounts.exception.SaveException;
import com.arvend.accounts.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name="CRUD REST APIs for Accounts in Eazybank",
        description = "CRUD REST APIs in Eazybank to Create, Update, Fetch and Delete account details"
)
@RestController
@RequestMapping(path="/api/v1/accounts", produces= (MediaType.APPLICATION_JSON_VALUE))
@Validated
public class AccountsController {

    @Autowired
    AccountsService service;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountContactInfoDto accountsContactInfoDto;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Account"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description =  "HTTP Status CREATED"
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
    @PostMapping
    public ResponseEntity<ResponseDto> createAccount (@Valid @RequestBody CreateAccountDto createAccountDto, @RequestParam String createBy)
            throws NotFoundException, InternalException, AccountTypeException {

        service.createAccount(createAccountDto, createBy);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201, AccountConstant.MESSAGE_201));
    }

    @Operation(
            summary = "Get Account REST API",
            description = "REST API to GET account details"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description =  "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<ResponseWithDataDto<ReadCustomerAccountDto>> getCustomerWithAccount(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 Digit") String mobileNumber)
            throws NotFoundException, InternalException {

         ReadCustomerAccountDto readCustomerAccountDto = service.getCustomerWithAccount(mobileNumber);

         return ResponseEntity
                 .status(HttpStatus.OK)
                 .body(new ResponseWithDataDto<>(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200, readCustomerAccountDto));
    }

    @Operation(
            summary = "Delete Account REST API",
            description = "REST API to Delete account details"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description =  "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error"
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteAccountWithCustomer(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 Digit") String mobileNumber,
            @RequestParam long accountNumber,
            @RequestParam String updateBy)
            throws NotFoundException, InternalException {



        service.deleteAccountByCustomer(mobileNumber, accountNumber, updateBy);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
    }

    @Operation(
            summary = "Get Build Information",
            description = "Get build information that is deployed into accounts microservices"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description =  "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error"
                    )
            }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(
            summary = "Get Java Version",
            description = "Get Java version details that is installed into account microservice"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description =  "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error"
                    )
            }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME")+" "+environment.getProperty("APP_VERSION"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Get Contact Info details that is installed into account microservice"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description =  "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error"
                    )
            }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

}
