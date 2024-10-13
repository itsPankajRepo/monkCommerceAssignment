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
public class ApplicableCoupon implements Serializable {

    @Serial
    private static final long serialVersionUID = 1057424477248178713L;
    private String couponId;
    private String type;
    private double discount;
    private List<CartItems> freeCartItem;
}
