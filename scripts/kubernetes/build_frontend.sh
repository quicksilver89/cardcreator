#!/bin/sh
set -e

cd "$(dirname "$0")/.."

./frontend/docker/build.sh
minikube image load cardcreator_frontend

cd "../kubernetes"

kubectl apply -f frontend.yaml -f ingress.yaml -f configmap-local.yaml -f secret-local.yaml
