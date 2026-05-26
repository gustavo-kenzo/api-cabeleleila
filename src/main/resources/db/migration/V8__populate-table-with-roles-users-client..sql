-- Defini roles ADMIN e CLIENT --
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_CLIENT');

-- Cria user Leila --
INSERT INTO users (name, email, password)
VALUES ('Leila', 'leila@email.com', '$2a$10$LNShYRoJP4M2JtL.4k5vJ.VX2oTMrsEDV.KMLdwOV6/JzoFzKm2di');

-- Associa user com role --
INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE u.email = 'leila@email.com';