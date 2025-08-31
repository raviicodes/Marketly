package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.payload.ProductDTO;
import com.Marketly.MarketlyBackend.payload.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
     ProductResponseDTO getProductsByKeyword(String keyword) ;
     ProductDTO addProduct(ProductDTO productDTO, Long categoryId);
     ProductResponseDTO getProducts(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
     ProductResponseDTO getProductsByCategoryId(Long categoryId);
     ProductDTO updateProduct(ProductDTO productDTO,Long productId);
     ProductDTO deleteProduct(Long productId);
     ProductDTO updateImage(Long productId, MultipartFile file) throws IOException;
}
