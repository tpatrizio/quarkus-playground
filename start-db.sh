#!/usr/bin/env bash
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 \
    --name postgres-quarkus-playground -e POSTGRES_USER=playground \
    -e POSTGRES_PASSWORD=playground -e POSTGRES_DB=playground \
    -p 5432:5432 postgres:10.5
