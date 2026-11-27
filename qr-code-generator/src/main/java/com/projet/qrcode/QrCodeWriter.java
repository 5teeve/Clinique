package com.projet.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class QrCodeWriter {

    private static final String FORMAT = "JPEG";
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public void prepare() throws IOException {
        Path root = QrConfig.CDN_ROOT;

        try {
            Files.createDirectories(root);
        } catch (AccessDeniedException e) {
            throw new IOException(
                    "impossible de creer " + root + " : permission refusee. "
                            + "Donnez les droits a l'utilisateur courant ou modifiez QrConfig.CDN_ROOT.",
                    e);
        }

        if (!Files.isWritable(root)) {
            throw new IOException("le dossier " + root + " n'est pas accessible en ecriture.");
        }
    }

    public boolean writeIfMissing(PatientDto patient) throws IOException, WriterException {
        Path file = path(patient.id());

        if (Files.exists(file)) {
            return false;
        }

        Files.createDirectories(file.getParent());
        writeImage(publicUrl(patient.id()), file);

        return true;
    }

    public Path path(int patientId) {
        return QrConfig.CDN_ROOT
                .resolve("patient")
                .resolve(String.valueOf(patientId))
                .resolve("qrcode")
                .resolve(patientId + "-qrcode.jpeg");
    }

    private String publicUrl(int patientId) {
        String base = QrConfig.PUBLIC_URL;

        if (base.contains("{id}")) {
            return base.replace("{id}", String.valueOf(patientId));
        }

        return (base.endsWith("/") ? base : base + "/") + patientId;
    }

    private void writeImage(String content, Path file) throws IOException, WriterException {
        Map<EncodeHintType, Object> hints = Map.of(
                EncodeHintType.CHARACTER_SET, "UTF-8",
                EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M,
                EncodeHintType.MARGIN, QrConfig.QR_MARGIN);

        BitMatrix matrix = new MultiFormatWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                QrConfig.QR_SIZE,
                QrConfig.QR_SIZE,
                hints);

        MatrixToImageWriter.writeToPath(
                matrix,
                FORMAT,
                file,
                new MatrixToImageConfig(BLACK, WHITE));
    }
}
