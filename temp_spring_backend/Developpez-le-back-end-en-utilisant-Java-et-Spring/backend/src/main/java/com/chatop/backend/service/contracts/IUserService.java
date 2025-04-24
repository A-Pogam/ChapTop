package com.chatop.backend.service.contracts;

import com.chatop.backend.model.User;

import java.util.Optional;

public interface IUserService {
  Optional<User> findByEmail(String email);
  Optional<User> findById(Long id);
  boolean existsByEmail(String email);
  boolean registerUser(User user);
  void updateUser(User user);
  void save(User user);
}

