package com.Marketly.MarketlyBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @NotBlank
    @Size(min = 5, message = "Street name must be atleast 5 characters")
    private String street;
    @NotBlank
    @Size(min = 5, message = "Building name must be atleast 5 characters")
    private String building;
    @NotBlank
    @Size(min = 2, message = "City name must be atleast 2 characters")
    private String city;
    @NotBlank
    @Size(min = 3 ,message = "Country Name must be at least 3 characters" )
    private String country;
    @NotBlank
    @Size(min = 6, message = "PinCode must be atleast 6 characters")
    private String pinCode;
 @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users=new ArrayList<>();
    public Address(String street, String building, String city, String country, String pinCode) {
        this.street = street;
        this.building = building;
        this.city = city;
        this.country = country;
        this.pinCode = pinCode;
    }
}
