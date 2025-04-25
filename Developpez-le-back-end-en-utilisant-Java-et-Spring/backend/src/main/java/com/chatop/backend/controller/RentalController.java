package com.chatop.backend.controller;

import com.chatop.backend.dto.RentalRequestDto;
import com.chatop.backend.dto.RentalResponseDto;
import com.chatop.backend.service.contracts.IRentalService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

  private final IRentalService rentalService;

  public RentalController(IRentalService rentalService) {
    this.rentalService = rentalService;
  }

  @GetMapping
  public ResponseEntity<?> getAllRentals() {
    List<RentalResponseDto> rentals = rentalService.getAllRentals();
    return ResponseEntity.ok(Map.of("rentals", rentals));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getRental(@PathVariable Integer id) {
    RentalResponseDto rental = rentalService.getRentalById(id);
    return ResponseEntity.ok(rental);
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createRental(
    @RequestParam String name,
    @RequestParam double surface,
    @RequestParam double price,
    @RequestParam String description,
    @RequestPart("picture") MultipartFile picture,
    Authentication authentication
  ) {
    RentalRequestDto dto = new RentalRequestDto();
    dto.setName(name);
    dto.setSurface(surface);
    dto.setPrice(price);
    dto.setDescription(description);
    dto.setPicture("/uploads/" + picture.getOriginalFilename());

    rentalService.createRental(dto, authentication);
    return ResponseEntity.ok(Map.of("message", "Rental created !"));
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> updateRental(
    @PathVariable Integer id,
    @RequestParam String name,
    @RequestParam double surface,
    @RequestParam double price,
    @RequestParam String description,
    @RequestPart("picture") MultipartFile picture,
    Authentication authentication
  ) {
    RentalRequestDto dto = new RentalRequestDto();
    dto.setName(name);
    dto.setSurface(surface);
    dto.setPrice(price);
    dto.setDescription(description);
    dto.setPicture("/uploads/" + picture.getOriginalFilename());

    rentalService.updateRental(id, dto);
    return ResponseEntity.ok(Map.of("message", "Rental updated !"));
  }

}
