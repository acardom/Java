USE `universidad`;

DELETE FROM `alumno_se_matricula_asignatura`;
DELETE FROM `persona`;
DELETE FROM `asignatura`;
DELETE FROM `curso_escolar`;
DELETE FROM `departamento`;
DELETE FROM `grado`;

LOCK TABLES `curso_escolar` WRITE;
INSERT INTO `curso_escolar` (`id`, `nombre`) VALUES (1,'2014-2015'),(2,'2015-2016'),(3,'2016-2017'),(4,'2017-2018'),(5,'2018-2019');
UNLOCK TABLES;

LOCK TABLES `departamento` WRITE;
INSERT INTO `departamento` (`id`, `nombre`) VALUES (1,'Informática'),(2,'Matemáticas'),(3,'Economía y Empresa'),(4,'Educación'),(5,'Agronomía'),(6,'Química y Física'),(7,'Filología'),(8,'Derecho'),(9,'Biología y Geología');
UNLOCK TABLES;

LOCK TABLES `grado` WRITE;
INSERT INTO `grado` (`id`, `nombre`) VALUES (1,'Grado en Ingeniería Agrícola (Plan 2015)'),(2,'Grado en Ingeniería Eléctrica (Plan 2014)'),(3,'Grado en Ingeniería Electrónica Industrial (Plan 2010)'),(4,'Grado en Ingeniería Informática (Plan 2015)'),(5,'Grado en Ingeniería Mecánica (Plan 2010)'),(6,'Grado en Ingeniería Química Industrial (Plan 2010)'),(7,'Grado en Biotecnología (Plan 2015)'),(8,'Grado en Ciencias Ambientales (Plan 2009)'),(9,'Grado en Matemáticas (Plan 2010)'),(10,'Grado en Química (Plan 2009)');
UNLOCK TABLES;

LOCK TABLES `persona` WRITE;
INSERT INTO `persona` (`id`, `nif`, `nombre`, `apellido1`, `apellido2`, `ciudad`, `direccion`, `telefono`, `fecha_nacimiento`, `sexo`, `tipo`) VALUES 
(1,'12345678A','Juan','Pérez','García','Madrid','Calle Falsa 123','600123456','1990-01-01','H','alumno'),
(2,'23456789B','Ana','López','Martínez','Barcelona','Avenida Siempre Viva 742','600234567','1991-02-02','M','alumno'),
(3,'34567890C','Luis','González','Rodríguez','Valencia','Plaza Mayor 1','600345678','1992-03-03','H','profesor'),
(4,'45678901D','María','Hernández','Sánchez','Sevilla','Calle Real 456','600456789','1993-04-04','M','profesor'),
(5,'38223286T','David','Schmidt','Fisher','Almería','C/ Venus','678516294','1978-01-19','H','profesor'),
(6,'04233869Y','José','Koss','Bayer','Almería','C/ Júpiter','628349590','1998-01-28','H','alumno'),
(7,'97258166K','Ismael','Strosin','Turcotte','Almería','C/ Neptuno',NULL,'1999-05-24','H','alumno'),
(8,'79503962T','Cristina','Lemke','Rutherford','Almería','C/ Saturno','669162534','1977-08-21','M','profesor'),
(9,'82842571K','Ramón','Herzog','Tremblay','Almería','C/ Urano','626351429','1996-11-21','H','alumno'),
(10,'61142000L','Esther','Spencer','Lakin','Almería','C/ Plutón',NULL,'1977-05-19','M','profesor'),
(11,'46900725E','Daniel','Herman','Pacocha','Almería','C/ Andarax','679837625','1997-04-26','H','alumno'),
(12,'85366986W','Carmen','Streich','Hirthe','Almería','C/ Almanzora',NULL,'1971-04-29','M','profesor'),
(13,'73571384L','Alfredo','Stiedemann','Morissette','Almería','C/ Guadalquivir','950896725','1980-02-01','H','profesor'),
(14,'82937751G','Manolo','Hamill','Kozey','Almería','C/ Duero','950263514','1977-01-02','H','profesor'),
(15,'80502866Z','Alejandro','Kohler','Schoen','Almería','C/ Tajo','668726354','1980-03-14','H','profesor'),
(16,'10485008K','Antonio','Fahey','Considine','Almería','C/ Sierra de los Filabres',NULL,'1982-03-18','H','profesor'),
(17,'85869555K','Guillermo','Ruecker','Upton','Almería','C/ Sierra de Gádor',NULL,'1973-05-05','H','profesor'),
(18,'04326833G','Micaela','Monahan','Murray','Almería','C/ Veleta','662765413','1976-02-25','H','profesor'),
(19,'11578526G','Inma','Lakin','Yundt','Almería','C/ Picos de Europa','678652431','1998-09-01','M','alumno'),
(20,'79221403L','Francesca','Schowalter','Muller','Almería','C/ Quinto pino',NULL,'1980-10-31','H','profesor'),
(21,'79089577Y','Juan','Gutiérrez','López','Almería','C/ Los pinos','678652431','1998-01-01','H','alumno'),
(22,'41491230N','Antonio','Domínguez','Guerrero','Almería','C/ Cabo de Gata','626652498','1999-02-11','H','alumno'),
(23,'64753215G','Irene','Hernández','Martínez','Almería','C/ Zapillo','628452384','1996-03-12','M','alumno'),
(24,'85135690V','Sonia','Gea','Ruiz','Almería','C/ Mercurio','678812017','1995-04-13','M','alumno');
UNLOCK TABLES;

LOCK TABLES `asignatura` WRITE;
INSERT INTO `asignatura` (`id`, `nombre`, `creditos`, `id_grado`) VALUES 
(1,'Matemáticas I',6,1),
(2,'Física I',6,1),
(3,'Química I',6,1),
(4,'Programación I',6,4),
(5,'Bases de Datos I',6,4);
UNLOCK TABLES;

LOCK TABLES `alumno_se_matricula_asignatura` WRITE;
INSERT INTO `alumno_se_matricula_asignatura` (`id_alumno`, `id_asignatura`, `id_curso_escolar`) VALUES 
(1,1,1),(2,1,1),(4,1,1),
(1,2,1),(2,2,1),(4,2,1),
(1,3,1),(2,3,1),(4,3,1),
(1,4,1),(2,4,1),(4,4,1),
(1,5,1),(2,5,1),(4,5,1);
UNLOCK TABLES;

LOCK TABLES `profesor` WRITE;
INSERT INTO `profesor` (`id_profesor`, `id_departamento`, `nombre`) VALUES 
(3,1,'Juan'),
(5,2,'Juan'),
(8,3,'Pablo'),
(10,4,'Sergio'),
(12,4,'Maria'),
(13,6,'Gadea'),
(14,1,'Gadea'),
(15,2,'Laura'),
(16,3,'Silvia'),
(17,4,'Alberto'),
(18,5,'Alberto'),
(20,6,'Jose');
UNLOCK TABLES;