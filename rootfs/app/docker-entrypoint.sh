#!/usr/bin/env bash



#export SENZING_ENGINE_CONFIGURATION_JSON="{\"PIPELINE\": {\"CONFIGPATH\": \"/etc/opt/senzing\",\"LICENSESTRINGBASE64\": \"${SENZING_LICENSE_BASE64_ENCODED}\",\"RESOURCEPATH\": \"/opt/senzing/g2/resources\",\"SUPPORTPATH\": \"/opt/senzing/data\"},\"SQL\": {\"BACKEND\": \"SQL\",\"CONNECTION\": \"postgresql://${POSTGRES_USERNAME:-postgres}:${POSTGRES_PASSWORD:-postgres}@${POSTGRES_HOST:-senzing-postgres}:${POSTGRES_PORT:-5432}:${POSTGRES_DB:-G2}/\"}}"
        
#export SENZING_ENGINE_CONFIGURATION_JSON="{\"PIPELINE\": {\"CONFIGPATH\": \"/etc/opt/senzing\", \"RESOURCEPATH\": \"/opt/senzing/g2/resources\", \"SUPPORTPATH\": \"/opt/senzing/data\" }, \"SQL\": { \"CONNECTION\": \"sqlite3://na:na@/var/opt/senzing/sqlite/G2C.db\"}}"

export SENZING_ENGINE_CONFIGURATION_JSON="{\"PIPELINE\": {\"CONFIGPATH\": \"/etc/opt/senzing\", \"RESOURCEPATH\": \"/opt/senzing/g2/resources\", \"SUPPORTPATH\": \"/opt/senzing/data\" }, \"SQL\": { \"CONNECTION\": \"sqlite3://na:na@/var/opt/senzing/sqlite/G2C.db\"}}"
mvn install

java -jar target/hello-world-app-0.1.jar

echo "$SENZING_ENGINE_CONFIGURATION_JSON"

ls /var/opt/senzing/sqlite
