#!/bin/sh

# List the contents of the database. Useful for testing.

aws dynamodb scan --table-name CardTable --endpoint-url http://localhost:42619