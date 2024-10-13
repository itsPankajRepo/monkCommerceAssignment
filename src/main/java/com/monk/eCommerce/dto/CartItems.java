package com.monk.eCommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItems implements Serializable {

    @Serial
    private static final long serialVersionUID = 1027858269978776457L;

    @NotNull
    @NotEmpty
    private String productId;

    @NotNull
    @NotEmpty
    private int quantity;

    @NotNull
    @NotEmpty
    private double price;

    private double discount;

    public CartItems(String productId, Integer quantity, Double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
