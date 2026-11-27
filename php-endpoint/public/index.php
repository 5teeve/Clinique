<?php

declare(strict_types=1);

require_once __DIR__ . '/../src/functions.php';
require_once __DIR__ . '/../src/Database.php';
require_once __DIR__ . '/../src/PatientRepository.php';

header('Content-Type: text/html; charset=UTF-8');
header('X-Content-Type-Options: nosniff');
header('Referrer-Policy: no-referrer');
header('Cache-Control: no-store, max-age=0');

$chemin = parse_url($_SERVER['REQUEST_URI'] ?? '/', PHP_URL_PATH);
$chemin = is_string($chemin) ? rawurldecode($chemin) : '/';
$chemin = $chemin === '' ? '/' : $chemin;

if ($chemin === '/' || $chemin === '/index.php') {
    afficher('home');
    exit;
}

if ($chemin === '/health') {
    header('Content-Type: application/json; charset=UTF-8');
    echo json_encode(['status' => 'ok']);
    exit;
}

$id = null;

if (preg_match('#/patient/([0-9]+)/?$#', $chemin, $correspondances) === 1) {
    $id = (int) $correspondances[1];
} elseif (preg_match('#/patient\.php/?$#', $chemin) === 1) {
    $idBrut = $_GET['id'] ?? null;
    if (is_string($idBrut) && ctype_digit($idBrut)) {
        $id = (int) $idBrut;
    }
}

if ($id === null) {
    afficher('error', [
        'titre' => 'Page introuvable',
        'message' => "L'adresse demandee n'existe pas.",
    ], 404);
    exit;
}

if ($id <= 0) {
    afficher('error', [
        'titre' => 'Identifiant invalide',
        'message' => "L'identifiant du patient doit etre un entier positif.",
    ], 400);
    exit;
}

try {
    $config = require __DIR__ . '/../src/config.php';
    $pdo = Database::connect($config['db']);
    $patients = new PatientRepository($pdo);
    $patient = $patients->findById($id);

    if ($patient === null) {
        afficher('error', [
            'titre' => 'Patient introuvable',
            'message' => 'Aucun patient ne correspond a cet identifiant.',
        ], 404);
        exit;
    }

    afficher('patient', ['patient' => $patient]);
} catch (Throwable $e) {
    error_log('[clinique] ' . $e->getMessage());
    afficher('error', [
        'titre' => 'Service indisponible',
        'message' => 'La fiche patient est temporairement inaccessible.',
    ], 503);
}
