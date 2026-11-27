package com.projet.qrcode;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

public final class PatientApiClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final JavaType PATIENT_LIST_TYPE = MAPPER.getTypeFactory()
            .constructCollectionType(List.class, PatientDto.class);

    public List<PatientDto> fetchAll() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder(URI.create(QrConfig.API_URL))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new IOException("l'API a repondu HTTP " + response.statusCode());
        }

        List<PatientDto> patients = MAPPER.readValue(response.body(), PATIENT_LIST_TYPE);
        return patients == null ? List.of() : patients;
    }
}
