#!/bin/sh
set -e

cd "$(dirname "$0")/.."

./springboot/docker/build.sh
minikube image load cardcreator_service

cd "../kubernetes"

kubectl apply -f service.yaml -f ingress.yaml -f configmap-local.yaml -f secret-local.yaml
