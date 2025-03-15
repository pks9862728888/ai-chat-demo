package com.demo.ai.aichatdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatClient chatClient;

  public Flux<String> generateChatResponse(String promptText) {
    return chatClient.prompt(promptText)
        .stream()
        .content();
  }

}
