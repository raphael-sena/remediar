# Script para Testar Acesso Global
# Execute como Administrador

Write-Host "🌍 Testando acesso global da aplicação Remediar..." -ForegroundColor Green

# 1. Obter IP público
Write-Host "📡 Obtendo IP público..." -ForegroundColor Yellow
try {
    $publicIP = (Invoke-WebRequest -Uri "https://api.ipify.org" -UseBasicParsing).Content
    Write-Host "✅ IP Público: $publicIP" -ForegroundColor Green
} catch {
    Write-Host "❌ Não foi possível obter o IP público" -ForegroundColor Red
    exit 1
}

# 2. Obter IP local
$localIP = (Get-NetIPAddress -AddressFamily IPv4 -InterfaceAlias "Ethernet*" | Where-Object {$_.IPAddress -notlike "169.254.*"}).IPAddress
if (-not $localIP) {
    $localIP = (Get-NetIPAddress -AddressFamily IPv4 -InterfaceAlias "Wi-Fi*" | Where-Object {$_.IPAddress -notlike "169.254.*"}).IPAddress
}
Write-Host "🏠 IP Local: $localIP" -ForegroundColor Cyan

# 3. Verificar se os containers estão rodando
Write-Host "`n📊 Verificando status dos containers..." -ForegroundColor Yellow
$containers = docker-compose ps --format "table {{.Name}}\t{{.Status}}\t{{.Ports}}"
Write-Host $containers -ForegroundColor White

# 4. Testar conectividade local
Write-Host "`n🧪 Testando conectividade local..." -ForegroundColor Yellow

$localTests = @(
    @{Name="Frontend (Nginx)"; URL="http://localhost"}
    @{Name="API Gateway"; URL="http://localhost/api"}
    @{Name="Frontend Direto"; URL="http://localhost:3000"}
    @{Name="PgAdmin"; URL="http://localhost:15433"}
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

# 5. Testar conectividade externa
Write-Host "`n🌐 Testando conectividade externa..." -ForegroundColor Yellow

$externalTests = @(
    @{Name="Frontend (HTTP)"; URL="http://$publicIP"}
    @{Name="API Gateway"; URL="http://$publicIP/api"}
    @{Name="Frontend Direto"; URL="http://$publicIP`:3000"}
    @{Name="PgAdmin"; URL="http://$publicIP`:15433"}
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

# 6. Testar portas com ferramenta online
Write-Host "`n🔍 Testando portas com ferramentas online..." -ForegroundColor Yellow

$ports = @(80, 443, 8080, 3000, 15433)
foreach ($port in $ports) {
    try {
        $testUrl = "https://canyouseeme.org/port/$port"
        Write-Host "📋 Teste manual da porta $port`: $testUrl" -ForegroundColor Cyan
    } catch {
        Write-Host "⚠️ Não foi possível testar porta $port automaticamente" -ForegroundColor Yellow
    }
}

# 7. Verificar configurações de rede
Write-Host "`n🔧 Verificando configurações de rede..." -ForegroundColor Yellow

# Verificar se as portas estão sendo usadas
$netstat = netstat -an | Select-String ":80|:443|:8080|:3000|:15433"
Write-Host "Portas em uso:" -ForegroundColor Cyan
$netstat | ForEach-Object { Write-Host "  $_" -ForegroundColor White }

# Verificar regras do firewall
Write-Host "`n🛡️ Verificando regras do firewall..." -ForegroundColor Yellow
$firewallRules = netsh advfirewall firewall show rule name="Remediar*" | Select-String "Rule Name|Enabled|Direction|Action"
Write-Host "Regras do firewall:" -ForegroundColor Cyan
$firewallRules | ForEach-Object { Write-Host "  $_" -ForegroundColor White }

# 8. Informações de diagnóstico
Write-Host "`n📋 Informações de Diagnóstico:" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "IP Público: $publicIP" -ForegroundColor Yellow
Write-Host "IP Local: $localIP" -ForegroundColor Yellow
Write-Host "Data/Hora: $(Get-Date)" -ForegroundColor Yellow

Write-Host "`n🌍 URLs de Acesso:" -ForegroundColor Green
Write-Host "Frontend: http://$publicIP" -ForegroundColor Cyan
Write-Host "API: http://$publicIP/api" -ForegroundColor Cyan
Write-Host "Frontend Direto: http://$publicIP`:3000" -ForegroundColor Cyan
Write-Host "PgAdmin: http://$publicIP`:15433" -ForegroundColor Cyan

# 9. Recomendações
Write-Host "`n💡 Recomendações:" -ForegroundColor Green

$recommendations = @(
    "Configure um domínio para URLs mais profissionais",
    "Configure SSL/HTTPS para segurança",
    "Configure DNS dinâmico se o IP mudar frequentemente",
    "Configure monitoramento e alertas",
    "Configure backup automático",
    "Monitore o uso de recursos da máquina"
)

foreach ($rec in $recommendations) {
    Write-Host "• $rec" -ForegroundColor White
}

# 10. Próximos passos
Write-Host "`n🚀 Próximos Passos:" -ForegroundColor Green
Write-Host "1. Se os testes externos falharam, configure port forwarding no roteador" -ForegroundColor White
Write-Host "2. Configure um domínio (DNS dinâmico ou próprio)" -ForegroundColor White
Write-Host "3. Configure SSL/HTTPS com Let's Encrypt" -ForegroundColor White
Write-Host "4. Configure monitoramento e alertas" -ForegroundColor White

Write-Host "`n✅ Teste de acesso global concluído!" -ForegroundColor Green 