package com.chatop.backend.service.contracts;

import com.chatop.backend.dto.LoginRequestDto;
import com.chatop.backend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface IUserService {
  ResponseEntity<?> register(User user);
  ResponseEntity<?> login(LoginRequestDto loginDto);
  ResponseEntity<?> getCurrentUser(Authentication authentication);

  Optional<User> findByEmail(String email);
  Optional<User> findById(Long id);
  boolean existsByEmail(String email);
  void updateUser(User user);
  void save(User user);
}
