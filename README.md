# Recursos

| Operação | Request | Códigos sucesso | Códigos de erro |
| -------- | ------- | --------------- | --------------- |
| [Adicionar transação](#adicionar-transação) | POST /transacoes | 201, 200 | 400, 422  
| [Alterar transação](#alterar-transação) | PUT /transacoes/{id} | 204, 200 | 400, 422
| Listar transações do usuario | GET /transacoes?idUsuario=id | 200 | 
| Remover transação | DELETE /transacoes/{id} | 204 | 400, 404

# Contrato API

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
            {"field": "idUsuario", "error": "Id do usuário é obrigatório"}
        ]
    }

### Alterar transação
