package com.chatop.backend.service.contracts;

import com.chatop.backend.dto.RentalRequestDto;
import com.chatop.backend.dto.RentalResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRentalService {
  List<RentalResponseDto> getAllRentals();
  RentalResponseDto getRentalById(Integer id);
  void createRental(RentalRequestDto dto, Authentication authentication);
  void updateRental(Integer id, String name, double surface, double price, String description, MultipartFile picture);


}
