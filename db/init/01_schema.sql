-- Extensión para generar UUID (PostgreSQL ≥ 13)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ============== TABLAS =================

-- 1) Franquicia
CREATE TABLE IF NOT EXISTS franchise (
  id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name        VARCHAR(150) NOT NULL,
  created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
  updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
  CONSTRAINT uq_franchise_name UNIQUE (name)
);

-- 2) Sucursal (pertenece a una franquicia)
CREATE TABLE IF NOT EXISTS branch (
  id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  franchise_id  UUID NOT NULL REFERENCES franchise(id) ON DELETE CASCADE,
  name          VARCHAR(150) NOT NULL,
  created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
  updated_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
  -- Evita nombres duplicados de sucursal dentro de la MISMA franquicia
  CONSTRAINT uq_branch_name_per_franchise UNIQUE (franchise_id, name)
);

-- 3) Producto (pertenece a una sucursal)
CREATE TABLE IF NOT EXISTS product (
  id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  branch_id  UUID NOT NULL REFERENCES branch(id) ON DELETE CASCADE,
  name       VARCHAR(150) NOT NULL,
  stock      INTEGER NOT NULL DEFAULT 0 CHECK (stock >= 0),
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  -- Evita nombres duplicados de producto dentro de la MISMA sucursal
  CONSTRAINT uq_product_name_per_branch UNIQUE (branch_id, name)
);

-- ============== TRIGGERS updated_at ==============
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER trg_franchise_updated
BEFORE UPDATE ON franchise FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_branch_updated
BEFORE UPDATE ON branch FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_product_updated
BEFORE UPDATE ON product FOR EACH ROW EXECUTE FUNCTION set_updated_at();