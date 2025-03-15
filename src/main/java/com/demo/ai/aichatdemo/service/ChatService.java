package com.demo.ai.aichatdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatClient chatClient;

  public Flux<String> generateChatResponse(String promptText, String chatId) {
    return chatClient.prompt()
        .user(promptText)
        .advisors(a -> a
            .param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
            .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
        )
        .stream()
        .content();
  }

}
