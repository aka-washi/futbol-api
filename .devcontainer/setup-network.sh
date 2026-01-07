#!/bin/bash

# Create futbol-network if it doesn't exist
NETWORK_NAME="futbol-network"

if ! docker network ls | grep -q "$NETWORK_NAME"; then
    echo "Creating $NETWORK_NAME network..."
    docker network create --driver bridge "$NETWORK_NAME"
    echo "✅ $NETWORK_NAME network created successfully"
else
    echo "✅ $NETWORK_NAME network already exists"
fi