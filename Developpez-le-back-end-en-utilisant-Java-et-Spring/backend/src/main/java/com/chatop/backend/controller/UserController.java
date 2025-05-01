package com.chatop.backend.controller;

import com.chatop.backend.dto.LoginRequestDto;
import com.chatop.backend.model.User;
import com.chatop.backend.service.contracts.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

  private final IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    return userService.register(user);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto) {
    return userService.login(loginDto);
  }

  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser(Authentication authentication) {
    return userService.getCurrentUser(authentication);
  }
}
