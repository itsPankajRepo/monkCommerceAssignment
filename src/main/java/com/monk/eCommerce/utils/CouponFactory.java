package com.monk.eCommerce.utils;

import com.monk.eCommerce.constant.MessageConstant;
import com.monk.eCommerce.enums.CouponType;
import com.monk.eCommerce.exception.ECommerceException;
import com.monk.eCommerce.handler.ApplyCouponOnCart;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CouponFactory {

    @Autowired
    private List<ApplyCouponOnCart> applyCouponOnCarts;

    private static final Map<CouponType, ApplyCouponOnCart> validationType = validatorTypeMap();


    private static Map<CouponType, ApplyCouponOnCart> validatorTypeMap() {
        return new EnumMap<>(CouponType.class);
    }


    @PostConstruct
    public void initValidatorType() {
        for (ApplyCouponOnCart validation : applyCouponOnCarts) {
            validationType.put(validation.getType(), validation);
        }
    }

    public static ApplyCouponOnCart getCouponInstance(CouponType type) {
        ApplyCouponOnCart listener = validationType.get(type);
        if (listener == null) {
            log.error("Unknown coupon type: {}", type);
            throw new ECommerceException(MessageConstant.CONFIGURATION_NOT_FOUND);
        }
        return listener;
    }
}
