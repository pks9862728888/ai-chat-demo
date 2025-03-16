package com.demo.ai.aichatdemo.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringAIConfig {
  private final VectorStore vectorStore;

  private static final String CUSTOM_USER_TEXT_ADVISE = """
			
			Context information is below, surrounded by ---------------------

			---------------------
			{question_answer_context}
			---------------------
			
			You are an expert in writing dsl function and highly trained to provide valuable answers from context.

			Given the context and provided history information and not prior knowledge,
			reply to the user comment. If the answer is not in the context, inform
			the user that you can't answer the question. Try to provide examples in your answer.
			""";

  @Bean
  public ChatMemory chatMemory() {
    return new InMemoryChatMemory();
  }

  @Bean
  public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
    return builder
        .defaultAdvisors(
            new MessageChatMemoryAdvisor(chatMemory),
            new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder()
                .topK(10)
                .similarityThreshold(0.5f)
                .build(), CUSTOM_USER_TEXT_ADVISE)
        )
        .build();
  }
}
