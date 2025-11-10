package com.Marketly.MarketlyBackend.service;

import com.Marketly.MarketlyBackend.entity.Address;
import com.Marketly.MarketlyBackend.entity.User;
import com.Marketly.MarketlyBackend.exceptions.ResourceNotFoundException;
import com.Marketly.MarketlyBackend.payload.AddressDTO;
import com.Marketly.MarketlyBackend.payload.AddressResponseDTO;
import com.Marketly.MarketlyBackend.payload.LoginRequest;
import com.Marketly.MarketlyBackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
   private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
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

    @Override
    @Transactional
    public AddressResponseDTO addAddress(AddressDTO addressDTO) {
         String userName= SecurityContextHolder.getContext().getAuthentication().getName();
         User user=userRepository.findByUserName(userName).orElseThrow(()->new ResourceNotFoundException("user","userName",userName));
         Address address=modelMapper.map(addressDTO,Address.class);
         address.setUser(user);
         user.getAddresses().add(address);
      User savedUser=   userRepository.save(user);
      List<AddressDTO>savedAddress=savedUser.getAddresses().stream().map(item->modelMapper.map(item,AddressDTO.class)).toList();
       return new AddressResponseDTO(savedAddress);
    }


    @Override
    public AddressResponseDTO getAllAddress() {
        String userName=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUserName(userName).orElseThrow(()->new ResourceNotFoundException("user","userName",userName));
         List<AddressDTO>addresses=user.getAddresses().stream().map(item->modelMapper.map(item,AddressDTO.class)).toList();
         return new AddressResponseDTO(addresses);
    }

    @Override
    public AddressDTO deleteAddress( long addressId) {
       String userName=SecurityContextHolder.getContext().getAuthentication().getName();
       User user=userRepository.findByUserName(userName).orElseThrow(()-> new ResourceNotFoundException("username","user",userName));
       Address addresToRemove=user.getAddresses().stream().filter(item->item.getAddressId()==addressId).findFirst().orElseThrow(()->new ResourceNotFoundException("address","addressId",addressId));
       user.getAddresses().remove(addresToRemove);
       userRepository.save(user);
       return modelMapper.map(addresToRemove,AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress( AddressDTO addressDTO, long addressId) {
        return null;
    }

}
