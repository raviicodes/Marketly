package com.Marketly.MarketlyBackend.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 3,max = 20)
    private String userName;
    @NotBlank
    @Size(min=6,max = 12)
    private String password;
    @Email
    private String email;
    private Set<String>roles;
}
