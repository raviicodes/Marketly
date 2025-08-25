package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Category;
import com.Marketly.MarketlyBackend.exceptions.ApiException;
import com.Marketly.MarketlyBackend.exceptions.ResourceNotFoundException;
import com.Marketly.MarketlyBackend.repository.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements  CategoryService {

    private final CategoryRepository categoryRepository;
    CategoryServiceImpl(CategoryRepository categoryRepository){
         this.categoryRepository=categoryRepository;
    }

    public List<Category> getAllCategories() {
              return categoryRepository.findAll();
    }

    @Override
   public  void addCategory(Category category) {
             Optional<Category> existingCategory= categoryRepository.findByCategoryName(category.getCategoryName());
             if(existingCategory.isPresent()) throw new ApiException("Category with the categoryName : "+ category.getCategoryName() + "already exists");
             categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(long categoryId) {
           Category existingCategory=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
           categoryRepository.delete(existingCategory);
    }

    @Override
    public void updateCategory(long categoryId,Category category) {
            Category existingCategory=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
            existingCategory.setCategoryName(category.getCategoryName());
            categoryRepository.save(existingCategory);
    }

}
