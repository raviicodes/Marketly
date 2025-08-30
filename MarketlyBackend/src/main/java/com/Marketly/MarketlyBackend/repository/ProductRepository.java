package com.Marketly.MarketlyBackend.repository;

import com.Marketly.MarketlyBackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
