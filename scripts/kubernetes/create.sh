#!/bin/sh
set -e

minikube start --driver=docker
kubectl create -f https://raw.githubusercontent.com/keycloak/keycloak-quickstarts/refs/heads/main/kubernetes/keycloak.yaml
minikube addons enable ingress


echo "Update the ingress.yaml and configmap.yaml file for the IP $(minikube ip)"
