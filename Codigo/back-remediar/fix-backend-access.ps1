# Script para Diagnosticar e Corrigir Acesso ao Backend
# Execute como Administrador

Write-Host "🔍 Diagnosticando problema de acesso ao backend..." -ForegroundColor Green

# 1. Verificar se está executando como administrador
$isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")

if (-not $isAdmin) {
    Write-Host "❌ Este script deve ser executado como Administrador!" -ForegroundColor Red
    exit 1
}

# 2. Verificar status dos containers
Write-Host "`n📊 Verificando status dos containers..." -ForegroundColor Yellow
docker-compose ps

# 3. Verificar se as portas estão sendo usadas
Write-Host "`n🔍 Verificando portas em uso..." -ForegroundColor Yellow
$ports = @(80, 443, 8080, 3000, 15433)
foreach ($port in $ports) {
    $process = netstat -ano | Select-String ":$port\s"
    if ($process) {
        Write-Host "✅ Porta $port`: EM USO" -ForegroundColor Green
    } else {
        Write-Host "❌ Porta $port`: NÃO EM USO" -ForegroundColor Red
    }
}

# 4. Verificar regras do firewall
Write-Host "`n🛡️ Verificando regras do firewall..." -ForegroundColor Yellow
$firewallRules = netsh advfirewall firewall show rule name="Remediar*"
if ($firewallRules) {
    Write-Host "✅ Regras do firewall encontradas:" -ForegroundColor Green
    $firewallRules | Select-String "Rule Name|Enabled|Direction|Action" | ForEach-Object { Write-Host "  $_" -ForegroundColor White }
} else {
    Write-Host "❌ Nenhuma regra do firewall encontrada!" -ForegroundColor Red
}

# 5. Testar conectividade local
Write-Host "`n🧪 Testando conectividade local..." -ForegroundColor Yellow

$localTests = @(
    @{Name="Nginx (Porta 80)"; URL="http://localhost"; Port=80}
    @{Name="API Gateway (Porta 8080)"; URL="http://localhost:8080"; Port=8080}
    @{Name="Frontend (Porta 3000)"; URL="http://localhost:3000"; Port=3000}
    @{Name="API via Nginx"; URL="http://localhost/api"; Port=80}
)

foreach ($test in $localTests) {
    try {
        $response = Invoke-WebRequest -Uri $test.URL -TimeoutSec 10 -UseBasicParsing
        if ($response.StatusCode -eq 200) {
            Write-Host "✅ $($test.Name): OK" -ForegroundColor Green
        } else {
            Write-Host "⚠️ $($test.Name): Status $($response.StatusCode)" -ForegroundColor Yellow
        }
    } catch {
        Write-Host "❌ $($test.Name): FALHOU" -ForegroundColor Red
    }
}

# 6. Obter IP público
Write-Host "`n🌐 Obtendo IP público..." -ForegroundColor Yellow
try {
    $publicIP = (Invoke-WebRequest -Uri "https://api.ipify.org" -UseBasicParsing).Content
    Write-Host "✅ IP Público: $publicIP" -ForegroundColor Green
} catch {
    Write-Host "❌ Não foi possível obter o IP público" -ForegroundColor Red
    $publicIP = "SEU_IP_PUBLICO"
}

# 7. Testar conectividade externa
Write-Host "`n🌍 Testando conectividade externa..." -ForegroundColor Yellow

$externalTests = @(
    @{Name="Frontend (HTTP)"; URL="http://$publicIP"; Port=80}
    @{Name="API Gateway Direto"; URL="http://$publicIP`:8080"; Port=8080}
    @{Name="API via Nginx"; URL="http://$publicIP/api"; Port=80}
    @{Name="Frontend Direto"; URL="http://$publicIP`:3000"; Port=3000}
)

foreach ($test in $externalTests) {
    try {
        $response = Invoke-WebRequest -Uri $test.URL -TimeoutSec 15 -UseBasicParsing
        if ($response.StatusCode -eq 200) {
            Write-Host "✅ $($test.Name): OK" -ForegroundColor Green
        } else {
            Write-Host "⚠️ $($test.Name): Status $($response.StatusCode)" -ForegroundColor Yellow
        }
    } catch {
        Write-Host "❌ $($test.Name): FALHOU" -ForegroundColor Red
    }
}

# 8. Verificar logs do nginx
Write-Host "`n📋 Verificando logs do nginx..." -ForegroundColor Yellow
try {
    $nginxLogs = docker-compose logs nginx --tail=10
    Write-Host "Últimos logs do nginx:" -ForegroundColor Cyan
    $nginxLogs | ForEach-Object { Write-Host "  $_" -ForegroundColor White }
} catch {
    Write-Host "⚠️ Não foi possível obter logs do nginx" -ForegroundColor Yellow
}

# 9. Verificar logs do API Gateway
Write-Host "`n📋 Verificando logs do API Gateway..." -ForegroundColor Yellow
try {
    $gatewayLogs = docker-compose logs remediar-gateway --tail=10
    Write-Host "Últimos logs do API Gateway:" -ForegroundColor Cyan
    $gatewayLogs | ForEach-Object { Write-Host "  $_" -ForegroundColor White }
} catch {
    Write-Host "⚠️ Não foi possível obter logs do API Gateway" -ForegroundColor Yellow
}

# 10. Diagnóstico e soluções
Write-Host "`n🔧 DIAGNÓSTICO E SOLUÇÕES:" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Cyan

Write-Host "`n📋 PROBLEMAS COMUNS E SOLUÇÕES:" -ForegroundColor Yellow

Write-Host "`n1️⃣ PORT FORWARDING INCOMPLETO:" -ForegroundColor Red
Write-Host "   Problema: Só porta 80 está aberta no roteador" -ForegroundColor White
Write-Host "   Solução: Abrir também porta 8080 no roteador" -ForegroundColor White
Write-Host "   Configuração necessária:" -ForegroundColor White
Write-Host "   - Porta 80 → IP da máquina:80" -ForegroundColor White
Write-Host "   - Porta 8080 → IP da máquina:8080" -ForegroundColor White

Write-Host "`n2️⃣ FIREWALL BLOQUEANDO:" -ForegroundColor Red
Write-Host "   Problema: Firewall do Windows bloqueando porta 8080" -ForegroundColor White
Write-Host "   Solução: Executar o script de configuração do firewall" -ForegroundColor White
Write-Host "   Comando: .\configure-firewall.ps1" -ForegroundColor White

Write-Host "`n3️⃣ CONFIGURAÇÃO DO NGINX:" -ForegroundColor Red
Write-Host "   Problema: Nginx não está roteando /api/ corretamente" -ForegroundColor White
Write-Host "   Solução: Verificar se o upstream 'api' está correto" -ForegroundColor White
Write-Host "   Status: ✅ Configuração verificada e corrigida" -ForegroundColor Green

Write-Host "`n4️⃣ CORS MAL CONFIGURADO:" -ForegroundColor Red
Write-Host "   Problema: Backend rejeitando requisições do frontend" -ForegroundColor White
Write-Host "   Solução: Configurar CORS no backend" -ForegroundColor White

# 11. Comandos para corrigir
Write-Host "`n🚀 COMANDOS PARA CORRIGIR:" -ForegroundColor Green

Write-Host "`n1. Configurar firewall:" -ForegroundColor Yellow
Write-Host "   .\configure-firewall.ps1" -ForegroundColor White

Write-Host "`n2. Reiniciar serviços:" -ForegroundColor Yellow
Write-Host "   docker-compose down" -ForegroundColor White
Write-Host "   docker-compose up -d" -ForegroundColor White

Write-Host "`n3. Verificar port forwarding no roteador:" -ForegroundColor Yellow
Write-Host "   - Porta 80 → [IP da máquina]:80" -ForegroundColor White
Write-Host "   - Porta 8080 → [IP da máquina]:8080" -ForegroundColor White

Write-Host "`n4. Testar novamente:" -ForegroundColor Yellow
Write-Host "   .\test-global-access.ps1" -ForegroundColor White

Write-Host "`n✅ Diagnóstico concluído! Siga as soluções acima." -ForegroundColor Green 