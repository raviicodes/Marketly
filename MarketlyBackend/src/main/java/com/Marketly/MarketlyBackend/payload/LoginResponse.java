package com.Marketly.MarketlyBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String userName;
    private List<String>roles;
    private String token;

}
