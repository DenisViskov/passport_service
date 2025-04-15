#!/bin/bash

# Имя образа приложения
IMAGE_NAME="passport-service:latest"
# Имя JAR-файла в deploy/
JAR_NAME="passport_service-0.0.1-SNAPSHOT.jar"

# Функция для вывода сообщений
log() {
    echo "[INFO] $1"
}

# Проверка, что Docker доступен
if ! command -v docker &> /dev/null; then
    echo "Ошибка: Docker не установлен или недоступен"
    exit 1
fi

# Остановка всех контейнеров, использующих образ passport-service
log "Останавливаю контейнеры приложения..."
CONTAINERS=$(docker ps -q --filter "ancestor=$IMAGE_NAME")
if [ -n "$CONTAINERS" ]; then
    docker stop $CONTAINERS
    if [ $? -ne 0 ]; then
        echo "Ошибка: Не удалось остановить контейнеры"
        exit 1
    fi
    log "Контейнеры остановлены"
else
    log "Активные контейнеры не найдены"
fi

# Удаление всех контейнеров, использующих образ passport-service
log "Удаляю контейнеры приложения..."
CONTAINERS_ALL=$(docker ps -a -q --filter "ancestor=$IMAGE_NAME")
if [ -n "$CONTAINERS_ALL" ]; then
    docker rm $CONTAINERS_ALL
    if [ $? -ne 0 ]; then
        echo "Ошибка: Не удалось удалить контейнеры"
        exit 1
    fi
    log "Контейнеры удалены"
else
    log "Контейнеры не найдены"
fi

# Удаление образа passport-service:latest
log "Удаляю образ $IMAGE_NAME..."
if docker image inspect $IMAGE_NAME &> /dev/null; then
    docker rmi $IMAGE_NAME
    if [ $? -ne 0 ]; then
        echo "Ошибка: Не удалось удалить образ $IMAGE_NAME"
        exit 1
    fi
    log "Образ $IMAGE_NAME удален"
else
    log "Образ $IMAGE_NAME не найден"
fi

# Удаление JAR-файла из deploy/
log "Удаляю JAR-файл из deploy/..."
if [ -f "deploy/$JAR_NAME" ]; then
    rm "deploy/$JAR_NAME"
    if [ $? -ne 0 ]; then
        echo "Предупреждение: Не удалось удалить deploy/$JAR_NAME"
    else
        log "JAR-файл deploy/$JAR_NAME удален"
    fi
else
    log "JAR-файл deploy/$JAR_NAME не найден"
fi

log "Очистка завершена успешно!"