package com.demo.ai.aichatdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatModel chatModel;

  public Flux<String> generateChatResponse(String promptText) {
    return chatModel.stream(promptText);
  }

}
