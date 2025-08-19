-- extra fields so we can ingest from external sources and show rich cards
ALTER TABLE products
    ADD COLUMN IF NOT EXISTS source VARCHAR (64),
    ADD COLUMN IF NOT EXISTS external_id VARCHAR (128),
    ADD COLUMN IF NOT EXISTS url TEXT,
    ADD COLUMN IF NOT EXISTS image_url TEXT,
    ADD COLUMN IF NOT EXISTS brand VARCHAR (128),
    ADD COLUMN IF NOT EXISTS category VARCHAR (128),
    ADD COLUMN IF NOT EXISTS price_cents BIGINT,
    ADD COLUMN IF NOT EXISTS currency VARCHAR (8),
    ADD COLUMN IF NOT EXISTS popularity INTEGER DEFAULT 0;

-- helpful uniqueness to avoid duplicates per source
CREATE UNIQUE INDEX IF NOT EXISTS ux_products_source_external
    ON products (COALESCE (source,'?'), COALESCE (external_id,'?'));
