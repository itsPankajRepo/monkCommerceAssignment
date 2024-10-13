package com.monk.eCommerce.enums;

import com.monk.eCommerce.dto.BuyXGetYCondition;
import com.monk.eCommerce.dto.CartWiseCondition;
import com.monk.eCommerce.dto.Condition;
import com.monk.eCommerce.dto.ProductWiseCondition;

public enum CouponType {
    CART_WISE {
        @Override
        public boolean isConditionValid(Condition details) {
            return details instanceof CartWiseCondition ;
        }
    },
    PRODUCT_WISE {
        @Override
        public boolean isConditionValid(Condition details) {
            return details instanceof ProductWiseCondition;
        }
    },
    BUY_X_GET_Y {
        @Override
        public boolean isConditionValid(Condition details) {
            return details instanceof BuyXGetYCondition;
        }
    };

    public abstract boolean isConditionValid(Condition details);
}
