package com.demo.ai.aichatdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vector-store-data-loader")
@RequiredArgsConstructor
public class VectorStoreDataLoaderController extends GenericExceptionHandler {
}
