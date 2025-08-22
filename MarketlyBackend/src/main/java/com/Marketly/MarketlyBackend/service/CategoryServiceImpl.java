package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements  CategoryService {
    List<Category> CategoryList=new ArrayList<>();
    @Override
    public List<Category> getAllCategories() {
        return CategoryList;
    }

    @Override
    public String addCategory(Category category) {
         category.setCategoryId(CategoryList.size()+1);
         CategoryList.add(category);
         return "category with name : "+ category.getCategoryName()+" added successfully";
    }
}
