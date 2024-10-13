package com.monk.eCommerce.handler;

import com.monk.eCommerce.constant.MessageConstant;
import com.monk.eCommerce.dto.CartRequestDto;
import com.monk.eCommerce.dto.CartResponseDto;
import com.monk.eCommerce.dto.CartWiseCondition;
import com.monk.eCommerce.enums.CouponType;
import com.monk.eCommerce.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class ApplyCartWiseCouponOnCart extends ApplyCouponOnCart {

    private final CouponService couponService;


    @Override
    public CouponType getType() {
        return CouponType.CART_WISE;
    }


    @Override
    public CartResponseDto applyCoupon(CartRequestDto cartRequestDto) {
        var coupon = couponService.getCouponByType(CouponType.CART_WISE.name());
        var condition = (CartWiseCondition) coupon.getCondition();
        var totalPrice = findTotalPriceOfCartItems(cartRequestDto.getItems());
        double totalDiscount = 0;
        boolean isCouponApplied = false;
        if (totalPrice >= condition.getThreshold()) {
            totalDiscount = totalPrice * (condition.getDiscount() / 100);
            isCouponApplied = true;
        }
        var couponType = isCouponApplied ? CouponType.CART_WISE.name() : MessageConstant.NO_COUPON_APPLICABLE;
        return createCartResponseDto(cartRequestDto.getItems(), totalPrice, totalDiscount, couponType);
    }


}
