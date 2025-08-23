package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Category;

import java.util.List;


public interface CategoryService {
       List<Category>getAllCategories();
      void addCategory(Category category);
      void deleteCategory(long categoryId);
      void updateCategory(long categoryId,Category category);
}
