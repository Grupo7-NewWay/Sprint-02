#!/bin/bash

echo "============================================"
echo "Iniciando ambiente: $(date)"
echo "============================================"

# 1. Copiar o .jar compilado para a pasta do Docker
cp /home/ubuntu/Sprint-02/java/apache-poi-eventos/target/apache-poi-eventos-1.0-SNAPSHOT.jar \
   /home/ubuntu/Sprint-02/java/app.jar

# 2. Subir Docker Compose
cd /home/ubuntu/Sprint-02
docker compose up -d

# 3. Aguardar container jar-processor ficar pronto
echo "Aguardando container jar-processor ficar pronto..."
until docker compose ps jar-processor | grep -q "running"; do
  sleep 2
done
echo "Container pronto!"

# 4. Configurar CRON
DOCKER_BIN=$(which docker)
COMPOSE_DIR="/home/ubuntu/Sprint-02"
LOG_PATH="/home/ubuntu/log-projeto.log"

CRON_JOB="*/5 * * * * cd $COMPOSE_DIR && $DOCKER_BIN compose run --rm jar-processor >> $LOG_PATH 2>&1"

(crontab -l 2>/dev/null | grep -qF "$COMPOSE_DIR") \
  && echo "CRON já configurado, pulando." \
  || (crontab -l 2>/dev/null; echo "$CRON_JOB") | crontab -

echo "Agendamento CRON configurado:"
crontab -l

echo "--------------------------------------------"
echo "Ambiente pronto! Logs em: $LOG_PATH"
echo "--------------------------------------------"