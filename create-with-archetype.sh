#!/usr/bin/env bash
set -e

# This helper shows the Maven-archetype approach for creating the skeleton.
# Run from an empty directory.

mvn archetype:generate \
  -DgroupId=com.example.microservices \
  -DartifactId=microservices-platform \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false

cd microservices-platform
rm -rf src

mkdir -p libraries services

for module in common-models common-utils; do
  cd libraries
  mvn archetype:generate \
    -DgroupId=com.example.microservices \
    -DartifactId="$module" \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
  cd ..
done

for module in customer-service order-service notification-service; do
  cd services
  mvn archetype:generate \
    -DgroupId=com.example.microservices \
    -DartifactId="$module" \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false
  cd ..
done

echo "Skeleton created. Next, convert the root POM to packaging=pom and register the five modules."
