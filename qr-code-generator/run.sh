#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

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

mvn -q clean package

exec java -jar target/qr-code-generator.jar
