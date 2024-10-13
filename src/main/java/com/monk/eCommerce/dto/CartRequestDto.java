package com.monk.eCommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1083442366011518858L;

    @NotNull
    @NotEmpty
    private List<CartItems> items;
}
