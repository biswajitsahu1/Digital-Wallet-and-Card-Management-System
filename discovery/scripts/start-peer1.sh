#!/bin/bash
export SPRING_PROFILE=peer1
export EUREKA_HOSTNAME=peer1.eureka.local
export SERVER_PORT=8761
export EUREKA_DEFAULT_ZONE=http://eureka:password@peer2.eureka.local:8762/eureka/
export LOGGING_FILE_PATH=logs
mkdir -p logs
java -jar target/discovery-0.0.1-SNAPSHOT.jar --spring.profiles.active=$SPRING_PROFILE