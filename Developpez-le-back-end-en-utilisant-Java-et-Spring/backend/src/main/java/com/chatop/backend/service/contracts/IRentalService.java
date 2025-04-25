package com.chatop.backend.service.contracts;

import com.chatop.backend.dto.RentalRequestDto;
import com.chatop.backend.dto.RentalResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IRentalService {
  List<RentalResponseDto> getAllRentals();
  RentalResponseDto getRentalById(Integer id);
  void createRental(RentalRequestDto dto, Authentication authentication);
  void updateRental(Integer id, RentalRequestDto dto);
}

