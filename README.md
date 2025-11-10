# 🐇 RabbitMicro

## 📘 Visão Geral

O **RabbitMicro** é um projeto de **microserviços** construído com **Spring Boot** e **RabbitMQ**, voltado para demonstrar uma arquitetura desacoplada e escalável para troca de mensagens entre módulos.  
Ele implementa **produção e consumo de mensagens** via filas RabbitMQ, aplicando princípios de **Clean Architecture** e **DDD (Domain-Driven Design)**.

---

## 🏗️ Estrutura do Projeto
RabbitMicro-master/
<br>
├── src/main/java/dev/Zerphyis/microRabbitMq/
<br>
│ ├── Application/
<br>
│ │ ├── dto/ 
<br>
│ │ ├── mapper/ 
<br>
│ │ ├── services/
<br>
│ │ ├── useCases/
<br>
│ ├── MicroRabbitMqApplication.java 
<br>
├── pom.xml 
<br>
└── .github/workflows/ci.yml 
<br>

---

## ⚙️ Funcionalidades Principais

### 🔹 1. Comunicação via RabbitMQ
- Envio e consumo de mensagens assíncronas entre **producers** e **consumers**.
- Uso de **Exchange**, **Queue** e **Routing Key** configurados para cada domínio.

### 🔹 2. Módulo de Produtos
- CRUD completo de produtos.
- Produz e consome mensagens relacionadas a operações de produtos.
- Classes principais:
  - `ProductServiceRabbit` → envia mensagens ao RabbitMQ.
  - `ConsumeService` → consome e processa mensagens recebidas.

### 🔹 3. Módulo de Usuários
- Registro, login e desativação de usuários.
- Comunicação via filas para eventos relacionados a usuários.
- Casos de uso: `DeactiveUserUseCase`, `FindUsersUseCase`.

### 🔹 4. Módulo de E-mails
- Serviço de envio de mensagens de e-mail via `EmailProducerService`.

---

# 🧩 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **RabbitMQ**
- **Lombok**
- **MapStruct** (mapeamento de DTOs)
- **Maven**
- **GitHub Actions** (CI/CD)

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java 17+
- Maven 3.8+
- RabbitMQ rodando localmente (porta padrão `5672`)

### Passos

#### 1. Clone o repositório:
   ```bash
   git clone https://github.com/Zerphyis/RabbitMicro.git
   cd RabbitMicro-master
````
#### 2.Configure as credenciais do RabbitMQ em:
````bash
src/main/resources/application.properties
````
####  3.Execute o projeto:
````bash
mvn spring-boot:run
````
#### 4.Acesse:
````bash
http://localhost:8080
````
### 🧠 Conceitos Aplicados
Clean Architecture: Separação clara entre camadas (DTO → Service → UseCase).
<br>
Event-Driven Architecture: Comunicação baseada em eventos.
<br>
Desacoplamento total: Cada módulo (usuário, produto, e-mail) pode operar de forma independente.
<br>

### 🧪 Testes
Para rodar os testes automatizados:


````bash
mvn test
````

