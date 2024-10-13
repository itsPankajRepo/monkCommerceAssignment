package com.monk.eCommerce.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "couponType"  // Name of the field that will carry the type information
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BuyXGetYCondition.class, name = "BUY_X_GET_Y"),
        @JsonSubTypes.Type(value = CartWiseCondition.class, name = "CART_WISE"),
        @JsonSubTypes.Type(value = ProductWiseCondition.class, name = "PRODUCT_WISE")
})
public sealed abstract class Condition permits CartWiseCondition, ProductWiseCondition, BuyXGetYCondition {
}
