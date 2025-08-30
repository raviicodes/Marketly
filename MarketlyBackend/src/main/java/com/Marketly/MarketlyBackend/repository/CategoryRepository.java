package com.Marketly.MarketlyBackend.repository;

import com.Marketly.MarketlyBackend.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByCategoryName(@NotBlank(message = "categoryName can't be blank") @Size(min=3, message = "size of the category should not be less than 3") String categoryName);
}
