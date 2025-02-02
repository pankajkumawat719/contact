package com.contact.entities;

import java.util.*;
import lombok.Builder;
import com.contact.entities.Providers;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
public class User {

  @Id
  private String userId;

  @Column(name = "user_name", nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  private String password;

  @Column(length = 10000)
  private String about;

  @Column(length = 1000)
  private String profilePic;

  private String phoneNumber;
  private boolean enabled = false;
  private boolean emailVerified = false;
  private boolean phoneVerified = false;

  // signup using self , google.......
  @Enumerated(value = EnumType.STRING)
  private Providers provider = Providers.SELF;
  private String providerUserID;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<Contact> contacts = new ArrayList<>();

}
