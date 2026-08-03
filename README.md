# Order Express API

API REST para um sistema de delivery de comida, construída com **Spring Boot** e **Spring Data JPA**. Permite gerenciar usuários, endereços, itens (produtos) e pedidos, incluindo o relacionamento entre pedidos e seus itens.

## Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- SQLite
- Lombok
- Spring Security

## Estrutura de domínio

### User
Representa um usuário do sistema (cliente), utilizado também para autenticação (`UserDetails`).

| Campo | Tipo | Descrição                                                   |
|---|---|-------------------------------------------------------------|
| `user_id` | String (UUID) | Identificador único                                         |
| `name` | String | Nome do usuário                                             |
| `email` | String | E-mail (usado como acessar o sistema além de se credenciar) |
| `password` | String | Senha                                                       |
| `role` | UserRole | Permissão de usuários (`ADMIN`, `CLIENT`)                   |

### Address
Endereço de entrega vinculado a um usuário.

| Campo | Tipo | Descrição |
|---|---|---|
| `address_id` | String (UUID) | Identificador único |
| `city` | String | Cidade |
| `street` | String | Rua |
| `number` | Integer | Número |
| `state` | String | Estado |
| `user` | User | Usuário dono do endereço (`@ManyToOne`) |

### Item (Produto)
Item disponível para compra, apenas usuários com permissão de ADMIN podem criar novos itens.
Segue no projeto "seeds" com alguns itens de exemplos

| Campo | Tipo | Descrição |
|---|---|---|
| `item_id` | String (UUID) | Identificador único |
| `price` | BigDecimal | Preço unitário |

### Order (Pedido)
Pedido feito por um usuário, associado a um endereço de entrega.

| Campo | Tipo | Descrição |
|---|---|---|
| `order_id` | String (UUID) | Identificador único |
| `order_status` | OrderStatus | Status atual do pedido |
| `total_price` | BigDecimal | Valor total do pedido |
| `user` | User | Usuário que fez o pedido (`@ManyToOne`) |
| `address` | Address | Endereço de entrega (`@ManyToOne`) |

**Status possíveis (`OrderStatus`):**

| Status | Descrição |
|---|---|
| `RECEIVED` | Pedido recebido |
| `IN_PREPARATION` | Pedido em preparo |
| `OUT_FOR_DELIVERY` | Saiu para entrega |
| `DELIVERED` | Entregue |
| `CANCELLED` | Cancelado |

### OrderItem
Relaciona um `Order` a um `Item`, guardando a quantidade pedida.

| Campo | Tipo | Descrição |
|---|---|---|
| `order_item_id` | String (UUID) | Identificador único |
| `quantity` | Integer | Quantidade do item no pedido |
| `unit_price` | BigDecimal | Preço unitário no momento do pedido |
| `subtotal` | BigDecimal | `quantity * unit_price` |
| `order` | Order | Pedido ao qual pertence (`@ManyToOne`) |
| `item` | Item | Item pedido (`@ManyToOne`) |

## Rotas da API

### Address

Base: `/v1/addresses`

| Verbo | Rota | Descrição | Retorno |
|---|---|---|---|
| `GET` | `/v1/users/{userId}/addresses` | Lista todos os endereços de um usuário | `200 OK` — `List<ResponseAddressDTO>` |

**`ResponseAddressDTO`:**
```json
[
  {
    "city": "São Paulo",
    "street": "Rua das Flores",
    "number": 123,
    "state": "SP"
  }
]
```

### Order

Base: `/v1/orders`

| Verbo | Rota | Descrição | Retorno |
|---|---|---|---|
| `GET` | `/v1/orders` | Lista todos os pedidos | `200 OK` — `List<OrderResponseDTO>` |
| `GET` | `/v1/orders/{id}` | Busca um pedido específico pelo ID | `200 OK` — `OrderResponseDTO` |
| `GET` | `/v1/orders/user/{id}` | Lista todos os pedidos de um usuário | `200 OK` — `List<OrderResponseDTO>` |
| `POST` | `/v1/orders` | Cria um novo pedido com seus itens | `201 CREATED` |
| `PATCH` | `/v1/orders/{id}/status` | Atualiza apenas o status do pedido | `200 OK` |
| `DELETE` | `/v1/orders/{id}` | Remove um pedido e seus itens | `200 OK` |

**Corpo da requisição — `POST /v1/orders` (`CreateOrderDTO`):**
```json
{
  "order_status": "RECEIVED",
  "total_price": 79.90,
  "user_id": "user-uuid-1",
  "address_id": "address-uuid-1",
  "items": [
    { "item_id": "item-uuid-1", "quantity": 2 },
    { "item_id": "item-uuid-2", "quantity": 1 }
  ]
}
```

**Corpo da requisição — `PATCH /v1/orders/{id}/status` (`UpdateStatusDTO`):**
```json
{
  "order_status": "IN_PREPARATION"
}
```

**Retorno — `GET /v1/orders` e `GET /v1/orders/{id}` (`OrderResponseDTO`):**
```json
{
  "userName": "Luca",
  "userEmail": "luca1234@gmail.com",
  "addressStreet": "Rua das Flores",
  "addressNumber": 123,
  "addressCity": "São Paulo",
  "orderStatus": "RECEIVED",
  "totalPrice": 79.90,
  "items": [
    { "quantity": 2 },
    { "quantity": 1 }
  ]
}
```

## Códigos de status HTTP utilizados

| Código | Quando ocorre |
|---|---|
| `200 OK` | Requisição processada com sucesso (GET, PATCH, DELETE) |
| `201 CREATED` | Recurso criado com sucesso (POST) |
| `404 Not Found` | Recurso não encontrado (usuário, endereço, pedido ou item inexistente) |
| `500 Internal Server Error` | Erro inesperado no servidor |

## Regras de negócio importantes

- Um `Order` é criado com um `User` e um `Address` existentes; caso algum `id` informado não exista, a criação é rejeitada.
- Os `OrderItem` são criados a partir de uma lista de `item_id` + `quantity`; se algum `item_id` não existir, o pedido inteiro é rejeitado (nenhum dado é salvo, graças ao uso de `@Transactional`).
- Ao deletar um `Order`, todos os `OrderItem` vinculados também são removidos.
- O `total_price` deve ser sempre maior que `0`.
- Clientes não podem editar o status de um pedido nem excluir pedidos. Apenas o admin.

## Rodando o projeto

```bash
./mvnw spring-boot:run
```

A aplicação sobe por padrão na porta `3002`, com o banco SQLite configurado localmente.