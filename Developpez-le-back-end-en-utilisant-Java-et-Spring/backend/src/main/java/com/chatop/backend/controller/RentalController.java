package com.chatop.backend.controller;

import com.chatop.backend.dto.RentalResponseDto;
import com.chatop.backend.service.contracts.IRentalService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

  private final IRentalService rentalService;

  public RentalController(IRentalService rentalService) {
    this.rentalService = rentalService;
  }

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllRentals() {
    return ResponseEntity.ok(Map.of("rentals", rentalService.getAllRentals()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<RentalResponseDto> getRental(@PathVariable Integer id) {
    return ResponseEntity.ok(rentalService.getRentalById(id));
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> createRental(
    @RequestParam String name,
    @RequestParam double surface,
    @RequestParam double price,
    @RequestParam String description,
    @RequestPart("picture") MultipartFile picture,
    Authentication authentication
  ) {
    rentalService.createRental(name, surface, price, description, picture, authentication);
    return ResponseEntity.ok(Map.of("message", "Rental created !"));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> updateRental(
    @PathVariable Integer id,
    @RequestParam String name,
    @RequestParam double surface,
    @RequestParam double price,
    @RequestParam String description,
    @RequestPart("picture") MultipartFile picture
  ) {
    rentalService.updateRental(id, name, surface, price, description, picture);
    return ResponseEntity.ok(Map.of("message", "Rental updated !"));
  }
}
