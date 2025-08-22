package com.Marketly.MarketlyBackend.controller;

import com.Marketly.MarketlyBackend.entity.Category;
import com.Marketly.MarketlyBackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
       CategoryService categoryService;
      @GetMapping("/api/public/categories")
      public List<Category> getAllCategoryies(){
           return categoryService.getAllCategories();
      }
      @PostMapping("/api/admin/category")
      public String addCategory(@RequestBody Category category){
                       return categoryService.addCategory(category);
      }

}
