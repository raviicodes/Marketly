package com.Marketly.MarketlyBackend.controller;

import com.Marketly.MarketlyBackend.config.DefaultValues;
import com.Marketly.MarketlyBackend.payload.ProductDTO;
import com.Marketly.MarketlyBackend.payload.ProductResponseDTO;
import com.Marketly.MarketlyBackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
     private ProductService productService;
   @GetMapping("/public/products")
    public ResponseEntity<ProductResponseDTO>getProducts(
            @RequestParam(name = "pageNumber",defaultValue = DefaultValues.pageNumber,required = false) Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue = DefaultValues.pageSize,required = false)Integer pageSize,
            @RequestParam(name="sortOrder",defaultValue = DefaultValues.SORT_ORDER,required = false) String sortOrder,
            @RequestParam(name = "sortBy",defaultValue = DefaultValues.SORT_PRODUCT_BY,required = false) String sortBy
    ){
         ProductResponseDTO productResponseDTO=productService.getProducts(pageNumber,pageSize,sortBy,sortOrder);
         return new ResponseEntity<>(productResponseDTO,HttpStatus.OK);
    }
     @PostMapping("/admin/categories/{categoryId}/product")
     public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product, @PathVariable Long categoryId){
            ProductDTO savedProduct=productService.addProduct(product,categoryId);
             return  new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
     }

}
