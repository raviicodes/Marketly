package com.Marketly.MarketlyBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long productId;
    private String productName;
    private Long quantity;
    private Long price;
    private Long specialPrice;
    private String description;
    private String image;
    private Long discount;

     @ManyToOne
     @JoinColumn(name = "category_Id")
     private Category category;
     @ManyToOne
      @JoinColumn(name = "seller_Id")
     private User user;
}
