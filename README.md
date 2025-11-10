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
