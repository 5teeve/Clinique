<?php

declare(strict_types=1);

$chemin = parse_url($_SERVER['REQUEST_URI'] ?? '/', PHP_URL_PATH);
$chemin = is_string($chemin) ? $chemin : '/';

if ($chemin !== '/' && $chemin !== '') {
    $fichier = realpath(__DIR__ . $chemin);
    $racine = realpath(__DIR__);

    if ($fichier !== false
        && $racine !== false
        && str_starts_with($fichier, $racine . DIRECTORY_SEPARATOR)
        && is_file($fichier)) {
        return false;
    }
}

require __DIR__ . '/index.php';
