package com.eazybyte.loanservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(
        name="Response Data",
        description = "Schema to hold successful response with Data information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponseWithDataDTO<T> {

    @Schema(description = "Status code in the response")
    private String statusCode;

    @Schema(description = "Current Time of response return")
    private LocalDateTime timestamp;

    @Schema(description = "Status message in the response")
    private String message;

    @Schema(description = "Success or fail")
    private boolean Success;

    @Schema(description = "Data in the response")
    private T data;

}
