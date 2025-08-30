package com.Marketly.MarketlyBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long categoryId;
     @NotBlank(message = "categoryName can't be blank")
     @Size(min=3, message = "size of the category should not be less than 3")
    private String categoryName;
     @OneToMany(mappedBy = "category" ,cascade = CascadeType.ALL,orphanRemoval = true)
     private List<Product> products;
}
