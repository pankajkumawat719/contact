package com.contact.repositoryes;

import java.util.Optional;
import com.contact.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  // we can aad custom methods

  Optional<User> findByEmail(String email);

  Optional<User> findByEmailAndPassword(String email, String password);
}
