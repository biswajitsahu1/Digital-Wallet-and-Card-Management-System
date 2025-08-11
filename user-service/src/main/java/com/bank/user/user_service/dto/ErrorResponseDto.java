package com.bank.user.user_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Schema(name = "ErrorResponse", description = "Standard error response format")
public class ErrorResponseDto {

    @Schema(description = "Request path where the error occurred")
    private String path;

    @Schema(description = "HTTP status code")
    private String status;

    @Schema(description = "Error message")
    private String message;

    @Schema(description = "Timestamp of the error")
    private LocalDateTime timestamp;

    @Schema(description = "Request trace ID (useful for debugging)")
    private String traceId;
}
