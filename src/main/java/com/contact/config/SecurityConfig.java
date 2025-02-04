package com.contact.config;

import java.io.IOException;
import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
  private SecurityCustomUserDetailService userDetailService;

  // configuration of authentication provider

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {

    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

    // User details service ka object
    // daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);

    daoAuthenticationProvider.setUserDetailsService(userDetailService);

    // password encoder ka object
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;

  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    // configuration of http security

    httpSecurity.authorizeHttpRequests(authorize -> {
      authorize.requestMatchers("/user/**").authenticated();
      authorize.anyRequest().permitAll();
    });

    httpSecurity.formLogin(formLogin -> {
      formLogin.loginPage("/login");
      formLogin.loginProcessingUrl("/authenticate");

      formLogin.defaultSuccessUrl("/user/dashboard");

      // formLogin.failureUrl("/login?error=true");
      formLogin.usernameParameter("email");
      formLogin.passwordParameter("password");

      /*
       * 
       * formLogin.failureHandler(new AuthenticationFailureHandler() {
       * 
       * @Override
       * public void onAuthenticationFailure(HttpServletRequest request,
       * HttpServletResponse response,
       * AuthenticationException exception) throws IOException, ServletException {
       * throw new UnsupportedOperationException("Unsupported operation");
       * 
       * }
       * });
       * 
       * formLogin.successHandler(new AuthenticationSuccessHandler() {
       * 
       * @Override
       * public void onAuthenticationSuccess(HttpServletRequest request,
       * HttpServletResponse response,
       * Authentication authentication) throws IOException, ServletException {
       * // TODO Auto-generated method stub
       * throw new
       * UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'"
       * );
       * }
       * 
       * });
       * 
       */

    });

    httpSecurity.csrf(AbstractHttpConfigurer::disable);

    httpSecurity.logout(logoutForm -> {
      logoutForm.logoutUrl("/logout");
      logoutForm.logoutSuccessUrl("/login");
    });

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
