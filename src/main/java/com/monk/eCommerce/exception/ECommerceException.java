package com.monk.eCommerce.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ECommerceException extends RuntimeException {

    private static final long serialVersionUID = 4642679525595927897L;
    private final String message;

    public ECommerceException(String message) {
        this.message = message;
    }
}
