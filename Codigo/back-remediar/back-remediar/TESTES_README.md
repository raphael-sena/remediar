# Testes Unitários e de Integração - Backend Remediar

Este documento descreve os testes implementados para o backend da aplicação Remediar.

## Estrutura dos Testes

### Testes Unitários

Os testes unitários estão localizados em `src/test/java/com/remediar/back_remediar/` e seguem a estrutura:

```
src/test/java/com/remediar/back_remediar/
├── controller/
│   └── AuthenticationControllerTest.java
├── service/
│   └── SolicitacaoServiceTest.java
└── integration/
    └── AuthenticationIntegrationTest.java
```

### Testes Implementados

#### 1. AuthenticationControllerTest
**Localização**: `controller/AuthenticationControllerTest.java`

**Funcionalidades testadas**:
- Login com credenciais válidas
- Login com credenciais inválidas
- Registro de usuário com dados válidos
- Registro de usuário com email já existente
- Registro de usuário com dados inválidos

**Cobertura**:
- Validação de entrada
- Respostas HTTP corretas
- Interação com serviços dependentes
- Tratamento de erros

#### 2. SolicitacaoServiceTest
**Localização**: `service/SolicitacaoServiceTest.java`

**Funcionalidades testadas**:
- Busca de solicitação por ID
- Listagem de pedidos paginados
- Listagem de doações paginadas
- Atualização de status de solicitação
- Aprovação de solicitação
- Cancelamento de solicitação

**Cobertura**:
- Operações CRUD básicas
- Validação de dados
- Tratamento de exceções
- Interação com repositórios

#### 3. AuthenticationIntegrationTest
**Localização**: `integration/AuthenticationIntegrationTest.java`

**Funcionalidades testadas**:
- Fluxo completo de registro e login
- Validação de dados no banco
- Criptografia de senhas
- Geração de tokens JWT
- Diferentes roles de usuário

**Cobertura**:
- Integração com banco de dados
- Configuração do Spring Security
- Validação de transações
- Persistência de dados

## Como Executar os Testes

### Pré-requisitos
- Java 21
- Maven 3.6+
- Banco de dados configurado (H2 para testes)

### Executar Todos os Testes
```bash
cd Codigo/back-remediar/back-remediar
mvn test
```

### Executar Testes Específicos
```bash
# Executar apenas testes unitários
mvn test -Dtest="*Test"

# Executar apenas testes de integração
mvn test -Dtest="*IntegrationTest"

# Executar teste específico
mvn test -Dtest="AuthenticationControllerTest"

# Executar método específico
mvn test -Dtest="AuthenticationControllerTest#login_WithValidCredentials_ShouldReturnToken"
```

### Executar com Cobertura
```bash
mvn test jacoco:report
```

### Executar em Modo Debug
```bash
mvn test -Dmaven.surefire.debug
```

## Configuração de Teste

### Perfil de Teste
Os testes usam o perfil `test` que configura:
- Banco de dados H2 em memória
- Logging reduzido
- Configurações específicas para teste

### Configuração do application-test.properties
```properties
# Configurações específicas para testes
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
```

## Padrões de Teste

### Nomenclatura
- Classes: `{ClassName}Test`
- Métodos: `{methodName}_{condition}_{expectedResult}`

### Estrutura AAA (Arrange, Act, Assert)
```java
@Test
void methodName_WithCondition_ShouldReturnExpectedResult() {
    // Arrange - Preparar dados e mocks
    when(mock.method()).thenReturn(value);
    
    // Act - Executar método testado
    Result result = service.method(input);
    
    // Assert - Verificar resultado
    assertThat(result).isEqualTo(expected);
    verify(mock).method();
}
```

### Mocks
- Uso do Mockito para mocks
- `@Mock` para dependências
- `@InjectMocks` para classe testada
- `@ExtendWith(MockitoExtension.class)` para configuração

### Assertions
- JUnit 5 assertions
- AssertJ para assertions mais expressivas
- Verificação de exceções com `assertThrows`

## Cobertura de Testes

### Métricas Alvo
- **Cobertura de Linhas**: 80%+
- **Cobertura de Branches**: 70%+
- **Cobertura de Métodos**: 90%+

### Relatórios
Os relatórios de cobertura são gerados em:
- `target/site/jacoco/index.html` (Jacoco)
- Console durante execução

## Boas Práticas

### 1. Isolamento
- Cada teste deve ser independente
- Usar `@BeforeEach` para setup
- Limpar estado entre testes

### 2. Nomes Descritivos
- Nomes que descrevem o cenário
- Incluir condição e resultado esperado
- Usar padrão `should_` ou `when_`

### 3. Dados de Teste
- Usar dados realistas
- Evitar dados hardcoded
- Usar builders quando apropriado

### 4. Mocks
- Mock apenas dependências externas
- Verificar interações importantes
- Usar mocks apropriados para cada cenário

### 5. Assertions
- Uma assertion por teste quando possível
- Verificar comportamento, não implementação
- Usar assertions específicas

## Troubleshooting

### Problemas Comuns

#### 1. Testes Falhando por Configuração
```bash
# Verificar configuração do banco
mvn test -Dspring.profiles.active=test
```

#### 2. Timeout em Testes
```bash
# Aumentar timeout
mvn test -Dsurefire.timeout=300
```

#### 3. Problemas de Memória
```bash
# Aumentar heap
mvn test -Xmx1024m
```

### Logs de Debug
```bash
# Habilitar logs detalhados
mvn test -Dlogging.level.com.remediar=DEBUG
```

## Próximos Passos

### Testes Pendentes
- [ ] Testes para todos os controllers
- [ ] Testes para todos os services
- [ ] Testes de repository
- [ ] Testes de validação
- [ ] Testes de segurança

### Melhorias
- [ ] Adicionar testes de performance
- [ ] Implementar testes de contrato
- [ ] Adicionar testes de mutação
- [ ] Configurar CI/CD para testes

## Contribuição

Para adicionar novos testes:

1. Seguir a estrutura de nomenclatura
2. Usar padrão AAA
3. Adicionar documentação
4. Manter cobertura alta
5. Executar todos os testes antes do commit 