/*INSERTS*/
/* Insert table Sctrp
INSERT INTO sctrp (id, codigo, emision, vencimiento) VALUES(1, '7010511700767P', '2020-07-01', '2020-07-31');
INSERT INTO sctrp (id, codigo, emision, vencimiento) VALUES(2, '7010511700768P', '2020-07-01', '2020-07-31');
*/

/* Insert table Sctrs
INSERT INTO sctrs (id, codigo, emision, vencimiento) VALUES(1, '7010511700767S', '2020-07-01', '2020-07-31');
INSERT INTO sctrs (id, codigo, emision, vencimiento) VALUES(2, '7010511700768S', '2020-07-01', '2020-07-31');
*/

/* Insert table Carnets
INSERT INTO carnets (id, codigo, emision, vencimiento) VALUES(1, '000000', '2020-07-01', '2020-07-31');
INSERT INTO carnets (id, codigo, emision, vencimiento) VALUES(2, '021745', '2020-07-01', '2020-07-31');
INSERT INTO carnets (id, codigo, emision, vencimiento) VALUES(3, '021746', '2020-07-01', '2020-07-31');
INSERT INTO carnets (id, codigo, emision, vencimiento) VALUES(4, '021747', '2020-07-01', '2020-07-31');
INSERT INTO carnets (id, codigo, emision, vencimiento) VALUES(5, '021748', '2020-07-01', '2020-07-31');
INSERT INTO carnets (id, codigo, emision, vencimiento) VALUES(6, '021749', '2020-07-01', '2020-07-31');
*/

/* Insert table Inducciones
INSERT INTO inducciones (id, codigo, emision, vencimiento) VALUES(1, '000000', '2020-01-01', '2020-12-31');
INSERT INTO inducciones (id, codigo, emision, vencimiento) VALUES(2, 'I02', '2020-02-01', '2020-02-28');
INSERT INTO inducciones (id, codigo, emision, vencimiento) VALUES(3, 'I03', '2020-03-01', '2020-03-31');
INSERT INTO inducciones (id, codigo, emision, vencimiento) VALUES(4, 'I04', '2020-04-01', '2020-04-30');
INSERT INTO inducciones (id, codigo, emision, vencimiento) VALUES(5, 'I05', '2020-05-01', '2020-05-31');
INSERT INTO inducciones (id, codigo, emision, vencimiento) VALUES(6, 'I06', '2020-06-01', '2020-06-30');
*/

/* Insert table documentos
INSERT INTO documentos (id, nombre) VALUES(1, 'DNI');
INSERT INTO documentos (id, nombre) VALUES(2, 'PAS');
INSERT INTO documentos (id, nombre) VALUES(3, 'PTP');
INSERT INTO documentos (id, nombre) VALUES(4, 'PEX');
INSERT INTO documentos (id, nombre) VALUES(5, 'CDI');
*/

/* Insert table colaboradores
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(1, 'MARGARET CRISTIN', 'ZAMBRANO  PARICA', 1, '00388803', 'Frozen', 1, 1, 1, 2);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(2, 'RAFAEL ANTONIO', 'SUAREZ TORREALBA', 2, '28478463', 'Frozen', 1, 1, 2, 3);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(3, 'MICHELL ALFONZO', 'SERMEÑO  ESCOBAR', 3, '2374748', 'Frozen', 1, 1, 3, 4);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(4, 'YEISON ABEL', 'BAUTISTA ARCIA', 4, '109318846', 'Frozen', 2, 2, 4, 5);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(5, 'JUAN JOSE', 'BRUCE ALVARADO', 5, '20446299', 'Frozen', 2, 2, 5, 1);

INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(6, 'CRISTIN', 'ZAMBRANO  PARICA', 1, '00388803', 'Frozen', 1, 1, 1, 2);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(7, 'ANTONIO', 'SUAREZ TORREALBA', 2, '28478463', 'Frozen', 1, 1, 2, 3);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(8, 'ALFONZO', 'SERMEÑO  ESCOBAR', 3, '2374748', 'Frozen', 1, 1, 3, 4);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(9, 'ABEL', 'BAUTISTA ARCIA', 4, '109318846', 'Frozen', 2, 2, 4, 5);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(10, 'JOSE', 'BRUCE ALVARADO', 5, '20446299', 'Frozen', 2, 2, 5, 1);

INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(11, 'MARGARET', 'ZAMBRANO  PARICA', 1, '00388803', 'Frozen', 1, 1, 1, 2);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(12, 'RAFAEL', 'SUAREZ TORREALBA', 2, '28478463', 'Frozen', 1, 1, 2, 3);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(13, 'MICHELL', 'SERMEÑO  ESCOBAR', 3, '2374748', 'Frozen', 1, 1, 3, 4);
INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(14, 'YEISON', 'BAUTISTA ARCIA', 4, '109318846', 'Frozen', 2, 2, 4, 5);
--INSERT INTO colaboradores (id, nombre, apellido, documento_id, numero_doc, empresa, sctrp_id, sctrs_id, carnets_id, induccion_id)  VALUES(15, 'JUAN', 'BRUCE ALVARADO', 5, '20446299', 'Frozen', 2, 2, 5, 1);
*/

/*  Tablas para agregar seguridad
INSERT INTO users (id, username, password, enabled) VALUES(1, 'SUPERVI01', '$2a$10$HEx.6yYHOW.3/xssX2an8O/dXpgiicxPNKs3oPgHpc7AZ.yVXvWnS', True);
INSERT INTO users (id, username, password, enabled) VALUES(2, 'SUPERVI02', '$2a$10$p/sSjNd9D4jFo2loc93hi.K7PTXCDXkyN6vIjAv8m0.kZ5G1rLvuy', True);
INSERT INTO users (id, username, password, enabled) VALUES(3, 'SUPERVI03', '$2a$10$virCz/DLGYCH5v7CHZXgA.QwJIgmCSIbULLDrPd6KCfmCmPhYL3da', True);
INSERT INTO users (id, username, password, enabled) VALUES(4, 'SUPERVI04', '$2a$10$UU9X1l13hHZuQbV8Bh.UY.22KiQNc/PK0dA6mZ/4lgp5GwpzunKMW', True);
INSERT INTO users (id, username, password, enabled) VALUES(5, 'SUPERVI05', '$2a$10$q97POk8q9L.D0sKGOHcZYufmTqD9bD6FHzPpo6uyWLzjZcBe9XFdy', True);
INSERT INTO users (id, username, password, enabled) VALUES(6, 'Leslie', '$2a$10$2KhsPMWZKx.Bo3waK0JEjOcKNmSqaN3r54HQkrVishG5cxLL32GRS', True);

INSERT INTO authorities (id, user_id, authority) VALUES(1, 1, 'ROLE_USER');
INSERT INTO authorities (id, user_id, authority) VALUES(2, 2, 'ROLE_USER');
INSERT INTO authorities (id, user_id, authority) VALUES(3, 3, 'ROLE_USER');
INSERT INTO authorities (id, user_id, authority) VALUES(4, 4, 'ROLE_USER');
INSERT INTO authorities (id, user_id, authority) VALUES(5, 5, 'ROLE_USER');
INSERT INTO authorities (id, user_id, authority) VALUES(6, 6, 'ROLE_USER');
INSERT INTO authorities (id, user_id, authority) VALUES(7, 6, 'ROLE_ADMIN');
 */