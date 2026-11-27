-- Base de donnees de la clinique (MySQL).
-- Executer avec : mysql -uroot -p < database/001-migration.sql

CREATE DATABASE IF NOT EXISTS clinique
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE clinique;

CREATE TABLE IF NOT EXISTS patient (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255),
    telephone VARCHAR(255)
);
