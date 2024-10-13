package com.monk.eCommerce.handler;

import com.monk.eCommerce.constant.MessageConstant;
import com.monk.eCommerce.dto.*;
import com.monk.eCommerce.enums.CouponType;
import com.monk.eCommerce.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class ApplyBuyXGetYWiseCouponOnCart extends ApplyCouponOnCart {

    private final CouponService couponService;

    @Override
    public CouponType getType() {
        return CouponType.BUY_X_GET_Y;
    }

    @Override
    public CartResponseDto applyCoupon(CartRequestDto cartRequestDto) {
        var coupon = couponService.getCouponByType(CouponType.BUY_X_GET_Y.name());
        var condition = (BuyXGetYCondition) coupon.getCondition();

        List<String> buyProducts = condition.getBuyProducts();
        List<String> getProducts = condition.getGetProducts();
        int buyX = condition.getBuyX();
        int getY = condition.getGetY();
        int repetitionLimit = condition.getRepetitionLimit();


        // Initialize counters to track the quantities
        var buyProductCount = getBuyProductCount(cartRequestDto.getItems(), buyProducts);

        var totalPrice = findTotalPriceOfCartItems(cartRequestDto.getItems());

        // Ensure we have at least 'buyX' distinct products from the buy list
        if (buyProductCount.size() < buyX) {
            // Not enough distinct products from the "buy" list to apply coupon
            return createCartResponseDto(cartRequestDto.getItems(), totalPrice, 0.0, MessageConstant.NO_COUPON_APPLICABLE);
        }


        // Calculate how many free items we can give based on the getY products
        int freeItemsCount = calculateNumberOfFreeItemsCanBeGiven(buyProductCount, buyX, getY, repetitionLimit);

        var updatedCartWithFreeItems = updateCartWithFreeItems(cartRequestDto.getItems(), getProducts, freeItemsCount);
        var totalDiscount = findTotalDiscountOfCartItems(cartRequestDto.getItems());
        return createCartResponseDto(cartRequestDto.getItems(), totalPrice, totalDiscount, CouponType.BUY_X_GET_Y.name(), updatedCartWithFreeItems);
    }


    public int calculateNumberOfFreeItemsCanBeGiven(Map<String, Integer> buyProductMap, int buyX, int getY, int repetitionLimit) {
        // Calculate the minimum number of sets we can create across the distinct products
        int totalSets = Integer.MAX_VALUE;

        // Iterate through the distinct products to find the limiting factor
        for (Map.Entry<String, Integer> entry : buyProductMap.entrySet()) {
            totalSets = Math.min(totalSets, entry.getValue());  // Find the limiting product with the smallest quantity
        }

        // Determine the number of applications of the coupon based on the repetition limit
        int maxApplications = Math.min(totalSets, repetitionLimit);

        // Return the number of free items that can be given
        return maxApplications * getY;
    }


    public List<CartItems> updateCartWithFreeItems(List<CartItems> cartItemsList, List<String> freeItemList, int numberOfFreeItems) {
        List<CartItems> freeCartItemList = new ArrayList<>();
//        AtomicInteger numberOfFreeItem = new AtomicInteger(numberOfFreeItems);
//        cartItemsList.stream().filter(cartItem -> freeItemList.contains(cartItem.getProductId())).
//                forEach(
//                        cartItem -> {
//                            if (numberOfFreeItem.get() > 0) {
//                                freeCartItemList.add(new CartItems(cartItem.getProductId(), 1, cartItem.getPrice()));
//                                numberOfFreeItem.decrementAndGet();
//                            }
//
//                        });
        int i = 0;
        while (numberOfFreeItems > 0) {
            CartItems freeItem = new CartItems(freeItemList.get(i), 1, 0.0, 0.0);
            freeCartItemList.add(freeItem);
            i++;
            numberOfFreeItems--;
            if (i >= freeItemList.size())
                i = 0;
        }

        return freeCartItemList;
    }


    public Map<String, Integer> getBuyProductCount(List<CartItems> cartItems, List<String> buyProducts) {

        var buyProductCount = new HashMap<String, Integer>();

        cartItems.stream().filter(cartItem -> buyProducts.contains(cartItem.getProductId())).
                forEach(cartItem -> buyProductCount.put(cartItem.getProductId(), buyProductCount.getOrDefault(cartItem.getProductId(), 0) + cartItem.getQuantity()));

        return buyProductCount;
    }

}
