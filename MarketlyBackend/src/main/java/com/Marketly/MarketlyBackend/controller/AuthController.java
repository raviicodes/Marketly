package com.Marketly.MarketlyBackend.controller;

import com.Marketly.MarketlyBackend.entity.AppRole;
import com.Marketly.MarketlyBackend.entity.Role;
import com.Marketly.MarketlyBackend.entity.User;
import com.Marketly.MarketlyBackend.exceptions.ApiException;
import com.Marketly.MarketlyBackend.payload.LoginRequest;
import com.Marketly.MarketlyBackend.payload.LoginResponse;
import com.Marketly.MarketlyBackend.payload.MessageResponse;
import com.Marketly.MarketlyBackend.payload.SignUpRequest;
import com.Marketly.MarketlyBackend.repository.RoleRepository;
import com.Marketly.MarketlyBackend.repository.UserRepository;
import com.Marketly.MarketlyBackend.security.jwt.JwtUtils;
import com.Marketly.MarketlyBackend.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
     private PasswordEncoder encoder;
     @Autowired
     private  AuthenticationManager authenticationManager;
     @Autowired
     private JwtUtils jwtUtils;
     @Autowired
     private UserRepository userRepository;
     @Autowired
     private RoleRepository roleRepository;
     @PostMapping("/signin")
     public ResponseEntity<LoginResponse> userSignIn(@RequestBody LoginRequest loginRequest){
         Authentication authentication;
         try{
              authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
         } catch (AuthenticationException e) {
             throw new RuntimeException(e);
         }
         SecurityContextHolder.getContext().setAuthentication(authentication);
         UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
         List<String>roles=userDetails.getAuthorities().stream().map(item->item.getAuthority()).toList();
         ResponseCookie cookieToken=jwtUtils.generateJwtCookie(userDetails);
          LoginResponse loginResponse=new LoginResponse(userDetails.getUsername(),roles);
          return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookieToken.toString()).body(loginResponse);
    }
    @PostMapping("/signup")
    public ResponseEntity<?>userSignUp(@Valid @RequestBody SignUpRequest signUpRequest){
                if(userRepository.existsByUserName(signUpRequest.getUserName())){
                     return  ResponseEntity.badRequest().body(new MessageResponse("UserName is already taken"));
                }
                 if(userRepository.existsByEmail(signUpRequest.getEmail())){
                     return ResponseEntity.badRequest().body(new MessageResponse("Email is already taken"));
                 }
        User user=new User(signUpRequest.getUserName(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        Set<String>strRoles=signUpRequest.getRoles();
        Set<Role>roles=new HashSet<>();
         if(strRoles==null){
             Role role=roleRepository.findByRoleName(AppRole.ROLE_USER).orElseThrow(()->new RuntimeException("Role_User not found"));
             roles.add(role);
         }
         else {
             strRoles.forEach(role -> {
                 switch (role) {
                     case "admin":
                         Role roleAdmin = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                 .orElseThrow(() -> new RuntimeException("Error: " + AppRole.ROLE_ADMIN + " not found in DB"));
                         roles.add(roleAdmin);
                         break;

                     case "seller":
                         Role roleSeller = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                 .orElseThrow(() -> new RuntimeException("Error: " + AppRole.ROLE_SELLER + " not found in DB"));
                         roles.add(roleSeller);
                         break;

                     default:
                         Role roleUser = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                 .orElseThrow(() -> new RuntimeException("Error: " + AppRole.ROLE_USER + " not found in DB"));
                         roles.add(roleUser);
                         break;
                 }
             });

         }
         user.setRole(roles);
        userRepository.save(user);
        return ResponseEntity.ok().body(new MessageResponse("User registered"));
    }
     @GetMapping("/userName")
    public  ResponseEntity<?> getUserName(Authentication authentication){
             return ResponseEntity.ok().body(authentication.getPrincipal());
     }
     @GetMapping("/signOut")
    public ResponseEntity<?>signOut(){
           ResponseCookie signOutCookie=jwtUtils.logoutResponseCookie();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,signOutCookie.toString()).body("loggedout");
     }
}
