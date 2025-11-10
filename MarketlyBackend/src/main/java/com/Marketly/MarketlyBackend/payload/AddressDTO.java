package com.Marketly.MarketlyBackend.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private long addressId;
    @NotBlank
    @Size(min = 2, message = "street name should be minimum 2 characters")
    private String street;
    @NotBlank
    @Size(min = 2, message = "building name should be minimum 2 characters")
    private String building;
    @NotBlank
    @Size(min = 2, message = "city name should be minimum 2 characters")
    private String city;
    @NotBlank
    @Size(min = 2, message = "state name should be minimum 2 characters")
    private String state;
    @NotBlank
    @Size(min = 5, message = "zip code should be minimum 5 characters")
    private String zipCode;
}
