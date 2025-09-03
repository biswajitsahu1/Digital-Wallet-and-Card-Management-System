#!/bin/bash

# Define the base directory
BASE_DIR="card-service"
MAIN_JAVA_DIR="$BASE_DIR/src/main/java/com/bank/card/card_service"
MAIN_RESOURCES_DIR="$BASE_DIR/src/main/resources"
TEST_JAVA_DIR="$BASE_DIR/src/test/java/com/bank/card/card_service"
DOCKER_DIR="$BASE_DIR/src/docker"
OPENAPI_DIR="$BASE_DIR/src/openapi"
SCRIPTS_DIR="$BASE_DIR/src/scripts"

# Create the main project directory and subdirectories
echo "Creating project directories for $BASE_DIR..."
mkdir -p "$MAIN_JAVA_DIR"/{config,controller,service,repository,entity,dto,util,exception}
mkdir -p "$MAIN_RESOURCES_DIR"
mkdir -p "$TEST_JAVA_DIR"/{controller,service}
mkdir -p "$DOCKER_DIR"
mkdir -p "$OPENAPI_DIR"
mkdir -p "$SCRIPTS_DIR"

# Create the main files
echo "Creating files..."
touch "$BASE_DIR"/README.md
touch "$BASE_DIR"/pom.xml
touch "$MAIN_JAVA_DIR"/CardServiceApplication.java
touch "$MAIN_JAVA_DIR"/config/SecurityConfig.java
touch "$MAIN_JAVA_DIR"/config/JpaConfig.java
touch "$MAIN_JAVA_DIR"/config/StripeConfig.java
touch "$MAIN_JAVA_DIR"/config/OpenApiConfig.java
touch "$MAIN_JAVA_DIR"/controller/CardController.java
touch "$MAIN_JAVA_DIR"/service/CardService.java
touch "$MAIN_JAVA_DIR"/repository/CardRepository.java
touch "$MAIN_JAVA_DIR"/entity/Card.java
touch "$MAIN_JAVA_DIR"/dto/CardAddRequest.java
touch "$MAIN_JAVA_DIR"/dto/CardAddResponse.java
touch "$MAIN_JAVA_DIR"/dto/CardDto.java
touch "$MAIN_JAVA_DIR"/util/AesEncryptionUtil.java
touch "$MAIN_JAVA_DIR"/util/JwtUtil.java
touch "$MAIN_JAVA_DIR"/exception/CardNotFoundException.java
touch "$MAIN_JAVA_DIR"/exception/InvalidCardException.java
touch "$MAIN_JAVA_DIR"/exception/GlobalExceptionHandler.java
touch "$MAIN_RESOURCES_DIR"/application.yml
touch "$MAIN_RESOURCES_DIR"/logback-spring.xml
touch "$MAIN_RESOURCES_DIR"/bootstrap.yml
touch "$DOCKER_DIR"/Dockerfile
touch "$OPENAPI_DIR"/card-service-docs.yaml
touch "$SCRIPTS_DIR"/startup.sh
touch "$TEST_JAVA_DIR"/CardServiceApplicationTests.java

echo "Project structure for $BASE_DIR created successfully! ✅"