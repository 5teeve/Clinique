<?php

declare(strict_types=1);

$titre = $titre ?? 'Clinique';
$classeCarte = $statut >= 400 ? 'carte erreur' : 'carte';
?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><?= e($titre) ?> - Clinique</title>
    <style>
        :root {
            --vert: #0f766e;
            --vert-fonce: #115e59;
            --gris: #475569;
            --fond: #f8fafc;
        }
        * { box-sizing: border-box; }
        body {
            margin: 0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            font-family: system-ui, -apple-system, "Segoe UI", sans-serif;
            color: #0f172a;
            background: var(--fond);
            line-height: 1.5;
        }
        header {
            background: var(--vert);
            color: #fff;
            padding: 1rem 1.5rem;
        }
        header div {
            max-width: 640px;
            margin: 0 auto;
            font-weight: 700;
            font-size: 1.2rem;
        }
        main {
            flex: 1;
            width: 100%;
            max-width: 640px;
            margin: 0 auto;
            padding: 2rem 1.5rem;
        }
        .carte {
            background: #fff;
            border: 1px solid #e2e8f0;
            border-radius: 14px;
            padding: 1.5rem;
            box-shadow: 0 2px 8px rgb(15 23 42 / 0.06);
        }
        h1 { margin-top: 0; color: var(--vert-fonce); font-size: 1.5rem; }
        dl { margin: 0; }
        dt { color: var(--gris); font-size: .85rem; text-transform: uppercase; letter-spacing: .03em; }
        dd { margin: .2rem 0 1.1rem; font-size: 1.15rem; font-weight: 600; }
        .btn {
            display: inline-block;
            padding: .65rem 1rem;
            border-radius: 9px;
            background: var(--vert);
            color: #fff;
            text-decoration: none;
            font-weight: 600;
        }
        .btn:hover { background: var(--vert-fonce); }
        .meta { color: var(--gris); font-size: .9rem; }
        .erreur { border-left: 4px solid #b91c1c; }
        footer {
            text-align: center;
            color: var(--gris);
            font-size: .85rem;
            padding: 1.5rem;
        }
    </style>
</head>
<body>
<header><div>Clinique</div></header>
<main>
    <div class="<?= $classeCarte ?>"><?= $contenu ?></div>
</main>
<footer>Fiche accessible via QR code</footer>
</body>
</html>
