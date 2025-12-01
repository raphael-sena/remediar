#!/bin/bash

# Cores para o output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

echo -e "${GREEN}🕵️  Diagnóstico Rápido da Conexão Nginx -> Gateway (Linux)...${NC}"

# --- Verificação do API Gateway ---
echo -e "\n${YELLOW}🔎 Verificando o API Gateway (remediar-gateway)...${NC}"
GATEWAY_ID=$(docker-compose ps -q remediar-gateway)
GATEWAY_STATUS=$(docker inspect -f '{{.State.Status}}' $GATEWAY_ID 2>/dev/null)

if [ "$GATEWAY_STATUS" != "running" ]; then
    echo -e "${RED}❌ ERRO: O container 'remediar-gateway' não está rodando.${NC}"
    docker-compose logs --tail=30 remediar-gateway
    exit 1
else
    echo -e "${GREEN}✅ O container 'remediar-gateway' está 'running'.${NC}"
    echo -e "Abaixo estão os últimos logs de inicialização dele:"
    echo -e "${CYAN}----------------------------------------------------${NC}"
    docker-compose logs --tail=30 remediar-gateway
    echo -e "${CYAN}----------------------------------------------------${NC}"
    echo -e "👉 Verifique nos logs acima se há erros (stack traces) ou se a aplicação iniciou com sucesso (Ex: 'Started ...Application')."
fi


# --- Verificação do Nginx ---
echo -e "\n${YELLOW}🔎 Verificando o Nginx (remediar-nginx)...${NC}"
NGINX_ID=$(docker-compose ps -q nginx)
NGINX_STATUS=$(docker inspect -f '{{.State.Status}}' $NGINX_ID 2>/dev/null)

if [ "$NGINX_STATUS" != "running" ]; then
    echo -e "${RED}❌ ERRO: O container 'nginx' não está rodando.${NC}"
    docker-compose logs --tail=30 nginx
    exit 1
else
    echo -e "${GREEN}✅ O container 'nginx' está 'running'.${NC}"
    echo -e "Abaixo estão os últimos logs de erro do Nginx:"
    echo -e "${CYAN}----------------------------------------------------${NC}"
    docker-compose exec nginx cat /var/log/nginx/error.log | tail -n 20
    echo -e "${CYAN}----------------------------------------------------${NC}"
    echo -e "👉 Verifique nos logs acima por erros como 'connection refused' ou 'host not found'."
fi


# --- Diagnóstico Final ---
echo -e "\n${GREEN}👨‍⚕️  Diagnóstico Final e Próximos Passos${NC}"
echo "Analise os logs que foram exibidos acima para identificar a causa raiz:"
echo ""
echo -e "${YELLOW}CASO 1: Logs do Gateway mostram ERROS (Stack Trace, 'Application failed to start')${NC}"
echo "  - Causa: O API Gateway não está iniciando corretamente."
echo "  - Solução: Verifique as configurações no 'application.yml' do gateway e as dependências dele (como o 'back-remediar')."
echo ""
echo -e "${YELLOW}CASO 2: Logs do Nginx mostram 'connection refused'${NC}"
echo "  - Causa: O Nginx consegue encontrar o Gateway, mas o Gateway recusa a conexão. Isso confirma o 'CASO 1'."
echo "  - Solução: Foque em consertar o API Gateway."
echo ""
echo -e "${YELLOW}CASO 3: Logs do Nginx mostram 'host not found: remediar-gateway'${NC}"
echo "  - Causa: Problema de rede no Docker. O Nginx não consegue 'enxergar' o Gateway."
echo "  - Solução: Verifique se ambos estão na mesma rede no 'docker-compose.yml' e reinicie o Docker."
echo ""
echo "Se nenhum erro claro aparecer, tente fazer uma requisição que falha (como o cadastro) e rode o script novamente para capturar os logs de erro em tempo real." 