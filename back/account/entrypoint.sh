#!/bin/bash

./mvnw clean package -DskipTests
cp target/account-*.jar /app.jar
eval "$@"
