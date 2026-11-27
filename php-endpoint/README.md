# Endpoint PHP public - Clinique

Ce programme recoit l'URL encodee dans les QR codes :

```text
https://clinique.mg/patient/{id}
```

et affiche la fiche du patient correspondant.

Le endpoint lit directement la table MySQL `patient` avec PDO. Il est
independant de Tomcat : il peut etre heberge sur un virtual host Apache ou
Nginx.

## Routes

| Methode | URL | Resultat |
|---|---|---|
| GET | `/` | Page d'accueil du endpoint |
| GET | `/health` | `{"status":"ok"}` |
| GET | `/patient/{id}` | Fiche patient |
| GET | `/patient.php?id={id}` | Variante sans reecriture d'URL |
| GET | `/patient/999` | 404 si le patient n'existe pas |

## Configuration

Toute la configuration est dans `src/config.php` :

```php
return [
    'db' => [
        'host' => '127.0.0.1',
        'port' => '3306',
        'name' => 'clinique',
        'user' => 'root',
        'password' => 'root',
    ],
];
```

Modifier ce fichier pour changer les acces a la base de donnees.

## Structure

```text
src/
├── config.php            # configuration de la base
├── Database.php          # connexion PDO
├── PatientRepository.php # lecture des patients
├── functions.php         # fonctions utilitaires des vues
└── views/
    ├── layout.php        # gabarit HTML commun
    ├── home.php          # page d'accueil
    ├── patient.php       # fiche patient
    └── error.php         # page d'erreur
```

Les vues ne contiennent que du HTML : les fonctions (`e`, `texte`,
`lienTelephone`, `afficher`) sont dans `src/functions.php`.

## Test local avec le serveur PHP integre

```bash
./run.sh
```

Puis :

```bash
curl http://127.0.0.1:8000/patient/1
```

Le serveur ecoute sur `127.0.0.1:8000`, ce qui correspond a
`QrConfig.PUBLIC_URL` dans `qr-code-generator/src/main/java/com/projet/qrcode/QrConfig.java`.

## Apache

Le fichier `public/.htaccess` active la reecriture :

```apache
RewriteEngine On
RewriteCond %{REQUEST_FILENAME} !-f
RewriteCond %{REQUEST_FILENAME} !-d
RewriteRule ^ index.php [QSA,L]
```

Exemple de virtual host : `apache-vhost.conf.example`.

## Nginx

Exemple de configuration : `nginx.conf.example`.

## Securite

- Les identifiants sont valides et passes dans une requete preparee.
- Les donnees patients sont echappees avant affichage.
- Les erreurs SQL ne sont pas exposees au client.
- La reponse est en `Cache-Control: no-store` pour eviter le cache des
  donnees personnelles.

> Attention : une URL de QR code est publique. Toute personne qui possede
> l'image peut ouvrir la fiche patient. Ne pas y exposer de donnees medicales
> sensibles sans authentification complementaire.
