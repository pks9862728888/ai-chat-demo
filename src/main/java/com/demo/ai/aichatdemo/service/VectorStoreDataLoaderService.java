package com.demo.ai.aichatdemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.demo.ai.aichatdemo.enums.EmbeddingFileTypeEnum.INBUILT_XPATH_FUNCTIONS;

@Slf4j
@Service
@RequiredArgsConstructor
public class VectorStoreDataLoaderService {
  public static final String JSON = ".json";
  public static final String KEY = "key";
  public static final String FILE_NAME = "fileName";
  public static final String TYPE = "type";
  private final VectorStore vectorStore;

  @Value("${dir.inbuiltXpathFunctionDataDumpDir}")
  private String inbuiltXpathFunctionDataDumpDir;

  //  @PostConstruct
  @Async
  public void loadXpathFunctionDataInVectorStore() throws IOException {
    log.info("Loading all xpath-function files in vector store");
    File[] files = Paths.get(inbuiltXpathFunctionDataDumpDir).toFile()
        .listFiles((dir, name) -> name.endsWith(JSON));
    if (Objects.isNull(files)) {
      log.error("No xpath-function files found to store into vector store: {}", inbuiltXpathFunctionDataDumpDir);
      return;
    }
    for (File file : files) {
      log.info("Loading data from file: {}", file.getName());
      vectorStore.add(List.of(Document.builder()
          .text(Files.readString(file.toPath()))
          .metadata(Map.of(KEY, String.format("%s,%s", file.getName(), INBUILT_XPATH_FUNCTIONS.name()),
              FILE_NAME, file.getName(),
              TYPE, INBUILT_XPATH_FUNCTIONS.name()))
          .build()));
      log.info("Loaded embedding file: {}", file.getName());
    }
    log.info("Loaded all files in vector store");
  }
}
