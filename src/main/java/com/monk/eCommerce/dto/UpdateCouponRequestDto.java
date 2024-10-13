package com.monk.eCommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCouponRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -563229363755750575L;

    @NotNull
    private String id;

    private Condition condition;

    private Integer expireTimeInMonth;

    private Boolean isValid;
}
