package com.Marketly.MarketlyBackend.utils;

import com.Marketly.MarketlyBackend.entity.User;
import com.Marketly.MarketlyBackend.exceptions.ResourceNotFoundException;
import com.Marketly.MarketlyBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public  class Authutil {
    @Autowired
    private UserRepository userRepository;
    public String loggedInEmail(){
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUserName(userName).orElseThrow(()-> new ResourceNotFoundException("user","userName",userName));
         return user.getEmail();
    }
    public Long LoggedInId(){
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUserName(userName).orElseThrow(()-> new ResourceNotFoundException("user","userName",userName));
        return user.getUserId();
    }
    public User loggedInUser(){
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
         return userRepository.findByUserName(userName).orElseThrow(()-> new ResourceNotFoundException("user","userName",userName));
    }
}
