package com.chatop.backend.controller;

import com.chatop.backend.dto.LoginRequestDto;
import com.chatop.backend.dto.UserResponseDto;
import com.chatop.backend.model.User;
import com.chatop.backend.security.JwtService;
import com.chatop.backend.service.contracts.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

  private final JwtService jwtService;
  private final IUserService userService;
  private final PasswordEncoder passwordEncoder;


  public UserController(JwtService jwtService, IUserService userService, PasswordEncoder passwordEncoder) {
    this.jwtService = jwtService;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    if (userService.existsByEmail(user.getEmail())) {
      return ResponseEntity.badRequest().body("Email already in use");
    }

    userService.registerUser(user);
    return ResponseEntity.ok("User registered successfully");
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto) {
    System.out.println("try to connect with " + loginDto.getEmail());

    Optional<User> userOpt = userService.findByEmail(loginDto.getEmail());

    if (userOpt.isEmpty()) {
      System.out.println("User not found");
      return ResponseEntity.status(401).body("Invalid credentials");
    }

    User user = userOpt.get();
    System.out.println("User found : " + user.getEmail());

    if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
      System.out.println("password incorrect");
      return ResponseEntity.status(401).body("Invalid credentials");
    }

    Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null);
    String token = jwtService.generateToken(auth);
    return ResponseEntity.ok(Map.of("token", token));
  }

  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser(Authentication authentication) {
    String email = authentication.getName();

    Optional<User> userOpt = userService.findByEmail(email);

    if (userOpt.isEmpty()) {
      return ResponseEntity.status(404).body("User not found");
    }

    User user = userOpt.get();

    UserResponseDto userDto = new UserResponseDto();
    userDto.setId(user.getId());
    userDto.setEmail(user.getEmail());
    userDto.setName(user.getName());
    userDto.setCreated_at(user.getCreatedAt());
    userDto.setUpdated_at(user.getUpdatedAt());

    return ResponseEntity.ok(userDto);
  }

}
