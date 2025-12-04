# Configuração CORS - Serviço PIX

Este documento descreve a configuração CORS (Cross-Origin Resource Sharing) implementada no serviço PIX.

## Configuração Implementada

### Classe de Configuração
- **Arquivo**: `src/main/java/com/remediar/pix_microservice/Config/CorsConfig.java`
- **Funcionalidade**: Configuração global de CORS para todos os endpoints do serviço

### Propriedades Configuráveis

As seguintes propriedades podem ser configuradas nos arquivos de propriedades:

```properties
# Origens permitidas (atualmente permitindo todas as origens)
cors.allowed-origins=*

# Métodos HTTP permitidos
cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS

# Headers permitidos
cors.allowed-headers=*

# Permite credenciais (cookies, headers de autorização)
cors.allow-credentials=true

# Tempo de cache para preflight requests (em segundos)
cors.max-age=3600
```

## Ambientes

### Desenvolvimento (`application-dev.properties`)
- Configurações permissivas para facilitar o desenvolvimento
- **Permite todas as origens** (`*`)
- Logging detalhado habilitado
- Métodos HTTP expandidos incluindo HEAD, TRACE, CONNECT

### Produção (`application-prod.properties`)
- **Permite todas as origens** (`*`)
- Métodos HTTP básicos (GET, POST, PUT, DELETE, OPTIONS)
- Headers permitidos: todos (`*`)

## ⚠️ Configuração Atual

**ATENÇÃO**: A configuração atual permite **todas as origens** (`*`) em todos os ambientes. Isso é útil para desenvolvimento e testes, mas deve ser revisado para produção.

### Configuração Atual
```properties
cors.allowed-origins=*
cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
cors.allowed-headers=*
cors.allow-credentials=true
cors.max-age=3600
```

## Como Usar

### Para Desenvolvimento
```bash
# Executar com perfil de desenvolvimento
java -jar pix-microservice.jar --spring.profiles.active=dev
```

### Para Produção
```bash
# Executar com perfil de produção
java -jar pix-microservice.jar --spring.profiles.active=prod
```

### Configuração Personalizada
Para configurar origens específicas no futuro, edite o arquivo de propriedades correspondente:

```properties
# Exemplo: configurar origens específicas
cors.allowed-origins=http://localhost:3000,http://meuapp.com,https://app.remediar.com.br
```

## Endpoints Afetados

A configuração CORS se aplica a todos os endpoints do serviço:
- `POST /pix/gerar` - Geração de QR Code PIX
- `POST /pix/confirmar` - Confirmação de pagamento

## Segurança

### ⚠️ Considerações Importantes

**Configuração Atual**: Permite todas as origens (`*`)
- ✅ **Vantagem**: Facilita desenvolvimento e testes
- ❌ **Desvantagem**: Menos seguro para produção

### Recomendações para Produção (Futuro)
Quando estiver pronto para produção, considere:

1. **Configure origens específicas**: Substitua `*` por domínios específicos
2. **Limite os headers**: Configure apenas os headers necessários
3. **Use HTTPS**: Sempre use HTTPS em produção
4. **Monitore logs**: Acompanhe logs de requisições CORS

### Exemplo de Configuração Segura (Futuro)
```properties
cors.allowed-origins=https://app.remediar.com.br,https://admin.remediar.com.br
cors.allowed-methods=GET,POST,OPTIONS
cors.allowed-headers=Content-Type,Authorization
cors.allow-credentials=true
cors.max-age=3600
```

## Troubleshooting

### Problemas Comuns

1. **Erro de CORS no navegador**
   - Com a configuração atual (`*`), isso não deve acontecer
   - Se acontecer, verifique se o serviço está rodando

2. **Preflight requests falhando**
   - Verifique se `OPTIONS` está em `allowed-methods`
   - Confirme se os headers estão corretos

3. **Credenciais não sendo enviadas**
   - Certifique-se de que `allow-credentials=true`
   - Com `*` como origem, credenciais podem não funcionar em alguns navegadores

### Logs de Debug
Para habilitar logs detalhados de CORS:
```properties
logging.level.org.springframework.web=DEBUG
logging.level.com.remediar=DEBUG
```

## Atualizações

Para atualizar a configuração CORS:
1. Modifique o arquivo de propriedades correspondente
2. Reinicie o serviço
3. Teste a conectividade

**Nota**: As configurações CORS são aplicadas em tempo de inicialização, então é necessário reiniciar o serviço para aplicar mudanças.

## URLs de Teste

Para testar a conectividade CORS, você pode usar:

- **Desenvolvimento**: http://localhost:8083/pix/gerar
- **Produção**: http://200.97.247.84:8083/pix/gerar (se o serviço estiver exposto)

**Importante**: Com a configuração atual (`*`), qualquer origem pode acessar o serviço. 