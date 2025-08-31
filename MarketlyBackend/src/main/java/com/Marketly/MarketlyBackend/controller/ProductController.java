package com.Marketly.MarketlyBackend.controller;

import com.Marketly.MarketlyBackend.config.DefaultValues;;
import com.Marketly.MarketlyBackend.payload.ProductDTO;
import com.Marketly.MarketlyBackend.payload.ProductResponseDTO;
import com.Marketly.MarketlyBackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    @GetMapping("/public/categories/{categoryId}/products")
     public ResponseEntity<ProductResponseDTO> getProductsByCategoryId(@PathVariable Long categoryId){
        ProductResponseDTO response=productService.getProductsByCategoryId(categoryId);
         return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponseDTO> getProductsByKeyword(@PathVariable String keyword){
        ProductResponseDTO response=productService.getProductsByKeyword(keyword);
         return new ResponseEntity<>(response,HttpStatus.FOUND);
    }
     @PostMapping("/admin/categories/{categoryId}/product")
     public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product, @PathVariable Long categoryId){
            ProductDTO savedProduct=productService.addProduct(product,categoryId);
             return  new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
     }

      @PutMapping("/admin/products/{productId}")
     public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO product,@PathVariable Long productId){
          ProductDTO productDTO = productService.updateProduct(product, productId);
          return new ResponseEntity<>(productDTO,HttpStatus.OK);
      }

      @DeleteMapping("/admin/products/{productId}")
     public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProductDTO=productService.deleteProduct(productId);
         return new ResponseEntity<>(deletedProductDTO,HttpStatus.OK);
      }
      @PutMapping("/admin/products/{productId}/image")
     public ResponseEntity<ProductDTO> updateImageOfProduct(@PathVariable Long productId, @RequestParam("image")MultipartFile image) throws IOException {
                    ProductDTO productDTO=productService.updateImage(productId,image);
                    return new ResponseEntity<>(productDTO,HttpStatus.OK);
      }
}
