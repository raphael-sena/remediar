# Script para Transformar Máquina Local em Servidor Global
# Execute como Administrador

Write-Host "🌍 Transformando sua máquina em servidor global..." -ForegroundColor Green

# Verificar se está executando como administrador
$isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")

if (-not $isAdmin) {
    Write-Host "❌ Este script deve ser executado como Administrador!" -ForegroundColor Red
    Write-Host "Clique com o botão direito no PowerShell e selecione 'Executar como administrador'" -ForegroundColor Yellow
    exit 1
}

# 1. Configurar Firewall para acesso global
Write-Host "🔧 Configurando firewall para acesso global..." -ForegroundColor Yellow

# Remover regras existentes
netsh advfirewall firewall delete rule name="Remediar Global*" 2>$null

# Adicionar regras para acesso global
netsh advfirewall firewall add rule name="Remediar Global HTTP" dir=in action=allow protocol=TCP localport=80
netsh advfirewall firewall add rule name="Remediar Global HTTPS" dir=in action=allow protocol=TCP localport=443
netsh advfirewall firewall add rule name="Remediar Global API" dir=in action=allow protocol=TCP localport=8080
netsh advfirewall firewall add rule name="Remediar Global Frontend" dir=in action=allow protocol=TCP localport=3000
netsh advfirewall firewall add rule name="Remediar Global PgAdmin" dir=in action=allow protocol=TCP localport=15433

Write-Host "✅ Firewall configurado para acesso global" -ForegroundColor Green

# 2. Obter IP público
Write-Host "🌐 Obtendo informações de rede..." -ForegroundColor Yellow

try {
    $publicIP = (Invoke-WebRequest -Uri "https://api.ipify.org" -UseBasicParsing).Content
    Write-Host "✅ IP Público: $publicIP" -ForegroundColor Green
} catch {
    Write-Host "⚠️ Não foi possível obter o IP público automaticamente" -ForegroundColor Yellow
    $publicIP = "SEU_IP_PUBLICO"
}

# 3. Configurar DNS dinâmico (opcional)
Write-Host "📝 Configurando DNS dinâmico..." -ForegroundColor Yellow

$dnsConfig = @"
# Configuração DNS Dinâmico
# Opções gratuitas:
# 1. No-IP: https://www.noip.com/ (gratuito)
# 2. DuckDNS: https://www.duckdns.org/ (gratuito)
# 3. FreeDNS: https://freedns.afraid.org/ (gratuito)

# Exemplo para No-IP:
# Baixe o cliente DUC e configure:
# - Username: seu_usuario
# - Password: sua_senha
# - Hostname: remediar.ddns.net

# Exemplo para DuckDNS:
# 1. Crie uma conta em https://www.duckdns.org
# 2. Crie um subdomínio: remediar
# 3. URL será: https://remediar.duckdns.org
"@

$dnsConfig | Out-File -FilePath "$env:USERPROFILE\Desktop\DNS-Config.txt" -Encoding UTF8

# 4. Configurar SSL/HTTPS
Write-Host "🔒 Configurando SSL/HTTPS..." -ForegroundColor Yellow

# Criar script para configurar SSL
$sslScript = @"
# Script para configurar SSL com Let's Encrypt
# Execute este script após configurar o DNS

# 1. Instalar Certbot (se não estiver instalado)
# Baixe de: https://certbot.eff.org/

# 2. Obter certificado SSL
certbot certonly --standalone -d remediar.seu-dominio.com

# 3. Configurar renovação automática
# Adicione ao agendador de tarefas do Windows:
# certbot renew --quiet
"@

$sslScript | Out-File -FilePath "$env:USERPROFILE\Desktop\SSL-Setup.txt" -Encoding UTF8

# 5. Configurar Nginx para produção
Write-Host "🌐 Configurando Nginx para produção..." -ForegroundColor Yellow

$nginxConfig = @"
user  nginx;
worker_processes  auto;

error_log  /var/log/nginx/error.log warn;
pid        /var/run/nginx.pid;

events {
    worker_connections  1024;
}

http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;
    
    # Configurações de segurança
    server_tokens off;
    add_header X-Frame-Options DENY;
    add_header X-Content-Type-Options nosniff;
    add_header X-XSS-Protection "1; mode=block";
    
    # Configurações de performance
    sendfile        on;
    tcp_nopush      on;
    tcp_nodelay     on;
    keepalive_timeout  65;
    types_hash_max_size 2048;
    
    # Gzip compression
    gzip on;
    gzip_vary on;
    gzip_min_length 1024;
    gzip_types text/plain text/css text/xml text/javascript application/javascript application/xml+rss application/json;

    # Upstreams
    upstream api {
        server remediar-gateway:8080;
    }

    upstream frontend {
        server front-remediar:3000;
    }

    # Servidor HTTP (redireciona para HTTPS)
    server {
        listen       80;
        server_name  _;
        return 301 https://`$server_name`$request_uri;
    }

    # Servidor HTTPS
    server {
        listen       443 ssl http2;
        server_name  remediar.seu-dominio.com;

        # SSL Configuration
        ssl_certificate /etc/letsencrypt/live/remediar.seu-dominio.com/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/remediar.seu-dominio.com/privkey.pem;
        ssl_protocols TLSv1.2 TLSv1.3;
        ssl_ciphers ECDHE-RSA-AES256-GCM-SHA512:DHE-RSA-AES256-GCM-SHA512:ECDHE-RSA-AES256-GCM-SHA384:DHE-RSA-AES256-GCM-SHA384;
        ssl_prefer_server_ciphers off;

        # API Gateway
        location /api/ {
            proxy_pass         http://api/;
            proxy_http_version 1.1;
            proxy_set_header   Host              `$host;
            proxy_set_header   X-Real-IP         `$remote_addr;
            proxy_set_header   X-Forwarded-For   `$proxy_add_x_forwarded_for;
            proxy_set_header   X-Forwarded-Proto `$scheme;
        }

        # Frontend
        location / {
            proxy_pass         http://frontend/;
            proxy_http_version 1.1;
            proxy_set_header   Host              `$host;
            proxy_set_header   X-Real-IP         `$remote_addr;
            proxy_set_header   X-Forwarded-For   `$proxy_add_x_forwarded_for;
            proxy_set_header   X-Forwarded-Proto `$scheme;
        }
    }
}
"@

$nginxConfig | Out-File -FilePath "nginx-global.conf" -Encoding UTF8

# 6. Configurar monitoramento
Write-Host "📊 Configurando monitoramento..." -ForegroundColor Yellow

$monitoringScript = @"
# Script de monitoramento para servidor global

# 1. Instalar ferramentas de monitoramento
# - CPU: Task Manager ou Process Explorer
# - Rede: NetWorx ou GlassWire
# - Disco: CrystalDiskInfo

# 2. Configurar alertas
# - Uptime Robot: https://uptimerobot.com/ (gratuito)
# - Pingdom: https://pingdom.com/ (pago)

# 3. Logs importantes
# - Nginx: /var/log/nginx/
# - Docker: docker-compose logs
# - Sistema: Event Viewer
"@

$monitoringScript | Out-File -FilePath "$env:USERPROFILE\Desktop\Monitoring-Setup.txt" -Encoding UTF8

# 7. Configurar backup automático
Write-Host "💾 Configurando backup automático..." -ForegroundColor Yellow

$backupScript = @"
# Script de backup automático

# 1. Backup do banco de dados
docker exec db_remediar pg_dump -U remediar db_remediar > backup_$(Get-Date -Format 'yyyyMMdd_HHmmss').sql

# 2. Backup dos arquivos de configuração
Copy-Item docker-compose.yml backup/
Copy-Item nginx-global.conf backup/

# 3. Backup para nuvem (opcional)
# - Google Drive
# - OneDrive
# - Dropbox
# - AWS S3
"@

$backupScript | Out-File -FilePath "$env:USERPROFILE\Desktop\Backup-Setup.txt" -Encoding UTF8

# 8. Iniciar serviços
Write-Host "🚀 Iniciando serviços..." -ForegroundColor Yellow

docker-compose down
docker-compose build --no-cache
docker-compose up -d

# 9. Aguardar inicialização
Write-Host "⏳ Aguardando inicialização dos serviços..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# 10. Verificar status
Write-Host "📊 Status dos containers:" -ForegroundColor Green
docker-compose ps

# 11. Informações finais
Write-Host "`n🌍 SERVIDOR GLOBAL CONFIGURADO!" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "IP Público: $publicIP" -ForegroundColor Yellow
Write-Host "Frontend: http://$publicIP" -ForegroundColor Cyan
Write-Host "API: http://$publicIP/api" -ForegroundColor Cyan
Write-Host "PgAdmin: http://$publicIP:15433" -ForegroundColor Cyan

Write-Host "`n📋 PRÓXIMOS PASSOS:" -ForegroundColor Green
Write-Host "1. Configure um domínio (DNS dinâmico ou domínio próprio)" -ForegroundColor White
Write-Host "2. Configure SSL/HTTPS com Let's Encrypt" -ForegroundColor White
Write-Host "3. Configure monitoramento e alertas" -ForegroundColor White
Write-Host "4. Configure backup automático" -ForegroundColor White
Write-Host "5. Configure firewall do roteador (port forwarding)" -ForegroundColor White

Write-Host "`n⚠️ IMPORTANTE:" -ForegroundColor Yellow
Write-Host "- Configure port forwarding no seu roteador (portas 80, 443, 8080)" -ForegroundColor White
Write-Host "- Use um domínio para URLs mais profissionais" -ForegroundColor White
Write-Host "- Configure SSL para segurança" -ForegroundColor White
Write-Host "- Monitore o uso de recursos da máquina" -ForegroundColor White

Write-Host "`n📁 Arquivos de configuração criados na área de trabalho:" -ForegroundColor Green
Write-Host "- DNS-Config.txt" -ForegroundColor White
Write-Host "- SSL-Setup.txt" -ForegroundColor White
Write-Host "- Monitoring-Setup.txt" -ForegroundColor White
Write-Host "- Backup-Setup.txt" -ForegroundColor White
Write-Host "- nginx-global.conf" -ForegroundColor White

Write-Host "`n✅ Sua máquina está pronta para ser um servidor global!" -ForegroundColor Green 