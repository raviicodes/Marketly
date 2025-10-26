package com.Marketly.MarketlyBackend.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${spring.app.jwtExpirationMS}")
    private long jwtExpirationMS;
    @Value("${spring.app.jwtsecret}")
      private String jwtsecret;
    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);

    // get the token from the 'Authorization' header
     public String getTokenFromHeader(HttpServletRequest request){
             String bearerToken=request.getHeader("Authorization");
              if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
                   return bearerToken.substring(7);
              }
              else return null;
     }
     // Generate token from username.
    public String generateToken(UserDetails user){
          String userName=user.getUsername();
          return Jwts.builder().subject(userName).issuedAt(new Date()).expiration(new Date(new Date().getTime()+jwtExpirationMS)).signWith(key()).compact();
    }
    // Get userName form token
    public String getUserNameViaToken(String token){
           return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
    }
    // generate secret key
    public Key key(){
       return   Keys.hmacShaKeyFor(
               Decoders.BASE64.decode(jwtsecret)
       );
    }
    //  validate the jwt token
    public boolean validateToken(String token){
        try{
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token);
            return true;
        }
        catch(MalformedJwtException e){
            logger.debug("Invalid jwt token: {}",e.getMessage());

        }
        catch(ExpiredJwtException e){
            logger.debug("Invalid jwt token: {}",e.getMessage());

        }
        catch(UnsupportedJwtException e){
            logger.debug("Invalid JWT token:{}",e.getMessage());

        }
        catch(IllegalArgumentException e){
            logger.debug("Invalid argument exception:{}",e.getMessage());

        }
         return false;
    }
}
