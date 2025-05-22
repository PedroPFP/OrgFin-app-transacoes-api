#!/bin/bash

set -e

export AWS_ACCESS_KEY_ID=000000000000 AWS_SECRET_ACCESS_KEY=000000000000

# comandos awsLocal

echo "Criação do dynamodb iniciada!"
awslocal dynamodb create-table --cli-input-json file:///docker-config/dynamodb_config.json --region sa-east-1