#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo "Endpoint PHP : http://127.0.0.1:8000/patient/{id}"
exec php -S 127.0.0.1:8000 -t public public/router.php
