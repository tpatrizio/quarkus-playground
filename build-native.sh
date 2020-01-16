#!/usr/bin/env bash
./mvnw package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t quarkus-playground/getting-started .
#docker run -i --rm -p 8080:8080 --name quarkus-getting-started quarkus-playground/getting-started 
