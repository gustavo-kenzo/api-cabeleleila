# 💇‍♀️ CabeleLEILA — Sistema de Agendamento Online

> API REST desenvolvida como teste prático de entrevista técnica, com o objetivo de permitir agendamentos de serviços do salão de beleza da Leila.

---

## 📋 Sobre o Projeto

O sistema contempla dois perfis de acesso:

| Perfil | Role | Descrição |
|--------|------|-----------|
| **Cliente** | `ROLE_CLIENT` | Agendamento, alteração e histórico de serviços |
| **Admin / Leila** | `ROLE_ADMIN` | Gestão operacional e gerencial do salão |

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|-----------|-----|-----------|
| **Java** | 21  | Linguagem principal |
| **Spring Boot** | 4.0.6 | Framework da aplicação |
| **Spring Security** | 4.0.6 (Gerenciado) | Autenticação e autorização |
| **Spring Data JPA** | 4.0.6 (Gerenciado) | Persistência e acesso a dados |
| **JWT (Auth0)** | 4.5.2| Geração e validação de tokens de autenticação |
| **MySQL** | Gerenciado | Banco de dados relacional |
| **Flyway** | Gerenciado | Versionamento e migração do banco de dados |
| **Lombok** | Gerenciado | Redução de boilerplate (getters, construtores, etc.) |
| **Bean Validation** | Gerenciado | Validação dos dados de entrada nas requisições |
| **SpringDoc / Swagger UI** | 3.0.2 | Documentação interativa da API |

---

## 📐 Arquitetura e Estrutura do Projeto

O projeto segue uma arquitetura em camadas, organizada pelo pacote base `br.com.dsin.cabeleleila`:

```
cabeleleila/
├── config/                  # Configurações de segurança (Spring Security + Security Filter)
├── controller/              # Controllers REST
├── domain/
│   ├── repository/          # Interfaces JPA
│   ├── security/            # Entidades de segurança (User, Role)
│   └── *.java               # Entidades de domínio (Appointment, Client, ServiceProvided, ScheduleStatus)
├── dto/
│   ├── request/             # Objetos de entrada (Records com validações)
│   └── response/            # Objetos de saída (Records de resposta)
├── exceptions/              # Exceções de negócio e handler global
├── mapper/                  # Conversão entre entidades e DTOs
└── service/                 # Regras de negócio
    └── security/            # Serviços de autenticação, token e usuário
```

---

## ✅ Funcionalidades Implementadas

### Para a Cliente (`ROLE_CLIENT`)
- **Cadastro** de nova conta (público, sem autenticação)
- **Agendamento** de serviços com data futura obrigatória
- **Alteração** de agendamento:
    - Permitida pelo sistema quando faltam **mais de 48 horas** para a data agendada
    - Com **menos de 48 horas**, a alteração é bloqueada e o sistema orienta o contato por telefone
- **Confirmação de sugestão**: ao identificar outro agendamento da mesma cliente na mesma semana, a resposta inclui uma sugestão de data; a cliente pode confirmar via endpoint dedicado
- **Histórico** de agendamentos por período (filtro por data inicial e final), com paginação
- **Visualizar histórico** de agendamentos por período

### Operacional (`ROLE_ADMIN`)
- **Listar todos os agendamentos**, com filtro opcional por status e paginação
- **Alterar agendamentos** de qualquer cliente (sem restrição de prazo, para atender solicitações por telefone)
- **Atualizar o status** de cada agendamento individualmente (`PENDING`, `CONFIRMED`, `COMPLETED`, `CANCELED`, `NO_SHOW`)
- **Consultar** clientes individualmente ou listá-los com paginação
- **Cadastrar serviços** oferecidos pelo salão (nome, preço, descrição)
- **Visualizar histórico** de agendamentos de qualquer cliente por período

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** com autenticação stateless.

1. O cliente realiza login via `POST /login` com `username` (e-mail) e `password`
2. A API retorna um token JWT
3. Todas as demais requisições devem incluir o token no header:
   ```
   Authorization: Bearer <token>
   ```

> **Nota:** Por se tratar de um teste prático, o token JWT foi configurado **sem data de expiração**. Na prática, pode-se definir um tempo de expiração (o código já contém o método `dataExpiracao()` comentado no `TokenService`).

---

## 🗃️ Banco de Dados

O schema é gerenciado automaticamente pelo **Flyway**. As migrations estão em `src/main/resources/db/migration/`:

| Migration | Descrição |
|-----------|-----------|
| `V1` | Criação da tabela `clients` |
| `V2` | Criação da tabela `services` |
| `V3` | Criação da tabela `appointments` |
| `V4` | Criação da tabela `users` |
| `V5` | Criação da tabela `roles` |
| `V6` | Criação da tabela de associação `user_role` |
| `V7` | Adição da coluna `user_id` em `clients` |
| `V8` | Seed inicial: roles `ROLE_ADMIN` / `ROLE_CLIENT` e usuário administrador (Leila) |

### Usuário administrador padrão (criado pelo seed)

| Campo | Valor                  |
|-------|------------------------|
| E-mail | `leila@email.com`      |
| Senha | `123456` *(em bcrypt)* |

---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos

- **Java 21** instalado
- **MySQL 8.x** rodando localmente
- **Maven** instalado (ou use o wrapper `./mvnw`)

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/cabeleleila.git
cd cabeleleila
```

### 2. Configure o banco de dados

Certifique-se de que o MySQL está rodando. O banco `cabeleleila` será criado automaticamente na primeira execução (por causa do `createDatabaseIfNotExist=true`).

Se necessário, ajuste as credenciais no arquivo `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/cabeleleila?createDatabaseIfNotExist=true
    username: root
    password: root
```

### 3. Configure a variável de ambiente do JWT (opcional)

Por padrão, o secret JWT usa o valor `12345678`. Mas é possível definir uma variável de ambiente para isso

```bash
CABELELEILA_SECRET=seu_secret_aqui
```

### 4. Execute a aplicação

```bash
mvnw spring-boot:run
```

ou, compilando antes:

```bash
mvnw spring-boot:run
java -jar target/cabeleleila-*.jar
```

A aplicação estará disponível em: `http://localhost:8080`

### 5. Acesse a documentação Swagger

```
http://localhost:8080/swagger-ui.html
```

---

## 🌐 Principais Endpoints

### Autenticação
| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| `POST` | `/login` | Público | Autenticação e geração do token JWT |
| `POST` | `/client` | Público | Cadastro de nova cliente |

### Agendamentos (Cliente)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/appointment` | Criar agendamento |
| `PUT` | `/appointment/{id}` | Alterar agendamento (regra de 48h) |
| `PATCH` | `/appointment/{id}/confirm-suggestion` | Confirmar sugestão de data da mesma semana |
| `GET` | `/appointment/{userId}/history` | Histórico de agendamentos por período |

### Agendamentos (Admin)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/admin/appointments` | Listar todos os agendamentos (com filtro por status) |
| `PUT` | `/admin/appointments/{id}` | Alterar agendamento de qualquer cliente |
| `PATCH` | `/admin/appointments/{id}/status` | Atualizar status de um agendamento |

### Clientes e Serviços (Admin)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/client` | Listar clientes |
| `GET` | `/client/{id}` | Detalhar cliente com agendamentos |
| `POST` | `/service` | Cadastrar novo serviço |

---


Projeto desenvolvido exclusivamente para fins de avaliação técnica.