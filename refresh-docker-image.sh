#!/bin/bash

# Script to refresh Docker image with the new version of the application

echo "Refreshing Docker image with the new version of the application..."

# Step 1: Build the application with Maven (optional, can be skipped if already built)
echo "Building the application..."
./mvnw clean package -DskipTests

# Step 2: Stop the running containers
echo "Stopping running containers..."
docker-compose down

# Step 3: Rebuild the Docker image
echo "Rebuilding Docker image..."
docker-compose build --no-cache

# Step 4: Start the containers with the new image
echo "Starting containers with the new image..."
docker-compose up -d

echo "Docker image has been refreshed with the new version of the application!"
echo "The application is now running at http://localhost:8081"