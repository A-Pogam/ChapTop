package com.chatop.backend.controller;

import com.chatop.backend.model.User;
import com.chatop.backend.security.JwtService;
import com.chatop.backend.service.contracts.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

  private final JwtService jwtService;
  private final IUserService userService;

  public UserController(JwtService jwtService, IUserService userService) {
    this.jwtService = jwtService;
    this.userService = userService;
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
  public ResponseEntity<?> login(Authentication authentication) {
    String token = jwtService.generateToken(authentication);
    return ResponseEntity.ok(Map.of("token", token));
  }
}
