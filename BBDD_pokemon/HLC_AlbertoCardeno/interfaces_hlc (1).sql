-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-10-2022 a las 18:48:13
-- Versión del servidor: 10.4.25-MariaDB
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `interfaces_hlc`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `arma`
--

CREATE TABLE `arma` (
  `Tipo` varchar(20) DEFAULT NULL,
  `Cadencia` varchar(20) DEFAULT NULL,
  `Daño` varchar(20) DEFAULT NULL,
  `Usuario` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `arma`
--

INSERT INTO `arma` (`Tipo`, `Cadencia`, `Daño`, `Usuario`) VALUES
('190', '50', 'aaa', 'aaa'),
('Altura', 'Peso', 'Raza', 'Nombre de Usuario');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE `cuenta` (
  `Nombre` varchar(20) DEFAULT NULL,
  `Apellido` varchar(20) DEFAULT NULL,
  `Gmail` varchar(20) DEFAULT NULL,
  `Usuario` varchar(20) NOT NULL,
  `Contraseña` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cuenta`
--

INSERT INTO `cuenta` (`Nombre`, `Apellido`, `Gmail`, `Usuario`, `Contraseña`) VALUES
('aaa', 'aaa', 'aaa@gmail.com', 'aaa', '1234245'),
('Nombre', 'Apellido', 'Gmail@gmail.com', 'Nombre de Usuario', 'Contraseña');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personaje`
--

CREATE TABLE `personaje` (
  `Raza` varchar(20) DEFAULT NULL,
  `Peso` varchar(20) DEFAULT NULL,
  `Altura` varchar(20) DEFAULT NULL,
  `Usuario` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `personaje`
--

INSERT INTO `personaje` (`Raza`, `Peso`, `Altura`, `Usuario`) VALUES
('aaa', '12', '120', 'aaa'),
('Tipo', 'Cadencia', 'Daño', 'Nombre de Usuario');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `arma`
--
ALTER TABLE `arma`
  ADD PRIMARY KEY (`Usuario`);

--
-- Indices de la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD PRIMARY KEY (`Usuario`);

--
-- Indices de la tabla `personaje`
--
ALTER TABLE `personaje`
  ADD PRIMARY KEY (`Usuario`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD CONSTRAINT `cuenta_ibfk_1` FOREIGN KEY (`Usuario`) REFERENCES `arma` (`Usuario`),
  ADD CONSTRAINT `cuenta_ibfk_2` FOREIGN KEY (`Usuario`) REFERENCES `personaje` (`Usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
