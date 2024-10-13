package com.monk.eCommerce.exception;

import com.monk.eCommerce.constant.MessageConstant;
import com.monk.eCommerce.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ECommerceExceptionHandler {

    @ExceptionHandler(ECommerceException.class)
    public final ResponseEntity<ApiResponse> handleCodeException(ECommerceException eCommerceException) {

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(eCommerceException.getMessage(),
                MessageConstant.STATUS_FAILURE, 400));
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse> handleCodeException(Exception exception) {

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(exception.getMessage(), MessageConstant.STATUS_FAILURE));
    }
}
