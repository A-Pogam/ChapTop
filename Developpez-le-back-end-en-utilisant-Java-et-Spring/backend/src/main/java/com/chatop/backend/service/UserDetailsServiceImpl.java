package com.chatop.backend.service;

import com.chatop.backend.exception.PasswordNotFoundException;
import com.chatop.backend.model.User;
import com.chatop.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> optionalUser = userRepository.findByEmail(email);

    if (optionalUser.isEmpty()) {
      throw new UsernameNotFoundException("Email " + email + " does not match any user.");
    }

    User user = optionalUser.get();

    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      throw new PasswordNotFoundException("Password is empty or null for user: " + email);
    }

    // Si tu veux gérer les rôles plus tard, tu peux ajouter getRole() ici
    return org.springframework.security.core.userdetails.User
      .withUsername(user.getEmail())
      .password(user.getPassword())
      .roles("USER") // Valeur par défaut en attendant un champ "role"
      .build();
  }
}
