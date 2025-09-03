package com.Marketly.MarketlyBackend.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private  Long productId;
    @Size(min = 3)
    @NotBlank
    private String productName;
    private Long quantity;
    private Long price;
    private Long specialPrice;
    @Size(min = 6)
    @NotBlank
    private String description;
    private String image;
    private Long discount;
}
