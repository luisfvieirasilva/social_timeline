#!/bin/bash

echo "Creating user.username unique constrain"
docker-compose exec neo4j cypher-shell -u neo4j -p admin "CREATE CONSTRAINT user_unique_username FOR (user:User) REQUIRE user.username IS UNIQUE"
docker_exit_code=$?
if [ $docker_exit_code -ne 0 ]; then
  echo "fail to create user.username constrain" >&2
  exit $docker_exit_code
fi
