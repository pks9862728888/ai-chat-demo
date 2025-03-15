package com.demo.ai.aichatdemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class FunctionParams {
  private String parameterName;
  private String description;
}
