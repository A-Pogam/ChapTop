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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService implements IRentalService {

  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;

  public RentalService(RentalRepository rentalRepository, UserRepository userRepository) {
    this.rentalRepository = rentalRepository;
    this.userRepository = userRepository;
  }

  // dossier backend/uploads (défini dans application.properties)
  @Value("${file.upload-dir}")
  private String uploadDir;

  /** URL de base du backend — constante */
  private static final String SERVER_URL = "http://localhost:3001";


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
  public void createRental(RentalRequestDto dto, Authentication auth) {

    String imageUrl = savePicture(dto.getPicture());

    User user = userRepository.findByEmail(auth.getName())
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
  public void updateRental(Integer id,
                           String name,
                           double surface,
                           double price,
                           String description,
                           MultipartFile picture) {

    Rental rental = rentalRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Rental not found"));

    rental.setName(name);
    rental.setSurface(surface);
    rental.setPrice(price);
    rental.setDescription(description);

    if (picture != null && !picture.isEmpty()) {
      rental.setPicture(savePicture(picture));
    }

    rental.setUpdatedAt(Timestamp.from(Instant.now()));
    rentalRepository.save(rental);
  }

  private String savePicture(MultipartFile picture) {
    if (picture == null || picture.isEmpty()) return null;

    try {
      String original = picture.getOriginalFilename();
      String fileName = System.currentTimeMillis() + "_" + original;

      Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
      if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

      picture.transferTo(uploadPath.resolve(fileName).toFile());

      return SERVER_URL + "assets/" + fileName;

    } catch (IOException e) {
      throw new RuntimeException("Failed to save the picture", e);
    }
  }


  private RentalResponseDto toDto(Rental r) {
    RentalResponseDto dto = new RentalResponseDto();
    dto.setId(r.getId());
    dto.setName(r.getName());
    dto.setSurface(r.getSurface());
    dto.setPrice(r.getPrice());
    dto.setPicture(r.getPicture());
    dto.setDescription(r.getDescription());
    dto.setOwner_id(r.getOwner().getId());
    dto.setCreated_at(r.getCreatedAt());
    dto.setUpdated_at(r.getUpdatedAt());
    return dto;
  }
}
