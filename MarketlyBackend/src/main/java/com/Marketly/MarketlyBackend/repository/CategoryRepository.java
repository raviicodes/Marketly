package com.Marketly.MarketlyBackend.repository;

import com.Marketly.MarketlyBackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
