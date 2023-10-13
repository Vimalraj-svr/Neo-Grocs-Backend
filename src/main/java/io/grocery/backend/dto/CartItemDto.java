package io.grocery.backend.dto;

import io.grocery.backend.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long productId;
    private String image;
    private Integer quantity;

    // Constructor that accepts a Products object
    public CartItemDto(Products product) {
        this.productId = product.getId(); // Set the productId based on the product's ID
        // You can set other properties if needed based on the 'product' object
    }
}
