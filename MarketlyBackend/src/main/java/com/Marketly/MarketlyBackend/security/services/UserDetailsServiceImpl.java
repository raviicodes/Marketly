package com.Marketly.MarketlyBackend.security.services;

import com.Marketly.MarketlyBackend.entity.User;
import com.Marketly.MarketlyBackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsImpl userDetails;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("user not found with given userName"+username));
         return userDetails.build(user);
    }
}
