package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.User;
import com.Marketly.MarketlyBackend.payload.LoginRequest;

public interface UserService {
    public User getUserByName(LoginRequest loginRequest);
}
