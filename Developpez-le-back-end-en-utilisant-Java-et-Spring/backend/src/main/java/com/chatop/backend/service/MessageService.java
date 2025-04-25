package com.chatop.backend.service;

import com.chatop.backend.dto.MessageRequestDto;
import com.chatop.backend.model.Message;
import com.chatop.backend.model.Rental;
import com.chatop.backend.model.User;
import com.chatop.backend.repository.MessageRepository;
import com.chatop.backend.repository.RentalRepository;
import com.chatop.backend.repository.UserRepository;
import com.chatop.backend.service.contracts.IMessageService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class MessageService implements IMessageService {

  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;
  private final MessageRepository messageRepository;

  public MessageService(RentalRepository rentalRepository, UserRepository userRepository, MessageRepository messageRepository) {
    this.rentalRepository = rentalRepository;
    this.userRepository = userRepository;
    this.messageRepository = messageRepository;
  }

  @Override
  public void createMessage(MessageRequestDto dto) {
    User user = userRepository.findById(dto.getUser_id().longValue())
      .orElseThrow(() -> new RuntimeException("User not found"));
    Rental rental = rentalRepository.findById(dto.getRental_id())
      .orElseThrow(() -> new RuntimeException("Rental not found"));

    Message message = new Message();
    message.setMessage(dto.getMessage());
    message.setUser(user);
    message.setRental(rental);
    message.setCreatedAt(Timestamp.from(Instant.now()));
    message.setUpdatedAt(Timestamp.from(Instant.now()));

    messageRepository.save(message);
  }
}
