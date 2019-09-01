#!/bin/bash
BASEDIR=$(dirname "$0")
DIR="$( cd "$( dirname "$BASEDIR" )" >/dev/null 2>&1 && pwd )"
java -jar ${DIR}/target/ProdLinearity-1.0-SNAPSHOT.jar $@

