package com.monk.eCommerce.service;

import com.monk.eCommerce.constant.MessageConstant;
import com.monk.eCommerce.dto.CartRequestDto;
import com.monk.eCommerce.dto.CreateCouponRequestDto;
import com.monk.eCommerce.dto.UpdateCouponRequestDto;
import com.monk.eCommerce.entity.Coupon;
import com.monk.eCommerce.exception.ECommerceException;
import com.monk.eCommerce.repository.CouponRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;


    /**
     * Function to create new coupon
     */
    public Coupon createCoupon(CreateCouponRequestDto createCouponRequestDto) {
        log.info("Inside CouponService::createCoupon");
        Coupon couponDto = Coupon.builder().
                type(createCouponRequestDto.getType())
                .condition(createCouponRequestDto.getCondition())
                .createdOn(LocalDateTime.now())
                .isValid(true)
                .expireDate(LocalDateTime.now().plusMonths(createCouponRequestDto.getExpireTimeInMonth())).build();
        log.info("Going to save coupon {}", couponDto);
        return couponRepository.save(couponDto);
    }


    /**
     * Function to fetch all valid coupons
     */
    public List<Coupon> fetchAllCoupons() {
        return couponRepository.findAllByIsValid(true);
    }


    /**
     * Function to fetch coupon with requested id
     */
    public Coupon getCoupon(String id) {
        var couponOpt = couponRepository.findById(id);
        if (couponOpt.isEmpty()) {
            log.info("No coupon found with given id : {}", id);
            throw new ECommerceException(MessageConstant.NO_COUPON_FOUND);
        }
        return couponOpt.get();
    }


    /**
     * Function to fetch coupon with requested id
     */
    public Coupon getCouponByType(String couponType) {
        var couponOpt = couponRepository.findByTypeAndIsValid(couponType, true);
        if (couponOpt.isEmpty()) {
            log.info("No coupon found with given type : {}", couponType);
            throw new ECommerceException(MessageConstant.NO_COUPON_FOUND);
        }
        return couponOpt.get();
    }


    /**
     * Function to update coupon
     */
    public void updateCoupon(UpdateCouponRequestDto updateCouponRequestDto) {
        var existingCoupon = getCoupon(updateCouponRequestDto.getId());
        log.info("Existing coupon with given id {} is {}", updateCouponRequestDto.getId(), existingCoupon);
        if (Objects.nonNull(updateCouponRequestDto.getExpireTimeInMonth())) {
            var newExpiryDate = existingCoupon.getExpireDate().plusMonths(updateCouponRequestDto.getExpireTimeInMonth());
            log.info("Updating the expire time the new time will be {}", newExpiryDate);
            existingCoupon.setExpireDate(newExpiryDate);
        }

        if (Objects.nonNull(updateCouponRequestDto.getCondition())) {
            var isUserNewConditionValid = existingCoupon.getType().isConditionValid(updateCouponRequestDto.getCondition());
            if (!isUserNewConditionValid)
                throw new ECommerceException(MessageConstant.INVALID_CONDITION_PROVIDED);
            log.info("Going to updating the condition");
            existingCoupon.setCondition(updateCouponRequestDto.getCondition());
        }

        if (Objects.nonNull(updateCouponRequestDto.getIsValid())) {
            existingCoupon.setValid(updateCouponRequestDto.getIsValid());
        }

        couponRepository.save(existingCoupon);
    }


    /**
     * Function to delete coupon
     */
    public void deleteCoupon(String id) {
        var existingCoupon = getCoupon(id);
        log.info("Going to delete coupon with id {}", id);
        existingCoupon.setValid(false);
        couponRepository.save(existingCoupon);
    }


    /**
     * Function to mark coupon as invalid
     */
    public void markCouponAsInValid(String id) {
        var coupon = couponRepository.findById(id).orElseThrow();
        coupon.setValid(false);
        couponRepository.save(coupon);
    }
}
