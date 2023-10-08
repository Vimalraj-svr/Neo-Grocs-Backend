package io.grocery.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    
    private String productName;
    private Integer productPrice;
    private String productDesc;
    private Integer quantity;
    private String image;
}
