#!/bin/bash

echo "============================================"
echo "Iniciando instalação: $(date)"
echo "============================================"

# Atualizar sistema
sudo apt update && sudo apt upgrade -y
sudo apt-get install -y curl git unzip wget software-properties-common

# 1. Git
sudo apt install git -y
git --version

# 2. MySQL Server
sudo apt-get install -y mysql-server
sudo systemctl start mysql
sudo systemctl enable mysql

# 3. Node.js (versão 20 LTS)
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt-get install -y nodejs
node -v
npm -v

# 4. JRE
sudo apt install default-jre -y
java -version

# 5. Maven
sudo apt install maven -y
mvn -version

# 6. Docker
sudo apt install docker.io -y
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker $USER

# 7. Docker Compose v2 (standalone, compatível com docker-compose)
# Remove plugin v2 do apt para evitar conflito
sudo apt-get remove -y docker-compose-plugin 2>/dev/null || true
sudo curl -fsSL "https://github.com/docker/compose/releases/download/v2.27.0/docker-compose-linux-x86_64" \
  -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# Verifica instalação
docker-compose --version || { echo "ERRO: docker-compose não instalado corretamente"; exit 1; }

# 8. CRON
sudo apt install cron -y
sudo systemctl enable cron
sudo systemctl start cron

echo "--------------------------------------------"
echo "Instalação concluída!"
echo "ATENÇÃO: Faça logout e login novamente para"
echo "         aplicar as permissões do Docker."
echo "Quando estiver pronto, execute: bash start.sh"
echo "--------------------------------------------"