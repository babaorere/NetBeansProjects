-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 08, 2023 at 08:36 AM
-- Server version: 8.0.35-0ubuntu0.22.04.1
-- PHP Version: 8.1.2-1ubuntu2.14

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestion_v2023_0`
--
CREATE DATABASE IF NOT EXISTS `gestion_v2023_0` DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci;
USE `gestion_v2023_0`;

-- --------------------------------------------------------

--
-- Table structure for table `avance_efectivo`
--

DROP TABLE IF EXISTS `avance_efectivo`;
CREATE TABLE IF NOT EXISTS `avance_efectivo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `benef_razonsocial` varchar(768) NOT NULL,
  `benef_rif_ci` varchar(32) NOT NULL,
  `fecha_pago` date NOT NULL,
  `observacion` varchar(768) NOT NULL,
  `banco` varchar(256) NOT NULL,
  `cuenta` varchar(20) NOT NULL,
  `cheque` varchar(20) NOT NULL,
  `fecha_cheque` date NOT NULL,
  `endosable_sn` char(1) NOT NULL DEFAULT 'N',
  `a_pagar_bs` decimal(32,2) NOT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ava_efe_aux_report`
--

DROP TABLE IF EXISTS `ava_efe_aux_report`;
CREATE TABLE IF NOT EXISTS `ava_efe_aux_report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codpresu` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `partida` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `total` decimal(32,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`),
  KEY `iduser` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ava_efe_rpt_summary`
--

DROP TABLE IF EXISTS `ava_efe_rpt_summary`;
CREATE TABLE IF NOT EXISTS `ava_efe_rpt_summary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `num` bigint NOT NULL,
  `fecha` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `total` decimal(32,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bancos`
--

DROP TABLE IF EXISTS `bancos`;
CREATE TABLE IF NOT EXISTS `bancos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cuenta` varchar(20) NOT NULL,
  `banco` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `saldoi` decimal(32,2) NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `iduser` bigint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bancos`
--

INSERT INTO `bancos` (`id`, `cuenta`, `banco`, `saldoi`, `registro`, `ejefis`, `ejefismes`, `iduser`) VALUES
(1, '11111111111111111111', 'BANCO DE VENEZUELA', '1000.00', '2023-01-02 18:19:54', 2023, 202301, 1),
(2, '22222222222222222222', 'BANCO BANESCO', '2000.00', '2023-01-02 18:20:15', 2023, 202301, 1);

-- --------------------------------------------------------

--
-- Table structure for table `bancos_cheque`
--

DROP TABLE IF EXISTS `bancos_cheque`;
CREATE TABLE IF NOT EXISTS `bancos_cheque` (
  `id_cheque` bigint NOT NULL,
  `banco` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cuenta` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cheque` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `monto` decimal(32,2) NOT NULL,
  `op` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `idop` bigint NOT NULL,
  `anulado` tinyint NOT NULL DEFAULT '0',
  `conciliado` tinyint NOT NULL DEFAULT '0',
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id_cheque`),
  UNIQUE KEY `cuenta_cheque` (`cuenta`,`cheque`),
  KEY `cuenta` (`cuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bancos_ch_anu`
--

DROP TABLE IF EXISTS `bancos_ch_anu`;
CREATE TABLE IF NOT EXISTS `bancos_ch_anu` (
  `id_ch_anu` bigint NOT NULL AUTO_INCREMENT,
  `banco` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cuenta` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cheque_anu` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cheque_nvo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `monto` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `pag_ava` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `num_pag_ava` bigint NOT NULL,
  `motivo_anu` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha_anu` date NOT NULL,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id_ch_anu`),
  KEY `cuenta` (`cuenta`),
  KEY `ejefismes` (`ejefismes`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bancos_ch_anu_aux_rpt`
--

DROP TABLE IF EXISTS `bancos_ch_anu_aux_rpt`;
CREATE TABLE IF NOT EXISTS `bancos_ch_anu_aux_rpt` (
  `id_ch_anu` bigint NOT NULL AUTO_INCREMENT,
  `banco` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cuenta` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cheque_anu` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cheque_nvo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `monto` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `pag_ava` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `num_pag_ava` bigint NOT NULL,
  `motivo_anu` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha_anu` date NOT NULL,
  `idsession` bigint NOT NULL,
  `fchsession` date NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id_ch_anu`),
  KEY `cuenta` (`cuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bancos_operaciones`
--

DROP TABLE IF EXISTS `bancos_operaciones`;
CREATE TABLE IF NOT EXISTS `bancos_operaciones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cuenta` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `banco` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `descripcion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `tipo_operacion` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `soporte_o_cheque` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `conciliado` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `debe` decimal(32,2) NOT NULL,
  `haber` decimal(32,2) NOT NULL,
  `registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_trans_ori` bigint NOT NULL DEFAULT '0',
  `cuenta_numop` bigint NOT NULL,
  `cuenta_numop_tipo` bigint NOT NULL DEFAULT '0',
  `num_pag_ava` bigint NOT NULL,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  UNIQUE KEY `numop` (`ejefis`,`cuenta`,`cuenta_numop`),
  UNIQUE KEY `cuenta_soporte` (`cuenta`,`soporte_o_cheque`),
  KEY `cuenta` (`cuenta`),
  KEY `id_trans_ori` (`id_trans_ori`),
  KEY `soporte` (`soporte_o_cheque`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Dumping data for table `bancos_operaciones`
--

INSERT INTO `bancos_operaciones` (`id`, `cuenta`, `banco`, `descripcion`, `fecha`, `tipo_operacion`, `soporte_o_cheque`, `conciliado`, `debe`, `haber`, `registro`, `id_trans_ori`, `cuenta_numop`, `cuenta_numop_tipo`, `num_pag_ava`, `ejefis`, `ejefismes`, `iduser`, `anulado_sn`) VALUES
(1, '22222222222222222222', 'BANCO BANESCO', 'PRUEBA', '24/10/2023', 'DP', '1', '', '11111111111.00', '0.00', '2023-10-24 12:18:08', 0, 1, 1, 0, 2023, 202310, 1, 'N'),
(2, '11111111111111111111', 'BANCO DE VENEZUELA', 'DODODODOD', '24/10/2023', 'DP', '2', '', '2000000000.00', '0.00', '2023-10-24 12:18:45', 0, 1, 1, 0, 2023, 202310, 1, 'N'),
(4, '22222222222222222222', 'BANCO BANESCO', 'OP-2 - CAPIP SISTEMAS,C.A.', '25/10/2023', 'CH', '12', 'EN TRANSITO', '0.00', '2.00', '2023-10-25 11:15:20', 0, 2, 1, 2, 2023, 202310, 0, 'N'),
(5, '22222222222222222222', 'BANCO BANESCO', 'OP-3 - CAPIP SISTEMAS,C.A.', '25/10/2023', 'CH', '2', 'EN TRANSITO', '0.00', '2.00', '2023-10-25 12:06:45', 0, 3, 2, 3, 2023, 202310, 0, 'N'),
(6, '22222222222222222222', 'BANCO BANESCO', 'OP-4 - CAPIP SISTEMAS,C.A.', '25/10/2023', 'CH', '3', 'EN TRANSITO', '0.00', '5.00', '2023-10-25 12:11:53', 0, 4, 3, 4, 2023, 202310, 0, 'N'),
(7, '22222222222222222222', 'BANCO BANESCO', 'OP-5 - CAPIP SISTEMAS,C.A.', '25/10/2023', 'CH', '4', 'EN TRANSITO', '0.00', '3.00', '2023-10-25 12:18:39', 0, 5, 4, 5, 2023, 202310, 0, 'N'),
(8, '22222222222222222222', 'BANCO BANESCO', 'OP-6 - CAPIP SISTEMAS,C.A.', '05/11/2023', 'CH', '1211', 'EN TRANSITO', '0.00', '2577331.27', '2023-11-05 21:42:41', 0, 6, 5, 6, 2023, 202311, 0, 'N'),
(9, '22222222222222222222', 'BANCO BANESCO', 'OP-7 - CAPIP SISTEMAS,C.A.', '05/11/2023', 'CH', '112', 'EN TRANSITO', '0.00', '100.00', '2023-11-05 23:10:10', 0, 7, 6, 7, 2023, 202311, 1, 'N');

-- --------------------------------------------------------

--
-- Table structure for table `banco_saldo_anual`
--

DROP TABLE IF EXISTS `banco_saldo_anual`;
CREATE TABLE IF NOT EXISTS `banco_saldo_anual` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `id_banco` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `saldoi` decimal(32,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `banco_saldo_anual`
--

INSERT INTO `banco_saldo_anual` (`id`, `id_user`, `id_session`, `id_banco`, `date_session`, `ejefis`, `saldoi`) VALUES
(1, 1, 1, 1, '2023-01-02 15:19:54', '2023-01-02', '1000.00'),
(2, 1, 1, 2, '2023-01-02 15:20:15', '2023-01-02', '2000.00');

-- --------------------------------------------------------

--
-- Table structure for table `beneficiario`
--

DROP TABLE IF EXISTS `beneficiario`;
CREATE TABLE IF NOT EXISTS `beneficiario` (
  `id_beneficiario` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `domicilio` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `telefonos` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `activo` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'S',
  PRIMARY KEY (`id_beneficiario`),
  UNIQUE KEY `razonsocial` (`razonsocial`),
  UNIQUE KEY `rif` (`rif_ci`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Dumping data for table `beneficiario`
--

INSERT INTO `beneficiario` (`id_beneficiario`, `id_user`, `id_session`, `date_session`, `razonsocial`, `rif_ci`, `domicilio`, `telefonos`, `activo`) VALUES
(1, 1, 1, '2022-09-28 12:17:20', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'CARACAS DISTRITO CAPITAL', '0402-993.76.96', 'S');

-- --------------------------------------------------------

--
-- Table structure for table `beneficiarios`
--

DROP TABLE IF EXISTS `beneficiarios`;
CREATE TABLE IF NOT EXISTS `beneficiarios` (
  `razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `domicilio` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `telefonos` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `numbenef` bigint NOT NULL AUTO_INCREMENT,
  `activo` tinyint DEFAULT '1',
  `iduser` bigint DEFAULT NULL,
  PRIMARY KEY (`numbenef`),
  UNIQUE KEY `razonsocial` (`razonsocial`),
  UNIQUE KEY `rif` (`rif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `causado`
--

DROP TABLE IF EXISTS `causado`;
CREATE TABLE IF NOT EXISTS `causado` (
  `id_causado` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `fecha_causado` date NOT NULL,
  `tipo_compr` char(2) NOT NULL,
  `benef_razonsocial` varchar(768) NOT NULL,
  `benef_rif_ci` varchar(32) NOT NULL,
  `observacion` varchar(768) NOT NULL,
  `resta_bs` decimal(32,2) NOT NULL,
  `is_iva_sn` char(1) DEFAULT 'N',
  `iva_ret_sn` char(1) NOT NULL DEFAULT 'N',
  `is_islr_sn` char(1) DEFAULT 'N',
  `islr_ret_sn` char(1) NOT NULL DEFAULT 'N',
  `is_imp_mun_sn` char(1) DEFAULT 'N',
  `imp_mun_ret_sn` char(1) NOT NULL DEFAULT 'N',
  `is_neg_pri_sn` char(1) DEFAULT 'N',
  `neg_pri_ret_sn` char(1) DEFAULT 'N',
  `is_uno_x_mil_sn` char(1) DEFAULT 'N',
  `uno_x_mil_ret_sn` char(1) DEFAULT 'N',
  `is_oret_sn` char(1) DEFAULT 'N',
  `oret_ret_sn` char(1) NOT NULL DEFAULT 'N',
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_causado`),
  KEY `ejefis` (`ejefis`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `causado`
--

INSERT INTO `causado` (`id_causado`, `id_user`, `id_session`, `date_session`, `ejefis`, `fecha_causado`, `tipo_compr`, `benef_razonsocial`, `benef_rif_ci`, `observacion`, `resta_bs`, `is_iva_sn`, `iva_ret_sn`, `is_islr_sn`, `islr_ret_sn`, `is_imp_mun_sn`, `imp_mun_ret_sn`, `is_neg_pri_sn`, `neg_pri_ret_sn`, `is_uno_x_mil_sn`, `uno_x_mil_ret_sn`, `is_oret_sn`, `oret_ret_sn`, `anulado_sn`) VALUES
(1, 0, 0, '2023-10-25 08:13:39', '2023-10-25', '2023-10-25', 'CO', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'FDSFDASFAF', '0.00', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'N', 'N', 'S', 'N'),
(2, 0, 0, '2023-10-25 09:06:45', '2023-10-25', '2023-10-25', 'CO', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'ERTESTS', '0.00', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'N', 'N', 'S', 'N'),
(3, 0, 0, '2023-10-25 09:11:53', '2023-10-25', '2023-10-25', 'CO', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'SDGSDGSG', '0.00', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'N', 'N', 'S', 'N'),
(4, 0, 0, '2023-10-25 09:18:39', '2023-10-25', '2023-10-25', 'CO', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'DGSGFSDG', '0.00', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'N', 'N', 'S', 'N'),
(5, 0, 0, '2023-11-05 18:42:41', '2023-11-05', '2023-11-05', 'CO', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'NFGNDNDNDND', '0.00', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'N', 'N', 'S', 'N'),
(6, 1, 17, '2023-11-05 20:10:10', '2023-11-05', '2023-11-05', 'CO', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'FHGFHDF', '0.00', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'S', 'N', 'N', 'N', 'S', 'N');

-- --------------------------------------------------------

--
-- Table structure for table `causado_aux_report`
--

DROP TABLE IF EXISTS `causado_aux_report`;
CREATE TABLE IF NOT EXISTS `causado_aux_report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codpresu` varchar(64) NOT NULL,
  `partida` varchar(768) NOT NULL,
  `total` decimal(32,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `conpresu` (`codpresu`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `causado_avance_efectivo_nn`
--

DROP TABLE IF EXISTS `causado_avance_efectivo_nn`;
CREATE TABLE IF NOT EXISTS `causado_avance_efectivo_nn` (
  `id_causado_avance_efectivo` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_avance_efectivo` bigint NOT NULL,
  `id_causado` bigint NOT NULL,
  `apagar_bs` decimal(32,2) NOT NULL,
  PRIMARY KEY (`id_causado_avance_efectivo`),
  KEY `id_cau` (`id_causado`),
  KEY `id_pag` (`id_avance_efectivo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Lleva el registro de cada uno de los pagos realizado a un Ca';

-- --------------------------------------------------------

--
-- Table structure for table `causado_det`
--

DROP TABLE IF EXISTS `causado_det`;
CREATE TABLE IF NOT EXISTS `causado_det` (
  `id_causado_det` bigint NOT NULL AUTO_INCREMENT,
  `id_causado` bigint NOT NULL,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `codpresu` varchar(64) NOT NULL,
  `partida` varchar(768) NOT NULL,
  `subtotal` decimal(32,2) NOT NULL,
  PRIMARY KEY (`id_causado_det`),
  KEY `codpresu` (`id_causado`,`codpresu`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `causado_det`
--

INSERT INTO `causado_det` (`id_causado_det`, `id_causado`, `id_user`, `id_session`, `date_session`, `codpresu`, `partida`, `subtotal`) VALUES
(2, 1, 0, 0, '2023-10-25 08:13:39', '00.00.00.00.000.00.00.10', 'PRUEBA 2023', '2.00'),
(3, 2, 0, 0, '2023-10-25 09:06:45', '00.00.00.00.000.00.00.10', 'PRUEBA 2023', '2.00'),
(4, 3, 0, 0, '2023-10-25 09:11:53', '00.00.00.00.000.00.00.10', 'PRUEBA 2023', '5.00'),
(5, 4, 0, 0, '2023-10-25 09:18:39', '00.00.00.00.000.00.00.10', 'PRUEBA 2023', '3.00'),
(6, 5, 0, 0, '2023-11-05 18:42:41', '00.00.00.00.000.00.00.10', 'PRUEBA 2023', '2577331.27'),
(7, 6, 1, 17, '2023-11-05 20:10:10', '00.00.00.00.000.00.00.10', 'PRUEBA 2023', '100.00');

-- --------------------------------------------------------

--
-- Table structure for table `causado_orden_pago`
--

DROP TABLE IF EXISTS `causado_orden_pago`;
CREATE TABLE IF NOT EXISTS `causado_orden_pago` (
  `id_causado_orden_pago` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_orden_pago` bigint NOT NULL,
  `id_causado` bigint NOT NULL,
  `apagar_bs` decimal(32,2) NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_causado_orden_pago`),
  KEY `id_cau` (`id_causado`),
  KEY `id_pag` (`id_orden_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Lleva el registro de cada uno de los pagos realizado a un Ca';

--
-- Dumping data for table `causado_orden_pago`
--

INSERT INTO `causado_orden_pago` (`id_causado_orden_pago`, `id_user`, `id_session`, `date_session`, `ejefis`, `id_orden_pago`, `id_causado`, `apagar_bs`, `anulado_sn`) VALUES
(1, 0, 0, '2023-10-25 08:15:23', '2023-10-25', 2, 1, '2.00', 'N'),
(2, 0, 0, '2023-10-25 09:06:45', '2023-10-25', 3, 2, '2.00', 'N'),
(3, 0, 0, '2023-10-25 09:11:53', '2023-10-25', 4, 3, '5.00', 'N'),
(4, 0, 0, '2023-10-25 09:18:39', '2023-10-25', 5, 4, '3.00', 'N'),
(5, 0, 0, '2023-11-05 18:42:41', '2023-11-05', 6, 5, '2577331.27', 'N'),
(6, 1, 17, '2023-11-05 20:10:10', '2023-11-05', 7, 6, '100.00', 'N');

-- --------------------------------------------------------

--
-- Table structure for table `causado_pag_hist`
--

DROP TABLE IF EXISTS `causado_pag_hist`;
CREATE TABLE IF NOT EXISTS `causado_pag_hist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_cau` bigint NOT NULL,
  `id_pag` bigint NOT NULL,
  `monto` decimal(32,2) NOT NULL,
  `eje_fis` bigint NOT NULL,
  `eje_fis_mes` bigint NOT NULL,
  `id_user` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cau` (`id_cau`),
  KEY `id_pag` (`id_pag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Lleva el registro de los pagos';

-- --------------------------------------------------------

--
-- Table structure for table `cau_rpt_summary`
--

DROP TABLE IF EXISTS `cau_rpt_summary`;
CREATE TABLE IF NOT EXISTS `cau_rpt_summary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `num` bigint NOT NULL,
  `fecha` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `total` decimal(32,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `compr_compra`
--

DROP TABLE IF EXISTS `compr_compra`;
CREATE TABLE IF NOT EXISTS `compr_compra` (
  `id_compr` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_causado` bigint NOT NULL,
  `id_orden_pago` bigint NOT NULL,
  `fecha_compr` date NOT NULL,
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `observacion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `num_fact` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `num_control` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha_fact` date NOT NULL,
  `total_bs` decimal(32,2) NOT NULL COMMENT 'El monto total de la orden, expresado en Bs.',
  `base_imponible_bs` decimal(32,2) NOT NULL,
  `iva_grav_bs` decimal(32,2) NOT NULL,
  `iva_porc_aplic` decimal(32,2) NOT NULL,
  `iva_porc_ret` decimal(32,2) NOT NULL,
  `islr_grav_bs` decimal(32,2) NOT NULL,
  `islr_porc_ret` decimal(32,2) NOT NULL,
  `imp_mun_grav_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `imp_mun_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `neg_pri_grav_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `neg_pri_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `oret_grav_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `oret_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_compr`),
  UNIQUE KEY `rif_ci_numfact` (`benef_rif_ci`,`num_fact`),
  KEY `ejefis` (`ejefis`),
  KEY `id_causado` (`id_causado`),
  KEY `id_pago` (`id_orden_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Dumping data for table `compr_compra`
--

INSERT INTO `compr_compra` (`id_compr`, `id_user`, `id_session`, `date_session`, `ejefis`, `id_causado`, `id_orden_pago`, `fecha_compr`, `benef_razonsocial`, `benef_rif_ci`, `observacion`, `num_fact`, `num_control`, `fecha_fact`, `total_bs`, `base_imponible_bs`, `iva_grav_bs`, `iva_porc_aplic`, `iva_porc_ret`, `islr_grav_bs`, `islr_porc_ret`, `imp_mun_grav_bs`, `imp_mun_bs`, `neg_pri_grav_bs`, `neg_pri_bs`, `oret_grav_bs`, `oret_bs`, `anulado_sn`) VALUES
(1, 1, 15, '2023-10-27 08:50:06', '2023-10-27', 0, 0, '2023-10-27', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'FGHDGFHDFH', '1', '1', '2023-01-01', '1000.00', '1000.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(2, 1, 18, '2023-11-06 08:11:14', '2023-11-06', 0, 0, '2023-11-06', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'GSDGSDG', '11', '1', '2023-02-01', '1.00', '1.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(3, 1, 19, '2023-11-06 09:21:04', '2023-11-06', 0, 0, '2023-11-06', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'FGHFDH', '3', '3', '2023-01-01', '3.00', '3.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(4, 0, 0, '2023-11-06 09:32:52', '2023-11-06', 0, 0, '2023-11-06', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'DFGFDSF', '4', '4', '2023-01-01', '4.00', '4.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(5, 0, 0, '2023-11-06 09:34:26', '2023-11-06', 0, 0, '2023-11-06', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'DSFGSDGSG', '5', '5', '2023-05-01', '2.00', '2.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(6, 0, 0, '2023-11-06 09:38:29', '2023-11-06', 0, 0, '2023-11-06', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'PHPHPH', '6', '6', '2023-02-01', '3.00', '3.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(7, 0, 0, '2023-11-07 07:25:04', '2023-11-07', 0, 0, '2023-11-07', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'JGJGFHJFHGJGJH', '7', 'KGUYK', '2023-01-01', '3.00', '3.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(8, 0, 0, '2023-11-07 07:33:56', '2023-11-07', 0, 0, '2023-11-07', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'GDFGDSG', '9', '66', '2023-01-01', '3.00', '3.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(9, 0, 0, '2023-11-07 07:39:35', '2023-11-07', 0, 0, '2023-11-07', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'UOIPUP', '10', '21', '2023-01-01', '9.00', '9.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(10, 0, 0, '2023-11-07 07:43:12', '2023-11-07', 0, 0, '2023-11-07', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'OÌUOYUOY', '12', '32423', '2023-01-01', '3.00', '3.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(11, 0, 0, '2023-11-07 07:52:49', '2023-11-07', 0, 0, '2023-11-07', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'ÑÑJJKÑ', '13', '3', '2023-01-01', '3.00', '3.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(12, 0, 0, '2023-11-07 07:58:43', '2023-11-07', 0, 0, '2023-11-07', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'GFDGSGS', '14', '', '2023-01-01', '3.00', '3.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(13, 0, 0, '2023-11-07 08:01:16', '2023-11-07', 0, 0, '2023-11-07', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'FDHDFH', '15', '454', '2023-01-01', '1.00', '1.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(14, 0, 0, '2023-11-07 15:34:29', '2023-11-07', 0, 0, '2023-11-07', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'DGSDGSDGDSFG', '17', '1', '2023-01-01', '1.00', '1.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N');

-- --------------------------------------------------------

--
-- Table structure for table `compr_det`
--

DROP TABLE IF EXISTS `compr_det`;
CREATE TABLE IF NOT EXISTS `compr_det` (
  `id_compr_det` bigint NOT NULL AUTO_INCREMENT,
  `id_compr` bigint NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cantpro` decimal(32,2) NOT NULL,
  `descpro` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT '',
  `punitario` decimal(32,2) NOT NULL,
  `codpresu` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `partida` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (`id_compr_det`),
  KEY `tipo_y_idcompr` (`tipo_compr`,`id_compr`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Dumping data for table `compr_det`
--

INSERT INTO `compr_det` (`id_compr_det`, `id_compr`, `tipo_compr`, `cantpro`, `descpro`, `punitario`, `codpresu`, `partida`) VALUES
(2, 1, 'CO', '1.00', 'HHIUHIU', '2.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(3, 2, 'CO', '1.00', 'EFEWFEW', '2.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(4, 3, 'CO', '1.00', 'GGSDFGSDG', '5.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(5, 4, 'CO', '1.00', 'DFASDFAD', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(6, 1, 'OC', '1.00', 'DFGSFDGS', '1000.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(7, 5, 'CO', '1.00', 'FGDSGSDG', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(8, 5, 'CO', '20.00', 'FSDAFASD', '2.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(9, 5, 'CO', '20.00', 'HFGDHDFHGD', '33.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(10, 5, 'CO', '10.00', 'VCBCBCXBVX', '33.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(11, 5, 'CO', '55.00', 'BFDSBSDB', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(12, 5, 'CO', '0.01', 'YTREYERY', '567.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(13, 5, 'CO', '7.00', 'KJHKGHKG', '78.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(14, 5, 'CO', '0.65', 'IIYUIYTUI', '44.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(15, 5, 'CO', '23.00', 'GHJGHFJ', '56.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(16, 5, 'CO', '6565.00', 'HGFDHDFH', '2.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(17, 5, 'CO', '66.00', 'DFGDFGS', '45.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(18, 5, 'CO', '67.00', 'JGFJFGHJ', '23.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(19, 5, 'CO', '45654.00', 'JGFJF', '56.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(20, 6, 'CO', '1.00', 'DGDFGDFS', '100.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(21, 2, 'OC', '1.00', 'DSFSAFAS', '1.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(22, 3, 'OC', '1.00', 'GDSFGSDGFS', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(23, 4, 'OC', '1.00', 'GDFSGDSG', '4.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(24, 5, 'OC', '1.00', 'FSDFAA', '2.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(25, 6, 'OC', '1.00', 'SDFSDF', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(26, 7, 'OC', '1.00', 'DSVDSFVSDV', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(27, 8, 'OC', '1.00', 'GDSF', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(28, 9, 'OC', '3.00', 'BDFBSDFB', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(29, 10, 'OC', '1.00', 'GEWEWG', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(30, 11, 'OC', '1.00', 'FAFEWFQ', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(31, 12, 'OC', '1.00', 'FASDFSDA', '3.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(32, 13, 'OC', '1.00', 'FFSD', '1.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023'),
(33, 14, 'OC', '1.00', 'FSDFSAFAS', '1.00', '00.00.00.00.000.00.00.10', 'PRUEBA 2023');

-- --------------------------------------------------------

--
-- Table structure for table `compr_otros`
--

DROP TABLE IF EXISTS `compr_otros`;
CREATE TABLE IF NOT EXISTS `compr_otros` (
  `id_compr` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_causado` bigint NOT NULL,
  `id_orden_pago` bigint NOT NULL,
  `fecha_compr` date NOT NULL,
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `observacion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `num_fact` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `num_control` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha_fact` date NOT NULL,
  `total_bs` decimal(32,2) NOT NULL COMMENT 'El monto total de la orden, expresado en Bs.',
  `base_imponible_bs` decimal(32,2) NOT NULL,
  `iva_grav_bs` decimal(32,2) NOT NULL,
  `iva_porc_aplic` decimal(32,2) NOT NULL,
  `iva_porc_ret` decimal(32,2) NOT NULL,
  `islr_grav_bs` decimal(32,2) NOT NULL,
  `islr_porc_ret` decimal(32,2) NOT NULL,
  `imp_mun_grav_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `imp_mun_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `neg_pri_grav_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `neg_pri_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `oret_grav_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `oret_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_compr`),
  KEY `ejefis` (`ejefis`),
  KEY `id_causado` (`id_causado`),
  KEY `id_pago` (`id_orden_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Dumping data for table `compr_otros`
--

INSERT INTO `compr_otros` (`id_compr`, `id_user`, `id_session`, `date_session`, `ejefis`, `id_causado`, `id_orden_pago`, `fecha_compr`, `benef_razonsocial`, `benef_rif_ci`, `observacion`, `num_fact`, `num_control`, `fecha_fact`, `total_bs`, `base_imponible_bs`, `iva_grav_bs`, `iva_porc_aplic`, `iva_porc_ret`, `islr_grav_bs`, `islr_porc_ret`, `imp_mun_grav_bs`, `imp_mun_bs`, `neg_pri_grav_bs`, `neg_pri_bs`, `oret_grav_bs`, `oret_bs`, `anulado_sn`) VALUES
(1, 0, 0, '2023-10-25 08:13:23', '2023-10-25', 1, 0, '2023-10-25', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'FDSFDASFAF', '', '', '2023-10-25', '2.00', '2.00', '0.00', '0.00', '0.00', '2.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(2, 0, 0, '2023-10-25 09:06:45', '2023-10-25', 2, 0, '2023-10-25', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'ERTESTS', '', '', '2023-10-25', '2.00', '2.00', '0.00', '0.00', '0.00', '2.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(3, 0, 0, '2023-10-25 09:11:53', '2023-10-25', 3, 0, '2023-10-25', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'SDGSDGSG', '', '', '2023-10-25', '5.00', '5.00', '0.00', '0.00', '0.00', '5.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(4, 0, 0, '2023-10-25 09:18:39', '2023-10-25', 4, 0, '2023-10-25', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'DGSGFSDG', '', '', '2023-10-25', '3.00', '3.00', '0.00', '0.00', '0.00', '3.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(5, 0, 0, '2023-11-05 18:42:41', '2023-11-05', 5, 0, '2023-11-05', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'NFGNDNDNDND', '', '1', '2023-11-05', '2577331.27', '2577331.27', '0.00', '0.00', '0.00', '2577331.27', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(6, 1, 17, '2023-11-05 20:10:10', '2023-11-05', 6, 0, '2023-11-05', 'CAPIP SISTEMAS,C.A.', 'J407111787', 'FHGFHDF', '', '', '2023-11-05', '100.00', '100.00', '0.00', '0.00', '0.00', '100.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N');

-- --------------------------------------------------------

--
-- Table structure for table `compr_rpt_summary`
--

DROP TABLE IF EXISTS `compr_rpt_summary`;
CREATE TABLE IF NOT EXISTS `compr_rpt_summary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `num` bigint NOT NULL,
  `edo` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `total` decimal(32,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `compr_servicio`
--

DROP TABLE IF EXISTS `compr_servicio`;
CREATE TABLE IF NOT EXISTS `compr_servicio` (
  `id_compr` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_causado` bigint NOT NULL,
  `id_orden_pago` bigint NOT NULL,
  `fecha_compr` date NOT NULL,
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `observacion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `num_fact` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `num_control` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha_fact` date NOT NULL,
  `total_bs` decimal(32,2) NOT NULL COMMENT 'El monto total de la orden, expresado en Bs.',
  `base_imponible_bs` decimal(32,2) NOT NULL,
  `iva_grav_bs` decimal(32,2) NOT NULL,
  `iva_porc_aplic` decimal(32,2) NOT NULL,
  `iva_porc_ret` decimal(32,2) NOT NULL,
  `islr_grav_bs` decimal(32,2) NOT NULL,
  `islr_porc_ret` decimal(32,2) NOT NULL,
  `imp_mun_grav_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `imp_mun_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `neg_pri_grav_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `neg_pri_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `oret_grav_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `oret_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_compr`),
  UNIQUE KEY `rif_ci_numfact` (`benef_rif_ci`,`num_fact`),
  KEY `ejefis` (`ejefis`),
  KEY `id_causado` (`id_causado`),
  KEY `id_pago` (`id_orden_pago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `creditoadicional`
--

DROP TABLE IF EXISTS `creditoadicional`;
CREATE TABLE IF NOT EXISTS `creditoadicional` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `soporte` varchar(768) NOT NULL,
  `descripcion` varchar(768) NOT NULL,
  `monto` decimal(32,2) NOT NULL DEFAULT '0.00',
  `fecha` date NOT NULL,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `creditoadicional_det`
--

DROP TABLE IF EXISTS `creditoadicional_det`;
CREATE TABLE IF NOT EXISTS `creditoadicional_det` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(768) NOT NULL,
  `partida` varchar(768) NOT NULL,
  `monto` decimal(32,2) NOT NULL,
  `registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  `id_cre_adi` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `creditoadicional_rpt_summary`
--

DROP TABLE IF EXISTS `creditoadicional_rpt_summary`;
CREATE TABLE IF NOT EXISTS `creditoadicional_rpt_summary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `soporte` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `descripcion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `monto` decimal(32,2) NOT NULL,
  `fecha` date NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `global`
--

DROP TABLE IF EXISTS `global`;
CREATE TABLE IF NOT EXISTS `global` (
  `id_global` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `nombre` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `valor` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `dato` binary(16) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`id_global`),
  KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Dumping data for table `global`
--

INSERT INTO `global` (`id_global`, `id_user`, `nombre`, `valor`, `dato`, `fecha`) VALUES
(3, 0, 'ejercicio_fiscal', '2023', NULL, NULL),
(8, 0, 'rpt_fecha_hora', '102', NULL, NULL),
(9, 0, 'DESC_1', 'DESC_1', NULL, NULL),
(10, 0, 'DESC_2', 'ADMINISTRACION', NULL, NULL),
(11, 0, 'DESC_3', 'DESC_3', NULL, NULL),
(12, 0, 'FUNC_1', 'FUNC_1', NULL, NULL),
(13, 0, 'FUNC_2', 'CANAIMA SGG', NULL, NULL),
(14, 0, 'FUNC_3', 'FUNC_3', NULL, NULL),
(15, 0, 'DESC_4', 'PRESIDENTE', NULL, NULL),
(16, 0, 'FUNC_4', 'CANAIMA SGG', NULL, NULL),
(17, 0, 'DESC_5', 'DESC_5', NULL, NULL),
(18, 0, 'FUNC_5', 'FUNC_5', NULL, NULL),
(19, 0, 'DESC_6', 'DESC_6', NULL, NULL),
(20, 0, 'FUNC_6', 'FUNC_6', NULL, NULL),
(21, 0, 'DESC_7', 'DESC_7', NULL, NULL),
(22, 0, 'FUNC_7', 'FUNC_7', NULL, NULL),
(23, 0, 'DESC_8', 'DESC_8', NULL, NULL),
(24, 0, 'FUNC_8', 'FUNC_8', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `histpre`
--

DROP TABLE IF EXISTS `histpre`;
CREATE TABLE IF NOT EXISTS `histpre` (
  `codpresupe` varchar(768) NOT NULL,
  `monto` varchar(768) NOT NULL,
  `numcompr` bigint NOT NULL,
  `fechacompr` varchar(10) NOT NULL,
  `tipocompr` varchar(2) NOT NULL,
  `observacion` varchar(768) NOT NULL,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  KEY `numcompr` (`numcompr`),
  KEY `tipocompr` (`tipocompr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `impuesto_retencion`
--

DROP TABLE IF EXISTS `impuesto_retencion`;
CREATE TABLE IF NOT EXISTS `impuesto_retencion` (
  `ivanumop` bigint NOT NULL,
  `ejefis` bigint NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `ejefismes` bigint NOT NULL COMMENT 'Conformado por seis (6) digitos, que representa el aÃ±o y mes correspondiente al ejercicio fiscal, formato aaaamm',
  `fecha_op` date NOT NULL,
  `total` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `contribuyente` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `iduser` bigint DEFAULT NULL,
  PRIMARY KEY (`ivanumop`),
  KEY `rif` (`rif`),
  KEY `fecha_op` (`fecha_op`),
  KEY `fecha_op_2` (`fecha_op`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `impuesto_retencion_det`
--

DROP TABLE IF EXISTS `impuesto_retencion_det`;
CREATE TABLE IF NOT EXISTS `impuesto_retencion_det` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `caunumop` bigint NOT NULL,
  `ivanumop` bigint NOT NULL,
  `fechafact` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `numfact` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cfactura` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `ndebito` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `ncredito` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `mtfactura` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `cexentas` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `bimponible` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `elicuota` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `impuesto` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `iretenido` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `isrlretenido` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `oretenciones` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `conceptoret` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `contribuyente` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fiva` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `facturaaft` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `tiretenido` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `ncontrol` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `tmtfactura` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fechaop` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `resta` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mtFactura_dbl` decimal(32,2) NOT NULL,
  `cexentas_dbl` decimal(32,2) NOT NULL,
  `bimponible_dbl` decimal(32,2) NOT NULL,
  `impuesto_dbl` decimal(32,2) NOT NULL,
  `iretenido_dbl` decimal(32,2) NOT NULL,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `iduser` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `caunumop` (`caunumop`),
  KEY `ivanumop` (`ivanumop`),
  KEY `ejefis` (`ejefis`),
  KEY `ejefismes` (`ejefismes`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `imp_mun`
--

DROP TABLE IF EXISTS `imp_mun`;
CREATE TABLE IF NOT EXISTS `imp_mun` (
  `id_imp_mun` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_imp_mun`),
  KEY `rif` (`benef_rif_ci`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `imp_mun_det`
--

DROP TABLE IF EXISTS `imp_mun_det`;
CREATE TABLE IF NOT EXISTS `imp_mun_det` (
  `id_imp_mun_det` bigint NOT NULL AUTO_INCREMENT,
  `id_imp_mun` bigint NOT NULL,
  `id_causado` bigint NOT NULL,
  `fecha_fact` date NOT NULL,
  `num_fact` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `id_compr` bigint NOT NULL COMMENT 'num_control\n',
  `total_fact` decimal(32,2) NOT NULL,
  `base_imponible` decimal(32,2) NOT NULL,
  `gravable_bs` decimal(32,2) NOT NULL,
  `retenido_bs` decimal(32,2) NOT NULL,
  `observacion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_imp_mun_det`),
  KEY `caunumop` (`id_causado`),
  KEY `imp_mun` (`id_imp_mun`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `islr_retencion`
--

DROP TABLE IF EXISTS `islr_retencion`;
CREATE TABLE IF NOT EXISTS `islr_retencion` (
  `id_islr_retencion` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_islr_retencion`),
  KEY `rif` (`benef_rif_ci`)
) ENGINE=InnoDB AUTO_INCREMENT=20231100000001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `islr_retencion_det`
--

DROP TABLE IF EXISTS `islr_retencion_det`;
CREATE TABLE IF NOT EXISTS `islr_retencion_det` (
  `id_islr_retencion_det` bigint NOT NULL AUTO_INCREMENT,
  `id_islr_retencion` bigint NOT NULL,
  `id_causado` bigint NOT NULL,
  `fecha_fact` date NOT NULL,
  `num_fact` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `id_compr` bigint NOT NULL COMMENT 'num_control\n',
  `total_fact` decimal(32,2) NOT NULL,
  `base_imponible` decimal(32,2) NOT NULL,
  `gravable_bs` decimal(32,2) NOT NULL,
  `islr_porc_ret` decimal(32,2) NOT NULL,
  `islr_retenido_bs` decimal(32,2) NOT NULL,
  `observacion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_islr_retencion_det`),
  KEY `caunumop` (`id_causado`),
  KEY `islr_numop` (`id_islr_retencion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `iva_aplicado`
--

DROP TABLE IF EXISTS `iva_aplicado`;
CREATE TABLE IF NOT EXISTS `iva_aplicado` (
  `id_iva_aplicado` bigint NOT NULL AUTO_INCREMENT,
  `id_part_ppto` bigint NOT NULL,
  `valor_porc` decimal(32,2) NOT NULL,
  PRIMARY KEY (`id_iva_aplicado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Guarda una correlacion del porcentaje aplicado para el IVA, ';

-- --------------------------------------------------------

--
-- Table structure for table `iva_retencion`
--

DROP TABLE IF EXISTS `iva_retencion`;
CREATE TABLE IF NOT EXISTS `iva_retencion` (
  `id_iva_retencion` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_iva_retencion`),
  KEY `rif` (`benef_rif_ci`)
) ENGINE=InnoDB AUTO_INCREMENT=20231100000001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `iva_retencion_det`
--

DROP TABLE IF EXISTS `iva_retencion_det`;
CREATE TABLE IF NOT EXISTS `iva_retencion_det` (
  `id_iva_retencion_det` bigint NOT NULL AUTO_INCREMENT,
  `id_iva_retencion` bigint NOT NULL,
  `id_causado` bigint NOT NULL,
  `fecha_fact` date NOT NULL,
  `num_fact` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `num_control` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `id_compr` bigint NOT NULL COMMENT 'num_control\n',
  `ndebito` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL COMMENT 'Num. de nota credito',
  `ncredito` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL COMMENT 'Num. de nota credito',
  `transaccion` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `factura_aft` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `total_fact` decimal(32,2) NOT NULL,
  `exento` decimal(32,2) NOT NULL,
  `base_imponible` decimal(32,2) NOT NULL,
  `iva_porc_aplic` decimal(32,2) NOT NULL,
  `iva_bs` decimal(32,2) NOT NULL,
  `iva_retenido` decimal(32,2) NOT NULL,
  `observacion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_iva_retencion_det`),
  KEY `caunumop` (`id_causado`),
  KEY `ivanumop` (`id_iva_retencion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `map_next_id`
--

DROP TABLE IF EXISTS `map_next_id`;
CREATE TABLE IF NOT EXISTS `map_next_id` (
  `clave` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL COMMENT 'Los primeros cuatro (4) digitos deben corresponder con el ejercicio fiscal, es decir el aÃ±o fiscal',
  `valor` bigint NOT NULL COMMENT 'Normalmente deberia corresponder a actualizaciones, que generen incrementos unitarios, y este valorgenerado podria ser utilizado como clave (indice) primario o no, en otras tablas, este seria el next id.',
  `id_user` bigint NOT NULL,
  PRIMARY KEY (`clave`),
  UNIQUE KEY `cuesec` (`clave`),
  KEY `cuenta` (`clave`),
  KEY `ejefis` (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Para llevar la cuenta del ultimo Id generado asociado a una ';

--
-- Dumping data for table `map_next_id`
--

INSERT INTO `map_next_id` (`clave`, `valor`, `id_user`) VALUES
('2023-1', 19, 1),
('2023-11111111111111111111', 1, 1),
('2023-11111111111111111111-DP', 1, 1),
('2023-22222222222222222222', 7, 1),
('2023-22222222222222222222-CH', 6, 0),
('2023-22222222222222222222-DP', 1, 1),
('2023-CAU', 6, 1),
('2023-CO', 6, 0),
('2023-OC', 14, 1),
('2023-PAG', 6, 0),
('2023-PAG-00000000000000000000', 6, 0);

-- --------------------------------------------------------

--
-- Table structure for table `neg_pri`
--

DROP TABLE IF EXISTS `neg_pri`;
CREATE TABLE IF NOT EXISTS `neg_pri` (
  `id_neg_pri` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_neg_pri`),
  KEY `rif` (`benef_rif_ci`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `neg_pri_det`
--

DROP TABLE IF EXISTS `neg_pri_det`;
CREATE TABLE IF NOT EXISTS `neg_pri_det` (
  `id_neg_pri_det` bigint NOT NULL AUTO_INCREMENT,
  `id_neg_pri` bigint NOT NULL,
  `id_causado` bigint NOT NULL,
  `fecha_fact` date NOT NULL,
  `num_fact` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `id_compr` bigint NOT NULL COMMENT 'num_control\n',
  `total_fact` decimal(32,2) NOT NULL,
  `base_imponible` decimal(32,2) NOT NULL,
  `gravable_bs` decimal(32,2) NOT NULL,
  `retenido_bs` decimal(32,2) NOT NULL,
  `observacion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_neg_pri_det`),
  KEY `caunumop` (`id_causado`),
  KEY `neg_pri` (`id_neg_pri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ordenpago_aux_report`
--

DROP TABLE IF EXISTS `ordenpago_aux_report`;
CREATE TABLE IF NOT EXISTS `ordenpago_aux_report` (
  `codpresu` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `partida` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `total` decimal(32,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ordenpago_det`
--

DROP TABLE IF EXISTS `ordenpago_det`;
CREATE TABLE IF NOT EXISTS `ordenpago_det` (
  `numpago` bigint NOT NULL,
  `numcompr` bigint NOT NULL,
  `cantpro` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `descpro` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT '',
  `punitario` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `stotal` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `codpresu` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `partida` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tipocompr` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `numcau` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  KEY `numpago` (`numpago`),
  KEY `tipocompr` (`tipocompr`),
  KEY `numcau` (`numcau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ordenpago_rpt_summary`
--

DROP TABLE IF EXISTS `ordenpago_rpt_summary`;
CREATE TABLE IF NOT EXISTS `ordenpago_rpt_summary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `num` bigint NOT NULL,
  `anu` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `total` decimal(32,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orden_pago`
--

DROP TABLE IF EXISTS `orden_pago`;
CREATE TABLE IF NOT EXISTS `orden_pago` (
  `id_orden_pago` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_cuenta` bigint NOT NULL DEFAULT '0',
  `num_x_cuenta` bigint NOT NULL DEFAULT '0',
  `num_x_pag` bigint NOT NULL DEFAULT '0',
  `benef_razonsocial` varchar(768) NOT NULL,
  `benef_rif_ci` varchar(32) NOT NULL,
  `fecha_pago` date NOT NULL,
  `observacion` varchar(768) NOT NULL,
  `banco` varchar(256) NOT NULL,
  `cuenta` varchar(20) NOT NULL,
  `cheque` varchar(20) NOT NULL,
  `fecha_cheque` date NOT NULL,
  `endosable_sn` char(1) NOT NULL,
  `total_bs` decimal(32,2) NOT NULL,
  `apagar_bs` decimal(32,2) NOT NULL,
  `iva_bs` decimal(32,2) NOT NULL,
  `resta_bs` decimal(32,2) NOT NULL,
  `iva_ret_bs` decimal(32,2) NOT NULL,
  `islr_ret_bs` decimal(32,2) NOT NULL,
  `imp_mun_ret_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `neg_pri_ret_bs` decimal(32,2) NOT NULL DEFAULT '0.00',
  `otras_ret_bs` decimal(32,2) NOT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_orden_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `orden_pago`
--

INSERT INTO `orden_pago` (`id_orden_pago`, `id_user`, `id_session`, `date_session`, `ejefis`, `id_cuenta`, `num_x_cuenta`, `num_x_pag`, `benef_razonsocial`, `benef_rif_ci`, `fecha_pago`, `observacion`, `banco`, `cuenta`, `cheque`, `fecha_cheque`, `endosable_sn`, `total_bs`, `apagar_bs`, `iva_bs`, `resta_bs`, `iva_ret_bs`, `islr_ret_bs`, `imp_mun_ret_bs`, `neg_pri_ret_bs`, `otras_ret_bs`, `anulado_sn`) VALUES
(2, 0, 0, '2023-10-25 08:15:20', '2023-10-25', 0, 1, 1, 'CAPIP SISTEMAS,C.A.', 'J407111787', '2023-10-25', 'FDSFDASFAF', 'BANCO BANESCO', '22222222222222222222', '12', '2023-10-25', 'N', '2.00', '2.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(3, 0, 0, '2023-10-25 09:06:45', '2023-10-25', 0, 2, 2, 'CAPIP SISTEMAS,C.A.', 'J407111787', '2023-10-25', 'ERTESTS', 'BANCO BANESCO', '22222222222222222222', '2', '2023-10-25', 'N', '2.00', '2.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(4, 0, 0, '2023-10-25 09:11:53', '2023-10-25', 0, 3, 3, 'CAPIP SISTEMAS,C.A.', 'J407111787', '2023-10-25', 'SDGSDGSG', 'BANCO BANESCO', '22222222222222222222', '3', '2023-10-25', 'N', '5.00', '5.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(5, 0, 0, '2023-10-25 09:18:39', '2023-10-25', 0, 4, 4, 'CAPIP SISTEMAS,C.A.', 'J407111787', '2023-10-25', 'DGSGFSDG', 'BANCO BANESCO', '22222222222222222222', '4', '2023-10-25', 'N', '3.00', '3.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(6, 0, 0, '2023-11-05 18:42:41', '2023-11-05', 0, 5, 5, 'CAPIP SISTEMAS,C.A.', 'J407111787', '2023-11-05', 'NFGNDNDNDND', 'BANCO BANESCO', '22222222222222222222', '1211', '2023-11-05', 'N', '2577331.27', '2577331.27', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N'),
(7, 1, 17, '2023-11-05 20:10:10', '2023-11-05', 0, 6, 6, 'CAPIP SISTEMAS,C.A.', 'J407111787', '2023-11-05', 'FHGFHDF', 'BANCO BANESCO', '22222222222222222222', '112', '2023-11-05', 'N', '100.00', '100.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'N');

-- --------------------------------------------------------

--
-- Table structure for table `otras_ret`
--

DROP TABLE IF EXISTS `otras_ret`;
CREATE TABLE IF NOT EXISTS `otras_ret` (
  `id_otras_ret` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_otras_ret`),
  KEY `rif` (`benef_rif_ci`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `otras_ret_det`
--

DROP TABLE IF EXISTS `otras_ret_det`;
CREATE TABLE IF NOT EXISTS `otras_ret_det` (
  `id_otras_ret_det` bigint NOT NULL AUTO_INCREMENT,
  `id_otras_ret` bigint NOT NULL,
  `id_causado` bigint NOT NULL,
  `fecha_fact` date NOT NULL,
  `num_fact` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `id_compr` bigint NOT NULL COMMENT 'num_control\n',
  `total_fact` decimal(32,2) NOT NULL,
  `base_imponible` decimal(32,2) NOT NULL,
  `gravable_bs` decimal(32,2) NOT NULL,
  `retenido_bs` decimal(32,2) NOT NULL,
  `observacion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de aÃ±o en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_otras_ret_det`),
  KEY `caunumop` (`id_causado`),
  KEY `oret` (`id_otras_ret`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `presupe`
--

DROP TABLE IF EXISTS `presupe`;
CREATE TABLE IF NOT EXISTS `presupe` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(768) NOT NULL DEFAULT '',
  `partida` varchar(768) NOT NULL DEFAULT '',
  `monto_ini` decimal(32,2) NOT NULL,
  `monto` decimal(32,2) NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  `anulado_sn` char(3) NOT NULL DEFAULT 'N',
  `cod_contable` varchar(768) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `presupe`
--

INSERT INTO `presupe` (`id`, `codigo`, `partida`, `monto_ini`, `monto`, `registro`, `ejefis`, `ejefismes`, `fchsession`, `idsession`, `iduser`, `anulado_sn`, `cod_contable`) VALUES
(197, '11.00.41.40.103.02.01.00', 'PRUEBA1', '300.00', '300.00', '2023-01-02 17:59:08', 2023, 202301, '2023-01-02', 1, 1, 'N', '10.23.43.98'),
(198, '11.00.52.00.402.01.01.00', 'PARTIDA2', '500.00', '500.00', '2023-01-02 18:00:55', 2023, 202301, '2023-01-02', 1, 1, 'N', '40.5.09.76'),
(199, '01.03.00.51.403.18.01.00', 'IMPUESTO AL VALOR AGREGADO', '20000800.00', '20000800.00', '2023-10-24 12:20:15', 2023, 202301, '2023-01-02', 1, 1, 'N', '45.97.343.0'),
(200, '00.00.00.00.000.00.00.10', 'PRUEBA 2023', '10000000.00', '7421517.73', '2023-11-07 18:34:29', 2023, 202310, '2023-10-03', 3, 1, 'N', '');

-- --------------------------------------------------------

--
-- Table structure for table `presupi`
--

DROP TABLE IF EXISTS `presupi`;
CREATE TABLE IF NOT EXISTS `presupi` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(768) NOT NULL,
  `partida` varchar(768) NOT NULL,
  `monto_ini` decimal(32,2) NOT NULL,
  `monto` decimal(32,2) NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tmppptoadicional`
--

DROP TABLE IF EXISTS `tmppptoadicional`;
CREATE TABLE IF NOT EXISTS `tmppptoadicional` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(768) NOT NULL DEFAULT '',
  `partida` varchar(768) NOT NULL DEFAULT '',
  `monto_ini` decimal(32,2) NOT NULL,
  `monto` decimal(32,2) NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  `anulado_sn` char(3) NOT NULL DEFAULT 'N',
  `cod_contable` varchar(768) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tmppptoadicional`
--

INSERT INTO `tmppptoadicional` (`id`, `codigo`, `partida`, `monto_ini`, `monto`, `registro`, `ejefis`, `ejefismes`, `fchsession`, `idsession`, `iduser`, `anulado_sn`, `cod_contable`) VALUES
(197, '11.00.41.40.103.02.01.00', 'PRUEBA1', '0.00', '0.00', '2023-10-21 18:05:35', 2023, 202301, '2023-01-02', 1, 1, 'N', '10.23.43.98'),
(198, '11.00.52.00.402.01.01.00', 'PARTIDA2', '0.00', '0.00', '2023-10-21 18:05:35', 2023, 202301, '2023-01-02', 1, 1, 'N', '40.5.09.76'),
(199, '01.03.00.51.403.18.01.00', 'IMPUESTO AL VALOR AGREGADO', '0.00', '0.00', '2023-10-21 18:05:35', 2023, 202301, '2023-01-02', 1, 1, 'N', '45.97.343.0'),
(200, '00.00.00.00.000.00.00.10', 'PRUEBA 2023', '0.00', '0.00', '2023-10-21 18:05:35', 2023, 202310, '2023-10-03', 3, 1, 'N', '');

-- --------------------------------------------------------

--
-- Table structure for table `trasppartidas`
--

DROP TABLE IF EXISTS `trasppartidas`;
CREATE TABLE IF NOT EXISTS `trasppartidas` (
  `id_trasp` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ppto_egr_ing` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `referencia` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha` date NOT NULL,
  `monto` decimal(32,2) NOT NULL,
  `concepto` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_trasp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `trasppartidas_det`
--

DROP TABLE IF EXISTS `trasppartidas_det`;
CREATE TABLE IF NOT EXISTS `trasppartidas_det` (
  `id_trasp_det` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `id_trasp` bigint NOT NULL,
  `ppto_egr_ing` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `tipo_ori_dest` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `codpresu` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `partida` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `monto` decimal(32,2) NOT NULL,
  PRIMARY KEY (`id_trasp_det`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `trasp_part_rpt_summary`
--

DROP TABLE IF EXISTS `trasp_part_rpt_summary`;
CREATE TABLE IF NOT EXISTS `trasp_part_rpt_summary` (
  `id_trasp_rpt` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `ppto_egr_ing` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `referencia` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha` date NOT NULL,
  `monto` decimal(32,2) NOT NULL,
  `concepto` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (`id_trasp_rpt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `unidad_tributaria_aplic`
--

DROP TABLE IF EXISTS `unidad_tributaria_aplic`;
CREATE TABLE IF NOT EXISTS `unidad_tributaria_aplic` (
  `id_unidad_tributaria_aplic` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `id_session` bigint NOT NULL,
  `date_session` datetime NOT NULL,
  `descripcion` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha_vigencia` date NOT NULL,
  `valor_bs` decimal(32,2) NOT NULL COMMENT 'EL valor como representacion del porcentaje de la taza aplicada, ej. si la taza es de 12%, el valor almacenado serÃ¡ 12,00',
  PRIMARY KEY (`id_unidad_tributaria_aplic`),
  KEY `descripcion` (`descripcion`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Dumping data for table `unidad_tributaria_aplic`
--

INSERT INTO `unidad_tributaria_aplic` (`id_unidad_tributaria_aplic`, `id_user`, `id_session`, `date_session`, `descripcion`, `fecha_vigencia`, `valor_bs`) VALUES
(1, 1, 37, '2022-09-02 15:20:05', 'UND.T', '2022-09-02', '5.00');

-- --------------------------------------------------------

--
-- Table structure for table `user_menu_track`
--

DROP TABLE IF EXISTS `user_menu_track`;
CREATE TABLE IF NOT EXISTS `user_menu_track` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `iduser` bigint NOT NULL,
  `class` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `op` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `obs` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `ejefis` bigint NOT NULL,
  `ejefismes` bigint NOT NULL,
  `userdatetime` datetime DEFAULT NULL,
  `servdatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=351 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Dumping data for table `user_menu_track`
--

INSERT INTO `user_menu_track` (`id`, `iduser`, `class`, `op`, `obs`, `ejefis`, `ejefismes`, `userdatetime`, `servdatetime`) VALUES
(1, 0, 'capipsistema.UserPassIn', '', '', 2023, 202301, '2023-01-02 15:17:30', '2023-01-02 18:17:32'),
(2, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202301, '2023-01-02 15:17:42', '2023-01-02 18:17:42'),
(3, 1, 'configuracion.Configuracion', '', '', 2023, 202301, '2023-01-02 15:17:52', '2023-01-02 18:17:52'),
(4, 1, 'compromisos.Compromiso', '', '', 2023, 202301, '2023-01-02 15:18:26', '2023-01-02 18:18:26'),
(5, 1, 'compromisos.PresupeSel', '', '', 2023, 202301, '2023-01-02 15:18:47', '2023-01-02 18:18:47'),
(6, 1, 'bancos.BancosContabilidad', '', '', 2023, 202301, '2023-01-02 15:19:22', '2023-01-02 18:19:22'),
(7, 1, 'bancos.BancosCuentasRegistro', '', '', 2023, 202301, '2023-01-02 15:19:26', '2023-01-02 18:19:26'),
(8, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202301, '2023-01-02 15:20:34', '2023-01-02 18:20:34'),
(9, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-02 08:47:09', '2023-10-02 11:47:09'),
(10, 0, 'capipsistema.UserPassChange', '', '', 2023, 202310, '2023-10-02 08:47:42', '2023-10-02 11:47:41'),
(11, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-02 08:47:45', '2023-10-02 11:47:45'),
(12, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-02 08:49:47', '2023-10-02 11:49:47'),
(13, 0, 'capipsistema.UserPassChange', '', '', 2023, 202310, '2023-10-02 08:49:51', '2023-10-02 11:49:50'),
(14, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-02 08:50:16', '2023-10-02 11:50:16'),
(15, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-02 08:50:28', '2023-10-02 11:50:28'),
(16, 1, 'presupuesto.PptoInstitucion', '', '', 2023, 202310, '2023-10-02 08:50:46', '2023-10-02 11:50:45'),
(17, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-03 08:22:50', '2023-10-03 11:22:50'),
(18, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-03 08:22:59', '2023-10-03 11:22:59'),
(19, 1, 'presupuesto.PptoInstitucion', '', '', 2023, 202310, '2023-10-03 08:23:03', '2023-10-03 11:23:02'),
(20, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-03 08:25:16', '2023-10-03 11:25:16'),
(21, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-03 16:36:30', '2023-10-03 19:36:30'),
(22, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-03 16:36:40', '2023-10-03 19:36:40'),
(23, 1, 'presupuesto.PptoInstitucion', '', '', 2023, 202310, '2023-10-03 16:36:55', '2023-10-03 19:36:55'),
(24, 1, 'causados.Causado', '', '', 2023, 202310, '2023-10-03 16:37:19', '2023-10-03 19:37:19'),
(25, 0, 'bancos.BancosChequeAvanceCambiar', '', '', 2023, 202310, '2023-10-11 09:31:41', '2023-10-11 12:31:40'),
(26, 0, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-11 09:32:12', '2023-10-11 12:32:11'),
(27, 0, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-11 09:32:19', '2023-10-11 12:32:18'),
(28, 0, 'bancos.BancosChequeAvanceCambiar', '', '', 2023, 202310, '2023-10-11 10:45:28', '2023-10-11 13:45:27'),
(29, 0, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-11 10:45:51', '2023-10-11 13:45:50'),
(30, 0, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-11 10:45:53', '2023-10-11 13:45:53'),
(31, 0, 'bancos.BancosChequeAvanceCambiar', '', '', 2023, 202310, '2023-10-11 10:46:39', '2023-10-11 13:46:39'),
(32, 0, 'bancos.BancosChequeAvanceCambiar', '', '', 2023, 202310, '2023-10-11 10:51:34', '2023-10-11 13:51:33'),
(33, 0, 'bancos.BancosChequeAvanceCambiar', '', '', 2023, 202310, '2023-10-11 11:08:04', '2023-10-11 14:08:03'),
(34, 0, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-11 11:11:24', '2023-10-11 14:11:23'),
(35, 0, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-11 11:11:26', '2023-10-11 14:11:26'),
(36, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-19 10:23:35', '2023-10-19 13:23:36'),
(37, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 10:23:44', '2023-10-19 13:23:44'),
(38, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 10:23:50', '2023-10-19 13:23:49'),
(39, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-19 10:47:12', '2023-10-19 13:47:12'),
(40, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-19 10:51:15', '2023-10-19 13:51:15'),
(41, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 10:51:32', '2023-10-19 13:51:31'),
(42, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 10:51:36', '2023-10-19 13:51:35'),
(43, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-19 10:56:52', '2023-10-19 13:56:52'),
(44, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 10:57:05', '2023-10-19 13:57:04'),
(45, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 10:59:06', '2023-10-19 13:59:06'),
(46, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-19 11:01:03', '2023-10-19 14:01:03'),
(47, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-19 11:05:59', '2023-10-19 14:05:59'),
(48, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-19 11:06:30', '2023-10-19 14:06:30'),
(49, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 11:07:01', '2023-10-19 14:07:01'),
(50, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-19 11:10:21', '2023-10-19 14:10:21'),
(51, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 11:10:38', '2023-10-19 14:10:37'),
(52, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 11:10:41', '2023-10-19 14:10:41'),
(53, 0, 'capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-19 11:11:42', '2023-10-19 14:11:42'),
(54, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 11:12:54', '2023-10-19 14:12:54'),
(55, 1, 'capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-19 11:12:57', '2023-10-19 14:12:57'),
(56, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 15:03:52', '2023-10-21 18:03:52'),
(57, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-21 15:04:12', '2023-10-21 18:04:11'),
(58, 1, 'com.principal.presupuesto.PptoInstitucion', '', '', 2023, 202310, '2023-10-21 15:04:22', '2023-10-21 18:04:21'),
(59, 1, 'com.principal.presupuesto.CreditoAdicional', '', '', 2023, 202310, '2023-10-21 15:05:36', '2023-10-21 18:05:35'),
(60, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-21 15:05:50', '2023-10-21 18:05:49'),
(61, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 15:21:58', '2023-10-21 18:21:58'),
(62, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 15:45:01', '2023-10-21 18:45:01'),
(63, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 15:46:40', '2023-10-21 18:46:40'),
(64, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 15:47:47', '2023-10-21 18:47:47'),
(65, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 16:07:45', '2023-10-21 19:07:45'),
(66, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 18:56:55', '2023-10-21 21:56:55'),
(67, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 18:57:38', '2023-10-21 21:57:38'),
(68, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 18:59:47', '2023-10-21 21:59:47'),
(69, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 19:00:15', '2023-10-21 22:00:15'),
(70, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 19:01:17', '2023-10-21 22:01:17'),
(71, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 19:02:20', '2023-10-21 22:02:20'),
(72, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 19:12:44', '2023-10-21 22:12:44'),
(73, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 19:13:23', '2023-10-21 22:13:23'),
(74, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 19:20:04', '2023-10-21 22:20:04'),
(75, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-21 19:20:37', '2023-10-21 22:20:37'),
(76, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-22 20:12:04', '2023-10-22 23:12:04'),
(77, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-22 20:14:26', '2023-10-22 23:14:26'),
(78, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-22 20:15:08', '2023-10-22 23:15:07'),
(79, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-22 20:15:27', '2023-10-22 23:15:27'),
(80, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 10:56:46', '2023-10-23 13:56:46'),
(81, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:02:51', '2023-10-23 14:02:50'),
(82, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:06:57', '2023-10-23 14:06:57'),
(83, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:13:20', '2023-10-23 14:13:20'),
(84, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:13:52', '2023-10-23 14:13:52'),
(85, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:14:23', '2023-10-23 14:14:22'),
(86, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:15:51', '2023-10-23 14:15:51'),
(87, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:18:47', '2023-10-23 14:18:46'),
(88, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:20:00', '2023-10-23 14:20:00'),
(89, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:23:27', '2023-10-23 14:23:27'),
(90, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:25:08', '2023-10-23 14:25:08'),
(91, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:27:28', '2023-10-23 14:27:28'),
(92, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:29:04', '2023-10-23 14:29:04'),
(93, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:32:33', '2023-10-23 14:32:33'),
(94, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:34:21', '2023-10-23 14:34:21'),
(95, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:35:17', '2023-10-23 14:35:17'),
(96, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:38:31', '2023-10-23 14:38:31'),
(97, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 11:39:54', '2023-10-23 14:39:53'),
(98, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 12:20:09', '2023-10-23 15:20:09'),
(99, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 12:35:43', '2023-10-23 15:35:43'),
(100, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-23 12:35:51', '2023-10-23 15:35:50'),
(101, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 12:42:06', '2023-10-23 15:42:06'),
(102, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-23 12:42:13', '2023-10-23 15:42:12'),
(103, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-23 12:42:25', '2023-10-23 15:42:25'),
(104, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 13:10:12', '2023-10-23 16:10:11'),
(105, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 13:13:01', '2023-10-23 16:13:00'),
(106, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 13:14:46', '2023-10-23 16:14:46'),
(107, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 13:15:46', '2023-10-23 16:15:45'),
(108, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 13:26:26', '2023-10-23 16:26:25'),
(109, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 13:27:20', '2023-10-23 16:27:20'),
(110, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 14:31:23', '2023-10-23 17:31:23'),
(111, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 14:36:52', '2023-10-23 17:36:52'),
(112, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 14:37:45', '2023-10-23 17:37:44'),
(113, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 15:41:25', '2023-10-23 18:41:25'),
(114, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 15:42:34', '2023-10-23 18:42:33'),
(115, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 15:49:01', '2023-10-23 18:49:00'),
(116, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 15:50:00', '2023-10-23 18:49:59'),
(117, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 15:51:41', '2023-10-23 18:51:41'),
(118, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 15:52:36', '2023-10-23 18:52:36'),
(119, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 15:55:35', '2023-10-23 18:55:35'),
(120, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 16:05:28', '2023-10-23 19:05:28'),
(121, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 16:17:15', '2023-10-23 19:17:15'),
(122, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 18:21:43', '2023-10-23 21:21:43'),
(123, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202310, '2023-10-23 18:24:58', '2023-10-23 21:24:58'),
(124, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 18:26:31', '2023-10-23 21:26:30'),
(125, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 18:27:18', '2023-10-23 21:27:17'),
(126, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-23 18:27:39', '2023-10-23 21:27:39'),
(127, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 18:42:41', '2023-10-23 21:42:40'),
(128, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 18:46:16', '2023-10-23 21:46:15'),
(129, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 18:50:37', '2023-10-23 21:50:36'),
(130, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 18:54:14', '2023-10-23 21:54:14'),
(131, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 18:55:54', '2023-10-23 21:55:53'),
(132, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 18:59:13', '2023-10-23 21:59:13'),
(133, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 19:18:44', '2023-10-23 22:18:43'),
(134, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 19:21:28', '2023-10-23 22:21:28'),
(135, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 19:22:11', '2023-10-23 22:22:11'),
(136, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 19:23:37', '2023-10-23 22:23:36'),
(137, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 19:25:07', '2023-10-23 22:25:07'),
(138, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 19:26:51', '2023-10-23 22:26:51'),
(139, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 19:28:13', '2023-10-23 22:28:13'),
(140, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 20:03:02', '2023-10-23 23:03:02'),
(141, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-23 20:06:17', '2023-10-23 23:06:17'),
(142, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-23 20:06:30', '2023-10-23 23:06:30'),
(143, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 07:01:07', '2023-10-24 10:01:07'),
(144, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 07:36:37', '2023-10-24 10:36:37'),
(145, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 08:42:19', '2023-10-24 11:42:19'),
(146, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-24 08:42:43', '2023-10-24 11:42:43'),
(147, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 08:46:55', '2023-10-24 11:46:54'),
(148, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 08:51:32', '2023-10-24 11:51:31'),
(149, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202310, '2023-10-24 08:55:21', '2023-10-24 11:55:20'),
(150, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 08:56:49', '2023-10-24 11:56:48'),
(151, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 08:59:23', '2023-10-24 11:59:23'),
(152, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 09:04:00', '2023-10-24 12:04:00'),
(153, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-24 09:04:20', '2023-10-24 12:04:19'),
(154, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-24 09:16:55', '2023-10-24 12:16:55'),
(155, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-24 09:17:05', '2023-10-24 12:17:04'),
(156, 1, 'com.principal.bancos.BancosContabilidad', '', '', 2023, 202310, '2023-10-24 09:17:13', '2023-10-24 12:17:12'),
(157, 1, 'com.principal.bancos.BancosDeposito', '', '', 2023, 202310, '2023-10-24 09:17:27', '2023-10-24 12:17:26'),
(158, 1, 'com.principal.presupuesto.PptoInstitucion', '', '', 2023, 202310, '2023-10-24 09:19:23', '2023-10-24 12:19:22'),
(159, 1, 'com.principal.presupuesto.PptoMod', '', '', 2023, 202310, '2023-10-24 09:19:41', '2023-10-24 12:19:41'),
(160, 1, 'com.principal.pagos.PagoOrden', '', '', 2023, 202310, '2023-10-24 09:20:32', '2023-10-24 12:20:31'),
(161, 1, 'com.principal.pagos.PagoDirectoSel', '', '', 2023, 202310, '2023-10-24 09:20:37', '2023-10-24 12:20:36'),
(162, 1, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 09:20:40', '2023-10-24 12:20:40'),
(163, 1, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-24 09:24:08', '2023-10-24 12:24:08'),
(164, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202310, '2023-10-24 09:28:04', '2023-10-24 12:28:04'),
(165, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 09:28:16', '2023-10-24 12:28:16'),
(166, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-24 09:28:39', '2023-10-24 12:28:38'),
(167, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 09:33:35', '2023-10-24 12:33:34'),
(168, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-24 09:36:53', '2023-10-24 12:36:53'),
(169, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 09:37:01', '2023-10-24 12:37:01'),
(170, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202310, '2023-10-24 09:37:09', '2023-10-24 12:37:08'),
(171, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-24 09:37:18', '2023-10-24 12:37:18'),
(172, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 05:49:18', '2023-10-25 08:49:18'),
(173, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202310, '2023-10-25 05:51:17', '2023-10-25 08:51:17'),
(174, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 06:32:25', '2023-10-25 09:32:25'),
(175, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 06:39:35', '2023-10-25 09:39:35'),
(176, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 06:45:39', '2023-10-25 09:45:38'),
(177, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 06:51:52', '2023-10-25 09:51:51'),
(178, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 06:58:05', '2023-10-25 09:58:05'),
(179, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 08:00:39', '2023-10-25 11:00:38'),
(180, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 08:03:50', '2023-10-25 11:03:50'),
(181, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-25 08:04:18', '2023-10-25 11:04:18'),
(182, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 08:12:17', '2023-10-25 11:12:16'),
(183, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-25 08:12:33', '2023-10-25 11:12:33'),
(184, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-25 08:16:32', '2023-10-25 11:16:32'),
(185, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 09:05:34', '2023-10-25 12:05:34'),
(186, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-25 09:05:52', '2023-10-25 12:05:52'),
(187, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-25 09:06:49', '2023-10-25 12:06:49'),
(188, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 09:11:20', '2023-10-25 12:11:19'),
(189, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-25 09:11:31', '2023-10-25 12:11:30'),
(190, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-25 09:11:55', '2023-10-25 12:11:54'),
(191, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202310, '2023-10-25 09:18:06', '2023-10-25 12:18:06'),
(192, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-25 09:18:22', '2023-10-25 12:18:22'),
(193, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-25 09:18:41', '2023-10-25 12:18:40'),
(194, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-27 07:05:01', '2023-10-27 10:05:00'),
(195, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 07:05:10', '2023-10-27 10:05:10'),
(196, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 07:10:04', '2023-10-27 10:10:04'),
(197, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 07:13:20', '2023-10-27 10:13:20'),
(198, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 07:21:38', '2023-10-27 10:21:38'),
(199, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 07:25:51', '2023-10-27 10:25:51'),
(200, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 07:47:47', '2023-10-27 10:47:47'),
(201, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 07:50:45', '2023-10-27 10:50:45'),
(202, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 08:00:37', '2023-10-27 11:00:36'),
(203, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 08:04:14', '2023-10-27 11:04:13'),
(204, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 08:09:38', '2023-10-27 11:09:38'),
(205, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202310, '2023-10-27 08:49:11', '2023-10-27 11:49:11'),
(206, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 08:49:20', '2023-10-27 11:49:20'),
(207, 1, 'com.principal.compromisos.Compromiso', '', '', 2023, 202310, '2023-10-27 08:49:25', '2023-10-27 11:49:24'),
(208, 1, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202310, '2023-10-27 08:49:54', '2023-10-27 11:49:53'),
(209, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 08:52:04', '2023-10-27 11:52:03'),
(210, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 09:00:05', '2023-10-27 12:00:05'),
(211, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 09:04:38', '2023-10-27 12:04:38'),
(212, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 09:11:14', '2023-10-27 12:11:14'),
(213, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 09:37:24', '2023-10-27 12:37:24'),
(214, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:19:01', '2023-10-27 13:19:01'),
(215, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:21:04', '2023-10-27 13:21:04'),
(216, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:21:50', '2023-10-27 13:21:49'),
(217, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:23:51', '2023-10-27 13:23:51'),
(218, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:27:27', '2023-10-27 13:27:27'),
(219, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:33:08', '2023-10-27 13:33:08'),
(220, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:37:13', '2023-10-27 13:37:13'),
(221, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:39:07', '2023-10-27 13:39:07'),
(222, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:40:25', '2023-10-27 13:40:25'),
(223, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:42:19', '2023-10-27 13:42:19'),
(224, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:44:47', '2023-10-27 13:44:47'),
(225, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:46:27', '2023-10-27 13:46:26'),
(226, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:48:03', '2023-10-27 13:48:03'),
(227, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 10:49:33', '2023-10-27 13:49:33'),
(228, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 12:47:26', '2023-10-27 15:47:26'),
(229, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 12:50:37', '2023-10-27 15:50:37'),
(230, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 13:05:39', '2023-10-27 16:05:39'),
(231, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 13:06:06', '2023-10-27 16:06:06'),
(232, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 14:49:48', '2023-10-27 17:49:48'),
(233, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 14:51:57', '2023-10-27 17:51:57'),
(234, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 14:55:59', '2023-10-27 17:55:59'),
(235, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 15:08:08', '2023-10-27 18:08:08'),
(236, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 15:11:58', '2023-10-27 18:11:58'),
(237, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 15:12:41', '2023-10-27 18:12:41'),
(238, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 15:13:14', '2023-10-27 18:13:14'),
(239, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 15:15:21', '2023-10-27 18:15:21'),
(240, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 15:41:43', '2023-10-27 18:41:43'),
(241, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 15:43:59', '2023-10-27 18:43:59'),
(242, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 16:58:42', '2023-10-27 19:58:42'),
(243, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:00:48', '2023-10-27 20:00:47'),
(244, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:14:18', '2023-10-27 20:14:18'),
(245, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:16:02', '2023-10-27 20:16:01'),
(246, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:18:32', '2023-10-27 20:18:32'),
(247, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:22:17', '2023-10-27 20:22:17'),
(248, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:23:02', '2023-10-27 20:23:02'),
(249, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:24:39', '2023-10-27 20:24:39'),
(250, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:26:07', '2023-10-27 20:26:07'),
(251, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:27:06', '2023-10-27 20:27:06'),
(252, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:27:21', '2023-10-27 20:27:21'),
(253, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:28:00', '2023-10-27 20:28:00'),
(254, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:28:41', '2023-10-27 20:28:40'),
(255, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:29:19', '2023-10-27 20:29:19'),
(256, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:29:48', '2023-10-27 20:29:48'),
(257, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:41:36', '2023-10-27 20:41:36'),
(258, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:44:05', '2023-10-27 20:44:05'),
(259, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:49:52', '2023-10-27 20:49:52'),
(260, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 17:50:52', '2023-10-27 20:50:52'),
(261, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 18:16:06', '2023-10-27 21:16:05'),
(262, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 18:20:01', '2023-10-27 21:20:01'),
(263, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 18:20:30', '2023-10-27 21:20:30'),
(264, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 18:27:46', '2023-10-27 21:27:46'),
(265, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 18:28:24', '2023-10-27 21:28:24'),
(266, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202310, '2023-10-27 19:49:46', '2023-10-27 22:49:46'),
(267, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-05 10:15:29', '2023-11-05 13:15:29'),
(268, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-05 10:29:01', '2023-11-05 13:29:01'),
(269, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-05 10:32:11', '2023-11-05 13:32:11'),
(270, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-05 10:45:32', '2023-11-05 13:45:32'),
(271, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-05 10:49:19', '2023-11-05 13:49:19'),
(272, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-05 10:50:21', '2023-11-05 13:50:21'),
(273, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-05 10:51:55', '2023-11-05 13:51:55'),
(274, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-05 10:53:56', '2023-11-05 13:53:56'),
(275, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-05 10:54:35', '2023-11-05 13:54:35'),
(276, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202311, '2023-11-05 14:23:37', '2023-11-05 17:23:37'),
(277, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-05 14:23:44', '2023-11-05 17:23:43'),
(278, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-05 14:29:38', '2023-11-05 17:29:38'),
(279, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-05 17:18:23', '2023-11-05 20:18:23'),
(280, 0, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202311, '2023-11-05 18:32:03', '2023-11-05 21:32:03'),
(281, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-05 18:32:51', '2023-11-05 21:32:51'),
(282, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-05 18:42:45', '2023-11-05 21:42:44'),
(283, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202311, '2023-11-05 19:08:38', '2023-11-05 22:08:38'),
(284, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-05 19:08:48', '2023-11-05 22:08:48'),
(285, 1, 'com.principal.pagos.PagoOrden', '', '', 2023, 202311, '2023-11-05 19:08:55', '2023-11-05 22:08:55'),
(286, 1, 'com.principal.bancos.BancosContabilidad', '', '', 2023, 202311, '2023-11-05 19:09:19', '2023-11-05 22:09:19'),
(287, 1, 'com.principal.presupuesto.PptoInstitucion', '', '', 2023, 202311, '2023-11-05 19:09:27', '2023-11-05 22:09:26'),
(288, 1, 'com.principal.reportes.ReporteSel', '', '', 2023, 202311, '2023-11-05 19:09:34', '2023-11-05 22:09:33'),
(289, 1, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-05 19:09:49', '2023-11-05 22:09:49'),
(290, 1, 'com.principal.presupuesto.PptoInstitucion', '', '', 2023, 202311, '2023-11-05 19:28:36', '2023-11-05 22:28:35'),
(291, 1, 'com.principal.pagos.PagoOrden', '', '', 2023, 202311, '2023-11-05 19:28:44', '2023-11-05 22:28:44'),
(292, 1, 'com.principal.pagos.PagoDirectoSel', '', '', 2023, 202311, '2023-11-05 19:28:47', '2023-11-05 22:28:46'),
(293, 1, 'com.principal.pagos.PagoDirectoNormal', '', '', 2023, 202311, '2023-11-05 19:28:49', '2023-11-05 22:28:49'),
(294, 1, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-05 20:09:44', '2023-11-05 23:09:44'),
(295, 1, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-05 20:10:14', '2023-11-05 23:10:13'),
(296, 1, 'com.principal.causados.Causado', '', '', 2023, 202311, '2023-11-05 20:10:14', '2023-11-05 23:10:13'),
(297, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-06 08:09:18', '2023-11-06 11:09:18'),
(298, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202311, '2023-11-06 08:09:58', '2023-11-06 11:09:58'),
(299, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-06 08:10:11', '2023-11-06 11:10:11'),
(300, 1, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-06 08:10:15', '2023-11-06 11:10:15'),
(301, 1, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-06 08:10:47', '2023-11-06 11:10:47'),
(302, 0, 'com.principal.capipsistema.UserPassIn', '', '', 2023, 202311, '2023-11-06 09:16:42', '2023-11-06 12:16:41'),
(303, 1, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-06 09:16:50', '2023-11-06 12:16:50'),
(304, 1, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-06 09:16:55', '2023-11-06 12:16:55'),
(305, 1, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-06 09:17:25', '2023-11-06 12:17:25'),
(306, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-06 09:32:15', '2023-11-06 12:32:15'),
(307, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-06 09:32:41', '2023-11-06 12:32:40'),
(308, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-06 09:32:54', '2023-11-06 12:32:54'),
(309, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-06 09:33:53', '2023-11-06 12:33:52'),
(310, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-06 09:34:17', '2023-11-06 12:34:17'),
(311, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-06 09:34:29', '2023-11-06 12:34:28'),
(312, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-06 09:37:57', '2023-11-06 12:37:56'),
(313, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-06 09:38:20', '2023-11-06 12:38:20'),
(314, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-06 09:38:32', '2023-11-06 12:38:31'),
(315, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-07 07:24:19', '2023-11-07 10:24:18'),
(316, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-07 07:24:48', '2023-11-07 10:24:47'),
(317, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 07:25:07', '2023-11-07 10:25:07'),
(318, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-07 07:33:22', '2023-11-07 10:33:21'),
(319, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-07 07:33:46', '2023-11-07 10:33:46'),
(320, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 07:33:59', '2023-11-07 10:33:59'),
(321, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-07 07:38:54', '2023-11-07 10:38:53'),
(322, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-07 07:39:23', '2023-11-07 10:39:23'),
(323, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 07:39:37', '2023-11-07 10:39:37'),
(324, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-07 07:42:26', '2023-11-07 10:42:26'),
(325, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-07 07:42:54', '2023-11-07 10:42:54'),
(326, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 07:43:15', '2023-11-07 10:43:14'),
(327, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-07 07:52:13', '2023-11-07 10:52:12'),
(328, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-07 07:52:37', '2023-11-07 10:52:37'),
(329, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 07:52:51', '2023-11-07 10:52:51'),
(330, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-07 07:58:14', '2023-11-07 10:58:14'),
(331, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-07 07:58:35', '2023-11-07 10:58:35'),
(332, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 07:58:45', '2023-11-07 10:58:45'),
(333, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-07 08:00:37', '2023-11-07 11:00:37'),
(334, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-07 08:01:01', '2023-11-07 11:01:00'),
(335, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 08:01:19', '2023-11-07 11:01:18'),
(336, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 08:04:06', '2023-11-07 11:04:06'),
(337, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 10:10:55', '2023-11-07 13:10:55'),
(338, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 10:12:33', '2023-11-07 13:12:33'),
(339, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 10:13:56', '2023-11-07 13:13:56'),
(340, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 10:14:36', '2023-11-07 13:14:36'),
(341, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 15:18:24', '2023-11-07 18:18:24'),
(342, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 15:25:39', '2023-11-07 18:25:39'),
(343, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 15:26:14', '2023-11-07 18:26:14'),
(344, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 15:26:48', '2023-11-07 18:26:48'),
(345, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 15:28:02', '2023-11-07 18:28:02'),
(346, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 15:28:36', '2023-11-07 18:28:36'),
(347, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 15:29:26', '2023-11-07 18:29:26'),
(348, 0, 'com.principal.compromisos.Compromiso', '', '', 2023, 202311, '2023-11-07 15:33:42', '2023-11-07 18:33:41'),
(349, 0, 'com.principal.compromisos.PresupeSel', '', '', 2023, 202311, '2023-11-07 15:34:09', '2023-11-07 18:34:08'),
(350, 0, 'com.principal.capipsistema.FrmPrincipal', '', '', 2023, 202311, '2023-11-07 15:34:31', '2023-11-07 18:34:30');

-- --------------------------------------------------------

--
-- Table structure for table `user_menu_track_summary`
--

DROP TABLE IF EXISTS `user_menu_track_summary`;
CREATE TABLE IF NOT EXISTS `user_menu_track_summary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `mnuclass` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `op` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `obs` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `userdatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `servdatetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fchsession` date NOT NULL,
  `idsession` bigint NOT NULL,
  `iduser` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id_user` bigint NOT NULL AUTO_INCREMENT,
  `user` varchar(32) NOT NULL,
  `pass` binary(16) DEFAULT NULL,
  `nombre` varchar(768) NOT NULL,
  `ppto` tinyint(1) DEFAULT NULL,
  `compr` tinyint(1) DEFAULT NULL,
  `cau` tinyint(1) DEFAULT NULL,
  `imp` tinyint(1) DEFAULT NULL,
  `pago` tinyint(1) DEFAULT NULL,
  `banco` tinyint(1) DEFAULT NULL,
  `cfg` tinyint(1) DEFAULT NULL,
  `rpt` tinyint(1) DEFAULT NULL,
  `active` enum('false','true') NOT NULL,
  `lastupdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `usuario_UNIQUE` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id_user`, `user`, `pass`, `nombre`, `ppto`, `compr`, `cau`, `imp`, `pago`, `banco`, `cfg`, `rpt`, `active`, `lastupdate`) VALUES
(1, 'ADMIN', 0x21232f297a57a5a743894a0e4a801fc3, 'ADMINISTRADOR (SUPER USUARIO)', 1, 1, 1, 1, 1, 1, 1, 1, 'true', NULL);
SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
