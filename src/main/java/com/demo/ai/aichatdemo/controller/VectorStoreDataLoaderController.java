package com.demo.ai.aichatdemo.controller;

import com.demo.ai.aichatdemo.service.VectorStoreDataLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/vector-store-data-loader")
@RequiredArgsConstructor
public class VectorStoreDataLoaderController extends GenericExceptionHandler {
  private final VectorStoreDataLoaderService vectorStoreDataLoaderService;

  @GetMapping("/xpath-functions/load")
  public ResponseEntity<String> loadXpathFunctionDataInVectorStore() throws IOException {
    log.info("Received request to load xpath function data in vector store...");
    vectorStoreDataLoaderService.loadXpathFunctionDataInVectorStore();
    return ResponseEntity.accepted()
        .body("JOB_ACCEPTED");
  }
}
