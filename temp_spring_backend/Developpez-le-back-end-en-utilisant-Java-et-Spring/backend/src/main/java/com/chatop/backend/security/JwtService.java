package com.chatop.backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

  private final JwtEncoder jwtEncoder;

  public JwtService(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
      .issuer("self")
      .issuedAt(now)
      .expiresAt(now.plus(1, ChronoUnit.DAYS))
      .subject(authentication.getName())
      .build();

    return this.jwtEncoder.encode(
      JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)
    ).getTokenValue();
  }
}
