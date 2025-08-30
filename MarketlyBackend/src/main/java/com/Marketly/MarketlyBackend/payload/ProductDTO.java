package com.Marketly.MarketlyBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private  Long productId;
    private String productName;
    private Long quantity;
    private Long price;
    private Long specialPrice;
    private String description;
    private String image;
    private Long discount;
}
