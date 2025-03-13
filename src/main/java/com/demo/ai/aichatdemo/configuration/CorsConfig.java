package com.demo.ai.aichatdemo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@Configuration
public class CorsConfig {

  @Value("${web.cors.allowed-origins}")
  private List<String> allowedOrigins;

  @Bean
  public CorsWebFilter corsWebFilter() {
    log.info("CORS allowed origins: {}", allowedOrigins);
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(allowedOrigins);
    configuration.applyPermitDefaultValues();

    UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();
    corsConfigSource.registerCorsConfiguration("/**", configuration);
    return new CorsWebFilter(corsConfigSource);
  }

}
