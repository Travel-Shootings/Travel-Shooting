package com.sparta.travelshooting.common.advice;

import com.sparta.travelshooting.common.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler({AccessDeniedException.class, IndexOutOfBoundsException.class, RejectedExecutionException.class,
            RuntimeException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiResponseDto> handleBadRequestException(Exception e) {
        return new ResponseEntity<>(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                new ApiResponseDto(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()
                        , HttpStatus.BAD_REQUEST.value())
                , HttpStatus.BAD_REQUEST
        );
    }
}