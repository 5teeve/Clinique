# Generateur de QR codes - Clinique

Programme Java autonome qui :

1. appelle l'API du framework :
   `GET http://localhost:8080/Clinique/app/api/patients` ;
2. encode dans chaque QR code l'URL publique PHP :
   `http://localhost:8000/patient/{id}` ;
3. sauvegarde le JPEG dans :
   `/usr/local/cdn/patient/{id}/qrcode/{id}-qrcode.jpeg`.

## Configuration

Toute la configuration est dans `src/main/java/com/projet/qrcode/QrConfig.java` :

```java
public static final String API_URL =
        "http://localhost:8080/Clinique/app/api/patients";

public static final String PUBLIC_URL =
        "http://localhost:8000/patient";

public static final Path CDN_ROOT =
        Path.of("/usr/local/cdn");

public static final int QR_SIZE = 512;
public static final int QR_MARGIN = 2;
```

Modifier cette classe pour changer l'URL publique, le dossier CDN ou la taille
des QR codes.

## Execution

```bash
./run.sh
```

Le script compile le JAR executable puis lance la generation.

## Resultat

```text
/usr/local/cdn/patient/1/qrcode/1-qrcode.jpeg
/usr/local/cdn/patient/2/qrcode/2-qrcode.jpeg
...
```

Les QR codes deja presents sont ignores.

Si `/usr/local/cdn` n'est pas accessible en ecriture, creer le dossier et
donner les droits a l'utilisateur courant :

```bash
sudo mkdir -p /usr/local/cdn
sudo chown -R "$USER":"$USER" /usr/local/cdn
```

Ou modifier `QrConfig.CDN_ROOT`.

## Build uniquement

```bash
mvn clean package
java -jar target/qr-code-generator.jar
```
