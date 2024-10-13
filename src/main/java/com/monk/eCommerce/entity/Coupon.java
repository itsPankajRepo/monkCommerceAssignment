package com.monk.eCommerce.entity;

import com.monk.eCommerce.dto.Condition;
import com.monk.eCommerce.enums.CouponType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon implements Serializable {

    @Serial
    private static final long serialVersionUID = -4929299438560395668L;
    @Id
    private String id;

    @Indexed(unique = true)
    private CouponType type;
    private Condition condition;
    private LocalDateTime createdOn;
    private LocalDateTime expireDate;
    private boolean isValid;
}

