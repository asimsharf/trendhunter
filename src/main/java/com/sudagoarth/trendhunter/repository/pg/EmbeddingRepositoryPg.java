package com.sudagoarth.trendhunter.repository.pg;

import com.sudagoarth.trendhunter.repository.EmbeddingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EmbeddingRepositoryPg implements EmbeddingRepository {

    private final JdbcTemplate jdbc;

    private static String toVectorLiteral(float[] v) {
        // pgvector accepts: '[1,2,3]' (no spaces)
        StringBuilder sb = new StringBuilder(v.length * 6);
        sb.append('[');
        for (int i = 0; i < v.length; i++) {
            if (i > 0) sb.append(',');
            // keep as float text; avoid scientific formatting for typical ranges
            sb.append(Float.toString(v[i]));
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public void upsert(UUID productId, float[] vector) {
        String vec = toVectorLiteral(vector);
        jdbc.update(
            """
            INSERT INTO product_embedding(product_id, embedding)
            VALUES (?, ?::vector)
            ON CONFLICT (product_id) DO UPDATE SET embedding = EXCLUDED.embedding
            """,
            ps -> {
                ps.setObject(1, productId);
                ps.setString(2, vec);
            }
        );
    }

    @Override
    public List<UUID> similar(float[] query, int limit) {
        String vec = toVectorLiteral(query);
        return jdbc.query(
            """
            SELECT product_id
            FROM product_embedding
            ORDER BY embedding <=> ?::vector
            LIMIT ?
            """,
            ps -> {
                ps.setString(1, vec);
                ps.setInt(2, limit);
            },
            (rs, i) -> (UUID) rs.getObject("product_id")
        );
    }
}
