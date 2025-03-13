# Getting Started <hr />

# Pre-requisites <hr />
To start docker image
```shell
docker run -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
```

Step 2:
Go inside shell and install required models: llama2 / gemma3

Step 3: To verify if docker container is started properly
```shell
curl -X POST http://localhost:11434/api/generate -d '{
  "model": "gemma3",
  "prompt": "what is java",
   "stream": false
 }'
```

### Reference Documentation <hr />
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.3/maven-plugin/build-image.html)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/3.4.3/specification/configuration-metadata/annotation-processor.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.3/reference/using/devtools.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/3.4.3/reference/web/reactive.html)
* [Ollama](https://docs.spring.io/spring-ai/reference/api/chat/ollama-chat.html)
