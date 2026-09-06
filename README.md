# Description
A web based app to allow custom design of cards.

A program designed as a learning tool to interface with AWS services.  
Uses:  
* AWS Lambdas
* DynamoDB

# Setup

### One Time Install
* AWS CLI
* AWS SAM CLI
* Docker
* Node.js
* `./backend/scripts/dynamodb_create_run.sh` Create a new database docker image and start it.

### Run On New Git Clone
* In frontend `npm install`

### Run Each Reboot
* `./run_servers.sh` Start the database docker image, the sam backend server, and the frontend web server.

# Compile

### Run On Each Backend Code Change
`./backend/scripts/sam_build.sh` Builds the Java AWS lambda server.

### Run On Each Frontend Code Change
`./frontend/scripts/build.sh` Builds the web code.

### Or Build Backend & Frontend
`./build.sh` Builds both the backend and frontend at the same time.

# Use App
`http://localhost:8080`

# Finish Using App
`./backend/scripts/dynamodb_delete.sh` Deletes the database docker image.