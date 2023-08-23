package com.sparta.travelshooting.common.advice;

import com.sparta.travelshooting.common.entity.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler({IllegalArgumentException.class, })
    public ResponseEntity<ApiResponseDto> handleException(Exception e) {
        return ResponseEntity.badRequest().body(
                new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value())
        );
    }
}