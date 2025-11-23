# VigiaEnchente

Por Lívia Mendes, Paulo Moura, Pedro Sanzio e Raphael Abade.

## Visão Geral

O VigiaEnchente é uma portal web para compartilhar informações sobre inundação, alagamento e dicas de segurança para seus usuários. O sistema é composto de uma página web, que disponibiliza informações para o usuário e permite cadastro para aquele que desejar receber notificações, e um back-end em Node.js responsável pela interface web e monitoramento do clima a partir de diferentes APIs. Este repositório contém o código-fonte e os recursos necessários para implantar e manter o sistema VigiaEnchente.

## Funcionalidades

- **Monitoramento em Tempo Real:** Monitora continuamente os níveis de água e as condições meteorológicas.
- **Sistema de Alertas:** Envia notificações e alertas quando são detectadas condições de inundação.
- **Interface de Usuário:** Fornece uma interface amigável para visualização de dados e gerenciamento de alertas.

## 🏗️ Arquitetura do Sistema

```
VigiaEnchente/
├── vigiaenchente-core/           # Domínio compartilhado
├── vigiaenchente-api/            # API REST principal
├── vigiaenchente-collector/      # Coleta de dados externos
├── vigiaenchente-analytics/      # Análise de riscos
└── vigiaenchente-notifier/       # Sistema de notificações
```

## 🚀 Tecnologias Utilizadas

### Core
- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.7** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **Spring Session JDBC** - Gerenciamento de sessões

### Database
- **MySQL 8.3** - Banco de dados principal
- **HikariCP** - Connection pool
- **Flyway/Liquibase** - Migrations (opcional)

### Cache & Performance
- **Caffeine Cache** - Cache em memória
- **Spring Cache Abstraction** - Abstração de cache

### External APIs
- **OpenWeather API** - Dados climáticos
- **Open-Meteo Flood API** - Dados de enchentes
- **IPInfo API** - Geolocalização
- **News API** - Notícias relacionadas

### Notifications
- **Web Push (nl.martijndwars)** - Notificações push
- **BouncyCastle** - Criptografia VAPID

### Utilities
- **Lombok** - Redução de boilerplate
- **MapStruct** - Mapeamento de objetos
- **Jackson** - Processamento JSON

### Testing
- **JUnit 5** - Framework de testes
- **Mockito** - Mocks
- **Spring Boot Test** - Testes de integração
- **H2 Database** - Banco em memória para testes

## 📋 Pré-requisitos

- Java 21 ou superior
- Maven 3.8+
- MySQL 8.0+
- Git

## ⚙️ Instalação e Configuração

### 1. Clone o Repositório

```bash
git clone https://github.com/your-repo/vigiaenchente.git
cd vigiaenchente
```

### 2. Configure o Banco de Dados

```bash
# Execute os scripts SQL
mysql -u root -p < database-schema.sql
mysql -u root -p < sample-data.sql
```

### 3. Configure as Variáveis de Ambiente

```bash
# Copie o template
cp .env.example .env

# Edite com seus valores
nano .env
```

### 4. Compile o Projeto

```bash
mvn clean install
```

### 5. Execute a Aplicação

```bash
# Modo desenvolvimento
mvn spring-boot:run

# Ou com profile específico
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 🔧 Configuração das APIs Externas

### OpenWeather API
1. Crie uma conta em [OpenWeather](https://openweathermap.org/api)
2. Obtenha sua API key
3. Configure `OPENWEATHER_API_KEY` no `.env`

### News API
1. Crie uma conta em [NewsAPI](https://newsapi.org/)
2. Obtenha sua API key
3. Configure `NEWS_API_KEY` no `.env`

### IPInfo API
1. Crie uma conta em [IPInfo](https://ipinfo.io/)
2. Obtenha seu token
3. Configure `IPINFO_TOKEN` no `.env`

### VAPID Keys (Push Notifications)
```bash
# Instale o web-push globalmente
npm install -g web-push

# Gere as chaves VAPID
npx web-push generate-vapid-keys

# Configure PUBLIC_VAPID_KEY e PRIVATE_VAPID_KEY no .env
```

## 📡 Endpoints da API

### Autenticação

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "usuario@example.com",
  "senha": "password123"
}
```

```http
POST /api/auth/register
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@example.com",
  "phone": "11987654321",
  "senha": "password123"
}
```

```http
POST /api/auth/logout
```

### Usuário

```http
GET /api/user/profile
```

```http
POST /api/user/address
Content-Type: application/json

{
  "street": "Rua das Flores",
  "num": "123",
  "cep": "30180000",
  "neighbor": "Centro",
  "city": "Belo Horizonte"
}
```

### Analytics

```http
GET /api/analytics/risk?latitude=-19.8949&longitude=-43.8148
```

### Notificações

```http
POST /api/notifications/subscribe
Content-Type: application/json

{
  "subscription": {
    "endpoint": "https://...",
    "keys": {
      "p256dh": "...",
      "auth": "..."
    }
  },
  "payload": {
    "title": "VigiaEnchente",
    "body": "Alerta de risco médio"
  }
}
```

## 🧪 Testes

### Executar Todos os Testes

```bash
mvn test
```

### Executar Testes de Integração

```bash
mvn verify
```

### Cobertura de Código

```bash
mvn jacoco:report
```

## 📊 Monitoramento

### Actuator Endpoints

```http
GET /actuator/health
GET /actuator/metrics
GET /actuator/prometheus
```

## 🔒 Segurança

- Senhas criptografadas com BCrypt
- Session management com Spring Session
- CORS configurado
- HTTPS recomendado em produção
- SQL injection prevention via JPA
- XSS protection

## 🐳 Docker

### Build da Imagem

```bash
docker build -t vigiaenchente:latest .
```

### Docker Compose

```bash
docker-compose up -d
```

## 📦 Deploy

### Build para Produção

```bash
mvn clean package -Pprod
```

### Executar JAR

```bash
java -jar target/vigiaenchente-api-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=prod
```

## 📈 Estrutura de Módulos Detalhada

### vigiaenchente-core
- Entidades de domínio
- Value Objects
- Exceções customizadas
- Utilitários compartilhados

### vigiaenchente-api
- Controllers REST
- Services de negócio
- Repositories
- Security configuration
- Session management

### vigiaenchente-collector
- Clientes para APIs externas
- Services de coleta de dados
- DTOs de resposta
- Mapeadores

### vigiaenchente-analytics
- Algoritmos de análise de risco
- Cálculos estatísticos
- Predições e tendências

### vigiaenchente-notifier
- Push notifications
- Email notifications (futuro)
- SMS notifications (futuro)
- Scheduler de notificações

## 🛠️ Próximos Passos

- [ ] Implementar testes unitários completos
- [ ] Adicionar métricas com Micrometer
- [ ] Implementar API Gateway
- [ ] Adicionar Circuit Breaker (Resilience4j)
- [ ] Implementar Redis para cache distribuído
- [ ] Adicionar documentação OpenAPI/Swagger
- [ ] Implementar notificações por email
- [ ] Implementar notificações por SMS
- [ ] Adicionar integração com WhatsApp
- [ ] Implementar Machine Learning para predições

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT.

## 👥 Autores

- **Seu Nome** - *Trabalho Inicial* - [GitHub](https://github.com/yourusername)

## 🙏 Agradecimentos

- Spring Boot Team
- OpenWeather API
- Open-Meteo Project
- Comunidade Java/Spring
