package com.demo.ai.aichatdemo.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAIConfig {

  @Bean
  public ChatMemory chatMemory() {
    return new InMemoryChatMemory();
  }

  @Bean
  public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
    return builder
        .defaultAdvisors(
            new MessageChatMemoryAdvisor(chatMemory)
        )
        .build();
  }
}
