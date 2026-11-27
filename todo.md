# Sprint 1
- CRUD patient

# Sprint 2
- Programme autonomme (main) [manao select * fotsiny]: repository spring
- QR code associer avec une url (patient/id) + id qui rediriger vers une page php
- QR code en jpeg et utiliser un CDN pour stocker les objets static (images, ...)
- Recupere dans la base, verifie si code qr existant dans le repertoire, si non : cree
- Virtual hosting via apache ou njx

## COMMENT:
- On a une PatientController issue de notre framework:
    - getAll(): annoter API retourne JSON

- Le main autonome:
    - recupere tous les patient (appele l'api getAll(): http client)
    - foreach passion, genereQRcode et sauvegarde
    - chemin sauvegarde: /usr/local/cdn/patient/[id]/qrcode/[id]-qrcode.jpeg