#!/bin/sh

set -e

if [ -z "${JAVA_OPTS}" ]; then
    echo "Java application will be started without any custom JAVA_OPTS.. Using defaults.."
else
    echo "Java application will be started with following JAVA_OPTS --> ${JAVA_OPTS}"
fi

if [ "${JVM_DEBUG_ENABLED}" = "true" ]; then
    echo "Starting Java application.. JVM remote debugging has been enabled.."
    java ${JAVA_OPTS} '-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n' -jar app.jar
else
    echo "Starting Java application.."
    java ${JAVA_OPTS} -jar app.jar
fi
