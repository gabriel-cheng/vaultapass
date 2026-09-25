ALTER TABLE "user" ADD COLUMN credentials_updated_at TIMESTAMP;
UPDATE "user" SET credentials_updated_at = created_at WHERE credentials_updated_at IS NULL;
ALTER TABLE "user" ALTER COLUMN credentials_updated_at SET NOT NULL;