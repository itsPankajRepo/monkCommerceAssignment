package com.monk.eCommerce.utils;

import java.time.LocalDateTime;

public class GenericUtil {

    public static boolean isCouponExpired(LocalDateTime expireDateTime){
       return LocalDateTime.now().isAfter(expireDateTime);
    }
}
