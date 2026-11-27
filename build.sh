#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# Le projet cible Java 17. Le JDK par defaut du systeme peut etre Java 8,
# on selectionne donc explicitement un JDK 17 ou plus recent.
jdk_majeur() {
    "$1/bin/javac" -version 2>&1 | sed -E 's/.* ([0-9]+)(\.[0-9]+)*.*/\1/'
}

if [ -n "${JAVA_HOME:-}" ] && [ -x "${JAVA_HOME}/bin/javac" ]; then
    if [ "$(jdk_majeur "${JAVA_HOME}")" -lt 17 ] 2>/dev/null; then
        unset JAVA_HOME
    fi
fi

if [ -z "${JAVA_HOME:-}" ]; then
    for candidate in /usr/lib/jvm/java-21-openjdk-amd64 /usr/lib/jvm/java-17-openjdk-amd64; do
        if [ -x "${candidate}/bin/javac" ]; then
            JAVA_HOME="${candidate}"
            break
        fi
    done
fi

if [ -z "${JAVA_HOME:-}" ]; then
    echo "ERREUR : aucun JDK 17+ trouve. Definissez JAVA_HOME." >&2
    exit 1
fi
export JAVA_HOME
export PATH="${JAVA_HOME}/bin:${PATH}"

TOMCAT_HOME="${TOMCAT_HOME:-/home/steeve/Documents/apache-tomcat-11.0.22}"

echo ">> JDK utilise : ${JAVA_HOME}"
echo ">> Installation du framework maison dans le depot Maven local..."
mvn -q org.apache.maven.plugins:maven-install-plugin:3.1.3:install-file \
    -Dfile=lib/framework.jar \
    -DgroupId=mg.itu.framework \
    -DartifactId=framework \
    -Dversion=1.0.0 \
    -Dpackaging=jar \
    -DgeneratePom=true

echo ">> Compilation et packaging..."
mvn -q clean package

if [ -d "${TOMCAT_HOME}/webapps" ]; then
    cp target/Clinique.war "${TOMCAT_HOME}/webapps/"
    echo "OK : Clinique.war deploye -> http://localhost:8080/Clinique/"
else
    echo "OK : target/Clinique.war genere."
    echo "     TOMCAT_HOME introuvable, copie le WAR dans webapps/ de Tomcat."
fi
