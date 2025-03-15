package com.demo.ai.aichatdemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FunctionDescription {
  private String name;
  private String syntax;
  private List<FunctionParams> parameters;
  private String returnValue;
  private List<String> descriptions;
  private List<FunctionExample> examples;
}
