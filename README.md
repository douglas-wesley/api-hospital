# API de Atendimento Hospitalar (Teste Técnico)

Esta é uma **API RESTful** desenvolvida em **Java 17** com **Spring Boot 3** para simular o fluxo de atendimento de emergência de um hospital, conforme os requisitos do teste técnico para Desenvolvedor Back-End.

O projeto implementa um fluxo completo de atendimento (da recepção à alta), controle de acesso baseado em papéis (RBAC) com JWT, tratamento de exceções global (`@ControllerAdvice`) e documentação de API com Swagger/OpenAPI.

A aplicação também inclui um `DataSeeder` que popula automaticamente o banco de dados com os 4 perfis de usuários necessários para os testes (`ATENDENTE`, `ENFERMEIRO`, `MEDICO`, `FARMACIA`).

## 🛠️ Stack de Tecnologias

- Java 17
- Spring Boot 3
- Spring Web (APIs REST)
- Spring Data JPA (Persistência)
- Spring Security (Autenticação/Autorização com JWT)
- Spring Validation (Validação de DTOs)
- PostgreSQL (Banco de dados relacional)
- Maven (Gerenciador de dependências)
- Lombok (Redução de boilerplate)
- SpringDoc OpenAPI (Swagger) (Documentação da API)
- Docker (Ambiente de execução containerizado)

## 🚀 Como Executar o Projeto

Existem duas formas de executar a aplicação: via Docker (Recomendado) ou Localmente.

### **Opção 1:** Executar com Docker (Recomendado)

Esta é a forma mais simples e rápida de subir todo o ambiente (API + Banco de Dados) sem a necessidade de configurar o Postgres localmente.

**Pré-requisitos:**
- Docker e Docker Compose instalados.

Passos:

1. Clone este repositório.

```bash
git clone https://github.com/douglas-wesley/api-hospital
```

2. Abra um terminal na raiz do projeto (onde está o docker-compose.yml).

3. Execute o comando para construir e subir os containers:

```bash
docker-compose up --build
```
4. Aguarde 1-2 minutos. 
5. O Docker irá baixar a imagem do Postgres, criar o banco api_hospital, compilar o projeto Java e iniciar a API.

A API estará disponível em: `http://localhost:8080`

### **Opção 2:** Executar Localmente

Esta opção requer que você tenha o **Java 17** e o **PostgreSQL** instalados na sua máquina.

#### 1. Configuração do Banco Local
- Inicie seu serviço local do PostgreSQL (ex: sudo systemctl start postgresql).

- Acesse o psql como superusuário: sudo -u postgres psql

- Crie o banco de dados (se ainda não existir):

```sql
CREATE DATABASE api_hospital;
```
- Defina uma senha para o usuário postgres (se ainda não tiver):

```sql
ALTER USER postgres PASSWORD 'sua_senha_aqui';
```

#### 2. Configuração do Projeto Java

- Abra o application.properties e preencha a sua senha local do PostgreSQL (`spring.datasource.password=sua_senha_aqui`).
- O mesmo vale para o JWT secret (`jwt.secret`), você pode definir qualquer valor seguro.

#### 3. Executar a Aplicação
- Importe o projeto em sua IDE (IntelliJ, VS Code, etc.) como um projeto Maven.
- Encontre e execute a classe principal ApiHositalApplication.java.

A API estará disponível em http://localhost:8080.

## 📄 Documentação da API

Com a API rodando (por qualquer um dos métodos acima), a documentação interativa completa de todos os endpoints está disponível no [Swagger UI](http://localhost:8080/swagger-ui.html)

O Swagger já está configurado para o fluxo de autenticação JWT (`Bearer Token`). Você pode usá-lo para realizar todos os testes.

## 🧪 Testando o Fluxo da API
A aplicação já vem com um `DataSeeder` que cria automaticamente os seguintes usuários no banco de dados para facilitar os testes:

 **Usuários Padrão:**
- `atendente@hospital.com` (Role: `ATENDENTE`)
- `enfermeiro@hospital.com` (Role: `ENFERMEIRO`)
- `medico@hospital.com` (Role: `MEDICO`)
- `farmacia@hospital.com` (Role: `FARMACIA`)

### 1. Fluxo Principal
1. **Login (Atendente):** `POST /auth/login` com credenciais do **Atendente**. 
   - Copie o token JWT retornado para usar nos próximos passos.
2. **Registrar ficha do Paciente (Atendente):** `POST /fichas`
   - Use o token do Atendente (no Header Authorization: Bearer <token>).
   - Envie o FichaCreateRequestDTO (ex: Paciente "José da Silva", CPF "111...").
   - Resultado: 201 Created. Anote o id da ficha (ex: 1). 
3. **Login (Enfermeiro):** `POST /auth/login`
   - Use as credenciais do **Enfermeiro** e copie o token JWT.
4. **Classificar Ficha (Enfermeiro):** `PUT /fichas/{id}/classificar`
   - Use o token do Enfermeiro.
   - Envie o `TriagemRequestDTO` (ex: Prioridade "ALTA", Sintomas "Dor no peito").
   - Resultado: 200 OK. Status da ficha atualizado para "`AGUARDANDO_MEDICO`"
5. **Login (Médico):** `POST /auth/login`
   - Use as credenciais do **Médico** e copie o token JWT.
6. **Atender Ficha (Médico):** `PUT /fichas/{id}/atender`
   - Use o token do Médico.
   - Envie o `AtendimentoMedicoRequestDTO` (ex: Diagnóstico "Infarto", Prescrição "Medicamento X").
   - Resultado: 200 OK. Status da ficha atualizado para "`AGUARDANDO_MEDICACAO`"
7. **Login (Farmácia):** `POST /auth/login`
   - Use as credenciais da **Farmácia** e copie o token JWT.
8. **Registrar Alta (Farmácia):** `PUT /fichas/{id}/alta`
   - Use o token da Farmácia.
   - _Restulado_: 200 OK. Status da ficha atualizado para "`AGUARDANDO_ALTA_MEDICA`"
9. **Concluir Atendimento (Médico):** `POST /fichas/{id}/concluir`
   - Use o token do Médico.
   - _Resultado_: 200 OK. Status da ficha atualizado para "`CONCLUIDO`"

### 2. Fluxo de Exceções
- **409 (Email Duplicado):** Tente `POST /auth/register` com um e-mail que já existe (ex: `atendente@hospital.com`).
- **403 (Permissão Negada):** Tente `PUT /fichas/{id}/classificar` (rota de Enfermeiro) usando o `token` de um `ATENDENTE`.
- **404 (Não Encontrado):** Tente `GET /fichas/999` (um ID que não existe).
- **400 (Regra de Negócio):** Tente `POST /fichas` para um paciente que já tem uma ficha ativa.

## 🧠 Decisões de Implementação
- **Segurança:** Utilização de JWT para autenticação e autorização, garantindo que apenas usuários com os papéis corretos possam acessar determinados endpoints.
- **Tratamento de Exceções:** Implementação de um `@ControllerAdvice` global para capturar e formatar erros de forma consistente.
- **Validação:** Uso do Spring Validation para garantir que os dados recebidos nas requisições estejam corretos antes de serem processados.
- **Documentação:** Integração com Swagger/OpenAPI para facilitar o entendimento e teste dos endpoints da API.
- **Data Seeder:** Criação automática dos usuários necessários para testes, agilizando o processo de verificação da aplicação.
- **Arquitetura:** Segregação clara entre camadas (Controller, Service, Repository) para manter o código organizado e de fácil manutenção.
- **Dockerização:** Uso do Docker Compose para simplificar o ambiente de desenvolvimento e testes.
- **Padrões de Código:** Adoção de boas práticas de codificação, como o uso de DTOs, Lombok para reduzir boilerplate e nomenclatura clara.

## 🤝 Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests para melhorias, correções de bugs ou novas funcionalidades.
