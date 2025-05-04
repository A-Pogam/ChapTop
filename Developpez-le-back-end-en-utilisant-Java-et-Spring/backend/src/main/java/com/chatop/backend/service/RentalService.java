package com.chatop.backend.service;

import com.chatop.backend.dto.RentalRequestDto;
import com.chatop.backend.dto.RentalResponseDto;
import com.chatop.backend.model.Rental;
import com.chatop.backend.model.User;
import com.chatop.backend.repository.RentalRepository;
import com.chatop.backend.repository.UserRepository;
import com.chatop.backend.service.contracts.IRentalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService implements IRentalService {

  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;

  public RentalService(RentalRepository rentalRepository, UserRepository userRepository) {
    this.rentalRepository = rentalRepository;
    this.userRepository = userRepository;
  }

  @Value("${file.upload-dir}")
  private String uploadDir;

  @Override
  public List<RentalResponseDto> getAllRentals() {
    return rentalRepository.findAll()
      .stream()
      .map(this::toDto)
      .collect(Collectors.toList());
  }

  @Override
  public RentalResponseDto getRentalById(Integer id) {
    return rentalRepository.findById(id)
      .map(this::toDto)
      .orElseThrow(() -> new RuntimeException("Rental not found"));
  }

  @Override
  public void createRental(RentalRequestDto dto, Authentication authentication) {
    MultipartFile picture = dto.getPicture();
    String imageUrl = null;

    if (picture != null && !picture.isEmpty()) {
      try {
        String originalFileName = picture.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalFileName;
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
          Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        picture.transferTo(filePath.toFile());

        imageUrl = "http://localhost:3001/uploads/" + fileName;

      } catch (IOException e) {
        throw new RuntimeException("Failed to save the picture", e);
      }
    }

    String currentUsername = authentication.getName();
    User user = userRepository.findByEmail(currentUsername)
      .orElseThrow(() -> new RuntimeException("User not found"));

    Rental rental = new Rental();
    rental.setName(dto.getName());
    rental.setSurface(dto.getSurface());
    rental.setPrice(dto.getPrice());
    rental.setDescription(dto.getDescription());
    rental.setPicture(imageUrl);
    rental.setOwner(user);
    rental.setCreatedAt(Timestamp.from(Instant.now()));
    rental.setUpdatedAt(Timestamp.from(Instant.now()));

    rentalRepository.save(rental);
  }



  @Override
  public void updateRental(Integer id, String name, double surface, double price, String description, MultipartFile picture) {
    Rental rental = rentalRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Rental not found"));

    rental.setName(name);
    rental.setSurface(surface);
    rental.setPrice(price);
    rental.setDescription(description);

    if (picture != null && !picture.isEmpty()) {
      String filePath = "/uploads/" + picture.getOriginalFilename();
      rental.setPicture(filePath);
    }

    rental.setUpdatedAt(Timestamp.from(Instant.now()));
    rentalRepository.save(rental);
  }


  private RentalResponseDto toDto(Rental rental) {
    RentalResponseDto dto = new RentalResponseDto();
    dto.setId(rental.getId());
    dto.setName(rental.getName());
    dto.setSurface(rental.getSurface());
    dto.setPrice(rental.getPrice());
    dto.setPicture(rental.getPicture());
    dto.setDescription(rental.getDescription());
    dto.setOwner_id(rental.getOwner().getId());
    dto.setCreated_at(rental.getCreatedAt());
    dto.setUpdated_at(rental.getUpdatedAt());
    return dto;
  }
}
