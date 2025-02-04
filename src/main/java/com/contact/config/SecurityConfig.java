package com.contact.config;

import java.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.contact.services.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

  // User created and login using in-memory authentication

  // @Bean
  // public UserDetailsService userDetailsService() {

  // UserDetails user = User.withUsername("admin")
  // .withDefaultPasswordEncoder()
  // .password("admin")
  // .roles("ADMIN", "USER")
  // .build();

  // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
  // return inMemoryUserDetailsManager;

  // }

  @Autowired
  private SecurityCustomUserDetailService securityCustomUserDetailService;

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {

    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

    // User details service ka object
    daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);

    // password encoder ka object
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
