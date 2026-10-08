#!/bin/sh
set -e

# Create a new database docker image. If one exists, the old one will be deleted first.

PORT=42619

cd "$(dirname "$0")/../../backend"

docker stop dynamodb > /dev/null 2>&1 || true
docker rm dynamodb > /dev/null 2>&1 || true
docker volume rm dynamodb > /dev/null 2>&1 || true

docker network create dynamodb > /dev/null 2>&1 || true

docker volume create dynamodb > /dev/null

docker run -d \
  --name dynamodb \
  --network dynamodb \
  -v dynamodb:/home/dynamodblocal \
  -p "$PORT:8000" \
  amazon/dynamodb-local \
  -jar DynamoDBLocal.jar -sharedDb -dbPath /home/dynamodblocal > /dev/null

until aws dynamodb list-tables --endpoint-url "http://localhost:$PORT" > /dev/null  2>&1; do
    echo "Waiting for docker dynamodb to start"
    sleep 1
done

npx --yes sam-dynamodb-create-table \
    -f template.yaml \
    -e "http://localhost:$PORT" \
    > /dev/null
