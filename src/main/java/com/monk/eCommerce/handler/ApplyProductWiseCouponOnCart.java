package com.monk.eCommerce.handler;

import com.monk.eCommerce.constant.MessageConstant;
import com.monk.eCommerce.dto.*;
import com.monk.eCommerce.enums.CouponType;
import com.monk.eCommerce.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class ApplyProductWiseCouponOnCart extends ApplyCouponOnCart {

    private final CouponService couponService;

    @Override
    public CouponType getType() {
        return CouponType.PRODUCT_WISE;
    }

    @Override
    public CartResponseDto applyCoupon(CartRequestDto cartRequestDto) {
        var coupon = couponService.getCouponByType(CouponType.PRODUCT_WISE.name());
        var condition = (ProductWiseCondition) coupon.getCondition();
        AtomicBoolean isCouponApplied = new AtomicBoolean(false);
        cartRequestDto.getItems().forEach(cartItems -> {
            var isDiscountProduct = condition.getProductList().contains(cartItems.getProductId());
            if (isDiscountProduct) {
                var totalPrice = cartItems.getPrice() * cartItems.getQuantity();
                var totalDiscount = totalPrice * (condition.getDiscount() / 100);
                cartItems.setDiscount(totalDiscount);
                isCouponApplied.set(true);
            }
        });
        var couponType = isCouponApplied.get() ? CouponType.PRODUCT_WISE.name() : MessageConstant.NO_COUPON_APPLICABLE;
        var totalPrice = findTotalPriceOfCartItems(cartRequestDto.getItems());
        var totalDiscount = findTotalDiscountOfCartItems(cartRequestDto.getItems());
        return createCartResponseDto(cartRequestDto.getItems(), totalPrice, totalDiscount, couponType);
    }
}
