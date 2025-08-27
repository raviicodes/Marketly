package com.Marketly.MarketlyBackend.controller;

import com.Marketly.MarketlyBackend.config.DefaultValues;
import com.Marketly.MarketlyBackend.payload.CategoryRequestDTO;
import com.Marketly.MarketlyBackend.payload.CategoryResponseDTO;
import com.Marketly.MarketlyBackend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
       CategoryService categoryService;
      @GetMapping("/public/categories")
      public ResponseEntity<CategoryResponseDTO> getAllCategoryies
              (
              @RequestParam(name = "pageNumber",defaultValue = DefaultValues.pageNumber,required = false) Integer pageNumber,
              @RequestParam(name = "pageSize",defaultValue = DefaultValues.pageSize,required = false)Integer pageSize,
              @RequestParam(name="sortBy",defaultValue = DefaultValues.SORT_CATEGORIES_BY,required = false) String sortBy,
              @RequestParam(name="sortOrder",defaultValue = DefaultValues.SORT_ORDER,required = false) String sortOrder
             )
      {
         CategoryResponseDTO response =categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
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
