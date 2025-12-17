#!/bin/bash

# Usage: ./run.sh [mode]
# Modes: producer, consumer, both (default: both)

MODE="${1:-both}"

# Validate mode
if [[ ! "$MODE" =~ ^(producer|consumer|both)$ ]]; then
    echo "Invalid mode: $MODE"
    echo "Usage: ./run.sh [producer|consumer|both]"
    exit 1
fi

# Load environment variables from .env file if it exists
if [ -f .env ]; then
    echo "Loading environment variables from .env..."
    while IFS= read -r line || [[ -n "$line" ]]; do
        # Skip comments and empty lines
        [[ -z "$line" || "$line" =~ ^# ]] && continue
        # Extract key and value, preserving literal $ signs
        key="${line%%=*}"
        value="${line#*=}"
        # Export the variable
        export "$key"="$value"
    done < .env
else
    echo "Warning: .env file not found. Copy .env.example to .env and configure it."
    exit 1
fi

export APP_MODE="$MODE"

echo "Building project..."
mvn clean package -DskipTests

echo "Running application in '$MODE' mode..."
mvn spring-boot:run
