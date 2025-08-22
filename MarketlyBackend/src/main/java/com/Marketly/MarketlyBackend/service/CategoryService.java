package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Category;

import java.util.List;


public interface CategoryService {
       List<Category>getAllCategories();
      String addCategory(Category category);
      String deleteCategory(int categoryId);
      String updateCategory(int categoryId,Category category);
}
