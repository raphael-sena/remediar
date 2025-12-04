# Remediar 💊

> A comprehensive web platform for managing medication donations and distribution for Solidarity Pharmacy NGO

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Next.js](https://img.shields.io/badge/Next.js-15.2-black.svg)](https://nextjs.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://www.docker.com/)

## 📋 Table of Contents

- [About](#-about)
- [Features](#-features)
- [Architecture](#-architecture)
- [Technologies](#-technologies)
- [Getting Started](#-getting-started)
- [Installation](#-installation)
- [Usage](#-usage)
- [API Documentation](#-api-documentation)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [Team](#-team)
- [Contributing](#-contributing)
- [License](#-license)

## 🎯 About

Remediar is a software development project aimed at creating a web system to optimize the management of Solidarity Pharmacy, an initiative that works in the collection and distribution of medication donations. The platform is designed to facilitate inventory control, ensuring that available medications are properly registered, monitored, and distributed. Additionally, the system manages both medication and financial donations, providing better organization for the NGO.

### Objectives

- 📦 **Inventory Management**: Efficient control of medication stock with real-time tracking
- 🤝 **Donation Management**: Streamlined process for both medication and financial donations
- 📊 **Analytics Dashboard**: Insights into donation patterns, expiration dates, and distribution
- 🔔 **Notifications**: Automated alerts for low stock, expiring medications, and pending requests
- 💳 **PIX Integration**: Secure payment processing for financial donations
- 📱 **WhatsApp Integration**: Direct communication with donors and beneficiaries
- 🔐 **Authentication & Authorization**: Secure access control with JWT tokens

### Social Impact

By improving the NGO's logistics, we aim to expand the social impact of the initiative, ensuring that medications reach those who need them most.

🔗 [Figma Prototype](https://www.figma.com/design/T2m5Cd3uWYrt4HE5F9UVDP/Remediar?node-id=0-1&t=4BHvcLfcryjjUWtI-1)

## ✨ Features

### For Employees/Administrators

- ✅ Complete inventory management (CRUD operations)
- ✅ Donation request approval workflow
- ✅ Medication request processing
- ✅ Dashboard with analytics and metrics
- ✅ Expiring medication alerts
- ✅ User management
- ✅ Report generation (Excel export)

### For Donors

- ✅ Register medication donations
- ✅ Financial donations via PIX
- ✅ Track donation history
- ✅ Real-time notifications

### For Beneficiaries

- ✅ Request medications
- ✅ Track request status
- ✅ View available medications
- ✅ Receive notifications

## 🏗️ Architecture

### System Architecture

```
┌─────────────────┐
│   Frontend      │
│   (Next.js)     │
└────────┬────────┘
         │
         │ HTTPS
         │
┌────────▼────────┐
│     Nginx       │
│  (Reverse Proxy)│
└────────┬────────┘
         │
         │
┌────────▼────────┐
│   API Gateway   │
│  (Spring Boot)  │
└────┬───┬───┬────┘
     │   │   │
     │   │   └──────────────┐
     │   │                  │
┌────▼───▼────┐  ┌─────────▼──────┐  ┌──────────────┐
│ Main API    │  │ Notification   │  │ PIX Service  │
│ (Spring)    │  │ Service        │  │ (Spring)     │
└──────┬──────┘  └────────┬───────┘  └─────┬────────┘
       │                  │                │
       │         ┌────────▼──────┐         │
       │         │   RabbitMQ    │         │
       │         │  (Messaging)  │         │
       │         └───────────────┘         │
       │                                   │
┌──────▼──────┐  ┌────────────┐  ┌─────────▼──────┐
│ PostgreSQL  │  │ PostgreSQL │  │   PostgreSQL   │
│  (Main DB)  │  │ (Notif DB) │  │    (PIX DB)    │
└─────────────┘  └────────────┘  └────────────────┘
       │
┌──────▼──────┐
│   Redis     │
│  (Cache)    │
└─────────────┘
```

### Microservices

1. **API Gateway (Port 8080)**: Routes requests to appropriate microservices
2. **Main Backend (Port 8081)**: Core business logic, inventory, and user management
3. **Notification Service (Port 8082)**: Handles email notifications via RabbitMQ
4. **PIX Microservice (Port 8083)**: Manages financial donations and payment processing
5. **Frontend (Port 3000)**: Next.js web application

## 🛠️ Technologies

### Backend

- **Language**: Java 21
- **Framework**: Spring Boot 3.4.3
- **Security**: Spring Security + JWT
- **Database**: PostgreSQL 16
- **Cache**: Redis 7
- **Messaging**: RabbitMQ 3.7.8
- **ORM**: Spring Data JPA with Hibernate
- **API Documentation**: SpringDoc OpenAPI 2.8.6
- **Object Mapping**: MapStruct 1.5.3
- **Build Tool**: Maven 3.6+
- **Containerization**: Docker & Docker Compose

### Frontend

- **Framework**: Next.js 15.2 with React 19
- **Language**: TypeScript 5.8
- **Styling**: Tailwind CSS 4.0
- **UI Components**: Radix UI, shadcn/ui
- **Forms**: React Hook Form + Zod validation
- **Charts**: Chart.js, Recharts
- **State Management**: Zustand
- **HTTP Client**: Axios
- **Testing**: Jest + React Testing Library

### Infrastructure

- **Proxy**: Nginx
- **Database Admin**: pgAdmin 4
- **Monitoring**: Docker stats, Uptime monitoring
- **SSL**: Let's Encrypt (Certbot)

## 🚀 Getting Started

### Prerequisites

- **Java**: JDK 21 or higher
- **Node.js**: 18+ (with npm)
- **Docker**: Latest version with Docker Compose
- **Git**: For version control
- **PostgreSQL**: 16+ (if running locally without Docker)

### System Requirements

- **RAM**: 8GB minimum, 16GB recommended
- **Storage**: 10GB free space
- **OS**: Windows 10/11, macOS, or Linux

## 📥 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/raphael-sena/remediar.git
cd remediar
```

### 2. Backend Setup

#### Using Docker (Recommended)

```bash
cd Codigo/back-remediar

# Create .env file with your configurations
cp .env.example .env

# Edit .env with your credentials
# POSTGRES_USER=your_user
# POSTGRES_PASSWORD=your_password
# PGADMIN_DEFAULT_EMAIL=your_email
# PGADMIN_DEFAULT_PASSWORD=your_password

# Start all services
docker-compose up -d

# Check services status
docker-compose ps
```

#### Local Development (Without Docker)

```bash
cd Codigo/back-remediar/back-remediar

# Configure application.properties with your database
# Install dependencies and build
mvn clean install

# Run the application
mvn spring-boot:run
```

### 3. Frontend Setup

```bash
cd Codigo/front-remediar

# Install dependencies
npm install

# Create .env.local file
echo "NEXT_PUBLIC_API_URL=http://localhost:8080/api" > .env.local
echo "NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api" >> .env.local
echo "NEXT_PUBLIC_BASE_URL=http://localhost:3000" >> .env.local

# Run development server
npm run dev
```

## 💻 Usage

### Access the Application

After starting all services:

- **Frontend**: http://localhost:3000
- **API Gateway**: http://localhost:8080
- **Main Backend**: http://localhost:8081
- **Notification Service**: http://localhost:8082
- **PIX Service**: http://localhost:8083
- **pgAdmin**: http://localhost:15433
- **RabbitMQ Management**: http://localhost:15672

### Default Credentials

**pgAdmin:**
- Email: As configured in .env
- Password: As configured in .env

**RabbitMQ:**
- Username: `rabbitmq`
- Password: `rabbitmq`

### Available Scripts

#### Backend

```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report

# Build production jar
mvn clean package -DskipTests

# Run specific microservice
cd back-remediar && mvn spring-boot:run
cd notification-service && mvn spring-boot:run
cd pix-microservice && mvn spring-boot:run
cd remediar-gateway && mvn spring-boot:run
```

#### Frontend

```bash
# Development server
npm run dev

# Build for production
npm run build

# Start production server
npm start

# Run tests
npm test

# Run tests with coverage
npm run test:coverage

# Lint code
npm run lint
```

## 📚 API Documentation

### Interactive API Documentation

Once the backend is running, access the Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

### Main Endpoints

#### Authentication

- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/forgot-password` - Request password reset

#### Medications

- `GET /api/medicamentos` - List all medications
- `GET /api/medicamentos/{id}` - Get medication by ID
- `POST /api/medicamentos` - Create new medication
- `PUT /api/medicamentos/{id}` - Update medication
- `DELETE /api/medicamentos/{id}` - Delete medication
- `GET /api/medicamentos/pesquisar/principioAtivoOrNomeComercial/{prefix}` - Search medications

#### Inventory

- `GET /api/estoque` - List inventory
- `GET /api/estoque/{id}` - Get inventory item
- `POST /api/estoque` - Create inventory item
- `PUT /api/estoque/{id}` - Update inventory item
- `DELETE /api/estoque/{id}` - Delete inventory item

#### Requests/Donations

- `GET /api/solicitacao/pedidos` - List medication requests
- `GET /api/solicitacao/doacoes` - List donations
- `POST /api/solicitacao/pedidos` - Create medication request
- `POST /api/solicitacao/doacoes` - Register donation
- `GET /api/solicitacao/{id}` - Get request details

#### Dashboard

- `GET /api/dashboard/medicamentos-mais-doados` - Most donated medications
- `GET /api/dashboard/total-medicamentos-doados` - Total donated medications
- `GET /api/dashboard/total-medicamentos-vencidos` - Expired medications count
- `GET /api/dashboard/total-solicitacoes-nao-atendidas` - Unattended requests
- `GET /api/dashboard/solicitacoes-por-faixa-etaria` - Requests by age group

### Postman Collection

Import the Postman collection for testing:

```bash
Codigo/requests/remediar.postman_collection.json
```

## 🧪 Testing

### Backend Testing

The backend includes comprehensive unit and integration tests:

```bash
# Run all backend tests
cd Codigo/back-remediar/back-remediar
mvn test

# Run specific test class
mvn test -Dtest=AuthenticationControllerTest

# Run with coverage report
mvn test jacoco:report
# View report at: target/site/jacoco/index.html
```

**Test Coverage:**
- Controllers: Authentication, Solicitation, Inventory, Medication
- Services: Business logic validation
- Integration: End-to-end flow testing

### Frontend Testing

```bash
# Run all frontend tests
cd Codigo/front-remediar
npm test

# Run in watch mode
npm run test:watch

# Generate coverage report
npm run test:coverage
# View report at: coverage/lcov-report/index.html
```

**Test Coverage:**
- Components: UI components, forms
- Pages: Login, dashboard, registration
- Contexts: Authentication, data management
- Hooks: Custom React hooks

### Automated Testing

Run all tests across the project:

```bash
# Windows PowerShell
.\run-tests.ps1

# This script runs:
# 1. Backend unit tests
# 2. Backend integration tests
# 3. Frontend unit tests
# 4. Generates coverage reports
```

For detailed testing documentation, see:
- [Backend Testing Guide](Codigo/back-remediar/back-remediar/TESTES_README.md)
- [Frontend Testing Guide](Codigo/front-remediar/TESTES_README.md)
- [Testing Summary](RESUMO_TESTES.md)

## 🌐 Deployment

### Docker Production Deployment

```bash
# Build and start all services
docker-compose up -d --build

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Stop and remove volumes (clean state)
docker-compose down -v
```

### Global Server Setup

Transform your local machine into a globally accessible server:

1. **Configure Port Forwarding** on your router (ports 80, 443, 8080)
2. **Set Static IP** for your machine
3. **Configure Dynamic DNS** (DuckDNS, No-IP)
4. **Setup SSL/HTTPS** with Let's Encrypt

For detailed instructions, see: [Global Server Setup Guide](Codigo/back-remediar/README-SERVIDOR-GLOBAL.md)

```bash
# Quick setup script (Windows)
cd Codigo/back-remediar
.\setup-global-server.ps1

# Test global access
.\test-global-access.ps1
```

### Cloud Deployment

For production environments, consider:

- **AWS**: EC2, RDS, ECS
- **Google Cloud**: Compute Engine, Cloud SQL
- **Azure**: Virtual Machines, Azure Database
- **Heroku**: Container deployment
- **DigitalOcean**: Droplets with Docker

### Environment Variables

Create appropriate `.env` files for production:

```env
# Database
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_secure_password

# pgAdmin
PGADMIN_DEFAULT_EMAIL=admin@remediar.com
PGADMIN_DEFAULT_PASSWORD=your_secure_password

# Application
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=your_jwt_secret_key

# Frontend
NEXT_PUBLIC_API_URL=https://your-domain.com/api
NEXT_PUBLIC_API_BASE_URL=https://your-domain.com/api
NEXT_PUBLIC_BASE_URL=https://your-domain.com
```

## 👥 Team

### Development Team

- **André Augusto Silva Carvalho**
- **Raphael Sena Augusto de Brito**
- **Gabriel Matos Martins Fialho da Silva**
- **Yan Mariz Magalhães Cota**
- **Miguel Pedrosa do Carmo Nonato**
- **Isaac Portela da Silva**

### Academic Advisors

- **Prof. Joyce Christina de Paiva Carvalho**
- **Prof. Soraia Lúcia da Silva**

## 🤝 Contributing

We welcome contributions! Please follow these guidelines:

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/amazing-feature`
3. **Commit your changes**: `git commit -m 'Add amazing feature'`
4. **Push to the branch**: `git push origin feature/amazing-feature`
5. **Open a Pull Request**

### Code Standards

- Follow existing code style and conventions
- Write tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting PR
- Keep commits atomic and meaningful

### Running Code Quality Checks

```bash
# Backend
mvn checkstyle:check
mvn test

# Frontend
npm run lint
npm test
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


## 🙏 Acknowledgments

- PUC Minas - Pontifícia Universidade Católica de Minas Gerais
- ONG Remediar
- All contributors and supporters of this project

---

**Built with ❤️ for social impact**

*Ensuring medications reach those who need them most*
