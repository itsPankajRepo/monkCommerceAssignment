package com.monk.eCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public final class BuyXGetYCondition extends Condition implements Serializable {

    @Serial
    private static final long serialVersionUID = 8898177464815888981L;
    private List<String> buyProducts;
    private List<String> getProducts;
    private int buyX;
    private int getY;
    private Integer repetitionLimit;
}
