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
public class CartItemsRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1300504690509643887L;

    @NotNull
    @NotEmpty
    private String productId;

    @NotNull
    @NotEmpty
    private int quantity;

    @NotNull
    @NotEmpty
    private double price;
}
