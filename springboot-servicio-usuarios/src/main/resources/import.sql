INSERT INTO usuarios (username, password, enabled, nombre, apellidos, email) VALUES ('gmena', '12345', 1, 'Gregorio', 'Mena Rodríguez', 'gmena@hiberus.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellidos, email) VALUES ('pperez', '12345', 1, 'Paco', 'Perez Perez', 'pperez@hiberus.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellidos, email) VALUES ('jgasrcia', '12345', 1, 'Juan', 'Garcia Perez', 'jgarcia@hiberus.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (2, 1);
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (3, 1);

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (2, 2);