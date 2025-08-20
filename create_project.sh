#!/bin/bash

# Define the base directory
BASE_DIR="notification-service"
MAIN_JAVA_DIR="$BASE_DIR/src/main/java/com/bank/notification-service"
MAIN_RESOURCES_DIR="$BASE_DIR/src/main/resources"
TEST_JAVA_DIR="$BASE_DIR/src/test/java/com/bank/notification-service"
DOCKER_DIR="$BASE_DIR/docker"
OPENAPI_DIR="$BASE_DIR/openapi"
SCRIPTS_DIR="$BASE_DIR/scripts"

# Create the main project directory and subdirectories
echo "Creating project directories..."
mkdir -p "$MAIN_JAVA_DIR"/{config,controller,service,listener,dto,util,exception}
mkdir -p "$MAIN_RESOURCES_DIR"
mkdir -p "$TEST_JAVA_DIR"/{service,listener}
mkdir -p "$DOCKER_DIR"
mkdir -p "$OPENAPI_DIR"
mkdir -p "$SCRIPTS_DIR"

# Create the main files
echo "Creating main files..."
touch "$BASE_DIR"/README.md
touch "$MAIN_JAVA_DIR"/NotificationServiceApplication.java
touch "$MAIN_JAVA_DIR"/config/KafkaConfig.java
touch "$MAIN_JAVA_DIR"/config/EmailConfig.java
touch "$MAIN_JAVA_DIR"/config/SecurityConfig.java
touch "$MAIN_JAVA_DIR"/config/OpenApiConfig.java
touch "$MAIN_JAVA_DIR"/controller/NotificationController.java
touch "$MAIN_JAVA_DIR"/service/NotificationService.java
touch "$MAIN_JAVA_DIR"/listener/KafkaNotificationListener.java
touch "$MAIN_JAVA_DIR"/dto/TransactionEventDto.java
touch "$MAIN_JAVA_DIR"/util/EmailTemplateUtil.java
touch "$MAIN_JAVA_DIR"/exception/NotificationFailedException.java
touch "$MAIN_JAVA_DIR"/exception/GlobalExceptionHandler.java
touch "$MAIN_RESOURCES_DIR"/application.yml
touch "$MAIN_RESOURCES_DIR"/logback-spring.xml
touch "$MAIN_RESOURCES_DIR"/bootstrap.yml
touch "$DOCKER_DIR"/Dockerfile
touch "$OPENAPI_DIR"/notification-service-docs.yaml
touch "$SCRIPTS_DIR"/startup.sh
touch "$TEST_JAVA_DIR"/NotificationServiceApplicationTests.java
touch "$TEST_JAVA_DIR"/service/NotificationServiceTest.java
touch "$TEST_JAVA_DIR"/listener/KafkaNotificationListenerTest.java

echo "Project structure created successfully!"


