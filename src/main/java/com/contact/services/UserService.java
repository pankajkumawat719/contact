package com.contact.services;

import com.contact.repositoryes.UserRepository;
import com.contact.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

  // email = username

  User saveUser(User user);

  Optional<User> getUserById(String id);

  Optional<User> updateUser(User user);

  void deleteUserById(String id);

  boolean isUserExist(String userId);

  boolean isUserExistByEmail(String email);

  List<User> getAllUsers();

}
