package com.Marketly.MarketlyBackend.controller;

import com.Marketly.MarketlyBackend.entity.Category;
import com.Marketly.MarketlyBackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
       CategoryService categoryService;
      @GetMapping("/public/categories")
      public ResponseEntity<List<Category>> getAllCategoryies(){
          List<Category>allCategories=categoryService.getAllCategories();
           return new ResponseEntity<>(allCategories,HttpStatus.OK);
      }
      @PostMapping("/admin/category")
      public ResponseEntity<String> addCategory(@RequestBody Category category){
          String msg=categoryService.addCategory(category);
           return new ResponseEntity<>(msg,HttpStatus.CREATED);
      }
      @DeleteMapping("/admin/category/{categoryId}")
      public ResponseEntity<?>deleteCategory(@PathVariable int categoryId ){
            try{
                String msg=categoryService.deleteCategory(categoryId);
                 return new ResponseEntity<>(msg,HttpStatus.OK);
            } catch (ResponseStatusException e) {
                return new ResponseEntity<>(e.getReason(),e.getStatusCode());
            }
        }
        @PutMapping("/admin/category/{id}")
        public  ResponseEntity<String> updateCategory(@PathVariable int id,@RequestBody Category category){
                try{
                     String msg=categoryService.updateCategory(id,category);
                      return new ResponseEntity<>(msg,HttpStatus.OK);
                }
                catch (ResponseStatusException e){
                     return new ResponseEntity<>(e.getReason(),e.getStatusCode());
                }
        }
}
