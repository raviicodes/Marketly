package com.Marketly.MarketlyBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "products")
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

     @ToString.Exclude
     @ManyToOne
     @JoinColumn(name = "category_id")
     private Category category;
     @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

}
