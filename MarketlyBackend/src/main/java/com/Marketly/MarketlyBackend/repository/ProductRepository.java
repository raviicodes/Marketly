package com.Marketly.MarketlyBackend.repository;

import com.Marketly.MarketlyBackend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAllByCategory_CategoryId(Long categoryId, Pageable pageable);
    Page<Product> findByProductNameContainingIgnoreCase(String keyword,Pageable pageable);
}
