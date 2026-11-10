# API REST - Baozi Store

Trabalho da disciplina **Desenvolvimento Web Back-End** (UNINTER).
Aluno: Rodrigo Soares - RU 3585667.

API REST simples para a Baozi Store, uma loja de pão chinês, com cadastro de **clientes**, **produtos** e **pedidos**.

## Tecnologias

- Java 21
- Spring Boot 4.1.2 (Spring Web MVC)
- Spring Data JPA (Hibernate)
- MySQL 8.0
- Maven
- Postman (testes)

## Estrutura

```
src/main/java/br/com/baozistore/
├── model/        Cliente, Produto, Pedido (entidades JPA)
├── repository/   ClienteRepository, ProdutoRepository, PedidoRepository
└── controller/   ClienteController, ProdutoController, PedidoController
```

## Endpoints

| Método | Caminho | Descrição |
|---|---|---|
| POST | `/clientes` | Cadastra um cliente |
| GET | `/clientes` | Lista todos os clientes |
| GET | `/clientes/{id}` | Consulta um cliente |
| PUT | `/clientes/{id}` | Atualiza um cliente |
| DELETE | `/clientes/{id}` | Apaga um cliente |
| POST | `/produtos` | Cadastra um produto |
| GET | `/produtos` | Lista todos os produtos |
| GET | `/produtos/{id}` | Consulta um produto |
| PUT | `/produtos/{id}` | Atualiza um produto |
| DELETE | `/produtos/{id}` | Apaga um produto |
| POST | `/pedidos` | Registra um pedido |
| GET | `/pedidos` | Lista todos os pedidos |
| GET | `/pedidos/{id}` | Consulta um pedido |
| PUT | `/pedidos/{id}` | Atualiza um pedido |
| DELETE | `/pedidos/{id}` | Apaga um pedido |

Exemplos de corpo JSON:

```json
POST /clientes  { "nome": "Rodrigo3585667", "clienteDesde": "2026-11-09" }
POST /produtos  { "nome": "Baozi Tradicional", "preco": 6.50, "estoque": true }
POST /pedidos   { "clienteId": 1, "produtoId": 1, "quantidade": 10 }
```

## Como rodar

1. Tenha o MySQL rodando em `localhost:3306`. O banco `baozistore` é criado sozinho.
2. Copie `local.properties.example` para `local.properties` e coloque o usuário e a senha do MySQL.
3. Rode a aplicação:

   ```
   ./mvnw spring-boot:run
   ```

   ou rode a classe `BaozistoreApplication` pela IDE.
4. A API sobe em `http://localhost:8080`.

## Testes no Postman

A coleção com todas as requisições está em `postman/BaoziStore.postman_collection.json`. No Postman, use **Import** e escolha esse arquivo.

## Documentos

- `docs/Trabalho-BaoziStore-Rodrigo3585667.pdf`: trabalho entregue
- `docs/diagrama-caso-de-uso.png`: diagrama de caso de uso
- `prints/`: prints dos testes no Postman
