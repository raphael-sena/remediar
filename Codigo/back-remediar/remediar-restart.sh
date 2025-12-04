#!/bin/bash
#
# Uso:   ./remediar-restart.sh [LoadInitialData] [DdlAuto]
# Exemplo: ./remediar-restart.sh false update

LoadInitialData=${1:-false}
DdlAuto=${2:-update}

echo "Reiniciando com:"
echo "  app.loadInitialData = $LoadInitialData"
echo "  spring.jpa.hibernate.ddl-auto = $DdlAuto"
echo

# 1) Parar containers (com ou sem volumes)
echo "Encerrando containers antigos..."
if [[ "$DdlAuto" == "create" || "$DdlAuto" == "create-drop" ]]; then
    echo "  Modo 'create'. Também removendo volumes de banco..."
    docker-compose down -v --remove-orphans
else
    echo "  Modo '$DdlAuto'. Mantendo volumes..."
    docker-compose down --remove-orphans
fi
echo

# 2) Limpar imagens intermediárias
echo "Limpando imagens intermediárias..."
docker image prune -f > /dev/null
echo

# 3) Injetar variáveis Spring
export SPRING_APPLICATION_JSON="{\"app.loadInitialData\":$LoadInitialData,\"spring.jpa.hibernate.ddl-auto\":\"$DdlAuto\"}"
echo "SPRING_APPLICATION_JSON = $SPRING_APPLICATION_JSON"
echo

# 4) Subir backend (gateway + serviços + infra)
echo "Reconstruindo e subindo containers..."
docker-compose up --build -d
echo

# 5) Aguarda inicialização
sleep 5

# 6) Status dos containers
echo "Containers em execução:"
docker ps
echo

# 7) Exibir logs da API Gateway
gateway=$(docker ps --filter "name=remediar-gateway" --format "{{.Names}}" | head -n1)
if [[ -z "$gateway" ]]; then
    echo "Erro: container 'remediar-gateway' não encontrado."
    exit 1
else
    echo "Exibindo logs da API Gateway ($gateway) — CTRL+C para sair:"
    docker logs -f "$gateway"
fi
