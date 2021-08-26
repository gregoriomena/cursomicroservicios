INSERT INTO usuarios (username, password, enabled, nombre, apellidos, email) VALUES ('gmena', '$2a$10$iukn9Z1OoYlhc91sa0b3jOyD/fg9Z/fgIN70Wldad8HXXUNP2eEdW', 1, 'Gregorio', 'Mena Rodríguez', 'gmena@hiberus.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellidos, email) VALUES ('pperez', '$2a$10$C5vDoin.h5qNN4IsLrWTLuhrcbzSKPypVDqxQyI/VeXcsv4laAxzq', 1, 'Paco', 'Perez Perez', 'pperez@hiberus.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellidos, email) VALUES ('jgasrcia', '$2a$10$ZZbyFJ0V.byoUgyTWTgrxelo/K8.IstWiI7G8N1.5fdJKTdMn/YGO', 1, 'Juan', 'Garcia Perez', 'jgarcia@hiberus.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (2, 1);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (3, 1);

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (1, 2);