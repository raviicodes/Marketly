package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements  CategoryService {
    List<Category> CategoryList=new ArrayList<>();
    @Override
    public List<Category> getAllCategories() {
        return CategoryList;
    }

    @Override
   public   String addCategory(Category category) {
         category.setCategoryId(CategoryList.size()+1);
         CategoryList.add(category);
         return "category with name : "+ category.getCategoryName()+" added successfully";
    }

    @Override
    public  String deleteCategory(int categoryId) {
           if(CategoryList.removeIf(item->item.getCategoryId()==categoryId)) return "Category with categoryId : "+categoryId+" delted";
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,"category not found with the given id");
    }

    @Override
    public String updateCategory(int categoryId,Category category) {
        Optional<Category> existingCategory = CategoryList.stream().filter(item -> item.getCategoryId() == categoryId).findFirst();
         if(existingCategory.isPresent()){
              existingCategory.get().setCategoryName(category.getCategoryName());
               return "updated";
         }
          throw new  ResponseStatusException(HttpStatus.NOT_FOUND,"category not  found");
    }

}
