package com.Marketly.MarketlyBackend.controller;

import com.Marketly.MarketlyBackend.entity.Category;
import com.Marketly.MarketlyBackend.payload.CategoryRequestDTO;
import com.Marketly.MarketlyBackend.payload.CategoryResponseDTO;
import com.Marketly.MarketlyBackend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
       CategoryService categoryService;
      @GetMapping("/public/categories")
      public ResponseEntity<CategoryResponseDTO> getAllCategoryies(){
         CategoryResponseDTO response =categoryService.getAllCategories();
           return new ResponseEntity<>(response,HttpStatus.OK);
      }

      @PostMapping("/admin/category")
      public ResponseEntity<CategoryRequestDTO> addCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
              CategoryRequestDTO savedCategory=  categoryService.addCategory(categoryRequestDTO);
               return new ResponseEntity<>(savedCategory,HttpStatus.CREATED);

      }
      @DeleteMapping("/admin/category/{categoryId}")
      public ResponseEntity<CategoryRequestDTO>deleteCategory(@PathVariable int categoryId ){

                CategoryRequestDTO deletedCategoryDTO=  categoryService.deleteCategory(categoryId);
                  return new ResponseEntity<>(deletedCategoryDTO,HttpStatus.OK);

        }
        @PutMapping("/admin/category/{id}")
        public  ResponseEntity<CategoryRequestDTO> updateCategory(@PathVariable int id, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
                 CategoryRequestDTO updateCategoryDTO=      categoryService.updateCategory(id,categoryRequestDTO);
                      return new ResponseEntity<>(updateCategoryDTO,HttpStatus.OK);
        }

}
