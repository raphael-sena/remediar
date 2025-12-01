# Script para Corrigir Acesso Global ao Backend
# Execute como Administrador

Write-Host "🔧 Corrigindo acesso global ao backend..." -ForegroundColor Green

# 1. Verificar se está executando como administrador
$isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")

if (-not $isAdmin) {
    Write-Host "❌ Este script deve ser executado como Administrador!" -ForegroundColor Red
    exit 1
}

Write-Host "`n📋 PROBLEMA IDENTIFICADO:" -ForegroundColor Yellow
Write-Host "O CORS estava configurado para aceitar apenas IPs específicos" -ForegroundColor White
Write-Host "Agora foi corrigido para aceitar qualquer origem" -ForegroundColor Green

# 2. Parar todos os serviços
Write-Host "`n🛑 Parando serviços..." -ForegroundColor Yellow
docker-compose down

# 3. Reconstruir as imagens com as correções de CORS
Write-Host "`n🔨 Reconstruindo imagens com correções de CORS..." -ForegroundColor Yellow
docker-compose build --no-cache back-remediar
docker-compose build --no-cache remediar-gateway

# 4. Iniciar serviços
Write-Host "`n▶️ Iniciando serviços..." -ForegroundColor Yellow
docker-compose up -d

# 5. Aguardar inicialização
Write-Host "`n⏳ Aguardando inicialização dos serviços..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# 6. Verificar status
Write-Host "`n📊 Status dos containers:" -ForegroundColor Green
docker-compose ps

# 7. Testar conectividade local
Write-Host "`n🧪 Testando conectividade local..." -ForegroundColor Yellow

$localTests = @(
    @{Name="Frontend (Nginx)"; URL="http://localhost"}
    @{Name="API Gateway"; URL="http://localhost:8080"}
    @{Name="API via Nginx"; URL="http://localhost/api"}
    @{Name="Backend Direto"; URL="http://localhost:8081"}
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

# 8. Obter IP público
Write-Host "`n🌐 Obtendo IP público..." -ForegroundColor Yellow
try {
    $publicIP = (Invoke-WebRequest -Uri "https://api.ipify.org" -UseBasicParsing).Content
    Write-Host "✅ IP Público: $publicIP" -ForegroundColor Green
} catch {
    Write-Host "❌ Não foi possível obter o IP público" -ForegroundColor Red
    $publicIP = "SEU_IP_PUBLICO"
}

# 9. Testar conectividade externa
Write-Host "`n🌍 Testando conectividade externa..." -ForegroundColor Yellow

$externalTests = @(
    @{Name="Frontend (HTTP)"; URL="http://$publicIP"}
    @{Name="API Gateway Direto"; URL="http://$publicIP`:8080"}
    @{Name="API via Nginx"; URL="http://$publicIP/api"}
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

# 10. Verificar logs
Write-Host "`n📋 Verificando logs do API Gateway..." -ForegroundColor Yellow
try {
    $gatewayLogs = docker-compose logs remediar-gateway --tail=5
    Write-Host "Últimos logs do API Gateway:" -ForegroundColor Cyan
    $gatewayLogs | ForEach-Object { Write-Host "  $_" -ForegroundColor White }
} catch {
    Write-Host "⚠️ Não foi possível obter logs" -ForegroundColor Yellow
}

# 11. Informações finais
Write-Host "`n✅ CORREÇÕES APLICADAS!" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Cyan

Write-Host "`n🔧 O que foi corrigido:" -ForegroundColor Yellow
Write-Host "1. ✅ CORS no backend - Agora aceita qualquer origem" -ForegroundColor Green
Write-Host "2. ✅ CORS no API Gateway - Agora aceita qualquer origem" -ForegroundColor Green
Write-Host "3. ✅ Headers de segurança adicionados" -ForegroundColor Green
Write-Host "4. ✅ Cache de preflight configurado" -ForegroundColor Green

Write-Host "`n🌍 URLs de Acesso:" -ForegroundColor Green
Write-Host "Frontend: http://$publicIP" -ForegroundColor Cyan
Write-Host "API Gateway: http://$publicIP`:8080" -ForegroundColor Cyan
Write-Host "API via Nginx: http://$publicIP/api" -ForegroundColor Cyan

Write-Host "`n⚠️ IMPORTANTE:" -ForegroundColor Yellow
Write-Host "Se ainda não funcionar externamente, configure port forwarding no roteador:" -ForegroundColor White
Write-Host "- Porta 80 → [IP da máquina]:80" -ForegroundColor White
Write-Host "- Porta 8080 → [IP da máquina]:8080" -ForegroundColor White

Write-Host "`n🚀 Para testar completamente:" -ForegroundColor Green
Write-Host ".\test-global-access.ps1" -ForegroundColor White

Write-Host "`n✅ Agora o backend deve receber requisições de qualquer lugar do mundo!" -ForegroundColor Green 