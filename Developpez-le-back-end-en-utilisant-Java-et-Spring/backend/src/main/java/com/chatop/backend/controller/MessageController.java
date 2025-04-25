package com.chatop.backend.controller;

import com.chatop.backend.dto.MessageRequestDto;
import com.chatop.backend.service.contracts.IMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  private final IMessageService messageService;

  public MessageController(IMessageService messageService) {
    this.messageService = messageService;
  }

  @PostMapping
  public ResponseEntity<?> createMessage(@RequestBody MessageRequestDto dto) {
    messageService.createMessage(dto);
    return ResponseEntity.ok(Map.of("message", "Message sent successfully !"));
  }
}
