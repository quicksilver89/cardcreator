#!/bin/sh
set -e

minikube start --driver=docker
minikube addons enable ingress


echo "Update the configmap.yaml file for the IP $(minikube ip)"
