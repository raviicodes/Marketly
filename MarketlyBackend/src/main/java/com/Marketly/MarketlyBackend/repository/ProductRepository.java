package com.Marketly.MarketlyBackend.repository;

import com.Marketly.MarketlyBackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategory_CategoryId(Long categoryId);
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
