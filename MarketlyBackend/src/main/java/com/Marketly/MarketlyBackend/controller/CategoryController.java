package com.Marketly.MarketlyBackend.controller;

import com.Marketly.MarketlyBackend.entity.Category;
import com.Marketly.MarketlyBackend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
      public ResponseEntity<String> addCategory(@Valid @RequestBody Category category){
          try{
              categoryService.addCategory(category);
               return new ResponseEntity<>("category added successfully",HttpStatus.CREATED);
          } catch (ResponseStatusException e) {
                return new ResponseEntity<>(e.getReason(),e.getStatusCode());
          }
      }
      @DeleteMapping("/admin/category/{categoryId}")
      public ResponseEntity<?>deleteCategory(@PathVariable int categoryId ){
            try{
                  categoryService.deleteCategory(categoryId);
                  return new ResponseEntity<>("category with  categoryId : " + categoryId +" deleted!",HttpStatus.OK);
            } catch (ResponseStatusException e) {
                return new ResponseEntity<>(e.getReason(),e.getStatusCode());
            }
        }
        @PutMapping("/admin/category/{id}")
        public  ResponseEntity<String> updateCategory(@PathVariable int id, @Valid @RequestBody Category category){
                try{
                      categoryService.updateCategory(id,category);
                      return new ResponseEntity<>("category with  categoryId : " + id +" updated!",HttpStatus.OK);
                }
                catch (ResponseStatusException e){
                     return new ResponseEntity<>(e.getReason(),e.getStatusCode());
                }
        }
}
