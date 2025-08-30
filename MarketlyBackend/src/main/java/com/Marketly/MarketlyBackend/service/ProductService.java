package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.payload.ProductDTO;
import com.Marketly.MarketlyBackend.payload.ProductResponseDTO;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO,Long categoryId);
    ProductResponseDTO getProducts(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
}
