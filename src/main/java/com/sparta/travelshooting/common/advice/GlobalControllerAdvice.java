package com.sparta.travelshooting.common.advice;

import com.sparta.travelshooting.common.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.concurrent.RejectedExecutionException;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler({AccessDeniedException.class, IndexOutOfBoundsException.class, RejectedExecutionException.class})
    public ResponseEntity<ApiResponseDto> handleBadRequestException(Exception e) {
        return new ResponseEntity<>(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResponseDto> handleNotFoundException(Exception e) {
        return new ResponseEntity<>(new ApiResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }


}