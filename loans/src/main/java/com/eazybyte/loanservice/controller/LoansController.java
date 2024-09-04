package com.eazybyte.loanservice.controller;

import com.eazybyte.loanservice.constant.LoansConstants;
import com.eazybyte.loanservice.dto.LoanContactInfo;
import com.eazybyte.loanservice.dto.ReadLoanDto;
import com.eazybyte.loanservice.dto.RequestLoanDto;
import com.eazybyte.loanservice.dto.response.APIErrorResponseDto;
import com.eazybyte.loanservice.dto.response.APIResponseDTO;
import com.eazybyte.loanservice.dto.response.APIResponseWithDataDTO;
import com.eazybyte.loanservice.services.ILoansService;
import com.eazybyte.loanservice.services.ServicesImpl.LoansServiceImpl;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author Innocent Udo
 */
@Tag(
        name = "CRUD REST APIs for Loans in InnocentUdo's Bank",
        description = "CRUD REST APIs in InnocentUdo's Bankto CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping("api/v1/loans")
@Validated
public class LoansController {

    @Autowired
    private LoansServiceImpl service;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private LoanContactInfo loanContactInfo;

    // CREATE API
    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside InnocentUdo's Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = APIErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping
    public ResponseEntity<APIResponseDTO> createLoan(
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
            String mobileNumber)
    {
        service.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new APIResponseDTO(LoansConstants.STATUS_201, LocalDateTime.now(), LoansConstants.MESSAGE_201, true));
    }

    // READ ALL API
    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = APIErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping
    public ResponseEntity<APIResponseWithDataDTO<ReadLoanDto>> fetchLoanDetails(
            @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber)
    {
        ReadLoanDto loanDTO = service.fetchLoan(mobileNumber);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new APIResponseWithDataDTO<ReadLoanDto>(
                        LoansConstants.STATUS_200,
                        LocalDateTime.now(),
                        LoansConstants.MESSAGE_200,
                        true,
                        loanDTO
                ));
    }

    // UPDATE API
    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = APIErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping()
    public ResponseEntity<APIResponseDTO> updateLoanDetails(@Valid @RequestBody RequestLoanDto loanDTO) {
        boolean isUpdated = service.updateLoan(loanDTO);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new APIResponseDTO(LoansConstants.STATUS_200, LocalDateTime.now(), LoansConstants.MESSAGE_200,true));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new APIResponseDTO(LoansConstants.STATUS_417, LocalDateTime.now(), LoansConstants.MESSAGE_417_UPDATE, false));
        }
    }

    // DELETE BY ID API
    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = APIErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping
    public ResponseEntity<APIResponseDTO> deleteLoanDetails(
            @RequestParam
            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
            String mobileNumber) {
        boolean isDeleted = service.deleteLoan(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new APIResponseDTO(LoansConstants.STATUS_200, LocalDateTime.now(), LoansConstants.MESSAGE_200,true));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new APIResponseDTO(LoansConstants.STATUS_417, LocalDateTime.now(), LoansConstants.MESSAGE_417_UPDATE, false));
        }
    }
    @Operation(
            summary = "Get Build Information",
            description = "Get build information that is deployed into Loans microservices"
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
            summary = "Get Contact Info",
            description = "Get Contact Info details that is installed into Loans microservice"
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
    public ResponseEntity<LoanContactInfo> getContactInfo()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanContactInfo);
    }

}
