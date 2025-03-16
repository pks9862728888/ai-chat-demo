package com.demo.ai.aichatdemo.service.crawlers;

import com.demo.ai.aichatdemo.configuration.DataCrawlerUriConfig;
import com.demo.ai.aichatdemo.model.FunctionData;
import com.demo.ai.aichatdemo.model.FunctionExample;
import com.demo.ai.aichatdemo.model.FunctionParams;
import com.google.gson.Gson;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class XpathFunctionDataCrawlerService {
  public static final String JSON = ".json";
  public static final String FUNCTION_PARAMETER = "functionParameter";
  private final DataCrawlerUriConfig dataCrawlerUriConfig;

  @Value("${dir.inbuiltXpathFunctionDataDumpDir}")
  private String inbuiltXpathFunctionDataDumpDir;

  @Async
  public void crawlNDownloadXpathFunctionData() {
    try {
      List<String> xpathFunctionNames = getAllDeclaredXpathFunctions();
      log.info("Total: {} xpath functions found: {}", xpathFunctionNames.size(), xpathFunctionNames);
      xpathFunctionNames.forEach(this::downloadXpathFunctionData);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Error while crawling and downloading xpath function data, {}", e.toString());
    }
  }

  private void downloadXpathFunctionData(String functionName) {
    try {
      String url = String.format(dataCrawlerUriConfig.getXpathFunctionDefinitionUrl(), functionName);
      log.info("Fetching function definition from url: {}", url);
      Document functionDefinitionDoc = Jsoup.connect(url).get();
      // Extract data
      FunctionData functionData = new FunctionData(functionName);
      extractDefinition(functionDefinitionDoc, functionData);
      extractSyntax(functionDefinitionDoc, functionData);
      extractFunctionParams(functionDefinitionDoc, functionData);
      extractReturnValue(functionDefinitionDoc, functionData);
      extractDescription(functionDefinitionDoc, functionData);
      extractExamples(functionDefinitionDoc, functionData);
      log.info("Function: {} dataExtractionSummary: {}",
          functionData.getName(), getDataExtractionSummary(functionData));
      saveFunctionDataAsJson(functionData);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Error while downloading xpath function data for: {}, {}", functionName, e.toString());
    }
  }

  private void saveFunctionDataAsJson(FunctionData functionData) {
    try {
      String json = new Gson().toJson(functionData);
      Path outputPath = Paths.get(inbuiltXpathFunctionDataDumpDir, functionData.getName() + JSON);
      log.info("Writing function data: {} in file: {}", functionData.getName(), outputPath);
      Files.write(outputPath, json.getBytes());
    } catch (IOException e) {
      log.error("Exception while saving json data for function: {}, ex: {}", functionData.getName(), e.toString());
    }
  }

  private String getDataExtractionSummary(FunctionData fd) {
    return "definitionExtracted: " + StringUtils.isNotBlank(fd.getDefinition()) + ", " +
        "syntaxExtracted: " + StringUtils.isNotBlank(fd.getSyntax()) + ", " +
        "functionParametersExtracted: " + !fd.getFunctionParameters().isEmpty() + ", " +
        "returnValueExtracted: " + StringUtils.isNotBlank(fd.getReturnValue()) + ", " +
        "descriptionsExtracted: " + !fd.getDescriptions().isEmpty() + ", " +
        "examplesExtracted: " + !fd.getExamples().isEmpty();
  }

  private void extractExamples(Document functionDefinitionDoc, FunctionData functionData) {
    log.info("Extracting examples for: {}", functionData.getName());
    Elements examplesElement = functionDefinitionDoc.selectXpath(
        "//main/article/section[@aria-labelledby='examples']//table//tr");
    List<FunctionExample> examples = new ArrayList<>();
    examplesElement.forEach(e -> {
      Elements xpathElements = e.selectXpath("td");
      List<String> rowDataList = xpathElements.stream()
          .map(Element::text)
          .toList();
      if (rowDataList.size() == 2) {
        examples.add(new FunctionExample(rowDataList.get(0), rowDataList.get(1)));
      }
    });
    functionData.setExamples(examples);
  }

  private void extractDescription(Document functionDefinitionDoc, FunctionData functionData) {
    log.info("Extracting function description value for: {}", functionData.getName());
    Elements descriptionsElement = functionDefinitionDoc.selectXpath(
        "//main/article/section[@aria-labelledby='description']/div/ul/li");
    List<String> descriptions = new ArrayList<>();
    descriptionsElement.forEach(e -> descriptions.add(e.text()));
    functionData.setDescriptions(descriptions);
  }

  private void extractReturnValue(Document functionDefinitionDoc, FunctionData functionData) {
    log.info("Extracting function return value for: {}", functionData.getName());
    Elements returnValEle = functionDefinitionDoc.selectXpath(
        "//main/article/section[@aria-labelledby='return_value']/div/p");
    StringBuilder returnValue = new StringBuilder();
    returnValEle.forEach(e -> returnValue.append(e.text()));
    functionData.setReturnValue(returnValue.toString());
  }

  private void extractFunctionParams(Document functionDefinitionDoc, FunctionData functionData) {
    log.info("Extracting function parameters for: {}", functionData.getName());
    Elements paramsEle = functionDefinitionDoc.selectXpath(
        "//main/article/section[@aria-labelledby='parameters']/div/dl");
    List<FunctionParams> functionParams = new ArrayList<>();
    extractFunctionParamKeyValue(functionData, paramsEle, functionParams);
    if (functionParams.isEmpty()) {
      extractFunctionParamString(functionDefinitionDoc, functionParams);
    }
    functionData.setFunctionParameters(functionParams);
  }

  private void extractFunctionParamString(Document functionDefinitionDoc, List<FunctionParams> functionParams) {
    Elements descEle = functionDefinitionDoc.selectXpath("//main/article/section[@aria-labelledby='parameters']//p");
    StringBuilder paramDesc = new StringBuilder();
    descEle.forEach(e -> paramDesc.append(e.text()));
    if (StringUtils.isNotBlank(paramDesc.toString())) {
      functionParams.add(new FunctionParams(FUNCTION_PARAMETER, paramDesc.toString()));
    }
  }

  private static void extractFunctionParamKeyValue(
      FunctionData functionData, Elements paramsEle, List<FunctionParams> functionParams) {
    paramsEle.forEach(e -> {
      Elements paramNameElements = e.selectXpath("//dt");
      Elements paramDescriptionElements = e.selectXpath("//dd/p");
      if (paramNameElements.size() != paramDescriptionElements.size()) {
        log.error("Param name & description do not match for function: {}, paramNameElements: {} description: {}",
            functionData.getName(), paramNameElements, paramDescriptionElements);
      } else {
        for (int i = 0; i < paramNameElements.size(); i++) {
          functionParams.add(new FunctionParams(paramNameElements.get(i).text(),
              paramDescriptionElements.get(i).text()));
        }
      }
    });
  }

  private void extractSyntax(Document functionDefinitionDoc, FunctionData functionData) {
    log.info("Extracting function syntax for: {}", functionData.getName());
    Elements syntaxEle = functionDefinitionDoc.selectXpath("//main/article/section/div/pre");
    if (syntaxEle.isEmpty()) {
      syntaxEle = functionDefinitionDoc.selectXpath("//main/article/section//pre");
    }
    StringBuilder syntax = new StringBuilder();
    syntaxEle.forEach(e -> syntax.append(e.text()));
    functionData.setSyntax(syntax.toString());
  }

  private static void extractDefinition(Document functionDefinitionDoc, FunctionData functionData) {
    log.info("Extracting function definition for: {}", functionData.getName());
    Elements definitionElement = functionDefinitionDoc.selectXpath("//main/article/div[@class='section-content']/p");
    StringBuilder definition = new StringBuilder();
    definitionElement.forEach(e -> definition.append(e.text()));
    functionData.setDefinition(definition.toString());
  }

  private List<String> getAllDeclaredXpathFunctions() throws IOException {
    String url = dataCrawlerUriConfig.getXpathFunctionListUrl();
    log.info("Fetching xpath function names from url: {}", url);
    Document xpathFuncListDoc = Jsoup.connect(url).get();
    Elements functionAnchors = xpathFuncListDoc.selectXpath(
        "//div[@class='sidebar-body']//li[@class='toggle'][3]//ol/li/a");
    return functionAnchors.stream()
        .map(e -> e.childNode(0).toString())
        .toList();
  }
}
