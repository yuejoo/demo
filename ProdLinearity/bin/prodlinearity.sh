#!/bin/bash
BASEDIR=$(dirname "$0")
DIR="$( cd "$( dirname "$BASEDIR" )" >/dev/null 2>&1 && pwd )"

# Use the prebuild jar
# java -jar ${DIR}/pre-build-jar/ProdLinearity-1.0-SNAPSHOT.jar $@
java -jar ${DIR}/target/ProdLinearity-1.0-SNAPSHOT.jar $@

