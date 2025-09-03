package com.bank.user.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private String path;
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private String traceId;
    private Map<String, String> errors;
}