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
* `dynamodb_create_run.sh` Create a new database docker image and start it.

### Run Each Reboot
* `dynamodb_start.sh` Start the database docker image.
* `frontend_run.sh` Start the web server.

### Run Each Java Code Change
`sam_build_run.sh` Builds and runs the AWS lambda server.

# Use App
`http://localhost:8080`

# Finish Using App
`dynamodb_delete.sh` Deletes the database docker image.

