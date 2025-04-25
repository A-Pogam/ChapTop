package com.chatop.backend.service.contracts;

import com.chatop.backend.dto.MessageRequestDto;

public interface IMessageService {
  void createMessage(MessageRequestDto dto);
}
