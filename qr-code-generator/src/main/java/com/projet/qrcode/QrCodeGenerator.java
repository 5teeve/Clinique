package com.projet.qrcode;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class QrCodeGenerator {

    private QrCodeGenerator() {
    }

    public static void main(String[] args) {
        try {
            run();
        } catch (Exception e) {
            System.err.println("Echec de la generation : " + describe(e));
            System.exit(1);
        }
    }

    private static void run() throws Exception {
        printConfiguration();

        QrCodeWriter writer = new QrCodeWriter();
        writer.prepare();

        List<PatientDto> patients = new PatientApiClient().fetchAll();

        if (patients.isEmpty()) {
            System.out.println("Aucun patient a traiter.");
            return;
        }

        int created = 0;
        int existing = 0;
        int errors = 0;

        for (PatientDto patient : patients) {
            try {
                if (writer.writeIfMissing(patient)) {
                    created++;
                    System.out.printf("[OK]     patient #%d -> %s%n",
                            patient.id(), writer.path(patient.id()));
                } else {
                    existing++;
                    System.out.printf("[EXISTE] patient #%d -> %s%n",
                            patient.id(), writer.path(patient.id()));
                }
            } catch (Exception e) {
                errors++;
                System.err.printf("[ERREUR] patient #%d : %s%n",
                        patient.id(), describe(e));
            }
        }

        System.out.println();
        System.out.printf("Termine : %d cree(s), %d ignore(s), %d erreur(s).%n",
                created, existing, errors);

        if (errors > 0) {
            System.exit(1);
        }
    }

    private static void printConfiguration() {
        System.out.println("API patients : " + QrConfig.API_URL);
        System.out.println("URL publique : " + QrConfig.PUBLIC_URL);
        System.out.println("Dossier CDN  : " + QrConfig.CDN_ROOT);
        System.out.println("Taille QR    : " + QrConfig.QR_SIZE + " px");
        System.out.println();
    }

    private static String describe(Exception e) {
        if (e instanceof FileSystemException fileError && fileError.getFile() != null) {
            String reason = fileError.getReason();
            return (reason == null || reason.isBlank() ? "erreur de fichier" : reason)
                    + " : " + fileError.getFile();
        }

        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }
}
