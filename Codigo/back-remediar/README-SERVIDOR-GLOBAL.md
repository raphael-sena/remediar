# 🌍 Transformando sua Máquina em Servidor Global

Este guia completo explica como transformar sua máquina local em um servidor global para a aplicação Remediar.

## 🎯 **O que você vai conseguir:**

- ✅ Aplicação acessível de qualquer lugar do mundo
- ✅ URLs profissionais com domínio próprio
- ✅ SSL/HTTPS para segurança
- ✅ Monitoramento e backup automático
- ✅ Configuração completa e segura

---

## 📋 **Pré-requisitos**

- Windows 10/11
- Docker Desktop instalado
- Acesso de administrador
- Roteador configurável
- Conexão de internet estável
- Domínio próprio (opcional, mas recomendado)

---

## 🚀 **Passo a Passo Completo**

### **Passo 1: Configurar a Máquina**

```powershell
# Execute como Administrador
cd "C:\workspace\plf-es-2025-1-ti4-1254100-remediar\Codigo\back-remediar"

# Executar script principal
.\setup-global-server.ps1
```

**O que este script faz:**
- Configura firewall para acesso global
- Obtém IP público automaticamente
- Cria arquivos de configuração
- Inicia todos os serviços
- Gera documentação de configuração

### **Passo 2: Configurar o Roteador**

**Importante:** Este é o passo mais crítico!

1. **Acesse seu roteador:**
   - Abra o navegador
   - Digite o IP do roteador (geralmente 192.168.1.1)
   - Use as credenciais da etiqueta do roteador

2. **Configure Port Forwarding:**
   - Vá para **Advanced** → **Port Forwarding** (ou similar)
   - Adicione as seguintes regras:

   ```
   Nome: Remediar-HTTP
   Porta Externa: 80
   Porta Interna: 80
   IP Interno: [IP da sua máquina]
   Protocolo: TCP

   Nome: Remediar-HTTPS
   Porta Externa: 443
   Porta Interna: 443
   IP Interno: [IP da sua máquina]
   Protocolo: TCP

   Nome: Remediar-API
   Porta Externa: 8080
   Porta Interna: 8080
   IP Interno: [IP da sua máquina]
   Protocolo: TCP
   ```

3. **Configure IP Fixo na Máquina:**
   - Abra **Configurações de Rede**
   - Configure IP fixo (ex: 192.168.1.100)
   - Use o IP do roteador como gateway

### **Passo 3: Configurar Domínio (Recomendado)**

#### **Opção A: DNS Dinâmico (Gratuito)**
1. **No-IP** (mais popular):
   - Acesse [noip.com](https://www.noip.com)
   - Crie conta gratuita
   - Escolha hostname (ex: remediar.ddns.net)
   - Configure no roteador

2. **DuckDNS** (alternativa):
   - Acesse [duckdns.org](https://www.duckdns.org)
   - Crie conta
   - Crie subdomínio (ex: remediar)
   - URL: https://remediar.duckdns.org

#### **Opção B: Domínio Próprio (Pago)**
1. Compre um domínio (ex: remediar.com.br)
2. Configure DNS para apontar para seu IP
3. Configure SSL/HTTPS

### **Passo 4: Configurar SSL/HTTPS**

```powershell
# Instalar Certbot
# Baixe de: https://certbot.eff.org/

# Obter certificado SSL
certbot certonly --standalone -d remediar.seu-dominio.com

# Configurar renovação automática
# Adicione ao agendador de tarefas do Windows
```

### **Passo 5: Testar Acesso Global**

```powershell
# Execute o script de teste
.\test-global-access.ps1
```

**Este script testa:**
- Conectividade local
- Conectividade externa
- Status dos containers
- Configurações de firewall
- Portas abertas

---

## 🌐 **URLs de Acesso**

Após a configuração, sua aplicação estará disponível em:

### **Com IP Público:**
- **Frontend**: `http://[SEU_IP_PUBLICO]`
- **API**: `http://[SEU_IP_PUBLICO]/api`
- **PgAdmin**: `http://[SEU_IP_PUBLICO]:15433`

### **Com Domínio:**
- **Frontend**: `https://remediar.seu-dominio.com`
- **API**: `https://remediar.seu-dominio.com/api`
- **PgAdmin**: `https://pgadmin.remediar.seu-dominio.com`

---

## 🔧 **Configurações Avançadas**

### **Monitoramento**

1. **Uptime Robot** (gratuito):
   - Acesse [uptimerobot.com](https://uptimerobot.com)
   - Configure alertas para suas URLs
   - Receba notificações quando o servidor cair

2. **Monitoramento Local:**
   - Use o Task Manager para CPU/RAM
   - Configure alertas de disco
   - Monitore logs do Docker

### **Backup Automático**

```powershell
# Script de backup (criar arquivo backup.ps1)
$date = Get-Date -Format "yyyyMMdd_HHmmss"
docker exec db_remediar pg_dump -U remediar db_remediar > "backup_$date.sql"

# Configurar no Agendador de Tarefas do Windows
# Executar diariamente às 2h da manhã
```

### **Segurança**

1. **Firewall:**
   - Mantenha apenas as portas necessárias abertas
   - Use senhas fortes no roteador
   - Configure VPN se necessário

2. **Aplicação:**
   - Mantenha Docker atualizado
   - Configure autenticação adequada
   - Monitore logs de acesso

---

## 🔍 **Solução de Problemas**

### **Problema: Não consigo acessar externamente**

**Soluções:**
1. Verifique port forwarding no roteador
2. Teste com [canyouseeme.org](https://canyouseeme.org)
3. Verifique se a aplicação está rodando
4. Confirme IP fixo na máquina

### **Problema: Conexão lenta**

**Soluções:**
1. Verifique velocidade da internet
2. Configure QoS no roteador
3. Otimize a aplicação
4. Use CDN se necessário

### **Problema: IP mudou**

**Soluções:**
1. Configure DNS dinâmico
2. Configure alertas para mudanças de IP
3. Use serviço de IP fixo (pago)

---

## 📊 **Monitoramento e Manutenção**

### **Comandos Úteis:**

```powershell
# Ver status dos containers
docker-compose ps

# Ver logs em tempo real
docker-compose logs -f

# Reiniciar serviços
docker-compose restart

# Ver uso de recursos
docker stats

# Backup manual
docker exec db_remediar pg_dump -U remediar db_remediar > backup.sql
```

### **Agendamento de Tarefas:**

1. **Backup diário** (2h da manhã)
2. **Renovação SSL** (mensal)
3. **Limpeza de logs** (semanal)
4. **Atualização de containers** (quinzenal)

---

## ⚠️ **Considerações Importantes**

### **Limitações:**
- Depende da estabilidade da sua internet
- IP pode mudar (use DNS dinâmico)
- Recursos limitados da máquina
- Sem redundância (backup é essencial)

### **Recomendações:**
- Use UPS para estabilidade
- Configure monitoramento
- Faça backup regular
- Mantenha sistema atualizado
- Considere servidor na nuvem para produção

---

## 🎉 **Parabéns!**

Sua máquina agora é um servidor global! 

### **Checklist Final:**
- [ ] Aplicação rodando localmente
- [ ] Port forwarding configurado
- [ ] IP fixo configurado
- [ ] Domínio configurado (opcional)
- [ ] SSL/HTTPS configurado
- [ ] Monitoramento ativo
- [ ] Backup automático
- [ ] Testes de acesso externo

### **Próximos Passos:**
1. Compartilhe as URLs com usuários
2. Configure monitoramento
3. Configure backup automático
4. Monitore performance
5. Considere upgrade para servidor na nuvem

---

## 📞 **Suporte**

Se encontrar problemas:

1. Verifique os logs: `docker-compose logs`
2. Teste conectividade: `.\test-global-access.ps1`
3. Verifique configurações do roteador
4. Consulte a documentação específica do seu roteador

**Sua aplicação Remediar agora está disponível globalmente! 🌍** 