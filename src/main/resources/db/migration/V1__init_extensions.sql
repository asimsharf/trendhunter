-- V1__init_extensions.sql

-- 1) pgvector extension
CREATE EXTENSION IF NOT EXISTS vector;

-- 2) product embeddings table (keep separate from JPA entities)
CREATE TABLE IF NOT EXISTS product_embedding (
                                                 product_id UUID PRIMARY KEY
                                                 REFERENCES products(id) ON DELETE CASCADE,
    embedding  vector(1536)
    );

-- 3) ANN index for cosine similarity
--    Use HNSW to support >2000 dims (IVFFlat caps at 2000)
--    You can tweak m / ef_construction later if needed.
CREATE INDEX IF NOT EXISTS idx_product_embedding_cosine
    ON product_embedding
    USING hnsw (embedding vector_cosine_ops)
    WITH (m = 16, ef_construction = 64);
