package com.contact.entities;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder

public class User implements UserDetails {

  @Id
  private String userId;

  @Column(name = "user_name", nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Getter(value = AccessLevel.NONE)
  private String password;

  @Column(length = 10000)
  private String about;

  @Column(length = 1000)
  private String profilePic;

  private String phoneNumber;

  @Getter(value = AccessLevel.NONE)
  private boolean enabled = true;
  private boolean emailVerified = false;
  private boolean phoneVerified = false;

  // signup using self , google.......
  @Enumerated(value = EnumType.STRING)
  private Providers provider = Providers.SELF;
  private String providerUserID;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)

  private List<Contact> contacts = new ArrayList<>();

  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roleList = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // list of roles[USER,ADMIN]
    // Collection of SimpGrantedAuthority[roles{ADMIN,USER}]
    Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
    return roles;
  }

  // email is my username

  @Override
  public String getUsername() {

    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  public boolean isAccountNonLocked() {
    return true;
  }

  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getPassword() {
    return this.password;
  }
}
