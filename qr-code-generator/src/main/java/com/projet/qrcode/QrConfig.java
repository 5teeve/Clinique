package com.projet.qrcode;

import java.nio.file.Path;

public final class QrConfig {

    public static final String API_URL =
            "http://localhost:8080/Clinique/app/api/patients";

    public static final String PUBLIC_URL =
            "http://localhost:8000/patient";

    public static final Path CDN_ROOT =
            Path.of("/usr/local/cdn");

    public static final int QR_SIZE = 512;
    public static final int QR_MARGIN = 2;

    private QrConfig() {
    }
}
