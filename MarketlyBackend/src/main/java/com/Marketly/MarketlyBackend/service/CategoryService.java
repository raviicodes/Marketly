package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Category;
import com.Marketly.MarketlyBackend.payload.CategoryRequestDTO;
import com.Marketly.MarketlyBackend.payload.CategoryResponseDTO;

import java.util.List;


public interface CategoryService {
        CategoryResponseDTO getAllCategories();
       CategoryRequestDTO addCategory(CategoryRequestDTO categoryRequestDTO);
       CategoryRequestDTO deleteCategory(long categoryId);
      CategoryRequestDTO updateCategory(long categoryId,CategoryRequestDTO categoryRequestDTO);
}
