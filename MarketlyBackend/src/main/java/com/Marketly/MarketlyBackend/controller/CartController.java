package com.Marketly.MarketlyBackend.controller;

import com.Marketly.MarketlyBackend.payload.CartDTO;
import com.Marketly.MarketlyBackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private  CartService cartService;
    @PostMapping("/cart/product/{productId}")
    public ResponseEntity<?>addProductToCart(@PathVariable Long productId){
          CartDTO cartDTO=cartService.addProductToCart(productId);
          return ResponseEntity.ok().body(cartDTO);
    }
}
