package com.monk.eCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicableCouponResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4351758475398777691L;
    private List<ApplicableCoupon> applicableCoupons;
}
