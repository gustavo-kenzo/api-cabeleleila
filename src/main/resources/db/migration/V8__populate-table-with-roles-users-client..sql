-- Defini roles ADMIN e CLIENT --
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_CLIENT');

-- Cria users Leila and Client --
INSERT INTO users (name, email, password)
VALUES ('Leila', 'leila@email.com', '$2a$10$LNShYRoJP4M2JtL.4k5vJ.VX2oTMrsEDV.KMLdwOV6/JzoFzKm2di');
INSERT INTO users (name, email, password)
VALUES ('Client', 'client@email.com', '$2a$10$LNShYRoJP4M2JtL.4k5vJ.VX2oTMrsEDV.KMLdwOV6/JzoFzKm2di');

-- Associa cada user com um role --
INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE u.email = 'leila@email.com';

INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON r.name = 'ROLE_CLIENT'
WHERE u.email = 'user@email.com';

-- Cria cliente padrão Client --
INSERT INTO clients (id, name, email, phone, active, user_id)
VALUES (
    1,
    'client',
    'client@email.com',
    '(11) 91122-3344',
    1,
    (SELECT id FROM users WHERE name = 'user')
);