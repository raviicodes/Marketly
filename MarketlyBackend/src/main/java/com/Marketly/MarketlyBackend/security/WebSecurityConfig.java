package com.Marketly.MarketlyBackend.security;

import com.Marketly.MarketlyBackend.security.jwt.AuthEntryPoint;
import com.Marketly.MarketlyBackend.security.jwt.AuthTokenFilter;
import com.Marketly.MarketlyBackend.security.services.UserDetailsImpl;
import com.Marketly.MarketlyBackend.security.services.UserDetailsServiceImpl;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
      @Autowired
      private UserDetailsServiceImpl userDetailsService;
      @Autowired
      private AuthEntryPoint unauthorizedHandler;

    @Bean
      public DaoAuthenticationProvider authenticationProvider(){
           DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider(userDetailsService);
            daoAuthenticationProvider.setPasswordEncoder(jwtPasswordEncoder());
             return daoAuthenticationProvider;
      }
      @Bean
     public PasswordEncoder jwtPasswordEncoder(){
           return new BCryptPasswordEncoder();
      }
      @Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
           return authConfig.getAuthenticationManager();
      }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,AuthTokenFilter authTokenFilter) throws Exception {
        http.authorizeHttpRequests((request)->
                request.requestMatchers("/api/auth/**","/api/public/**").permitAll()
                        .anyRequest().authenticated()
        );
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exception->exception.authenticationEntryPoint(unauthorizedHandler));
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
