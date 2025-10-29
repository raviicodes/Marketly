package com.Marketly.MarketlyBackend.controller;

import com.Marketly.MarketlyBackend.entity.AppRole;
import com.Marketly.MarketlyBackend.entity.Role;
import com.Marketly.MarketlyBackend.entity.User;
import com.Marketly.MarketlyBackend.payload.AllUserDTO;
import com.Marketly.MarketlyBackend.payload.LoginResponse;
import com.Marketly.MarketlyBackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
      @GetMapping("/")
      @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<?> getAllUsers(){
          List<User>users=userService.getAllUsers();
           if(users.isEmpty()) return ResponseEntity.ok().body(Collections.emptyList());
           else {
              List<LoginResponse>response = users.stream().map(user -> {
                   LoginResponse dto = mapper.map(user, LoginResponse.class);

                   // Convert Role objects → List<String>
                   List<String> roleNames = user.getRole()
                           .stream()
                           .map(item->item.getRoleName().name()).toList();

                   dto.setRoles(roleNames);
                   return dto;
               }).toList();
            AllUserDTO allUsers=new AllUserDTO(response);
               return ResponseEntity.ok().body(allUsers);
           }

      }
      @GetMapping("/{userId}")
      public ResponseEntity<LoginResponse> getUserwithId(@RequestParam long userId){
             User user=userService.getUserById(userId);
               LoginResponse response=mapper.map(user,LoginResponse.class);
              return ResponseEntity.ok().body(response);
      }

}
