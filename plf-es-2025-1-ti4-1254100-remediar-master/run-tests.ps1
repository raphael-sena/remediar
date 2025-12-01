# Script para executar todos os testes do projeto Remediar
# Autor: Assistente IA
# Data: 2025

Write-Host "🧪 Executando Testes do Projeto Remediar" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan

# Função para verificar se um comando existe
function Test-Command($cmdname) {
    return [bool](Get-Command -Name $cmdname -ErrorAction SilentlyContinue)
}

# Função para executar comando com tratamento de erro
function Invoke-CommandWithErrorHandling($command, $description) {
    Write-Host "`n📋 $description" -ForegroundColor Yellow
    Write-Host "Executando: $command" -ForegroundColor Gray
    
    try {
        Invoke-Expression $command
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ $description - SUCESSO" -ForegroundColor Green
        } else {
            Write-Host "❌ $description - FALHOU (Exit Code: $LASTEXITCODE)" -ForegroundColor Red
        }
    } catch {
        Write-Host "❌ $description - ERRO: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# Verificar pré-requisitos
Write-Host "`n🔍 Verificando pré-requisitos..." -ForegroundColor Blue

$javaInstalled = Test-Command "java"
$mavenInstalled = Test-Command "mvn"
$nodeInstalled = Test-Command "node"
$npmInstalled = Test-Command "npm"

Write-Host "Java: $(if ($javaInstalled) { '✅ Instalado' } else { '❌ Não encontrado' })" -ForegroundColor $(if ($javaInstalled) { 'Green' } else { 'Red' })
Write-Host "Maven: $(if ($mavenInstalled) { '✅ Instalado' } else { '❌ Não encontrado' })" -ForegroundColor $(if ($mavenInstalled) { 'Green' } else { 'Red' })
Write-Host "Node.js: $(if ($nodeInstalled) { '✅ Instalado' } else { '❌ Não encontrado' })" -ForegroundColor $(if ($nodeInstalled) { 'Green' } else { 'Red' })
Write-Host "npm: $(if ($npmInstalled) { '✅ Instalado' } else { '❌ Não encontrado' })" -ForegroundColor $(if ($npmInstalled) { 'Green' } else { 'Red' })

# Verificar se estamos no diretório correto
$currentDir = Get-Location
Write-Host "`n📁 Diretório atual: $currentDir" -ForegroundColor Blue

# Executar testes do Backend
if ($mavenInstalled) {
    Write-Host "`n🚀 Executando testes do Backend..." -ForegroundColor Magenta
    
    $backendPath = "Codigo/back-remediar/back-remediar"
    if (Test-Path $backendPath) {
        Push-Location $backendPath
        
        # Executar testes unitários
        Invoke-CommandWithErrorHandling "mvn test" "Testes Unitários do Backend"
        
        # Executar testes com cobertura
        Invoke-CommandWithErrorHandling "mvn test jacoco:report" "Testes com Cobertura do Backend"
        
        # Executar testes de integração
        Invoke-CommandWithErrorHandling "mvn test -Dtest='*IntegrationTest'" "Testes de Integração do Backend"
        
        Pop-Location
    } else {
        Write-Host "❌ Diretório do backend não encontrado: $backendPath" -ForegroundColor Red
    }
} else {
    Write-Host "❌ Maven não encontrado. Pulando testes do backend." -ForegroundColor Red
}

# Executar testes do Frontend
if ($npmInstalled) {
    Write-Host "`n🚀 Executando testes do Frontend..." -ForegroundColor Magenta
    
    $frontendPath = "Codigo/front-remediar"
    if (Test-Path $frontendPath) {
        Push-Location $frontendPath
        
        # Instalar dependências se necessário
        if (-not (Test-Path "node_modules")) {
            Write-Host "📦 Instalando dependências do frontend..." -ForegroundColor Yellow
            Invoke-CommandWithErrorHandling "npm install" "Instalação de Dependências"
        }
        
        # Executar testes
        Invoke-CommandWithErrorHandling "npm test" "Testes Unitários do Frontend"
        
        # Executar testes com cobertura
        Invoke-CommandWithErrorHandling "npm run test:coverage" "Testes com Cobertura do Frontend"
        
        # Executar testes em modo watch (opcional)
        Write-Host "`n💡 Para executar testes em modo watch, use: npm run test:watch" -ForegroundColor Cyan
        
        Pop-Location
    } else {
        Write-Host "❌ Diretório do frontend não encontrado: $frontendPath" -ForegroundColor Red
    }
} else {
    Write-Host "❌ npm não encontrado. Pulando testes do frontend." -ForegroundColor Red
}

# Resumo final
Write-Host "`n📊 Resumo da Execução de Testes" -ForegroundColor Cyan
Write-Host "=================================" -ForegroundColor Cyan

if ($mavenInstalled -and $npmInstalled) {
    Write-Host "✅ Todos os pré-requisitos atendidos" -ForegroundColor Green
} else {
    Write-Host "⚠️  Alguns pré-requisitos não foram encontrados" -ForegroundColor Yellow
}

Write-Host "`n📚 Documentação dos Testes:" -ForegroundColor Blue
Write-Host "- Backend: Codigo/back-remediar/back-remediar/TESTES_README.md" -ForegroundColor Gray
Write-Host "- Frontend: Codigo/front-remediar/TESTES_README.md" -ForegroundColor Gray

Write-Host "`n🎯 Próximos Passos:" -ForegroundColor Blue
Write-Host "1. Verificar relatórios de cobertura" -ForegroundColor Gray
Write-Host "2. Corrigir testes que falharam" -ForegroundColor Gray
Write-Host "3. Adicionar novos testes conforme necessário" -ForegroundColor Gray
Write-Host "4. Configurar CI/CD para execução automática" -ForegroundColor Gray

Write-Host "`n✨ Execução de testes concluída!" -ForegroundColor Green 