#!/bin/sh
set -e

minikube start --driver=docker
minikube addons enable ingress
