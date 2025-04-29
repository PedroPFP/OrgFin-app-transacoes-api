# OrgFin

Eu tenho um problema, quando vou gerir minhas finanças pessoais eu amo o excel por conta de sua flexibilidade e facilidade de uso
mas quando preciso fazer uma compra que não consigo anotar na hora acabo esquecendo e ocorrem inconsistências no final do mês.

Pensei em usar um app, e funcionou por um tempo, mas quando percebi o quão "trancados" eles são decidi abandonar.

Foi ai que nasceu o OrgFin, um projeto pessoal com um nome nem um pouco creativo, com o íntuito de possui um app/site 
para fazer adicionar os dados, e se aproveitando de uma Api Rest para mandar essas mesmas informações ao meu excel pessoal. 

Este repositório se trata de uma aplicação spring rest com integração ao dynamoDb, para armazenamento 
dos dados e ao aws cognito, para fazer a gestão de usuários.

# Repositórios

Repositório de infra(cloudfront) - [OrgFin-infra](https://github.com/PedroPFP/OrgFin-infra)

Repositório de aplicação REST    - [OrgFin-app-transacoesApi](https://github.com/PedroPFP/OrgFin-app-transacoes-api)

# Arquitetura

- O armazenamento de dados é realizado no dynamoDb, um banco de dados não relacional de propriedade da aws.
- O servidor é hospedado em um EC2 com estância reservada de um ano, visando a economia de custos no longo prazo
- O servidor é gerenciado pelo ECS, um serviço de gerenciamento de containers da aws
- O servidor possui um ASG que, quando o servidor passar de um processamento de 90% de CPU, irá criar uma nova instância em uma availability zone diferente
- Os usuários serão armazenados em um cognito user pool
- O cadastro de transações é realizado em um app flutter, e em uma aplicação web react
- A planilha irá realizar a requisição de todas as transações e transformar em um csv



![alt text](http://orgfinimages.s3.sa-east-1.amazonaws.com/Arquitetura.png)

# Próximas features



# Recursos

| Operação                                                       | Request                          | Códigos sucesso | Códigos de erro |
|----------------------------------------------------------------|----------------------------------| ---------- | --------------- |
| [Listar transações do usuário](#listar-transações-do-usuário ) | GET /transacoes/{idUsuario}      | 200 | 422                                                                                                                                                                                                 
| [Buscar transação](#buscar-transação)                          | GET /transacoes/{id}/{idUsuario} | 200 | 422 
| [Adicionar transação](#adicionar-transação)                    | POST /transacoes                 | 201 | 400, 422  
| [Alterar transação](#alterar-transação)                        | PUT /transacoes/{id}             | 204 | 400, 422
| [Remover transação](#remover-transação)                        | DELETE /transacoes/{id}          | 204 | 400


# Contrato API

### Listar transações do usuário

    - Requisição
    URI: /transacoes/{idUsuario}
    Método: GET

    - Resposta
    1. Sucesso

    Código: 200 - Ok
    Response:
    {
        [
            {
                "idUsuario": "string",
                "tipo": "string",
                "data": "date",
                "valor": "number",
                "titulo: "string", 
                "descricao": "string"
            }
        ]
    }

    2. Erro de validação

    Código: 404 - Not found
    Body:
    {
        "status": 404.
        "message": "Nenhuma transação encontrada.",
        "errors": []
    }

    Código: 422 - Unprocessable Entity
    Body: 
    {
        "status": 422,
        "message" "Erro de validação",
        "errors": [
            {"field": "idUsuario", "error": "Id do usuário deve existir na base de dados."}
        ]
    }


### Buscar transação

    - Requisição
    URI: /transacoes/{id}/{idUsuario}
    Método: GET

    - Resposta
    1. Sucesso

    Código: 200 - Ok
    Response:
    {
        "idUsuario": "string",
        "tipo": "string",
        "data": "date",
        "valor": "number",
        "titulo: "string", 
        "descricao": "string"
    }

    2. Erro de validação

    Código: 404 - Not found
    Body:
    {
        "status": 404.
        "message": "Nenhuma transação encontrada.",
        "errors": []
    }

    Código: 422 - Unprocessable Entity
    Body: 
    {
        "status": 422,
        "message" "Erro de validação",
        "errors": [
            {"field": "idUsuario", "error": "Id do usuário deve existir na base de dados."}
        ]
    }


### Adicionar transação
    
    - Requisição
    URI: /transacoes
    Método: POST
    Body:
    {
        "idUsuario": "string",
        "tipo": "string",
        "data": "date",
        "valor": "number",
        "titulo: "string", 
        "descricao": "string"
    }

    - Resposta
    1. Sucesso

    Código: 201 - Created

    2. Erro de validação

    Código: 422 - Unprocessable Entity
    Body: 
    {
        "status": 422,
        "message" "Erro de validação",
        "errors": [
            {"field": "idUsuario", "error": "Id do usuário deve existir na base de dados."}
        ]
    }

### Alterar transação

        - Requisição
        URI: /transacoes/{id}
        Método: PUT
        Body:
        {
            "idUsuario": "string",
            "tipo": "string",
            "data": "date",
            "valor": "number",
            "titulo: "string", 
            "descricao": "string"
        }
        
        - Resposta
        1. Sucesso

        Código: 204 - No content
        
        2. Erro de validação

        Código: 404 - Not found
        Body:
        {
            "status": 404.
            "message": "Transação não encontrada",
            "errors": []
        }

        Código: 422 - Unprocessable Entity
        Body: 
        {
            "status": 422,
            "message" "Erro de validação",
            "errors": [
                {"field": "idUsuario", "error": "Id do usuário deve existir na base de dados."}
            ]
        }

### Remover transação

        URI: /transacoes/{id}/{idUsuario}
        Método: DELETE
        
        - Resposta
        1. Sucesso

        Código: 204 - No content
        
        2. Erro de validação

        Código: 404 - Not found
        Body:
        {
            "status": 404.
            "message": "Transação não encontrada",
            "errors": []
        }

        Código: 422 - Unprocessable Entity
        Body: 
        {
            "status": 422,
            "message" "Erro de validação",
            "errors": [
                {"field": "idUsuario", "error": "Id do usuário deve existir na base de dados."}
            ]
        }