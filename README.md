# Desafio Técico - Desenvolvedor 

## Sistema Simplificado de Gerenciamento de Tarefas - API
API REST desenvolvida em Java para gerenciamento de usuários, projetos e tarefas, com autenticação JWT, controle de permissões por perfil e relatório resumido do projeto

---

## Clean Architecture  
A ideia de criar o projeto usando o Clean Architecture foi para separar as responsabilidades e criar uma estrutura que seja fácil de entender, manter e escalar. Ela é baseada em princípios como a inversão de dependências, a separação de preocupações e a independência de frameworks.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA / Hibernate
- PostgreSQL
- Spring Security com JWT
- Bean Validation
- Swagger / OpenAPI
- JUnit 5 + Mockito
- Docker
- Maven

---  

## Estrutura do Projeto

```
application/
    usercases/
    gateways/
    
domain/
    entities/

infra/
    controllers/
    persistence/
    gateways/
    security/
    excption/

```

---

## Decisões Técnicas e Tradeoffs

### Separação de camadas

- Controller -> entrada HTTP
- Use Cases -> regras de negócio
- Domain -> entidades puras
- Repository -> abstração de persistência
- Infra -> implementação técnica

### Benefícios

- Baixo acoplamento
- Alta testabilidade
- Alta escalabilidade evolutiva do sistema
- Facilidade para troca de tecnologias
- Facilidade de manutenção

### Tradeoff

- Maior número de classes
- Curva de entendimento inicial
- Maior tempo no desenvolvimento inicial

## Principais Funcionalidades

### Usuários

- Criar, atualizar e remover usuário
- Validação de email único
- Senha criptografada (BCrypt)

### Projetos

- Criar, atualizar e remover projetos
- Definição de owner (ADMIN)
- Associação de membros (MEMBER)

### Tarefas

- Criar, atualizar e remover tarefas
- Listagem com paginação
- Filtros por:
  - status
  - prioridade
  - responsável
  - range de datas
- Busca textual por título/descrição
- Ordenação por:
    - prioridade
    - data de criação
    - deadline

### Relatório

Resumo por projeto:

```json
{
  "byStatus": {
    "TODO": 12,
    "IN_PROGRESS": 3,
    "DONE": 45
  },
  "byPriority": {
    "LOW": 3,
    "MEDIUM": 2,
    "HIGH": 5,
    "CRITICAL": 1
  }
}
```

---

### Regras de Negócio

- Usuários só acessam tarefas de projetos aos quais pertencem
- Perfil ADMIN gerencia projetos e membros
- Perfil MEMBER gerencia apenas tarefas
- Tarefa DONE não pode voltar para TODO
- Tarefas CRITICAL só podem ser finalizadas por ADMIN
- Não é permitido mais de 5 tarefas IN_PROGRESS por responsável

---

## Testes
## Teste unitários
Os testes unitários foram aplicados na camada de Use Cases    
  
### UpdateTask  
- Não deve permitir que membro feche tarefa critica
- Não deve permitir que tarefa DONE retorne a TODO

### CreateUser
- Deve criar um usuário com senha criptografada

## Teste de integracao
O teste de integração foi realizado utilizando @SpringBootTest, validando o fluxo real da aplicação:

### ProjectController
- Criação de projeto através com usuário autenticado via JWT
- Owner e membros do projeto salvos corretamente

---

## Executar com Docker

### 1. Gerar o pacote da aplicação
```
./mvnw clean package -DskipTests
```
### 2. Subir containers
```
docker compose up -d --build
```

---

## Containers criados
### API
```
ssgt-api
```
### PostgreSQL
```
ssgt-bd
```

---

## Swegger / Documentação
```
http://localhost:8080/swagger-ui/index.html
```

⚠️ **Importante:** O endpoint para criar usuário não exige Autenticação.  
Crie o Usuário primeiro e use suas credenciais para obter o Token JWT.

## Desenvolvido por: ``` Marcos André ```