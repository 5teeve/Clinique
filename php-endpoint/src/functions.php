<?php

declare(strict_types=1);

function e(?string $valeur): string
{
    return htmlspecialchars($valeur ?? '', ENT_QUOTES | ENT_SUBSTITUTE, 'UTF-8');
}

function texte(?string $valeur, string $defaut = 'Non renseigne'): string
{
    $valeur = trim((string) $valeur);

    return $valeur === '' ? $defaut : $valeur;
}

function lienTelephone(?string $telephone): string
{
    $chiffres = preg_replace('/[^0-9+]/', '', (string) $telephone) ?? '';

    return 'tel:' . $chiffres;
}

function afficher(string $vue, array $donnees = [], int $statut = 200): void
{
    $fichier = __DIR__ . '/views/' . $vue . '.php';

    if (!is_file($fichier)) {
        throw new RuntimeException('Vue introuvable : ' . $vue);
    }

    http_response_code($statut);
    extract($donnees, EXTR_SKIP);

    ob_start();
    require $fichier;
    $contenu = (string) ob_get_clean();

    require __DIR__ . '/views/layout.php';
}
