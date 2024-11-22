#!/bin/bash

# Set the file path for the AAR file
AAR_FILE_PATH="./itell-release.aar"

# Check if the file exists
if [ -f "$AAR_FILE_PATH" ]; then
    echo "Found AAR file at $AAR_FILE_PATH"
else
    echo "AAR file not found at $AAR_FILE_PATH"
    exit 1
fi

# Install the AAR file using Maven
mvn install:install-file \
    -Dfile=$AAR_FILE_PATH \
    -DgroupId=com.github.hassanwasfy \
    -DartifactId=iTell \
    -Dversion=2.0 \
    -Dpackaging=aar \
    -DgeneratePom=true