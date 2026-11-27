<?php

declare(strict_types=1);

$titre = $titre ?? 'Erreur';
?>
<h1><?= e($titre) ?></h1>
<p><?= e($message ?? '') ?></p>
<p class="meta">Si le probleme persiste, contactez l'accueil de la clinique.</p>
