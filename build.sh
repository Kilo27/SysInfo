#!/bin/bash
# ============================================
# Build and run MyOshiProject as a fat JAR
# ============================================

# Exit on any error
set -e

# Project setup
JAR_NAME="MyOshiFat.jar"
MAIN_CLASS="Main"
SRC_DIR="src"
BUILD_DIR="build/out"
TEMP_DIR="temp"
LIBS_DIR="libs"

echo " Cleaning old build..."
rm -rf "$BUILD_DIR" "$TEMP_DIR" "$JAR_NAME"
mkdir -p "$BUILD_DIR" "$TEMP_DIR"

echo " Compiling source files..."
javac -d "$BUILD_DIR" -cp "$LIBS_DIR/*" "$SRC_DIR"/*.java

echo " Extracting dependencies..."
cd "$TEMP_DIR"
jar xf ../"$LIBS_DIR"/oshi-core-6.8.3.jar
jar xf ../"$LIBS_DIR"/jna-5.14.0.jar
jar xf ../"$LIBS_DIR"/jna-platform-5.14.0.jar
cd ..

echo " Creating fat JAR..."
jar cfm "$JAR_NAME" manifest.txt -C "$BUILD_DIR" . -C "$TEMP_DIR" .

echo " Cleaning up temporary files..."
rm -rf "$TEMP_DIR"

echo " Build complete: $JAR_NAME"

# Optional: run the jar automatically
if [ "$1" == "run" ]; then
  echo " Running application..."
  java -jar "$JAR_NAME"
fi

