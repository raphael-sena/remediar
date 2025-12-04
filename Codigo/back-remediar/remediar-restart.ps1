param(
    [string]$LoadInitialData = "false",
    [string]$DdlAuto = "update"
)

Write-Host "Reiniciando com:"
Write-Host "  app.loadInitialData = $LoadInitialData"
Write-Host "  spring.jpa.hibernate.ddl-auto = $DdlAuto"

# Encerrar containers (com ou sem volumes)
Write-Host "Encerrando containers antigos..."

if ($DdlAuto -eq "create" -or $DdlAuto -eq "create-drop") {
    Write-Host "  Modo 'create' detectado. Volumes de banco de dados também serão removidos."
    docker-compose down -v --remove-orphans
} else {
    Write-Host "  Modo '$DdlAuto' detectado. Volumes serão preservados."
    docker-compose down --remove-orphans
}

# Limpar imagens intermediárias
Write-Host "Limpando imagens intermediárias..."
docker image prune -f | Out-Null

# Injetar variáveis como JSON Spring
$env:SPRING_APPLICATION_JSON = "{`"app.loadInitialData`":$LoadInitialData,`"spring.jpa.hibernate.ddl-auto`":`"$DdlAuto`"}"
Write-Host "SPRING_APPLICATION_JSON: $($env:SPRING_APPLICATION_JSON)"

# Subir containers
Write-Host "Reconstruindo e subindo containers..."
docker-compose up --build -d

Start-Sleep -Seconds 5

Write-Host "Containers em execução:"
docker ps

# Mostrar logs da API Gateway
$gateway = docker ps --filter 'name=remediar-gateway' --format '{{.Names}}' | Select-Object -First 1

if ([string]::IsNullOrWhiteSpace($gateway)) {
    Write-Host "`nErro: Não foi possível encontrar o container 'remediar-gateway'."
} else {
    Write-Host "`nExibindo logs da API Gateway (CTRL+C para sair):"
    docker logs -f $gateway
}
