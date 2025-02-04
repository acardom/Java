DROP TABLE IF EXISTS `alumno_se_matricula_asignatura`;
DROP TABLE IF EXISTS `profesor`;
DROP TABLE IF EXISTS `departamento`;
DROP TABLE IF EXISTS `curso_escolar`;
DROP TABLE IF EXISTS `asignatura`;
DROP TABLE IF EXISTS `persona`;
DROP TABLE IF EXISTS `grado`;

DROP DATABASE IF EXISTS `universidad`;

CREATE DATABASE IF NOT EXISTS `universidad` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `universidad`;

CREATE TABLE `grado` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `nombre` varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 11 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `persona` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `nif` varchar(9) DEFAULT NULL,
    `nombre` varchar(25) NOT NULL,
    `apellido1` varchar(50) NOT NULL,
    `apellido2` varchar(50) DEFAULT NULL,
    `ciudad` varchar(25) NOT NULL,
    `direccion` varchar(50) NOT NULL,
    `telefono` varchar(9) DEFAULT NULL,
    `fecha_nacimiento` date NOT NULL,
    `sexo` enum('H', 'M') NOT NULL,
    `tipo` enum('profesor', 'alumno') NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `nif` (`nif`)
) ENGINE = InnoDB AUTO_INCREMENT = 25 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `asignatura` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `nombre` varchar(100) NOT NULL,
    `creditos` int unsigned NOT NULL,
    `id_grado` int unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `id_grado` (`id_grado`),
    CONSTRAINT `asignatura_ibfk_1` FOREIGN KEY (`id_grado`) REFERENCES `grado` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 11 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `curso_escolar` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `nombre` varchar(9) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 6 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `departamento` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `nombre` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `profesor` (
    `id_profesor` int unsigned NOT NULL,
    `id_departamento` int unsigned NOT NULL,
    `nombre` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id_profesor`),
    KEY `id_departamento` (`id_departamento`),
    CONSTRAINT `profesor_ibfk_1` FOREIGN KEY (`id_profesor`) REFERENCES `persona` (`id`),
    CONSTRAINT `profesor_ibfk_2` FOREIGN KEY (`id_departamento`) REFERENCES `departamento` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE `alumno_se_matricula_asignatura` (
    `id_alumno` int unsigned NOT NULL,
    `id_asignatura` int unsigned NOT NULL,
    `id_curso_escolar` int unsigned NOT NULL,
    PRIMARY KEY (`id_alumno`, `id_asignatura`, `id_curso_escolar`),
    KEY `id_asignatura` (`id_asignatura`),
    KEY `id_curso_escolar` (`id_curso_escolar`),
    CONSTRAINT `alumno_se_matricula_asignatura_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `persona` (`id`),
    CONSTRAINT `alumno_se_matricula_asignatura_ibfk_2` FOREIGN KEY (`id_asignatura`) REFERENCES `asignatura` (`id`),
    CONSTRAINT `alumno_se_matricula_asignatura_ibfk_3` FOREIGN KEY (`id_curso_escolar`) REFERENCES `curso_escolar` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;