<?php

declare(strict_types=1);

final class PatientRepository
{
    public function __construct(private readonly PDO $pdo)
    {
    }

    public function findById(int $id): ?array
    {
        $requete = $this->pdo->prepare(
            'SELECT id, nom, telephone FROM patient WHERE id = :id LIMIT 1'
        );
        $requete->execute(['id' => $id]);

        $patient = $requete->fetch();
        return $patient === false ? null : $patient;
    }
}
