package com.monk.eCommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Generic response for API's when it extend the object in the result and respond with the given status code message and status.
 *
 * @author Hem Chand
 * @version 1.0
 */
@Data
@ToString
@JsonInclude(value = Include.NON_NULL)
public class ApiResponse<T extends Object> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3689694387554313790L;

    /**
     * message : localized message for the various scenario.
     */
    private String message;

    /**
     * status : Success/Failure
     */
    private String status;

    /**
     * statusCode : status code for various senarios
     */
    private Integer statusCode;

    /**
     * result : object if required otherwise it will be null.
     */
    private T result;

    private List<String> warning;

    public ApiResponse(String message, String status, Integer statusCode) {
        super();
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
    }

    public ApiResponse(String message, String status, Integer statusCode, T result) {
        super();
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.result = result;
    }

    public ApiResponse(String status, Integer statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public ApiResponse(String message, String status) {
        super();
        this.message = message;
        this.status = status;
    }
}

