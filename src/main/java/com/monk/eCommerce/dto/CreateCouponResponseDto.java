package com.monk.eCommerce.dto;

import com.monk.eCommerce.enums.CouponType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCouponResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4584015467200517616L;
    private String id;
    private Condition condition;
}
