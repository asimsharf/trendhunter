package com.sudagoarth.trendhunter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@ConditionalOnProperty(prefix = "openai", name = "api-key")
public class OpenAIService {

    @Value("${openai.api-key:}")
    private String apiKey;

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns true if an API key is set (env or application.yml).
     */
    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank();
    }

    public float[] embedText(String text) {
        requireConfigured();
        try {
            String body = """
                      { "model": "text-embedding-3-large", "input": %s }
                    """.formatted(mapper.writeValueAsString(text));

            HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://api.openai.com/v1/embeddings")).header("Authorization", "Bearer " + apiKey).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(body)).build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() / 100 != 2) {
                throw new IllegalStateException("OpenAI error: " + res.statusCode() + " - " + res.body());
            }

            JsonNode root = mapper.readTree(res.body());
            JsonNode arr = root.path("data").get(0).path("embedding");
            float[] vec = new float[arr.size()];
            for (int i = 0; i < arr.size(); i++) vec[i] = (float) arr.get(i).asDouble();
            return vec;

        } catch (Exception e) {
            throw new RuntimeException("Embedding call failed", e);
        }
    }

    private void requireConfigured() {
        if (!isConfigured()) {
            throw new IllegalStateException("OpenAI API key is not configured (set OPENAI_API_KEY or openai.api-key).");
        }
    }
}
