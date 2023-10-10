package io.grocery.backend.dto;

import io.grocery.backend.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDto {
    private Long cartItemId;
    private Products product;
    private Integer quantity;
}
