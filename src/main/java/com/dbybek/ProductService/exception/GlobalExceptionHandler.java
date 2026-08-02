package com.dbybek.ProductService.exception;

import com.dbybek.ProductService.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Product Not Available
    @ExceptionHandler(ProductNotAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleProductNotAvailable(ProductNotAvailableException ex, HttpServletRequest request) {
        log.error("Product not found exception.", ex);
        return new ErrorDto(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    // Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("Validation Exception occurred.", ex);
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation exception occurred.",
                request.getRequestURI(),
                errors
        );
    }

    // Fallback (VERY IMPORTANT)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("Exception occurred", ex);
        return new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Something went wrong",
                request.getRequestURI()
        );
    }
}
