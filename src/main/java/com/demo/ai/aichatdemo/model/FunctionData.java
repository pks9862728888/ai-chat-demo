package com.demo.ai.aichatdemo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class FunctionData {
  private final String name;
  private String definition;
  private String syntax;
  private List<FunctionParams> functionParameters;
  private String returnValue;
  private List<String> descriptions;
  private List<FunctionExample> examples;
}
