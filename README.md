# OrgFin - API de Transações

Bem-vindo ao repositório da **OrgFin-app-transacoes-api**, uma aplicação pessoal construída para solucionar um problema comum: a dificuldade de registrar transações financeiras de forma rápida e flexível no dia a dia.

Com o objetivo de:

- Possuir um aplicativo/site para registrar rapidamente transações;
- Enviar os dados registrados para um Excel pessoal, via uma API REST.
---

## 🧩 Sobre este repositório

Este repositório contém a API REST construída com **Spring Boot**, com os seguintes recursos:

- Integração com o **Amazon DynamoDB** para armazenar as transações;
- Autenticação e gestão de usuários via **AWS Cognito**;
- Contêinerização com **Docker Compose**;
- Infraestrutura gerenciada em outro repositório: [orgfin-infra](https://github.com/PedroPFP/OrgFin-infra)

---

## 🚀 Como rodar o projeto
Este guia explica as principais formas de executar a API de transações do OrgFin, incluindo rodar via Docker Compose, e utilizando os serviços AWS reais.

---

## Rodando com Docker Compose

O projeto suporta execução via Docker Compose, usando containers para a aplicação e dependências como o DynamoDB local com LocalStack.

### Pré-requisitos

- Docker e Docker Compose instalados

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/OrgFin-app-transacoes-api.git
   cd OrgFin-app-transacoes-api
2. Suba os serviços:
   ```bash
   docker-compose up --build
3. A API estará disponível em: http://localhost:8080

---

## Rodando com Serviços AWS Reais

Se quiser rodar contra os serviços AWS (DynamoDB, Cognito) na nuvem, siga estes passos.

> ⚠️ Fique atento que pode e haverá cobranças sobre esses serviços!

### Pré-requisitos

- AWS CLI configurado com credenciais válidas; [Guia AWS de configuração](www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&ved=2ahUKEwiCq5Tf1LWNAxVRJ7kGHT1sGw8QFnoECAwQAQ&url=https%3A%2F%2Fdocs.aws.amazon.com%2Fcli%2Flatest%2Fuserguide%2Fgetting-started-quickstart.html&usg=AOvVaw2gYJArit75vNKcX46JWEck&opi=89978449)
- Infraestrutura provisionada (tabelas DynamoDB, pools Cognito, etc.) via repositório orgfin-infra usando cloudformation;
- Variáveis de ambiente configuradas com endpoints, ARNs e secrets corretos; [Guia AWS para criação de profiles e credenciais](https://docs.aws.amazon.com/pt_br/cli/v1/userguide/cli-configure-files.html)

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/OrgFin-app-transacoes-api.git
   cd OrgFin-app-transacoes-api
2. Certifique-se que o repositório [orgfin-infra](https://github.com/PedroPFP/OrgFin-infra) foi usado para criar a infraestrutura AWS:
3. Coloque o id do seu cognito user pool na variável "spring.cloud.aws.cognito.user-pool-id" no application-prod.yaml.
4. Execute a aplicação localmente com:
   ```bash
   ./mvnw clean spring-boot:run
---

## 📌 Futuro

- Integração com planilhas Excel via API externa ou exportação de dados;
- Interface web/mobile com autenticação via Cognito;

---

## 📄 Licença

Projeto pessoal. Sem licença definida no momento.
