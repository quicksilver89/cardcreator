#!/bin/sh

minikube start

kubectl rollout restart deployment service
kubectl rollout restart deployment frontend
kubectl rollout restart deployment angular
