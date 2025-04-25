package com.chatop.backend.service;

import com.chatop.backend.model.User;
import com.chatop.backend.repository.UserRepository;
import com.chatop.backend.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  @Override
  public boolean registerUser(User user) {
    if (existsByEmail(user.getEmail())) {
      return false;
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setCreatedAt(Timestamp.from(Instant.now()));
    user.setUpdatedAt(Timestamp.from(Instant.now()));

    userRepository.save(user);
    return true;
  }

  @Override
  public void updateUser(User user) {
    user.setUpdatedAt(Timestamp.from(Instant.now()));
    userRepository.save(user);
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }
}
