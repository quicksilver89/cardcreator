# Description
A web based app to allow custom design of cards.

This app is split into three layers:
* Frontend: Contains html, css, and ts. Runs as a web server and displays to the user.
* Backend: Contains java. Runs as a REST server and serves information to the frontend. Has a AWS lambda or a Spring Boot implementation.
* Database: No custom code. Runs as a server giving raw database access to the backend.

An app designed to help learn the following tools:  
* AWS Lambdas
* DynamoDB
* Spring Boot
* Kubernetes

# Setup

### One Time Install (as needed)
* AWS CLI
* AWS SAM CLI
* Docker
* Node.js
* Kubectl
* Minikube

### Run On New Git Clone
* In frontend `npm install`


# DynamoDb Locally
### Create
`./scripts/dynamodb/create_run.sh` Create a new database docker image and start it. This will delete any existing data.
### Run
`./scripts/dynamodb/start.sh` Start the database after each computer restart.
### View
`./scripts/dynamodb/contents.sh` View the contents of the database.
### Stop
`./scripts/dynamodb/stop.sh` Stop the database from running.
### Delete
`./scripts/dynamodb/delete.sh` Completely delete the database and docker image.

# AWS Locally
### Run
`./scripts/aws/run.sh` Run the server. Only needs to be done once per comptuer restart.
### Build
`./scripts/aws/build.sh` Build the AWS lambda service for local running.

# Spring Boot Locally
### Build & Run
`./scripts/springboot/run.sh` Build and run the spring boot service.

# Frontend Locally
### Run
`./scripts/frontend/run.sh` Run the server. Only needs to be done once per comptuer restart.
### Build
`./scripts/frontend/build.sh` Build the frontend.
### Access
`http://localhost:8080` Open in web browser.

# Kubernetes Locally
### Create
`./scripts/kubernetes/create.sh` Create a new minikube cluster.
### Build
`./scripts/kubernetes/build_backend.sh` Build the backend and push to minikube.  
`./scripts/kubernetes/build_frontend.sh` Build the frontend and push to minikube.
### Access
`minikube ip` View the ip of the minikube.  
`http://<minikube ip>` Open in web browser.
### Delete
`./scripts/kubernetes/delete.sh` Delete the minikube cluster.


# Deploy to AWS Cloud
### Backend
`./scripts/aws/deploy.sh` Build & deploy the backend to the AWS cloud.
### Frontend
`./scripts/frontend/deploy_to_aws.sh` Build & deploy the frontend to the AWS cloud.
### Access
The end of the backend deploy will tell you the url. Open in web browser.
