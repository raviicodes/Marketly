package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.User;
import com.Marketly.MarketlyBackend.payload.LoginRequest;

import java.util.List;

public interface UserService {
    public User getUserByName(LoginRequest loginRequest);
    public List<User> getAllUsers();
    public User getUserById(long userId);
}
