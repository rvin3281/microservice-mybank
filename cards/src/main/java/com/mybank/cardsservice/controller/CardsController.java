package com.mybank.cardsservice.controller;

import com.mybank.cardsservice.constants.CardsConstants;
import com.mybank.cardsservice.dto.CardsContactInfo;
import com.mybank.cardsservice.dto.ReadCardsDto;
import com.mybank.cardsservice.dto.RequestCardsDto;
import com.mybank.cardsservice.dto.response.APIErrorResponseDto;
import com.mybank.cardsservice.dto.response.APIResponseDto;
import com.mybank.cardsservice.dto.response.APIResponseWithDataDto;
import com.mybank.cardsservice.services.ServiceImpl.CardsServiceImpl;
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

@Tag(
        name = "CRUD REST APIs for Cards in InnocentUdo's Bank",
        description = "CRUD REST APIs in InnocentUdo's Bank to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping("/api/v1/cards")
@Validated
public class CardsController {

    @Autowired
    private CardsServiceImpl iCardsService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private CardsContactInfo cardsContactInfo;

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card inside InnocentUdo's Bank"
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
    public ResponseEntity<APIResponseDto> createCard(@Valid @RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        iCardsService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new APIResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201, LocalDateTime.now()));
    }

    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a mobile number"
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
    })
    @GetMapping
    public ResponseEntity<APIResponseWithDataDto<ReadCardsDto>> fetchCardDetails(@RequestParam
                                                     @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                     String mobileNumber) {
        ReadCardsDto readCardsDto = iCardsService.fetchCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new APIResponseWithDataDto<ReadCardsDto>(
                        CardsConstants.STATUS_201,
                        CardsConstants.MESSAGE_201,
                        LocalDateTime.now(),
                        readCardsDto));
    }

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
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
    })
    @PutMapping("/update")
    public ResponseEntity<APIResponseDto> updateCardDetails(@Valid @RequestBody RequestCardsDto requestCardsDto) {
        boolean isUpdated = iCardsService.updateCard(requestCardsDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new APIResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201, LocalDateTime.now()));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new APIResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE, LocalDateTime.now()));
        }
    }

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a mobile number"
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
    })
    @DeleteMapping("/delete")
    public ResponseEntity<APIResponseDto> deleteCardDetails(@RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        boolean isDeleted = iCardsService.deleteCard(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new APIResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201, LocalDateTime.now()));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new APIResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE, LocalDateTime.now()));
        }
    }

    @Operation(
            summary = "Get Build Information",
            description = "Get build information that is deployed into cards microservices"
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
            description = "Get Contact Info details that is installed into cards microservice"
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
    public ResponseEntity<CardsContactInfo> getContactInfo()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfo);
    }

}
