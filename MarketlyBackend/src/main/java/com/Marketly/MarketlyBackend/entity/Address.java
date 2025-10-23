package com.Marketly.MarketlyBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    @NotBlank
    @Size(min = 3, message = "street name should be minimum 3 characters")
    private String street;
    @NotBlank
    @Size(min = 4, message = "building name should be minimum 4 characters")
    private String building;
    @NotBlank
    @Size(min = 3, message = "city name should be minimum 3 characters")
    private String city;
    @NotBlank
    @Size(min = 3, message = "state name should be minimum 3 characters")
    private String state;
    @NotBlank
    @Size(min = 6, message = "zip code should be minimum 3 characters")
    private String zipCode;
    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users=new ArrayList<>();
    public Address(String street, String building, String city, String state, String zipCode) {
        this.street = street;
        this.building = building;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}


