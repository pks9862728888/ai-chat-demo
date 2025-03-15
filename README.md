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

### Vector Store <hr />
- Install extensions
```shell
sudo apt update
sudo apt install postgresql-16-pgvector
```
- As superuser below extensions needs to be installed - [source](https://docs.spring.io/spring-ai/reference/api/vectordbs/pgvector.html)
```sql
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
```
- Create table and index using scripts provided in `database` folder

### Login to postges for maintenance <hr />
- postgres is the superuser and has no password.
- For darkknight44 user, password is windows password.
```shell
wsl
sudo -i -u postgres
psql -U darkknight44 -d aichat -W
```
- Database is running on localhost:5432

### Reference Documentation <hr />
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.3/maven-plugin/build-image.html)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/3.4.3/specification/configuration-metadata/annotation-processor.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.3/reference/using/devtools.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/3.4.3/reference/web/reactive.html)
* [Ollama](https://docs.spring.io/spring-ai/reference/api/chat/ollama-chat.html)
