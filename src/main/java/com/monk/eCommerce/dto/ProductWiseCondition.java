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
public final class ProductWiseCondition extends Condition implements Serializable {

    @Serial
    private static final long serialVersionUID = -6162365606111653055L;
    private Double discount;
    private List<String> productList;
}
