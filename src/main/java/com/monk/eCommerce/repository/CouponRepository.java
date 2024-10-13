package com.monk.eCommerce.repository;

import com.monk.eCommerce.entity.Coupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends MongoRepository<Coupon, String> {

    List<Coupon> findAllByIsValid(boolean isValid);

    Optional<Coupon> findByTypeAndIsValid(String couponType, boolean isValid);
}
