package com.monk.eCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public final class CartWiseCondition extends Condition implements Serializable {

    @Serial
    private static final long serialVersionUID = 4332537491717574522L;
    private Double discount;
    private Integer threshold;

}
