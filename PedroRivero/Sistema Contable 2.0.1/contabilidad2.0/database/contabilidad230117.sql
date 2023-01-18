-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-01-2017 a las 04:52:56
-- Versión del servidor: 10.1.13-MariaDB
-- Versión de PHP: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `matahernandezasociados`
--
CREATE DATABASE IF NOT EXISTS `matahernandezasociados` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `matahernandezasociados`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mh_auxiliar`
--

CREATE TABLE `mh_auxiliar` (
  `emp_id` varchar(100) NOT NULL,
  `cta_id` varchar(100) NOT NULL,
  `aux_nombreCta` varchar(100) NOT NULL,
  `aux_tipoCta` varchar(15) NOT NULL,
  `aux_condicion` varchar(3) NOT NULL,
  `aux_id` varchar(100) NOT NULL,
  `aux_nombreAux` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `mh_auxiliar`
--

INSERT INTO `mh_auxiliar` (`emp_id`, `cta_id`, `aux_nombreCta`, `aux_tipoCta`, `aux_condicion`, `aux_id`, `aux_nombreAux`) VALUES
('001', '1', 'ACTIVOS', 'De Titulo', 'No', '', ''),
('003', '1', 'ACTIVOS', 'De Titulo', 'No', '', ''),
('100', '1', 'ACTIVOS', 'De Titulo', 'No', '', ''),
('001', '1150', 'ANTICIPO DE COMPRAS', 'De Movimiento', 'No', '', ''),
('003', '1150', 'ANTICIPO DE COMPRAS', 'De Movimiento', 'No', '', ''),
('001', '1110', 'BANCO DE VENEZUELA', 'De Movimiento', 'No', '', ''),
('003', '1110', 'BANCO DE VENEZUELA', 'De Movimiento', 'No', '', ''),
('999', '1110', 'BANCO DE VENEZUELA', 'De Movimiento', 'No', '', ''),
('100', '1110', 'CAJA', 'De Movimiento', 'No', '', ''),
('001', '1190', 'CARGOS DIFERIDOS', 'De Movimiento', 'No', '', ''),
('003', '1190', 'CARGOS DIFERIDOS', 'De Movimiento', 'No', '', ''),
('100', '1190', 'CARGOS POR IVA', 'De Movimiento', 'No', '', ''),
('001', '11', 'CIRCULANTE', 'De Titulo', 'No', '', ''),
('001', '21', 'CIRCULANTE', 'De Titulo', 'No', '', ''),
('100', '11', 'CIRCULANTE', 'De Titulo', 'No', '', ''),
('003', '11', 'CIRCULANTE', 'De Totales', 'No', '', ''),
('003', '21', 'CIRCULANTE', 'De Totales', 'No', '', ''),
('100', '21', 'CIRCULANTE', 'De Totales', 'No', '', ''),
('003', '4100', 'COMPRAS', 'De Movimiento', 'No', '', ''),
('100', '4200', 'COMPRAS', 'De Movimiento', 'No', '', ''),
('001', '4', 'COSTOS', 'De Titulo', 'No', '', ''),
('003', '4', 'COSTOS', 'De Titulo', 'No', '', ''),
('001', '4000', 'COSTOS DE VENTAS', 'De Movimiento', 'No', '', ''),
('003', '4000', 'COSTOS DE VENTAS', 'De Movimiento', 'No', '', ''),
('100', '4100', 'COSTOS DE VENTAS', 'De Movimiento', 'No', '', ''),
('100', '4', 'COSTOS DE VENTAS', 'De Titulo', 'No', '', ''),
('100', '2190', 'CREDITO POR IVA', 'De Movimiento', 'No', '', ''),
('100', '1120', 'CUENTAS POR COBRAR', 'De Movimiento', 'No', '', ''),
('001', '1120', 'CUENTAS POR COBRAR', 'De Movimiento', 'Si', '', ''),
('003', '1120', 'CUENTAS POR COBRAR', 'De Movimiento', 'Si', '', ''),
('001', '1120', 'CUENTAS POR COBRAR', 'De Movimiento', 'Si', '1121', 'CUENTAS POR COBRAR CLIENTES'),
('003', '1120', 'CUENTAS POR COBRAR', 'De Movimiento', 'Si', '1121', 'CUENTAS POR COBRAR CLIENTES'),
('001', '1120', 'CUENTAS POR COBRAR', 'De Movimiento', 'Si', '1123', 'OTRAS CUENTAS POR COBRAR'),
('003', '1120', 'CUENTAS POR COBRAR', 'De Movimiento', 'Si', '1123', 'OTRAS CUENTAS POR COBRAR'),
('001', '1120', 'CUENTAS POR COBRAR', 'De Movimiento', 'Si', '1122', 'P Y M POLLOS C A'),
('003', '1120', 'CUENTAS POR COBRAR', 'De Movimiento', 'Si', '1122', 'P Y M POLLOS C.A.'),
('100', '2120', 'CUENTAS POR PAGAR', 'De Movimiento', 'No', '', ''),
('001', '2120', 'CUENTAS POR PAGAR', 'De Movimiento', 'Si', '', ''),
('003', '2120', 'CUENTAS POR PAGAR', 'De Movimiento', 'Si', '', ''),
('001', '2120', 'CUENTAS POR PAGAR', 'De Movimiento', 'Si', '2121', 'PROVEEDORES'),
('003', '2120', 'CUENTAS POR PAGAR', 'De Movimiento', 'Si', '2121', 'PROVEEDORES'),
('001', '2120', 'CUENTAS POR PAGAR', 'De Movimiento', 'Si', '2122', 'SOCIOS'),
('003', '2120', 'CUENTAS POR PAGAR', 'De Movimiento', 'Si', '2122', 'SOCIOS'),
('100', '2730', 'DEFICIT', 'De Movimiento', 'No', '', ''),
('001', '1321', 'DEPRECIACION ACUMULADA', 'De Movimiento', 'No', '', ''),
('001', '1331', 'DEPRECIACION ACUMULADA', 'De Movimiento', 'No', '', ''),
('001', '1341', 'DEPRECIACION ACUMULADA', 'De Movimiento', 'No', '', ''),
('003', '1321', 'DEPRECIACION ACUMULADA', 'De Movimiento', 'No', '', ''),
('003', '1331', 'DEPRECIACION ACUMULADA', 'De Movimiento', 'No', '', ''),
('003', '1341', 'DEPRECIACION ACUMULADA', 'De Movimiento', 'No', '', ''),
('100', '1390', 'DEPRECIACION ACUMULADA', 'De Movimiento', 'No', '', ''),
('001', '1330', 'EQUIPOS DE COMPUTACION', 'De Movimiento', 'No', '', ''),
('003', '1330', 'EQUIPOS DE COMPUTACION', 'De Movimiento', 'No', '', ''),
('001', '9000', 'GANANCIAS Y PERDIDAS', 'De Movimiento', 'No', '', ''),
('003', '9000', 'GANANCIAS Y PERDIDAS', 'De Totales', 'No', '', ''),
('100', '9000', 'GANANCIAS Y PERDIDAS', 'De Totales', 'No', '', ''),
('001', '5', 'GASTOS', 'De Titulo', 'No', '', ''),
('003', '5', 'GASTOS', 'De Titulo', 'No', '', ''),
('100', '5', 'GASTOS', 'De Titulo', 'No', '', ''),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '', ''),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5002', 'ARRENDAMIENTO Y CONDOMINIO'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5013', 'BOLSAS Y MATERIAL DE EMPAQUE'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5008', 'CONTABILIDAD'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5004', 'ELECTRICIDAD'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5016', 'FLETES'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5006', 'GASTOS DE DEPRECIACION'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5005', 'GASTOS DE VEHICULOS'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5009', 'GASTOS DIVERSOS'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5015', 'HIDROCENTRO'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5010', 'IMPUESTOS MUNICIPALES'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5012', 'MANTENIMIENTO Y REPARACION'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5014', 'PRESTACIONES SOCIALES'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5003', 'PUBLICIDAD'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5017', 'RETIRO SOCIOS'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5001', 'SUELDOS Y SALARIOS'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5011', 'SUMINISTROS DE OFICINA'),
('100', '5000', 'GASTOS DE ADMINISTRACION', 'De Totales', 'Si', '5007', 'TELEFONO'),
('100', '4300', 'GASTOS DE IMPORTACION', 'De Movimiento', 'No', '', ''),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '', ''),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '', ''),
('999', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '', ''),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5014', 'ANTIGUEDAD'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5014', 'ANTIGUEDAD'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5015', 'BONOS'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5015', 'BONOS'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5002', 'COMISIONES'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5002', 'COMISIONES'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5012', 'COMISIONES Y CARGOS BANCARIOS'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5012', 'COMISIONES Y CARGOS BANCARIOS'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5011', 'EGRESOS VARIOS'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5011', 'EGRESOS VARIOS'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5007', 'FLETES'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5007', 'FLETES'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5010', 'GASTOS DE DEPRECIACION'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5010', 'GASTOS DE DEPRECIACION'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5003', 'GASTOS DE ELECTRICIDAD'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5003', 'GASTOS DE ELECTRICIDAD'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5005', 'GASTOS DE GESTIONES'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5005', 'GASTOS DE GESTIONES'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5006', 'GASTOS DE VEHICULOS'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5006', 'GASTOS DE VEHICULOS'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5001', 'HONORARIOS OPERACIONALES'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5001', 'HONORARIOS PROFESIONALES'),
('999', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5001', 'IMPRESORAS'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5008', 'REPARACION Y MANTENIMIENTO'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5008', 'REPARACION Y MANTENIMIENTO'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5004', 'RETIRO SOCIOS'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5004', 'RETIRO SOCIOS'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5013', 'SUELDOS Y SALARIOS'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5013', 'SUELDOS Y SALARIOS'),
('001', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5009', 'SUMINISTROS DE OFICINA'),
('003', '5000', 'GASTOS OPERACIONALES', 'De Movimiento', 'Si', '5009', 'SUMINISTROS DE OFICINA'),
('001', '2140', 'IMPUESTO S L RENTA P PAGAR', 'De Movimiento', 'No', '', ''),
('003', '2140', 'IMPUESTO S/L RENTA P/PAGAR', 'De Movimiento', 'No', '', ''),
('100', '2130', 'IMPUESTO SOBRE LA RENTA POR PAGAR', 'De Movimiento', 'No', '', ''),
('001', '1160', 'IMPUESTOS RETENIDOS', 'De Movimiento', 'No', '', ''),
('003', '1160', 'IMPUESTOS RETENIDOS', 'De Movimiento', 'No', '', ''),
('001', '3', 'INGRESOS', 'De Titulo', 'No', '', ''),
('003', '3', 'INGRESOS', 'De Titulo', 'No', '', ''),
('100', '3', 'INGRESOS', 'De Totales', 'No', '', ''),
('001', '7000', 'INGRESOS INTERESES CT CT', 'De Movimiento', 'No', '', ''),
('003', '7000', 'INGRESOS INTERESES CT. CT.', 'De Movimiento', 'No', '', ''),
('001', '6000', 'INTERESES BANCARIOS', 'De Movimiento', 'No', '', ''),
('003', '6000', 'INTERESES BANCARIOS', 'De Movimiento', 'No', '', ''),
('100', '1130', 'INVENTARIO DE MERCANCIAS', 'De Movimiento', 'No', '', ''),
('001', '1200', 'INVERSIONES', 'De Movimiento', 'Si', '', ''),
('003', '1200', 'INVERSIONES', 'De Movimiento', 'Si', '', ''),
('001', '1200', 'INVERSIONES', 'De Movimiento', 'Si', '1210', 'GRANJAS P Y M POLLOS C A'),
('003', '1200', 'INVERSIONES', 'De Movimiento', 'Si', '1210', 'GRANJAS P Y M POLLOS C.A.'),
('100', '2191', 'IVA POR PAGAR', 'De Movimiento', 'No', '', ''),
('999', '2000', 'MMMMM', 'De Titulo', 'Si', '', ''),
('100', '1310', 'MOBILIARIO', 'De Movimiento', 'No', '', ''),
('001', '1340', 'MOBILIARIO DE OFICINA', 'De Movimiento', 'No', '', ''),
('003', '1340', 'MOBILIARIO DE OFICINA', 'De Movimiento', 'No', '', ''),
('001', '2110', 'OBLIGACIONES BANCARIAS', 'De Movimiento', 'No', '', ''),
('003', '2110', 'OBLIGACIONES BANCARIAS', 'De Movimiento', 'No', '', ''),
('001', '1320', 'OFICINAS', 'De Movimiento', 'No', '', ''),
('003', '1320', 'OFICINAS', 'De Movimiento', 'No', '', ''),
('001', '2', 'PASIVOS', 'De Titulo', 'No', '', ''),
('003', '2', 'PASIVOS', 'De Titulo', 'No', '', ''),
('100', '2', 'PASIVOS', 'De Titulo', 'No', '', ''),
('001', '2700', 'PATRIMONIO', 'De Movimiento', 'No', '', ''),
('003', '2700', 'PATRIMONIO', 'De Movimiento', 'No', '', ''),
('100', '2700', 'PATRIMONIO', 'De Movimiento', 'No', '', ''),
('001', '27', 'PATRIMONIO', 'De Titulo', 'No', '', ''),
('003', '27', 'PATRIMONIO', 'De Titulo', 'No', '', ''),
('100', '27', 'PATRIMONIO', 'De Titulo', 'No', '', ''),
('001', '270', 'PATRIMONIO', 'De Totales', 'No', '', ''),
('003', '270', 'PATRIMONIO', 'De Totales', 'No', '', ''),
('100', '270', 'PATRIMONIO', 'De Totales', 'No', '', ''),
('100', '13', 'PROPIEDADES Y EQUIPOS', 'De Titulo', 'No', '', ''),
('999', '1120', 'PRUEBA', 'De Movimiento', 'No', '', ''),
('999', '2700', 'PRUEBA', 'De Movimiento', 'No', '', ''),
('999', '4000', 'PRUEBA', 'De Movimiento', 'No', '', ''),
('999', '1000', 'PRUEBA', 'De Movimiento', 'Si', '', ''),
('999', '1000', 'PRUEBA', 'De Movimiento', 'Si', '1100', 'AUXILIAR'),
('001', '2720', 'RESERVA LEGAL', 'De Movimiento', 'No', '', ''),
('003', '2720', 'RESERVA LEGAL', 'De Movimiento', 'No', '', ''),
('100', '2720', 'RESERVA LEGAL', 'De Movimiento', 'No', '', ''),
('100', '2710', 'UTILIDAD NO DISTRIBUIDAS', 'De Movimiento', 'No', '', ''),
('001', '2710', 'UTILIDADES NO DISTRIBUIDAS', 'De Movimiento', 'No', '', ''),
('003', '2710', 'UTILIDADES NO DISTRIBUIDAS', 'De Movimiento', 'No', '', ''),
('100', '3000', 'VENTAS', 'De Movimiento', 'No', '', ''),
('001', '3000', 'VENTAS', 'De Movimiento', 'Si', '', ''),
('003', '3000', 'VENTAS', 'De Movimiento', 'Si', '', ''),
('999', '3000', 'VENTAS', 'De Movimiento', 'Si', '', ''),
('001', '3000', 'VENTAS', 'De Movimiento', 'Si', '3100', 'AVES'),
('003', '3000', 'VENTAS', 'De Movimiento', 'Si', '3100', 'AVES'),
('003', '3000', 'VENTAS', 'De Movimiento', 'Si', '3110', 'SERVICIOS TECNICOS'),
('001', '3000', 'VENTAS', 'De Movimiento', 'Si', '3110', 'SOCIOS TECNICOS'),
('999', '3000', 'VENTAS', 'De Movimiento', 'Si', '3002', 'VENTA EN CHEQUES'),
('999', '3000', 'VENTAS', 'De Movimiento', 'Si', '3001', 'VENTAS DEL MES');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mh_comprobante`
--

CREATE TABLE `mh_comprobante` (
  `emp_id` varchar(100) NOT NULL,
  `cta_id` varchar(100) NOT NULL,
  `aux_id` varchar(100) NOT NULL,
  `comp_id` int(11) NOT NULL DEFAULT '0',
  `comp_fecha` date NOT NULL,
  `comp_concepto` varchar(50) NOT NULL,
  `comp_debe` double DEFAULT '0',
  `comp_haber` double NOT NULL DEFAULT '0',
  `comp_linea` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `mh_comprobante`
--

INSERT INTO `mh_comprobante` (`emp_id`, `cta_id`, `aux_id`, `comp_id`, `comp_fecha`, `comp_concepto`, `comp_debe`, `comp_haber`, `comp_linea`) VALUES
('001', '1110', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 96),
('001', '1110', '', 1, '2016-01-31', 'PRUEBA 1', 5000, 0, 141),
('001', '1120', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 97),
('001', '1120', '1121', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 114),
('001', '1120', '1122', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 115),
('001', '1120', '1123', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 116),
('001', '1150', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 98),
('001', '1160', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 99),
('001', '1190', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 100),
('001', '1200', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 101),
('001', '1200', '1210', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 117),
('001', '1200', '1210', 1, '2016-01-31', 'PRUEBA 1', 0, 5000, 142),
('001', '1320', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 102),
('001', '1321', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 103),
('001', '1330', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 104),
('001', '1331', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 105),
('001', '1340', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 106),
('001', '1341', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 107),
('001', '2110', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 108),
('001', '2120', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 109),
('001', '2120', '2121', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 118),
('001', '2120', '2122', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 119),
('001', '2140', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 110),
('001', '2700', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 111),
('001', '2710', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 112),
('001', '2720', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 113),
('001', '3000', '3100', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 120),
('001', '3000', '3110', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 121),
('001', '4000', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 122),
('001', '5000', '5001', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 123),
('001', '5000', '5002', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 124),
('001', '5000', '5003', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 125),
('001', '5000', '5004', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 126),
('001', '5000', '5005', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 127),
('001', '5000', '5006', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 128),
('001', '5000', '5007', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 129),
('001', '5000', '5008', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 130),
('001', '5000', '5009', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 131),
('001', '5000', '5010', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 132),
('001', '5000', '5011', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 133),
('001', '5000', '5012', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 134),
('001', '5000', '5013', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 135),
('001', '5000', '5014', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 136),
('001', '5000', '5015', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 137),
('001', '6000', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 138),
('001', '7000', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 139),
('001', '9000', '', 0, '2017-00-23', 'Comprobante Inicial', 0, 0, 140),
('003', '1110', '', 1, '2005-01-31', 'ASIENTO DE APERTURA', 206630390, 0, 1),
('003', '1110', '', 2, '2005-01-31', 'CHEQ. # 4265 DIST 2002', 0, 2314500, 12),
('003', '1110', '', 2, '2005-01-31', 'CHEQ. # 40575 SEPARADORES', 0, 4021786, 13),
('003', '1110', '', 2, '2005-01-31', 'CHEQ. # 40578 P Y M POLLOS', 0, 1150000, 14),
('003', '1110', '', 2, '2005-01-31', 'CHEQ. # 40579 DIST 2002', 0, 2288000, 15),
('003', '1110', '', 3, '2005-01-31', 'CHEQ. # 40500 CARLOD GOMEZ', 0, 1207000, 17),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40565 PERSONAL', 0, 270000, 19),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40563 PERSONAL', 0, 400000, 20),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40566 PERSONAL', 0, 350000, 21),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40571 T MELENDEZ', 0, 1300000, 22),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40573 SPERANDIO', 0, 2300000, 23),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40576 F GARCIA', 0, 5000000, 24),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40577', 0, 500000, 25),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40581 D CONDE', 0, 200000, 26),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40584 T MELENDEZ', 0, 700000, 27),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40585 F GARCIA ', 0, 4000000, 28),
('003', '1110', '', 4, '2005-01-31', 'CHEQ. # 40586', 0, 850000, 29),
('003', '1110', '', 2, '2005-02-28', 'CHEQ. # 40591 POLLONAS', 0, 52000000, 36),
('003', '1110', '', 2, '2005-02-28', 'CHEQ. # 40597 POLLITAS', 0, 32678400, 37),
('003', '1110', '', 2, '2005-02-28', 'CHEQ. # 40681 POLLITAS', 0, 32000000, 38),
('003', '1110', '', 3, '2005-02-28', 'DEP. # 25201', 25550000, 0, 41),
('003', '1110', '', 3, '2005-02-28', 'DEP. # 66254', 2280000, 0, 42),
('003', '1110', '', 3, '2005-02-28', 'DEP. # 42636', 12400000, 0, 43),
('003', '1110', '', 3, '2005-02-28', 'DEP. # 209436', 1800000, 0, 44),
('003', '1110', '', 3, '2005-02-28', 'DEP. # 68778', 25550000, 0, 45),
('003', '1110', '', 3, '2005-02-28', 'DEP. # 557878', 15312500, 0, 46),
('003', '1110', '', 3, '2005-02-28', 'DEP. # 5666', 11592299, 0, 47),
('003', '1110', '', 3, '2005-02-28', 'DEP. # 6666', 15000000, 0, 48),
('003', '1110', '', 4, '2005-02-28', 'CHEQ. # 43534', 0, 1025000, 50),
('003', '1110', '', 4, '2005-02-28', 'CHEQ. # 79798', 0, 800000, 51),
('003', '1110', '', 4, '2005-02-28', 'CHEQ. # 809', 0, 2500000, 52),
('003', '1110', '', 4, '2005-02-28', 'CHEQ. # 789', 0, 650000, 53),
('003', '1110', '', 4, '2005-02-28', 'CHEQ. # 798', 0, 400000, 54),
('003', '1110', '', 4, '2005-02-28', 'CHEQ. # 0099', 0, 500000, 55),
('003', '1110', '', 5, '2005-02-28', 'CHEQ. # 999', 0, 2208000, 60),
('003', '1110', '', 5, '2005-02-28', 'CHEQ. # 344', 0, 6708000, 61),
('003', '1110', '', 5, '2005-02-28', 'CHEQ. # ', 0, 2208000, 62),
('003', '1110', '', 6, '2005-02-28', 'CHEQ. # OOII', 0, 1210000, 64),
('003', '1110', '', 6, '2005-02-28', 'CHEQ. # 1444', 0, 2157500, 65),
('003', '1110', '', 6, '2005-02-28', 'CHEQ. # 25588', 0, 4600000, 66),
('003', '1110', '', 6, '2005-02-28', 'CHEQ. # JHJKHK', 0, 10000000, 67),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # HJKHJKJK', 0, 900000, 74),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # JKKJJ', 0, 525000, 75),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # ', 0, 1325000, 76),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # JKL', 0, 1055000, 77),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # ', 0, 2527000, 78),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # HUHK', 0, 1000000, 79),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # VVFF', 0, 550000, 80),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # 88777', 0, 6000000, 81),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # NNHHYY', 0, 1400000, 82),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # GGTT55', 0, 725000, 83),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # BBGG66', 0, 1120000, 84),
('003', '1110', '', 3, '2005-03-31', 'CHEQ. # BGGTT', 0, 150000, 85),
('003', '1110', '', 4, '2005-03-31', 'CHEQ. # HKHKJK', 0, 1446058, 91),
('003', '1110', '', 5, '2005-03-31', 'CHEQ. # MNNM,MM', 0, 30909504, 93),
('003', '1110', '', 5, '2005-03-31', 'CHEQ. # NMMMJJJ', 0, 32661380, 94),
('003', '1120', '1121', 3, '2005-02-28', 'COBROS DEL MES', 0, 109484799, 49),
('003', '1120', '1121', 1, '2005-03-31', 'INGRESOS DEL MES', 170066000, 0, 69),
('003', '1120', '1122', 2, '2005-01-31', 'CARGOS A PY M POLLOS', 9774286, 0, 16),
('003', '1120', '1122', 5, '2005-02-28', 'TRANSFERENCIAS A PY M POLLOS', 11124000, 0, 63),
('003', '1120', '1123', 3, '2005-01-31', 'CARGOS A PY M POLLOS', 1207000, 0, 18),
('003', '1120', '1123', 6, '2005-02-28', 'ASIENTO DE APERTURA', 17967500, 0, 68),
('003', '1120', '1123', 4, '2005-03-31', 'PREST S CHQ ', 1446058, 0, 92),
('003', '1200', '1210', 1, '2005-01-31', 'ASIENTO DE APERTURA', 25000000, 0, 11),
('003', '1340', '', 1, '2005-01-31', 'ASIENTO DE APERTURA', 8166808, 0, 2),
('003', '1341', '', 1, '2005-01-31', 'ASIENTO DE APERTURA', 0, 1149253, 3),
('003', '2110', '', 1, '2005-01-31', 'ASIENTO DE APERTURA', 0, 70000000, 4),
('003', '2120', '2121', 1, '2005-01-31', 'ASIENTO DE APERTURA', 0, 81152211, 5),
('003', '2120', '2121', 1, '2005-02-28', 'INGRESOS DEL MES', 0, 102436720, 34),
('003', '2120', '2121', 2, '2005-02-28', 'PAGO A PROVEEDORES', 102436800, 0, 39),
('003', '2120', '2121', 2, '2005-03-31', 'COMPRAS AVICENTRO AVES', 0, 115749320, 72),
('003', '2120', '2121', 5, '2005-03-31', 'CANCELACION DE PROVEEDORES', 63570884, 0, 95),
('003', '2120', '2122', 1, '2005-01-31', 'ASIENTO DE APERTURA', 0, 20000000, 6),
('003', '2140', '', 1, '2005-01-31', 'ASIENTO DE APERTURA', 0, 5624360, 7),
('003', '2700', '', 1, '2005-01-31', 'ASIENTO DE APERTURA', 0, 30000000, 8),
('003', '2710', '', 1, '2005-01-31', 'ASIENTO DE APERTURA', 0, 30150306, 9),
('003', '2720', '', 1, '2005-01-31', 'ASIENTO DE APERTURA', 0, 1721068, 10),
('003', '3000', '3100', 1, '2005-03-31', 'INGRESOS DEL MES', 0, 165796000, 70),
('003', '3000', '3110', 1, '2005-03-31', 'INGRESOS DEL MES', 0, 4270000, 71),
('003', '4100', '', 1, '2005-02-28', 'COMPRAS DEL MES', 102436720, 0, 35),
('003', '4100', '', 2, '2005-02-28', 'COMPRAS DEL MES', 14241600, 0, 40),
('003', '4100', '', 2, '2005-03-31', 'COMPRAS AVICENTRO AVES', 115749320, 0, 73),
('003', '5000', '5001', 4, '2005-01-31', 'PAGOS S/ CHEQUES', 14650000, 0, 30),
('003', '5000', '5001', 4, '2005-02-28', 'PAGOS S/ CHEQUES', 1150000, 0, 56),
('003', '5000', '5001', 3, '2005-03-31', 'PAGOS S/ CHEQUES', 3475000, 0, 86),
('003', '5000', '5004', 4, '2005-01-31', 'PAGOS S/ CHEQUES', 620000, 0, 31),
('003', '5000', '5004', 4, '2005-02-28', 'PAGOS S/ CHEQUES', 1200000, 0, 57),
('003', '5000', '5004', 3, '2005-03-31', 'PAGOS S/ CHEQUES', 8605000, 0, 87),
('003', '5000', '5005', 4, '2005-01-31', 'PAGOS S/ CHEQUES', 200000, 0, 32),
('003', '5000', '5005', 3, '2005-03-31', 'PAGOS S/ CHEQUES', 150000, 0, 88),
('003', '5000', '5006', 4, '2005-01-31', 'PAGOS S/ CHEQUES', 400000, 0, 33),
('003', '5000', '5007', 4, '2005-02-28', 'PAGOS S/ CHEQUES', 2500000, 0, 58),
('003', '5000', '5007', 3, '2005-03-31', 'PAGOS S/ CHEQUES', 2527000, 0, 89),
('003', '5000', '5009', 3, '2005-03-31', 'PAGOS S/ CHEQUES', 2520000, 0, 90),
('003', '5000', '5011', 4, '2005-02-28', 'PAGOS S/ CHEQUES', 1025000, 0, 59);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mh_concepto`
--

CREATE TABLE `mh_concepto` (
  `emp_id` varchar(100) NOT NULL,
  `conc_id` int(11) NOT NULL,
  `conc_descripcion` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `mh_concepto`
--

INSERT INTO `mh_concepto` (`emp_id`, `conc_id`, `conc_descripcion`) VALUES
('100', 1, 'Mi primer concepto'),
('100', 2, 'Mi primer asiento'),
('003', 3, 'ASIENTO DE APERTURA'),
('003', 4, 'INGRESOS DEL MES'),
('003', 5, 'DEP. # '),
('003', 6, 'DEPOSITOS DEL MES'),
('003', 7, 'CHEQ. # '),
('003', 8, 'CARGOS A PY M POLLOS'),
('003', 9, 'PAGOS S/ CHEQUES'),
('003', 10, 'COMPRAS DEL MES'),
('003', 11, 'PAGO A PROVEEDORES'),
('003', 12, 'P Y M POLLOS'),
('003', 13, 'TRANSFERENCIAS A PY M POLLOS'),
('003', 14, 'CHEQUES NO IDENTIFICADOS'),
('003', 15, 'COMPRAS AVICENTRO AVES'),
('003', 16, 'CANCELACION DE PROVEEDORES'),
('003', 17, 'COMPRAS S CHEQUES');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mh_cuentas`
--

CREATE TABLE `mh_cuentas` (
  `cta_id` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `mh_cuentas`
--

INSERT INTO `mh_cuentas` (`cta_id`) VALUES
('1'),
('1000'),
('11'),
('1110'),
('1120'),
('1130'),
('1150'),
('1160'),
('1190'),
('1200'),
('1240'),
('13'),
('1310'),
('1320'),
('1321'),
('1330'),
('1331'),
('1340'),
('1341'),
('1390'),
('2'),
('2000'),
('21'),
('2110'),
('2120'),
('2130'),
('2140'),
('2190'),
('2191'),
('27'),
('270'),
('2700'),
('2710'),
('2720'),
('2730'),
('3'),
('3000'),
('4'),
('4000'),
('4100'),
('4200'),
('4300'),
('5'),
('5000'),
('6000'),
('7000'),
('9000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mh_empresas`
--

CREATE TABLE `mh_empresas` (
  `emp_id` varchar(100) NOT NULL,
  `emp_nombre` varchar(100) NOT NULL,
  `emp_rif` varchar(20) NOT NULL,
  `emp_inicioFiscal` date NOT NULL,
  `emp_finFiscal` date NOT NULL,
  `emp_fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `mh_empresas`
--

INSERT INTO `mh_empresas` (`emp_id`, `emp_nombre`, `emp_rif`, `emp_inicioFiscal`, `emp_finFiscal`, `emp_fecha`) VALUES
('001', 'Joel Prueba 1', '000000-0', '2016-01-01', '2016-12-31', '2016-09-20'),
('002', 'Cadenas', '11111111', '2015-01-01', '2015-12-31', '2016-09-20'),
('003', 'TECNO ASESORES AVICOLA, C.A.', 'J-310225536-4', '2005-01-01', '2005-12-31', '2016-09-26'),
('100', 'TEREARTE S.R.L.', 'J-31000033-1', '2010-01-01', '2010-12-31', '2016-09-20'),
('995', 'LLLLLL', '999999', '2005-05-01', '2005-12-31', '2017-01-16'),
('997', 'PRUEBA 3', 'J55555555', '2017-01-01', '2017-12-31', '2017-01-15'),
('998', 'UUUUUUUUUU', '99999999999', '2017-01-01', '2017-07-31', '2017-01-16'),
('999', 'PRUEBA CONTABLE 2.0', 'J310000000', '2017-01-01', '2017-11-30', '2017-01-15');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `mh_auxiliar`
--
ALTER TABLE `mh_auxiliar`
  ADD PRIMARY KEY (`emp_id`,`cta_id`,`aux_id`),
  ADD KEY `aux_nombreCta` (`aux_nombreCta`,`aux_tipoCta`,`aux_condicion`,`aux_nombreAux`),
  ADD KEY `aux_id` (`aux_id`),
  ADD KEY `cta_id` (`cta_id`);

--
-- Indices de la tabla `mh_comprobante`
--
ALTER TABLE `mh_comprobante`
  ADD PRIMARY KEY (`emp_id`,`cta_id`,`aux_id`,`comp_linea`),
  ADD KEY `comp_linea` (`comp_linea`),
  ADD KEY `aux_id` (`aux_id`),
  ADD KEY `cta_id` (`cta_id`);

--
-- Indices de la tabla `mh_concepto`
--
ALTER TABLE `mh_concepto`
  ADD PRIMARY KEY (`conc_id`),
  ADD KEY `emp_id` (`emp_id`);

--
-- Indices de la tabla `mh_cuentas`
--
ALTER TABLE `mh_cuentas`
  ADD PRIMARY KEY (`cta_id`);

--
-- Indices de la tabla `mh_empresas`
--
ALTER TABLE `mh_empresas`
  ADD PRIMARY KEY (`emp_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `mh_comprobante`
--
ALTER TABLE `mh_comprobante`
  MODIFY `comp_linea` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=143;
--
-- AUTO_INCREMENT de la tabla `mh_concepto`
--
ALTER TABLE `mh_concepto`
  MODIFY `conc_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `mh_auxiliar`
--
ALTER TABLE `mh_auxiliar`
  ADD CONSTRAINT `mh_auxiliar_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `mh_empresas` (`emp_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `mh_auxiliar_ibfk_2` FOREIGN KEY (`cta_id`) REFERENCES `mh_cuentas` (`cta_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `mh_comprobante`
--
ALTER TABLE `mh_comprobante`
  ADD CONSTRAINT `mh_comprobante_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `mh_auxiliar` (`emp_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `mh_comprobante_ibfk_2` FOREIGN KEY (`aux_id`) REFERENCES `mh_auxiliar` (`aux_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `mh_comprobante_ibfk_3` FOREIGN KEY (`cta_id`) REFERENCES `mh_auxiliar` (`cta_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `mh_concepto`
--
ALTER TABLE `mh_concepto`
  ADD CONSTRAINT `mh_concepto_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `mh_empresas` (`emp_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
