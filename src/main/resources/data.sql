-- data.sql (Version Finale et Simplifiée)

-- 1. On s'assure que l'école 1 existe
INSERT INTO school (id, name, slug)
VALUES (1, 'Al Amal International School', 'al-amal')
ON CONFLICT (id) DO NOTHING;

-- 2. On s'assure que le Niveau 40 ('ce1') lié à l'école 1 existe
INSERT INTO level (id, school_id, slug, name)
VALUES (40, 1, 'ce1', 'CE1')
ON CONFLICT (id) DO NOTHING;