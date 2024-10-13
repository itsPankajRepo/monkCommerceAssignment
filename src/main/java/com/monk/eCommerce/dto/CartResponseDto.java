package com.monk.eCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -6827938796401012710L;

    private List<CartItems> items;
    private double totalPrice;
    private double totalDiscount;
    private double finalPrice;
    private String couponApplicable;
    private List<CartItems> freeCartItems;
}
