#!/bin/sh
set -e

cd "$(dirname "$0")/.."

./springboot/docker/build.sh
minikube image load cardcreator_service

./frontend/docker/build.sh
minikube image load cardcreator_frontend


cd "../kubernetes"
kubectl apply -f . -f local

kubectl rollout restart deployment service
kubectl rollout restart deployment frontend

MINIKUBE_IP=$(minikube ip)

echo ""
echo "To setup Keycloak navigate to https://auth.$MINIKUBE_IP.nip.io and login with admin/admin"
echo "Create a realm called 'cardcreator', a client called 'cardcreator' (add https://$MINIKUBE_IP/* to Valid redirect URIs), and a user with a password"
echo ""
echo "Access the app at https://$MINIKUBE_IP"
