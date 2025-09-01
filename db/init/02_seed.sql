-- ============== DATOS DE PRUEBA ==================
INSERT INTO franchise (name) VALUES ('TechFoods') ON CONFLICT DO NOTHING;
INSERT INTO franchise (name) VALUES ('EcoMarket') ON CONFLICT DO NOTHING;

-- Tomamos IDs para insertar sucursales/productos
WITH f AS (SELECT id FROM franchise WHERE name = 'TechFoods' LIMIT 1)
INSERT INTO branch (franchise_id, name)
SELECT f.id, v.name
FROM f, (VALUES ('Centro'), ('Norte')) AS v(name)
ON CONFLICT DO NOTHING;

WITH f AS (SELECT id FROM franchise WHERE name = 'EcoMarket' LIMIT 1)
INSERT INTO branch (franchise_id, name)
SELECT f.id, v.name
FROM f, (VALUES ('Sur'), ('Este')) AS v(name)
ON CONFLICT DO NOTHING;

-- Productos
WITH b AS (SELECT id FROM branch WHERE name='Centro' LIMIT 1)
INSERT INTO product (branch_id, name, stock)
SELECT b.id, v.name, v.stock
FROM b, (VALUES ('Manzanas', 50), ('Peras', 35)) AS v(name, stock)
ON CONFLICT DO NOTHING;

WITH b AS (SELECT id FROM branch WHERE name='Norte' LIMIT 1)
INSERT INTO product (branch_id, name, stock)
SELECT b.id, v.name, v.stock
FROM b, (VALUES ('Pan', 100), ('Leche', 80)) AS v(name, stock)
ON CONFLICT DO NOTHING;