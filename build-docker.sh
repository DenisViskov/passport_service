#!/bin/bash

# Проверка, что gradlew существует и исполняемый
if [ ! -f "./gradlew" ]; then
    echo "Ошибка: gradlew не найден в текущей директории"
    exit 1
fi

# Выполнение clean и bootJar
echo "Запускаем ./gradlew clean bootJar..."
./gradlew clean bootJar
if [ $? -ne 0 ]; then
    echo "Ошибка: Сборка Gradle не удалась"
    exit 1
fi

# Проверка, что JAR-файл создан
JAR_PATH="build/libs/passport_service-0.0.1-SNAPSHOT.jar"
if [ ! -f "$JAR_PATH" ]; then
    echo "Ошибка: JAR-файл $JAR_PATH не найден"
    exit 1
fi

# Копирование JAR в deploy/
echo "Копирую JAR в deploy/..."
mkdir -p deploy
cp "$JAR_PATH" deploy/
if [ $? -ne 0 ]; then
    echo "Ошибка: Не удалось скопировать JAR в deploy/"
    exit 1
fi

# Проверка, что Dockerfile существует
DOCKERFILE_PATH="deploy/Dockerfile"
if [ ! -f "$DOCKERFILE_PATH" ]; then
    echo "Ошибка: Dockerfile не найден в $DOCKERFILE_PATH"
    exit 1
fi

# Переход в директорию deploy/ и сборка Docker-образа
echo "Собираю Docker-образ..."
cd deploy
docker build -t passport-service:latest .
if [ $? -ne 0 ]; then
    echo "Ошибка: Сборка Docker-образа не удалась"
    cd ..
    exit 1
fi

# Удаление JAR-файла из deploy/
echo "Удаляю JAR-файл из deploy/..."
rm passport_service-0.0.1-SNAPSHOT.jar
if [ $? -ne 0 ]; then
    echo "Предупреждение: Не удалось удалить JAR-файл из deploy/"
else
    echo "JAR-файл успешно удален"
fi

# Возвращение в корневую директорию
cd ..

echo "Сборка завершена успешно! Образ passport-service:latest готов."