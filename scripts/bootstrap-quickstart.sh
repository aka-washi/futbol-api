#!/bin/bash

# Bootstrap API Quick Start Script
# This script demonstrates how to use the bootstrap endpoint to load data

API_BASE="http://localhost:8080/api/v1"

echo "========================================"
echo "Bootstrap API - Quick Start"
echo "========================================"
echo ""

# 1. Load Countries
echo "1. Loading Countries..."
curl -X POST "$API_BASE/bootstrap/load" \
  -H "Content-Type: application/json" \
  -d '{
    "entityType": "country"
  }' | jq .

echo ""
echo "========================================"
echo ""

# 2. Load Organizations (example with inline data)
echo "2. Loading Organizations..."
curl -X POST "$API_BASE/bootstrap/load" \
  -H "Content-Type: application/json" \
  -d '{
    "entityType": "organization",
    "data": [
      {
        "name": "FIFA",
        "code": "FIFA",
        "displayName": "FIFA",
        "countryDisplayName": "Switzerland"
      },
      {
        "name": "UEFA",
        "code": "UEFA",
        "displayName": "UEFA",
        "countryDisplayName": "Switzerland"
      }
    ]
  }' | jq .

echo ""
echo "========================================"
echo ""

# 3. Load Point Systems
echo "3. Loading Point Systems..."
curl -X POST "$API_BASE/bootstrap/load" \
  -H "Content-Type: application/json" \
  -d '{
    "entityType": "pointsystem",
    "data": [
      {
        "name": "Standard 3-1-0",
        "displayName": "Standard 3-1-0",
        "winPoints": 3,
        "drawPoints": 1,
        "lossPoints": 0
      }
    ]
  }' | jq .

echo ""
echo "========================================"
echo ""

# 4. Load Players (from file)
echo "4. Loading Players from file..."
curl -X POST "$API_BASE/bootstrap/load" \
  -H "Content-Type: application/json" \
  -d '{
    "entityType": "player"
  }' | jq .

echo ""
echo "========================================"
echo "Bootstrap Complete!"
echo "========================================"
