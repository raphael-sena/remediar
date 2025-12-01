# 🌍 Guia Completo: Configurando Roteador para Servidor Global

Este guia explica como configurar seu roteador para permitir acesso global à sua aplicação.

## 🔧 **Passo 1: Configurar Port Forwarding**

### **O que é Port Forwarding?**
Port Forwarding permite que conexões externas cheguem até sua máquina através do roteador.

### **Portas que precisam ser abertas:**
- **Porta 80** (HTTP) - Frontend principal
- **Porta 443** (HTTPS) - Frontend seguro
- **Porta 8080** (API) - API Gateway
- **Porta 3000** (Frontend direto) - Opcional
- **Porta 15433** (PgAdmin) - Opcional

---

## 📋 **Passo 2: Acessar o Roteador**

### **Como encontrar o IP do roteador:**
```powershell
# No PowerShell
ipconfig
```
Procure por "Default Gateway" (geralmente 192.168.1.1 ou 192.168.0.1)

### **Acessar a interface do roteador:**
1. Abra o navegador
2. Digite o IP do roteador (ex: http://192.168.1.1)
3. Digite usuário e senha (verifique na etiqueta do roteador)

---

## 🎯 **Passo 3: Configurar Port Forwarding**

### **Para Roteadores Comuns:**

#### **TP-Link:**
1. Vá para **Advanced** → **NAT Forwarding** → **Virtual Servers**
2. Clique em **Add New**
3. Configure cada porta:

```
Porta 80:
- Service Port: 80
- Internal Port: 80
- IP Address: [IP da sua máquina]
- Protocol: TCP
- Status: Enabled

Porta 443:
- Service Port: 443
- Internal Port: 443
- IP Address: [IP da sua máquina]
- Protocol: TCP
- Status: Enabled

Porta 8080:
- Service Port: 8080
- Internal Port: 8080
- IP Address: [IP da sua máquina]
- Protocol: TCP
- Status: Enabled
```

#### **D-Link:**
1. Vá para **Advanced** → **Port Forwarding**
2. Configure cada regra:

```
Nome: Remediar-HTTP
Protocolo: TCP
Porta Externa: 80
Porta Interna: 80
IP Interno: [IP da sua máquina]

Nome: Remediar-HTTPS
Protocolo: TCP
Porta Externa: 443
Porta Interna: 443
IP Interno: [IP da sua máquina]

Nome: Remediar-API
Protocolo: TCP
Porta Externa: 8080
Porta Interna: 8080
IP Interno: [IP da sua máquina]
```

#### **Netgear:**
1. Vá para **Advanced** → **Port Forwarding**
2. Configure cada porta:

```
Nome: Remediar Web
Porta Externa: 80
Porta Interna: 80
IP Interno: [IP da sua máquina]

Nome: Remediar Secure
Porta Externa: 443
Porta Interna: 443
IP Interno: [IP da sua máquina]

Nome: Remediar API
Porta Externa: 8080
Porta Interna: 8080
IP Interno: [IP da sua máquina]
```

#### **ASUS:**
1. Vá para **WAN** → **Virtual Server / Port Forwarding**
2. Configure cada regra:

```
Nome: Remediar-HTTP
Protocolo: TCP
Porta Externa: 80
Porta Interna: 80
IP Interno: [IP da sua máquina]

Nome: Remediar-HTTPS
Protocolo: TCP
Porta Externa: 443
Porta Interna: 443
IP Interno: [IP da sua máquina]

Nome: Remediar-API
Protocolo: TCP
Porta Externa: 8080
Porta Interna: 8080
IP Interno: [IP da sua máquina]
```

---

## 🌐 **Passo 4: Configurar DNS Dinâmico (Opcional)**

### **Por que usar DNS Dinâmico?**
- Seu IP público pode mudar
- DNS dinâmico mantém um domínio sempre apontando para seu IP

### **Serviços Gratuitos:**

#### **1. No-IP (Recomendado):**
1. Acesse [noip.com](https://www.noip.com)
2. Crie uma conta gratuita
3. Escolha um hostname (ex: remediar.ddns.net)
4. Baixe o cliente DUC
5. Configure no roteador ou no cliente

#### **2. DuckDNS:**
1. Acesse [duckdns.org](https://www.duckdns.org)
2. Crie uma conta
3. Crie um subdomínio (ex: remediar)
4. URL será: https://remediar.duckdns.org

#### **3. FreeDNS:**
1. Acesse [freedns.afraid.org](https://freedns.afraid.org)
2. Crie uma conta
3. Configure um subdomínio

### **Configurar no Roteador:**
1. Vá para **WAN** → **DDNS**
2. Selecione o provedor (No-IP, DuckDNS, etc.)
3. Digite usuário e senha
4. Digite o hostname
5. Salve e teste

---

## 🔒 **Passo 5: Configurar IP Fixo na Máquina**

### **Por que IP fixo?**
- Evita que o IP da máquina mude
- Mantém o port forwarding funcionando

### **Configurar no Windows:**
1. Abra **Configurações de Rede**
2. Clique em **Alterar opções do adaptador**
3. Clique com botão direito na rede → **Propriedades**
4. Selecione **Protocolo IP versão 4** → **Propriedades**
5. Selecione **Usar o seguinte endereço IP**
6. Configure:
   ```
   Endereço IP: 192.168.1.100 (ou outro IP livre)
   Máscara de sub-rede: 255.255.255.0
   Gateway padrão: [IP do roteador]
   DNS: 8.8.8.8, 8.8.4.4
   ```

---

## 🧪 **Passo 6: Testar a Configuração**

### **1. Testar localmente:**
```powershell
# Testar se a aplicação está rodando
curl http://localhost
curl http://localhost/api
```

### **2. Testar externamente:**
```powershell
# Obter IP público
curl https://api.ipify.org

# Testar acesso (de outro dispositivo/rede)
curl http://[SEU_IP_PUBLICO]
curl http://[SEU_IP_PUBLICO]/api
```

### **3. Testar com ferramentas online:**
- [canyouseeme.org](https://canyouseeme.org) - Testa se as portas estão abertas
- [whatismyipaddress.com](https://whatismyipaddress.com) - Mostra seu IP público

---

## ⚠️ **Considerações de Segurança**

### **1. Firewall do Roteador:**
- Mantenha o firewall ativado
- Abra apenas as portas necessárias
- Use senhas fortes no roteador

### **2. Segurança da Aplicação:**
- Configure SSL/HTTPS
- Use autenticação adequada
- Mantenha a aplicação atualizada

### **3. Monitoramento:**
- Configure alertas de segurança
- Monitore logs de acesso
- Use ferramentas de monitoramento

---

## 🔧 **Solução de Problemas**

### **Problema: Não consigo acessar externamente**

**Soluções:**
1. Verifique se o port forwarding está configurado
2. Verifique se o IP da máquina está correto
3. Verifique se a aplicação está rodando
4. Teste com `canyouseeme.org`

### **Problema: IP público mudou**

**Soluções:**
1. Configure DNS dinâmico
2. Atualize as configurações de DNS
3. Configure alertas para mudanças de IP

### **Problema: Conexão lenta**

**Soluções:**
1. Verifique a velocidade da internet
2. Configure QoS no roteador
3. Otimize a aplicação

---

## 📞 **Suporte por Marca de Roteador**

### **Links de Suporte:**
- **TP-Link**: [tp-link.com/support](https://www.tp-link.com/support/)
- **D-Link**: [dlink.com/support](https://support.dlink.com/)
- **Netgear**: [netgear.com/support](https://www.netgear.com/support/)
- **ASUS**: [asus.com/support](https://www.asus.com/support/)

### **Modelos Específicos:**
Se você tiver um modelo específico, posso fornecer instruções detalhadas.

---

## ✅ **Checklist Final**

- [ ] Port forwarding configurado (portas 80, 443, 8080)
- [ ] IP fixo configurado na máquina
- [ ] DNS dinâmico configurado (opcional)
- [ ] Firewall do Windows configurado
- [ ] Aplicação rodando e testada localmente
- [ ] Acesso externo testado
- [ ] SSL/HTTPS configurado (recomendado)
- [ ] Monitoramento configurado

**Após completar todos os passos, sua máquina estará pronta para servir a aplicação globalmente!** 