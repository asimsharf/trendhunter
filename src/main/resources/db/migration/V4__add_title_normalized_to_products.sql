-- Add the column if it doesn't exist
ALTER TABLE products
    ADD COLUMN IF NOT EXISTS title_normalized varchar(2048);

-- Optional: backfill from title_raw if empty
UPDATE products
SET title_normalized = title_raw
WHERE title_normalized IS NULL;
