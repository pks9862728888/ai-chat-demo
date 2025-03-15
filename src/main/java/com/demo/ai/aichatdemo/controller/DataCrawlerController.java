package com.demo.ai.aichatdemo.controller;

import com.demo.ai.aichatdemo.service.crawlers.XpathFunctionDataCrawlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/crawler")
@RequiredArgsConstructor
public class DataCrawlerController extends GenericExceptionHandler {
  private final XpathFunctionDataCrawlerService xpathFunctionDataCrawlerService;

  @GetMapping("/download/xpath-functions")
  public ResponseEntity<String> crawlNDownloadXpathFunctionData() {
    log.info("Received request to crawl and download xpath function data...");
    xpathFunctionDataCrawlerService.crawlNDownloadXpathFunctionData();
    return ResponseEntity.accepted()
        .body("JOB_ACCEPTED");
  }
}
