package com.monk.eCommerce.dto;

import com.monk.eCommerce.enums.CouponType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 578880018137707585L;

    @NotNull
    private CouponType type;

    @NotNull
    private Condition condition;

    private Integer expireTimeInMonth;

    @AssertTrue(message = "Details of the coupon are not valid")
    public boolean isConditionValid() {
        return type.isConditionValid(condition);
    }
}
