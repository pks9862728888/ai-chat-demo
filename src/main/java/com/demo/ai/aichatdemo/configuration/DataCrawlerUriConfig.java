package com.demo.ai.aichatdemo.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "url")
public class DataCrawlerUriConfig {
  private String xpathFunctionListUrl;
  private String xpathFunctionDefinitionUrl;
}
