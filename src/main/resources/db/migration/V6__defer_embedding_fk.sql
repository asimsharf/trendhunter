-- new Flyway migration, e.g. V6__defer_embedding_fk.sql
ALTER TABLE product_embedding DROP CONSTRAINT product_embedding_product_id_fkey;
ALTER TABLE product_embedding
    ADD CONSTRAINT product_embedding_product_id_fkey
        FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
            DEFERRABLE INITIALLY DEFERRED;
