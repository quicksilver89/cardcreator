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
* `./scripts/dynamodb_create_run.sh` Create a new database docker image and start it.

### Run On New Clone
* In frontend `npm install`

### Run Each Reboot
* `./scripts/dynamodb_start.sh` Start the database docker image.
* `./frontend/scripts/serve.sh` Start the web server.

# Compile

### Run On Each Backend Code Change
`./scripts/sam_build_run.sh` Builds and runs the AWS lambda server.

### Run On Each Frontend Code Change
`./frontend/scripts/build.sh` Builds the web code.

# Use App
`http://localhost:8080`

# Finish Using App
`./scripts/dynamodb_delete.sh` Deletes the database docker image.

