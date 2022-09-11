#!/bin/bash

./mvnw clean package -DskipTests
cp target/gerente-*.jar /app.jar
eval "$@"
