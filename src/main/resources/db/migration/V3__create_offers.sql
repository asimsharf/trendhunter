-- src/main/resources/db/migration/V3__create_offers.sql
CREATE TABLE IF NOT EXISTS offers
(
    id
    UUID
    PRIMARY
    KEY,
    product_id
    UUID
    NOT
    NULL
    REFERENCES
    products
(
    id
) ON DELETE CASCADE,
    source varchar
(
    64
),
    url text,
    price numeric
(
    12,
    2
),
    currency varchar
(
    8
),
    in_stock boolean NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT now
(
)
    );

CREATE INDEX IF NOT EXISTS ix_offers_product_id ON offers(product_id);
CREATE INDEX IF NOT EXISTS ix_offers_in_stock ON offers(in_stock);
