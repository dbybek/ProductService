package com.dbybek.ProductService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> errors;
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
