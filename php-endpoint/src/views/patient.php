<?php

declare(strict_types=1);

$id = (int) $patient['id'];
$titre = 'Patient #' . $id;
?>
<h1>Patient #<?= $id ?></h1>
<dl>
    <dt>Nom</dt>
    <dd><?= e(texte($patient['nom'] ?? null)) ?></dd>
    <dt>Telephone</dt>
    <dd><?= e(texte($patient['telephone'] ?? null)) ?></dd>
</dl>
<a class="btn" href="<?= e(lienTelephone($patient['telephone'] ?? null)) ?>">Appeler le patient</a>
