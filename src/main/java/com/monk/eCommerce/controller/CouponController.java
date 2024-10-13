package com.monk.eCommerce.controller;

import com.monk.eCommerce.constant.RequestURIConstants;
import com.monk.eCommerce.dto.ApiResponse;
import com.monk.eCommerce.dto.CartRequestDto;
import com.monk.eCommerce.dto.CreateCouponRequestDto;
import com.monk.eCommerce.dto.UpdateCouponRequestDto;
import com.monk.eCommerce.handler.CouponHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = RequestURIConstants.E_COMMERCE)
@Slf4j
@RequiredArgsConstructor
public class CouponController {

    private final CouponHandler couponHandler;


    /**
     * Function to create new coupons
     *
     * @param createCouponRequestDto - request body to create new coupon
     * @return - response of an api
     */
    @PostMapping(value = RequestURIConstants.CREATE_COUPON)
    public ApiResponse<Object> createCoupon(@RequestBody @Valid CreateCouponRequestDto createCouponRequestDto) {
        log.info("Inside CouponController::createCoupon with requestBody {} ", createCouponRequestDto);
        return couponHandler.createCoupon(createCouponRequestDto);
    }


    /**
     * Function to fetch all existing valid coupons
     *
     * @return - response of an api
     */
    @GetMapping(value = RequestURIConstants.FETCH_COUPONS)
    public ApiResponse<Object> fetchCoupons() {
        log.info("Inside CouponController::fetchCoupon");
        return couponHandler.fetchCoupons();
    }


    /**
     * Function to get requested coupons
     *
     * @param id - id of coupon
     * @return - response of an api
     */
    @GetMapping(value = RequestURIConstants.GET_COUPON)
    public ApiResponse<Object> getCoupon(@RequestParam(name = "id") String id) {
        log.info("Inside CouponController::getCoupon having id - {}", id);
        return couponHandler.getCoupon(id);
    }


    /**
     * Function to update existing coupon
     *
     * @param updateCouponRequestDto - request body to update new coupon
     * @return - response of an api
     */
    @PutMapping(value = RequestURIConstants.UPDATE_COUPON)
    public ApiResponse<Object> updateCoupon(@RequestBody @Valid UpdateCouponRequestDto updateCouponRequestDto) {
        log.info("Inside CouponController::updateCoupon with requestBody - {}", updateCouponRequestDto);
        return couponHandler.updateCoupon(updateCouponRequestDto);
    }


    /**
     * Function to delete coupons
     *
     * @param id - id of coupon
     * @return - response of an api
     */
    @DeleteMapping(value = RequestURIConstants.DELETE_COUPON)
    public ApiResponse<Object> deleteCoupon(@RequestParam(name = "id") String id) {
        log.info("Inside CouponController::deleteCoupon having id - {}", id);
        return couponHandler.deleteCoupon(id);
    }


    /**
     * Function to fetch applicable coupon
     *
     * @param cartRequestDto - cart request contains product id, quality and price
     * @return - response of an api
     */
    @PostMapping(value = RequestURIConstants.FETCH_APPLICABLE_COUPON)
    public ApiResponse<Object> fetchApplicableCoupon(@RequestBody @Valid CartRequestDto cartRequestDto) {
        log.info("Inside CouponController::fetchApplicableCoupon with request {}", cartRequestDto);
        return couponHandler.fetchApplicableCoupon(cartRequestDto);
    }


    /**
     * Function to apply given coupon on given cart
     *
     * @param id             - id of the coupon
     * @param cartRequestDto - cart request contains product id, quality and price
     * @return - response of an api
     */
    @PostMapping(value = RequestURIConstants.APPLY_COUPON_BY_ID)
    public ApiResponse<Object> applyCouponByID(@RequestParam(name = "id") String id, @RequestBody @Valid CartRequestDto cartRequestDto) {
        log.info("Inside CouponController::applyCouponByID with coupon id {}, request {}", id, cartRequestDto);
        return couponHandler.applyCoupon(id, cartRequestDto);
    }
}
