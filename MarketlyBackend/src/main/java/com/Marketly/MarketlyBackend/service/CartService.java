package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.payload.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long ProductId);
}
