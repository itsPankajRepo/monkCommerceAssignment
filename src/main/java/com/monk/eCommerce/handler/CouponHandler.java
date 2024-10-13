package com.monk.eCommerce.handler;

import com.monk.eCommerce.constant.MessageConstant;
import com.monk.eCommerce.dto.*;
import com.monk.eCommerce.exception.ECommerceException;
import com.monk.eCommerce.service.CouponService;
import com.monk.eCommerce.utils.CouponFactory;
import com.monk.eCommerce.utils.GenericUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.monk.eCommerce.constant.MessageConstant.STATUS_SUCCESS;

@Component
@RequiredArgsConstructor
@Slf4j
public class CouponHandler {


    private final CouponService couponService;

    @Value("${valid.coupon}")
    private List<String> validCouponList;


    /**
     * @param createCouponRequestDto - request body to create new coupon
     * @return - response of an api
     */
    public ApiResponse<Object> createCoupon(CreateCouponRequestDto createCouponRequestDto) {
        log.info("Inside CouponHandler::createCoupon");
        var newCoupon = couponService.createCoupon(createCouponRequestDto);
        var response = CreateCouponResponseDto.builder().id(newCoupon.getId()).condition(newCoupon.getCondition()).build();
        log.info("Coupon created :: {}", response);
        return new ApiResponse<>("Created Successfully", STATUS_SUCCESS, 200, response);
    }


    /**
     * Function to fetch all existing valid coupons
     *
     * @return - response of an api
     */
    public ApiResponse<Object> fetchCoupons() {
        log.info("Inside CouponHandler::fetchCoupons");
        var listOfCoupons = couponService.fetchAllCoupons();
        if (listOfCoupons.isEmpty()) {
            log.info("No coupons found");
            return new ApiResponse<>("No Coupons Found", "success", 200);
        }
        var responseList = listOfCoupons.stream().
                map(coupon -> CreateCouponResponseDto.builder().id(coupon.getId()).condition(coupon.getCondition()).build()).collect(Collectors.toList());
        log.info("Total number of valid coupon found are {}", responseList.size());
        return new ApiResponse<>("Coupon Fetched Successfully", STATUS_SUCCESS, 200, responseList);
    }


    /**
     * @param id - id of coupon
     * @return - response of an api
     */
    public ApiResponse<Object> getCoupon(String id) {
        log.info("Inside CouponHandler::getCoupon");
        var coupon = couponService.getCoupon(id);
        var response = CreateCouponResponseDto.builder().id(coupon.getId()).condition(coupon.getCondition()).build();
        return new ApiResponse<>("Coupon fetch Successfully", STATUS_SUCCESS, 200, response);

    }


    /**
     * @param updateCouponRequestDto - request body to update new coupon
     * @return - response of an api
     */
    public ApiResponse<Object> updateCoupon(UpdateCouponRequestDto updateCouponRequestDto) {
        log.info("Inside CouponHandler::updateCoupon");
        couponService.updateCoupon(updateCouponRequestDto);
        return new ApiResponse<>("Coupon updated Successfully", 200);

    }


    /**
     * Function to delete coupons
     *
     * @param id - id of coupon
     * @return - response of an api
     */
    public ApiResponse<Object> deleteCoupon(String id) {
        log.info("Inside CouponHandler::deleteCoupon");
        couponService.deleteCoupon(id);
        return new ApiResponse<>("Coupon deleted Successfully", 200);
    }


    /**
     * Function to apply given coupon on given cart
     *
     * @param id             - id of the coupon
     * @param cartRequestDto - cart request contains product id, quality and price
     * @return - response of an api
     */
    public ApiResponse<Object> applyCoupon(String id, @Valid CartRequestDto cartRequestDto) {
        log.info("Inside CouponHandler::applyCoupon");
        var coupon = couponService.getCoupon(id);
        var isCouponExpired = GenericUtil.isCouponExpired(coupon.getExpireDate());
        if (isCouponExpired) {
            couponService.markCouponAsInValid(coupon.getId());
            throw new ECommerceException(MessageConstant.COUPON_IS_EXPIRED);
        }
        var applyCouponInstance = CouponFactory.getCouponInstance(coupon.getType());
        var updatedCart = applyCouponInstance.applyCoupon(cartRequestDto);
        return new ApiResponse<>("Your updated cart ", STATUS_SUCCESS, 200, updatedCart);
    }


    /**
     * Function to fetch applicable coupons on given cart
     *
     * @param cartRequestDto - cart request contains product id, quality and price
     * @return - response of an api
     */
    public ApiResponse<Object> fetchApplicableCoupon(@Valid CartRequestDto cartRequestDto) {
        log.info("Inside CouponHandler::fetchApplicableCoupon");
        var applicableCouponList = validCouponList.stream().map(couponService::getCouponByType).filter(coupon -> !GenericUtil.isCouponExpired(coupon.getExpireDate())).toList();
        List<ApplicableCoupon> couponList = new ArrayList<>();
        applicableCouponList.forEach(coupon -> {
            var applyCouponInstance = CouponFactory.getCouponInstance(coupon.getType());
            var updatedCart = applyCouponInstance.applyCoupon(cartRequestDto);
            var applicableCoupon = ApplicableCoupon.builder().couponId(coupon.getId()).type(coupon.getType().name()).discount(updatedCart.getTotalDiscount()).freeCartItem(updatedCart.getFreeCartItems()).build();
            couponList.add(applicableCoupon);
            cartRequestDto.getItems().forEach(cartItem -> cartItem.setDiscount(0));
        });

        ApplicableCouponResponseDto applicableCouponResponseDto = new ApplicableCouponResponseDto(couponList);
        return new ApiResponse<>("Your applicable coupon list", STATUS_SUCCESS, 200, applicableCouponResponseDto);

    }
}
