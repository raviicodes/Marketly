package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.payload.CategoryRequestDTO;
import com.Marketly.MarketlyBackend.payload.CategoryResponseDTO;




public interface CategoryService {
        CategoryResponseDTO getAllCategories(Integer PageNumber,Integer pageSize,String sortBy,String sortOrder);
        CategoryRequestDTO addCategory(CategoryRequestDTO categoryRequestDTO);
       CategoryRequestDTO deleteCategory(long categoryId);
       CategoryRequestDTO updateCategory(long categoryId,CategoryRequestDTO categoryRequestDTO);
}
