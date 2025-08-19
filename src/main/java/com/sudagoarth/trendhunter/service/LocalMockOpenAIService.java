package com.sudagoarth.trendhunter.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/** Deterministic fake embeddings for local/dev testing (no external calls). */
@Service
@Profile({"dev","local","default"})
@ConditionalOnProperty(prefix = "ai", name = "provider", havingValue = "mock", matchIfMissing = true)
public class LocalMockOpenAIService extends OpenAIService {

    private static final int DIMS = 1536; // keep well below 2000
    @Override public boolean isConfigured() { return true; }

    @Override
    public float[] embedText(String text) {
        // simple hash-based pseudo-embedding
        byte[] b = text == null ? new byte[0] : text.getBytes(StandardCharsets.UTF_8);
        long seed = 1125899906842597L;
        for (byte value : b) seed = 31 * seed + value;
        Random r = new Random(seed);

        float[] v = new float[DIMS];
        double norm = 0.0;
        for (int i = 0; i < DIMS; i++) {
            v[i] = (float) (r.nextGaussian());
            norm += v[i] * v[i];
        }
        norm = Math.sqrt(norm);
        for (int i = 0; i < DIMS; i++) v[i] /= norm; // unit length
        return v;
    }
}
