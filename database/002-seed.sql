-- Donnees de demonstration.
-- Executer avec : mysql -uroot -p clinique < database/002-seed.sql
-- INSERT IGNORE : reexecutable sans creer de doublons.

USE clinique;

INSERT IGNORE INTO patient (id, nom, telephone) VALUES
    (1, 'Rakoto', '033 45 977 22'),
    (2, 'Rasoa',  '034 92 876 11'),
    (3, 'Rabe',   '032 23 499 01'),
    (4, 'Rado',   '038 13 789 21');
