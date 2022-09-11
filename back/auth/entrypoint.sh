#!/bin/bash

./mvnw clean package -DskipTests
cp target/auth-*.jar /app.jar
eval "$@"
