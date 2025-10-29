package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.User;
import com.Marketly.MarketlyBackend.exceptions.ResourceNotFoundException;
import com.Marketly.MarketlyBackend.payload.LoginRequest;
import com.Marketly.MarketlyBackend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
   private UserRepository userRepository;
    @Override
    public User getUserByName(LoginRequest loginRequest) {
        return userRepository.findByUserName(loginRequest.getUserName()).orElseThrow(()->new UsernameNotFoundException("user name with the username: "+loginRequest.getUserName()+" not found"));
    }

    @Override
    public List<User> getAllUsers() {
          return userRepository.findAll();
    }

    @Override
    public User getUserById(long userId) {
           return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("users","userId",userId));
    }

}
