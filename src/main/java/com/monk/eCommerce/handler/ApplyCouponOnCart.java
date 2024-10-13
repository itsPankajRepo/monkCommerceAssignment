package com.monk.eCommerce.handler;

import com.monk.eCommerce.dto.CartItems;
import com.monk.eCommerce.dto.CartRequestDto;
import com.monk.eCommerce.dto.CartResponseDto;
import com.monk.eCommerce.enums.CouponType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public abstract class ApplyCouponOnCart {

    public abstract CouponType getType();

    public abstract CartResponseDto applyCoupon(CartRequestDto cartRequestDto);


    public double findTotalPriceOfCartItems(List<CartItems> cartItemsList) {
        return cartItemsList.stream().mapToDouble(cartItems -> cartItems.getPrice() * cartItems.getQuantity()).sum();
    }

    public double findTotalDiscountOfCartItems(List<CartItems> cartItemsList) {
        return cartItemsList.stream().mapToDouble(CartItems::getDiscount).sum();
    }


    public CartResponseDto createCartResponseDto(List<CartItems> cartItem, double totalPrice, double totalDiscount, String couponType) {
        return CartResponseDto.builder().items(cartItem).totalPrice(totalPrice).totalDiscount(totalDiscount).finalPrice(totalPrice - totalDiscount).couponApplicable(couponType).build();
    }

    public CartResponseDto createCartResponseDto(List<CartItems> cartItem, double totalPrice, double totalDiscount, String couponType, List<CartItems> freeCartItems)  {
        return CartResponseDto.builder().items(cartItem).totalPrice(totalPrice).totalDiscount(totalDiscount).finalPrice(totalPrice - totalDiscount).couponApplicable(couponType).freeCartItems(freeCartItems).build();
    }

}

