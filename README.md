# Razzie Awards API

API desenvolvida com Spring Boot para importação e processamento de dados de filmes do prêmio Razzie Awards.

---

# Tecnologias utilizadas

- Java 25
- Spring Boot 3
- Spring Batch 6
- Spring Data JPA
- H2 Database
- Gradle
- Docker

---

# Pré-requisitos

Para executar localmente você precisa ter instalado:

- Java 25+
- Docker (opcional)
- Docker Compose (opcional)

---

# Clonando o projeto

```bash
git clone <URL_DO_REPOSITORIO>
cd razzieAwardsApi
```

---

# Executando localmente com Gradle

## Linux / Mac

```bash
./gradlew bootRun
```

## Windows

```bash
gradlew.bat bootRun
```

---

# Executando os testes

## Linux / Mac

```bash
./gradlew test
```

## Windows

```bash
gradlew.bat test
```

---

# Gerando o build

```bash
./gradlew build
```

O arquivo `.jar` será gerado em:

```text
build/libs/
```

---

# Executando via Docker

## Build da imagem

```bash
docker build -t razzie-awards-api .
```

---

## Executando o container

```bash
docker run -p 8080:8080 razzie-awards-api
```

---

# Executando via Docker Compose

## docker-compose.yml

```yaml
version: '3.9'

services:
  razzie-awards-api:
    build: .
    ports:
      - "8080:8080"
```

---

## Subindo a aplicação

```bash
docker compose up --build
```

---

# Endpoints

## Swagger UI

```text
http://localhost:8080/swagger-ui.html
```

---

## OpenAPI Docs

```text
http://localhost:8080/v3/api-docs
```

---

# Banco de dados

A aplicação utiliza H2 em memória.

## H2 Console

```text
http://localhost:8080/h2-console
```

## JDBC URL

```text
jdbc:h2:mem:razzieAwardsApiDB
```

## Usuário

```text
sa
```

## Senha

```text

```

---

# Spring Batch

A aplicação executa automaticamente um Job responsável pela importação do arquivo CSV durante a inicialização.

Arquivo utilizado:

```text
src/main/resources/Movielist.csv
```

---

# Logs

Para habilitar logs detalhados do Spring Batch:

```yaml
logging:
  level:
    org.springframework.batch: DEBUG
```

---

# Estrutura do projeto

```text
src/main/java
 ├── application
 ├── domain
 ├── infrastructure
 └── presentation
```

---

# Autor

Reinaldo Pádua
