package com.contact.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.contact.helper.AppConstants;
import com.contact.helper.ResourceNotFoundException;
import com.contact.entities.User;
import com.contact.repositoryes.UserRepository;

@Service
public class UserServiceImple implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public User saveUser(User user) {

    // generate random user id
    String userId = UUID.randomUUID().toString();
    user.setUserId(userId);
    // user.setPassword(userId);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // set user role
    user.setRoleList(List.of(AppConstants.ROLE_USER));
    logger.info(user.getProvider().toString());
    return userRepository.save(user);
  }

  @Override
  public Optional<User> getUserById(String id) {

    return userRepository.findById(id);
  }

  @Override
  public Optional<User> updateUser(User user) {
    User user2 = userRepository.findById(user.getUserId())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    // update user from newUser
    user2.setName(user.getName());
    user2.setEmail(user.getEmail());
    user2.setPassword(user.getPassword());
    user2.setAbout(user.getAbout());
    user2.setProfilePic(user.getProfilePic());
    user2.setPhoneNumber(user.getPhoneNumber());
    user2.setEnabled(user.isEnabled());
    user2.setEmailVerified(user.isEmailVerified());
    user2.setPhoneVerified(user.isPhoneVerified());
    user2.setProvider(user.getProvider());
    user2.setProviderUserID(user.getProviderUserID());

    User savedUser = userRepository.save(user2);
    return Optional.ofNullable(savedUser);

  }

  @Override
  public void deleteUserById(String id) {
    User user2 = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    userRepository.delete(user2);

  }

  @Override
  public boolean isUserExist(String userId) {
    User user2 = userRepository.findById(userId).orElse(null);
    return user2 != null ? true : false;
  }

  @Override
  public boolean isUserExistByEmail(String email) {
    User user = userRepository.findByEmail(email).orElse(null);

    return user != null ? true : false;
  }

  @Override
  public List<User> getAllUsers() {

    return userRepository.findAll();
  }

}
