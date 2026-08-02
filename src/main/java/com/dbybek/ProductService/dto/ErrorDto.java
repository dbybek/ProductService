package com.dbybek.ProductService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "timestamp",
        "status",
        "error",
        "message",
        "errors",
        "path"
})
public class ErrorDto {
    @Schema(
            description = "Time when the error occurred",
            example = "2026-07-11T15:01:09"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "HTTP status code",
            example = "400"
    )
    private int status;

    @Schema(
            description = "HTTP error reason",
            example = "Bad Request"
    )
    private String error;

    @Schema(
            description = "Error message",
            example = "Validation failed"
    )
    private String message;

    @Schema(
            description = "Field validation errors"
    )
    private Map<String, String> errors;

    @Schema(
            description = "API endpoint path",
            example = "/products"
    )
    private String path;

    public ErrorDto(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ErrorDto(int status, String error, String message, String path, Map<String, String> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }
}
