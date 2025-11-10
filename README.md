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

