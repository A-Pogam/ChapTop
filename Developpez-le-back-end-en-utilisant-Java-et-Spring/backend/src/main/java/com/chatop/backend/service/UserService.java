package com.chatop.backend.service;

import com.chatop.backend.dto.LoginRequestDto;
import com.chatop.backend.dto.UserResponseDto;
import com.chatop.backend.model.User;
import com.chatop.backend.repository.UserRepository;
import com.chatop.backend.security.JwtService;
import com.chatop.backend.service.contracts.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  @Override
  public ResponseEntity<?> register(User user) {

    if (existsByEmail(user.getEmail())) {
      return ResponseEntity.badRequest().body("Email already in use");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setCreatedAt(Timestamp.from(Instant.now()));
    user.setUpdatedAt(Timestamp.from(Instant.now()));
    userRepository.save(user);

    Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null);
    String token = jwtService.generateToken(auth);

    return ResponseEntity.ok(Map.of("token", token));
  }


  @Override
  public ResponseEntity<?> login(LoginRequestDto loginDto) {
    Optional<User> userOpt = findByEmail(loginDto.getEmail());

    if (userOpt.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), userOpt.get().getPassword())) {
      return ResponseEntity.status(401).body("Invalid credentials");
    }

    Authentication auth = new UsernamePasswordAuthenticationToken(userOpt.get().getEmail(), null);
    String token = jwtService.generateToken(auth);
    return ResponseEntity.ok(Map.of("token", token));
  }

  @Override
  public ResponseEntity<?> getCurrentUser(Authentication authentication) {
    String email = authentication.getName();
    Optional<User> userOpt = findByEmail(email);

    if (userOpt.isEmpty()) {
      return ResponseEntity.status(404).body("User not found");
    }

    User user = userOpt.get();
    UserResponseDto dto = new UserResponseDto();
    dto.setId(user.getId());
    dto.setEmail(user.getEmail());
    dto.setName(user.getName());
    dto.setCreated_at(user.getCreatedAt());
    dto.setUpdated_at(user.getUpdatedAt());

    return ResponseEntity.ok(dto);
  }

  // Méthodes internes utilisées dans l'interface
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
  public void updateUser(User user) {
    user.setUpdatedAt(Timestamp.from(Instant.now()));
    userRepository.save(user);
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }
}
