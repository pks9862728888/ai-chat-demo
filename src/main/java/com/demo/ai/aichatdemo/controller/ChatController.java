package com.demo.ai.aichatdemo.controller;

import com.demo.ai.aichatdemo.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController extends GenericExceptionHandler {
  private final ChatService chatService;

  @GetMapping("/hello")
  public String hello() {
    log.info("Hello controller...");
    return "Hello World!";
  }

  @GetMapping(value = "/get-response", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> generateChatResponse(@RequestParam("prompt") String prompt) {
    log.info("Get chat response...");
    return chatService.generateChatResponse(prompt)
        .doOnCancel(() -> log.info("Chat response generation cancelled!"))
        .doOnComplete(() -> log.info("Chat response generation complete!"));
  }
}
