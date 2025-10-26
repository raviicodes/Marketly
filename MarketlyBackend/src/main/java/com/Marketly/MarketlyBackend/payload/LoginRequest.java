package com.Marketly.MarketlyBackend.payload;

import com.Marketly.MarketlyBackend.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String userName;
    private String email;
    private String password;
}
