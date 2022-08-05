-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: capip
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ava_efe_aux_report`
--

DROP TABLE IF EXISTS `ava_efe_aux_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ava_efe_aux_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codpresu` varchar(32) CHARACTER SET latin1 NOT NULL,
  `partida` varchar(256) CHARACTER SET latin1 NOT NULL,
  `total` double NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`),
  KEY `iduser` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ava_efe_aux_report`
--

LOCK TABLES `ava_efe_aux_report` WRITE;
/*!40000 ALTER TABLE `ava_efe_aux_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `ava_efe_aux_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ava_efe_rpt_summary`
--

DROP TABLE IF EXISTS `ava_efe_rpt_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ava_efe_rpt_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL,
  `fecha` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8 NOT NULL,
  `total` double NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ava_efe_rpt_summary`
--

LOCK TABLES `ava_efe_rpt_summary` WRITE;
/*!40000 ALTER TABLE `ava_efe_rpt_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `ava_efe_rpt_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avance_efectivo`
--

DROP TABLE IF EXISTS `avance_efectivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avance_efectivo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `benef_razonsocial` varchar(128) NOT NULL,
  `benef_rif_ci` varchar(32) NOT NULL,
  `fecha_pago` date NOT NULL,
  `observacion` varchar(512) NOT NULL,
  `banco` varchar(128) NOT NULL,
  `cuenta` varchar(20) NOT NULL,
  `cheque` varchar(20) NOT NULL,
  `fecha_cheque` date NOT NULL,
  `endosable_sn` char(1) NOT NULL DEFAULT 'N',
  `a_pagar_bs` decimal(20,2) NOT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avance_efectivo`
--

LOCK TABLES `avance_efectivo` WRITE;
/*!40000 ALTER TABLE `avance_efectivo` DISABLE KEYS */;
/*!40000 ALTER TABLE `avance_efectivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banco_saldo_anual`
--

DROP TABLE IF EXISTS `banco_saldo_anual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `banco_saldo_anual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `id_banco` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `saldoi` decimal(20,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banco_saldo_anual`
--

LOCK TABLES `banco_saldo_anual` WRITE;
/*!40000 ALTER TABLE `banco_saldo_anual` DISABLE KEYS */;
/*!40000 ALTER TABLE `banco_saldo_anual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bancos`
--

DROP TABLE IF EXISTS `bancos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bancos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cuenta` varchar(20) NOT NULL,
  `banco` varchar(30) NOT NULL,
  `saldoi` double NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` int(11) NOT NULL,
  `ejefismes` int(11) NOT NULL,
  `iduser` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bancos`
--

LOCK TABLES `bancos` WRITE;
/*!40000 ALTER TABLE `bancos` DISABLE KEYS */;
/*!40000 ALTER TABLE `bancos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bancos_ch_anu`
--

DROP TABLE IF EXISTS `bancos_ch_anu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bancos_ch_anu` (
  `id_ch_anu` int(11) NOT NULL AUTO_INCREMENT,
  `banco` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `cuenta` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `cheque_anu` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `cheque_nvo` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `monto` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `pag_ava` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `num_pag_ava` int(11) NOT NULL,
  `motivo_anu` varchar(128) COLLATE utf8_spanish_ci NOT NULL,
  `fecha_anu` date NOT NULL,
  `ejefis` int(11) NOT NULL,
  `ejefismes` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`id_ch_anu`),
  KEY `cuenta` (`cuenta`),
  KEY `ejefismes` (`ejefismes`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bancos_ch_anu`
--

LOCK TABLES `bancos_ch_anu` WRITE;
/*!40000 ALTER TABLE `bancos_ch_anu` DISABLE KEYS */;
/*!40000 ALTER TABLE `bancos_ch_anu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bancos_ch_anu_aux_rpt`
--

DROP TABLE IF EXISTS `bancos_ch_anu_aux_rpt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bancos_ch_anu_aux_rpt` (
  `id_ch_anu` int(11) NOT NULL AUTO_INCREMENT,
  `banco` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `cuenta` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `cheque_anu` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `cheque_nvo` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `monto` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `pag_ava` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `num_pag_ava` int(11) NOT NULL,
  `motivo_anu` varchar(128) COLLATE utf8_spanish_ci NOT NULL,
  `fecha_anu` date NOT NULL,
  `idsession` int(11) NOT NULL,
  `fchsession` date NOT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`id_ch_anu`),
  KEY `cuenta` (`cuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bancos_ch_anu_aux_rpt`
--

LOCK TABLES `bancos_ch_anu_aux_rpt` WRITE;
/*!40000 ALTER TABLE `bancos_ch_anu_aux_rpt` DISABLE KEYS */;
/*!40000 ALTER TABLE `bancos_ch_anu_aux_rpt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bancos_cheque`
--

DROP TABLE IF EXISTS `bancos_cheque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bancos_cheque` (
  `id_cheque` int(11) NOT NULL,
  `banco` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `cuenta` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `cheque` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `monto` decimal(20,2) NOT NULL,
  `op` varchar(2) COLLATE utf8_spanish_ci NOT NULL,
  `idop` int(11) NOT NULL,
  `anulado` tinyint(4) NOT NULL DEFAULT '0',
  `conciliado` tinyint(4) NOT NULL DEFAULT '0',
  `ejefis` int(11) NOT NULL,
  `ejefismes` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`id_cheque`),
  UNIQUE KEY `cuenta_cheque` (`cuenta`,`cheque`),
  KEY `cuenta` (`cuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bancos_cheque`
--

LOCK TABLES `bancos_cheque` WRITE;
/*!40000 ALTER TABLE `bancos_cheque` DISABLE KEYS */;
/*!40000 ALTER TABLE `bancos_cheque` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bancos_operaciones`
--

DROP TABLE IF EXISTS `bancos_operaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bancos_operaciones` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cuenta` varchar(20) CHARACTER SET utf8 NOT NULL,
  `banco` varchar(30) CHARACTER SET utf8 NOT NULL,
  `descripcion` varchar(80) CHARACTER SET utf8 NOT NULL,
  `fecha` varchar(10) CHARACTER SET utf8 NOT NULL,
  `tipo_operacion` varchar(2) CHARACTER SET utf8 NOT NULL,
  `soporte_o_cheque` varchar(20) CHARACTER SET utf8 NOT NULL,
  `conciliado` varchar(11) CHARACTER SET utf8 NOT NULL,
  `debe` double NOT NULL,
  `haber` double NOT NULL,
  `registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_trans_ori` bigint(20) NOT NULL DEFAULT '0',
  `cuenta_numop` bigint(20) NOT NULL,
  `cuenta_numop_tipo` bigint(20) NOT NULL DEFAULT '0',
  `num_pag_ava` bigint(20) NOT NULL,
  `ejefis` bigint(20) NOT NULL,
  `ejefismes` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  UNIQUE KEY `numop` (`ejefis`,`cuenta`,`cuenta_numop`),
  UNIQUE KEY `cuenta_soporte` (`cuenta`,`soporte_o_cheque`),
  KEY `cuenta` (`cuenta`),
  KEY `id_trans_ori` (`id_trans_ori`),
  KEY `soporte` (`soporte_o_cheque`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bancos_operaciones`
--

LOCK TABLES `bancos_operaciones` WRITE;
/*!40000 ALTER TABLE `bancos_operaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `bancos_operaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `beneficiario`
--

DROP TABLE IF EXISTS `beneficiario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `beneficiario` (
  `id_beneficiario` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `domicilio` varchar(256) CHARACTER SET utf8 NOT NULL,
  `telefonos` varchar(32) CHARACTER SET utf8 NOT NULL,
  `activo` char(1) CHARACTER SET utf8 NOT NULL DEFAULT 'S',
  PRIMARY KEY (`id_beneficiario`),
  UNIQUE KEY `razonsocial` (`razonsocial`),
  UNIQUE KEY `rif` (`rif_ci`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beneficiario`
--

LOCK TABLES `beneficiario` WRITE;
/*!40000 ALTER TABLE `beneficiario` DISABLE KEYS */;
/*!40000 ALTER TABLE `beneficiario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `beneficiarios`
--

DROP TABLE IF EXISTS `beneficiarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `beneficiarios` (
  `razonsocial` varchar(80) CHARACTER SET utf8 NOT NULL,
  `domicilio` varchar(120) CHARACTER SET utf8 NOT NULL,
  `telefonos` varchar(30) CHARACTER SET utf8 NOT NULL,
  `rif` varchar(20) CHARACTER SET utf8 NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `numbenef` int(11) NOT NULL AUTO_INCREMENT,
  `activo` tinyint(4) DEFAULT '1',
  `iduser` int(11) DEFAULT NULL,
  PRIMARY KEY (`numbenef`),
  UNIQUE KEY `razonsocial` (`razonsocial`),
  UNIQUE KEY `rif` (`rif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beneficiarios`
--

LOCK TABLES `beneficiarios` WRITE;
/*!40000 ALTER TABLE `beneficiarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `beneficiarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cau_rpt_summary`
--

DROP TABLE IF EXISTS `cau_rpt_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cau_rpt_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL,
  `fecha` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8 NOT NULL,
  `total` double NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cau_rpt_summary`
--

LOCK TABLES `cau_rpt_summary` WRITE;
/*!40000 ALTER TABLE `cau_rpt_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `cau_rpt_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `causado`
--

DROP TABLE IF EXISTS `causado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `causado` (
  `id_causado` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `fecha_causado` date NOT NULL,
  `tipo_compr` char(2) NOT NULL,
  `benef_razonsocial` varchar(128) NOT NULL,
  `benef_rif_ci` varchar(32) NOT NULL,
  `observacion` varchar(512) NOT NULL,
  `resta_bs` decimal(20,2) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `causado`
--

LOCK TABLES `causado` WRITE;
/*!40000 ALTER TABLE `causado` DISABLE KEYS */;
/*!40000 ALTER TABLE `causado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `causado_aux_report`
--

DROP TABLE IF EXISTS `causado_aux_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `causado_aux_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codpresu` varchar(30) NOT NULL,
  `partida` varchar(100) NOT NULL,
  `total` double NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `conpresu` (`codpresu`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `causado_aux_report`
--

LOCK TABLES `causado_aux_report` WRITE;
/*!40000 ALTER TABLE `causado_aux_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `causado_aux_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `causado_avance_efectivo_nn`
--

DROP TABLE IF EXISTS `causado_avance_efectivo_nn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `causado_avance_efectivo_nn` (
  `id_causado_avance_efectivo` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_avance_efectivo` bigint(20) NOT NULL,
  `id_causado` bigint(20) NOT NULL,
  `apagar_bs` decimal(20,2) NOT NULL,
  PRIMARY KEY (`id_causado_avance_efectivo`),
  KEY `id_cau` (`id_causado`),
  KEY `id_pag` (`id_avance_efectivo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci COMMENT='Lleva el registro de cada uno de los pagos realizado a un Ca';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `causado_avance_efectivo_nn`
--

LOCK TABLES `causado_avance_efectivo_nn` WRITE;
/*!40000 ALTER TABLE `causado_avance_efectivo_nn` DISABLE KEYS */;
/*!40000 ALTER TABLE `causado_avance_efectivo_nn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `causado_det`
--

DROP TABLE IF EXISTS `causado_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `causado_det` (
  `id_causado_det` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_causado` bigint(20) NOT NULL,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `codpresu` varchar(32) NOT NULL,
  `partida` varchar(128) NOT NULL,
  `subtotal` decimal(20,2) NOT NULL,
  PRIMARY KEY (`id_causado_det`),
  KEY `codpresu` (`id_causado`,`codpresu`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `causado_det`
--

LOCK TABLES `causado_det` WRITE;
/*!40000 ALTER TABLE `causado_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `causado_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `causado_orden_pago`
--

DROP TABLE IF EXISTS `causado_orden_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `causado_orden_pago` (
  `id_causado_orden_pago` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_orden_pago` bigint(20) NOT NULL,
  `id_causado` bigint(20) NOT NULL,
  `apagar_bs` decimal(20,2) NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_causado_orden_pago`),
  KEY `id_cau` (`id_causado`),
  KEY `id_pag` (`id_orden_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci COMMENT='Lleva el registro de cada uno de los pagos realizado a un Ca';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `causado_orden_pago`
--

LOCK TABLES `causado_orden_pago` WRITE;
/*!40000 ALTER TABLE `causado_orden_pago` DISABLE KEYS */;
/*!40000 ALTER TABLE `causado_orden_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `causado_pag_hist`
--

DROP TABLE IF EXISTS `causado_pag_hist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `causado_pag_hist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_cau` int(11) NOT NULL,
  `id_pag` int(11) NOT NULL,
  `monto` double NOT NULL,
  `eje_fis` int(11) NOT NULL,
  `eje_fis_mes` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cau` (`id_cau`),
  KEY `id_pag` (`id_pag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci COMMENT='Lleva el registro de los pagos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `causado_pag_hist`
--

LOCK TABLES `causado_pag_hist` WRITE;
/*!40000 ALTER TABLE `causado_pag_hist` DISABLE KEYS */;
/*!40000 ALTER TABLE `causado_pag_hist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compr_compra`
--

DROP TABLE IF EXISTS `compr_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compr_compra` (
  `id_compr` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_causado` bigint(20) NOT NULL,
  `id_orden_pago` bigint(20) NOT NULL,
  `fecha_compr` date NOT NULL,
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) COLLATE utf8_spanish_ci NOT NULL,
  `observacion` varchar(512) CHARACTER SET utf8 NOT NULL,
  `num_fact` varchar(32) CHARACTER SET utf8 NOT NULL,
  `num_control` varchar(32) CHARACTER SET utf8 NOT NULL,
  `fecha_fact` date NOT NULL,
  `total_bs` decimal(20,2) NOT NULL COMMENT 'El monto total de la orden, expresado en Bs.',
  `base_imponible_bs` decimal(20,2) NOT NULL,
  `iva_grav_bs` decimal(20,2) NOT NULL,
  `iva_porc_aplic` decimal(20,2) NOT NULL,
  `iva_porc_ret` decimal(20,2) NOT NULL,
  `islr_grav_bs` decimal(20,2) NOT NULL,
  `islr_porc_ret` decimal(20,2) NOT NULL,
  `imp_mun_grav_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `imp_mun_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `neg_pri_grav_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `neg_pri_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `oret_grav_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `oret_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_compr`),
  UNIQUE KEY `rif_ci_numfact` (`benef_rif_ci`,`num_fact`),
  KEY `ejefis` (`ejefis`),
  KEY `id_causado` (`id_causado`),
  KEY `id_pago` (`id_orden_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compr_compra`
--

LOCK TABLES `compr_compra` WRITE;
/*!40000 ALTER TABLE `compr_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `compr_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compr_det`
--

DROP TABLE IF EXISTS `compr_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compr_det` (
  `id_compr_det` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_compr` bigint(20) NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8 NOT NULL,
  `cantpro` decimal(20,2) NOT NULL,
  `descpro` varchar(128) CHARACTER SET utf8 NOT NULL,
  `punitario` decimal(20,2) NOT NULL,
  `codpresu` varchar(32) CHARACTER SET utf8 NOT NULL,
  `partida` varchar(256) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id_compr_det`),
  KEY `tipo_y_idcompr` (`tipo_compr`,`id_compr`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compr_det`
--

LOCK TABLES `compr_det` WRITE;
/*!40000 ALTER TABLE `compr_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `compr_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compr_otros`
--

DROP TABLE IF EXISTS `compr_otros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compr_otros` (
  `id_compr` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_causado` bigint(20) NOT NULL,
  `id_orden_pago` bigint(20) NOT NULL,
  `fecha_compr` date NOT NULL,
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) COLLATE utf8_spanish_ci NOT NULL,
  `observacion` varchar(512) CHARACTER SET utf8 NOT NULL,
  `num_fact` varchar(32) CHARACTER SET utf8 NOT NULL,
  `num_control` varchar(32) CHARACTER SET utf8 NOT NULL,
  `fecha_fact` date NOT NULL,
  `total_bs` decimal(20,2) NOT NULL COMMENT 'El monto total de la orden, expresado en Bs.',
  `base_imponible_bs` decimal(20,2) NOT NULL,
  `iva_grav_bs` decimal(20,2) NOT NULL,
  `iva_porc_aplic` decimal(20,2) NOT NULL,
  `iva_porc_ret` decimal(20,2) NOT NULL,
  `islr_grav_bs` decimal(20,2) NOT NULL,
  `islr_porc_ret` decimal(20,2) NOT NULL,
  `imp_mun_grav_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `imp_mun_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `neg_pri_grav_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `neg_pri_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `oret_grav_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `oret_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_compr`),
  KEY `ejefis` (`ejefis`),
  KEY `id_causado` (`id_causado`),
  KEY `id_pago` (`id_orden_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compr_otros`
--

LOCK TABLES `compr_otros` WRITE;
/*!40000 ALTER TABLE `compr_otros` DISABLE KEYS */;
/*!40000 ALTER TABLE `compr_otros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compr_rpt_summary`
--

DROP TABLE IF EXISTS `compr_rpt_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compr_rpt_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL,
  `edo` char(1) CHARACTER SET utf8 NOT NULL,
  `fecha` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8 NOT NULL,
  `total` double NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compr_rpt_summary`
--

LOCK TABLES `compr_rpt_summary` WRITE;
/*!40000 ALTER TABLE `compr_rpt_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `compr_rpt_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compr_servicio`
--

DROP TABLE IF EXISTS `compr_servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compr_servicio` (
  `id_compr` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_causado` bigint(20) NOT NULL,
  `id_orden_pago` bigint(20) NOT NULL,
  `fecha_compr` date NOT NULL,
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) COLLATE utf8_spanish_ci NOT NULL,
  `observacion` varchar(512) CHARACTER SET utf8 NOT NULL,
  `num_fact` varchar(32) CHARACTER SET utf8 NOT NULL,
  `num_control` varchar(32) CHARACTER SET utf8 NOT NULL,
  `fecha_fact` date NOT NULL,
  `total_bs` decimal(20,2) NOT NULL COMMENT 'El monto total de la orden, expresado en Bs.',
  `base_imponible_bs` decimal(20,2) NOT NULL,
  `iva_grav_bs` decimal(20,2) NOT NULL,
  `iva_porc_aplic` decimal(20,2) NOT NULL,
  `iva_porc_ret` decimal(20,2) NOT NULL,
  `islr_grav_bs` decimal(20,2) NOT NULL,
  `islr_porc_ret` decimal(20,2) NOT NULL,
  `imp_mun_grav_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `imp_mun_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `neg_pri_grav_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `neg_pri_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `oret_grav_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `oret_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_compr`),
  UNIQUE KEY `rif_ci_numfact` (`benef_rif_ci`,`num_fact`),
  KEY `ejefis` (`ejefis`),
  KEY `id_causado` (`id_causado`),
  KEY `id_pago` (`id_orden_pago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compr_servicio`
--

LOCK TABLES `compr_servicio` WRITE;
/*!40000 ALTER TABLE `compr_servicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `compr_servicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditoadicional`
--

DROP TABLE IF EXISTS `creditoadicional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditoadicional` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `soporte` varchar(64) NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  `monto` decimal(16,2) NOT NULL DEFAULT '0.00',
  `fecha` date NOT NULL,
  `ejefis` bigint(20) NOT NULL,
  `ejefismes` bigint(20) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditoadicional`
--

LOCK TABLES `creditoadicional` WRITE;
/*!40000 ALTER TABLE `creditoadicional` DISABLE KEYS */;
/*!40000 ALTER TABLE `creditoadicional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditoadicional_det`
--

DROP TABLE IF EXISTS `creditoadicional_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditoadicional_det` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(32) NOT NULL,
  `partida` varchar(256) NOT NULL,
  `monto` double(15,2) NOT NULL,
  `registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint(20) NOT NULL,
  `ejefismes` bigint(20) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  `id_cre_adi` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditoadicional_det`
--

LOCK TABLES `creditoadicional_det` WRITE;
/*!40000 ALTER TABLE `creditoadicional_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `creditoadicional_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditoadicional_rpt_summary`
--

DROP TABLE IF EXISTS `creditoadicional_rpt_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditoadicional_rpt_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `soporte` varchar(64) CHARACTER SET utf8 NOT NULL,
  `descripcion` varchar(512) CHARACTER SET utf8 NOT NULL,
  `monto` decimal(20,2) NOT NULL,
  `fecha` date NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditoadicional_rpt_summary`
--

LOCK TABLES `creditoadicional_rpt_summary` WRITE;
/*!40000 ALTER TABLE `creditoadicional_rpt_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `creditoadicional_rpt_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `global`
--

DROP TABLE IF EXISTS `global`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `global` (
  `id_global` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `nombre` varchar(64) CHARACTER SET utf8 NOT NULL,
  `valor` varchar(256) CHARACTER SET utf8 NOT NULL,
  `dato` binary(16) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`id_global`),
  KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `global`
--

LOCK TABLES `global` WRITE;
/*!40000 ALTER TABLE `global` DISABLE KEYS */;
INSERT INTO `global` VALUES (3,0,'ejercicio_fiscal','2018',NULL,NULL),(8,0,'rpt_fecha_hora','102',NULL,NULL),(9,0,'DESC_1','DESC_1',NULL,NULL),(10,0,'DESC_2','ADMINISTRACION',NULL,NULL),(11,0,'DESC_3','DESC_3',NULL,NULL),(12,0,'FUNC_1','FUNC_1',NULL,NULL),(13,0,'FUNC_2','LENNIS',NULL,NULL),(14,0,'FUNC_3','FUNC_3',NULL,NULL),(15,0,'DESC_4','ALCALDE',NULL,NULL),(16,0,'FUNC_4','MONTEROLA',NULL,NULL),(17,0,'DESC_5','DESC_5',NULL,NULL),(18,0,'FUNC_5','FUNC_5',NULL,NULL),(19,0,'DESC_6','DESC_6',NULL,NULL),(20,0,'FUNC_6','FUNC_6',NULL,NULL),(21,0,'DESC_7','DESC_7',NULL,NULL),(22,0,'FUNC_7','FUNC_7',NULL,NULL),(23,0,'DESC_8','DESC_8',NULL,NULL),(24,0,'FUNC_8','FUNC_8',NULL,NULL);
/*!40000 ALTER TABLE `global` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `histpre`
--

DROP TABLE IF EXISTS `histpre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `histpre` (
  `codpresupe` varchar(29) NOT NULL,
  `monto` varchar(20) NOT NULL,
  `numcompr` int(11) NOT NULL,
  `fechacompr` varchar(10) NOT NULL,
  `tipocompr` varchar(2) NOT NULL,
  `observacion` varchar(500) NOT NULL,
  `ejefis` int(11) NOT NULL,
  `ejefismes` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  KEY `numcompr` (`numcompr`),
  KEY `tipocompr` (`tipocompr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `histpre`
--

LOCK TABLES `histpre` WRITE;
/*!40000 ALTER TABLE `histpre` DISABLE KEYS */;
/*!40000 ALTER TABLE `histpre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imp_mun`
--

DROP TABLE IF EXISTS `imp_mun`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imp_mun` (
  `id_imp_mun` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_imp_mun`),
  KEY `rif` (`benef_rif_ci`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imp_mun`
--

LOCK TABLES `imp_mun` WRITE;
/*!40000 ALTER TABLE `imp_mun` DISABLE KEYS */;
/*!40000 ALTER TABLE `imp_mun` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imp_mun_det`
--

DROP TABLE IF EXISTS `imp_mun_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imp_mun_det` (
  `id_imp_mun_det` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_imp_mun` bigint(20) NOT NULL,
  `id_causado` bigint(20) NOT NULL,
  `fecha_fact` date NOT NULL,
  `num_fact` varchar(32) CHARACTER SET utf8 NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8 NOT NULL,
  `id_compr` bigint(20) NOT NULL COMMENT 'num_control\n',
  `total_fact` decimal(20,2) NOT NULL,
  `base_imponible` decimal(20,2) NOT NULL,
  `gravable_bs` decimal(20,2) NOT NULL,
  `retenido_bs` decimal(20,2) NOT NULL,
  `observacion` varchar(512) CHARACTER SET utf8 NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_imp_mun_det`),
  KEY `caunumop` (`id_causado`),
  KEY `imp_mun` (`id_imp_mun`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imp_mun_det`
--

LOCK TABLES `imp_mun_det` WRITE;
/*!40000 ALTER TABLE `imp_mun_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `imp_mun_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `impuesto_retencion`
--

DROP TABLE IF EXISTS `impuesto_retencion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `impuesto_retencion` (
  `ivanumop` bigint(20) NOT NULL,
  `ejefis` int(11) NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `ejefismes` int(11) NOT NULL COMMENT 'Conformado por seis (6) digitos, que representa el año y mes correspondiente al ejercicio fiscal, formato aaaamm',
  `fecha_op` date NOT NULL,
  `total` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `contribuyente` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `rif` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `iduser` int(11) DEFAULT NULL,
  PRIMARY KEY (`ivanumop`),
  KEY `rif` (`rif`),
  KEY `fecha_op` (`fecha_op`),
  KEY `fecha_op_2` (`fecha_op`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impuesto_retencion`
--

LOCK TABLES `impuesto_retencion` WRITE;
/*!40000 ALTER TABLE `impuesto_retencion` DISABLE KEYS */;
/*!40000 ALTER TABLE `impuesto_retencion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `impuesto_retencion_det`
--

DROP TABLE IF EXISTS `impuesto_retencion_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `impuesto_retencion_det` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `caunumop` int(11) NOT NULL,
  `ivanumop` bigint(20) NOT NULL,
  `fechafact` varchar(10) CHARACTER SET utf8 NOT NULL,
  `numfact` varchar(10) CHARACTER SET utf8 NOT NULL,
  `cfactura` varchar(10) CHARACTER SET utf8 NOT NULL,
  `ndebito` varchar(20) CHARACTER SET utf8 NOT NULL,
  `ncredito` varchar(20) CHARACTER SET utf8 NOT NULL,
  `mtfactura` varchar(20) CHARACTER SET utf8 NOT NULL,
  `cexentas` varchar(20) CHARACTER SET utf8 NOT NULL,
  `bimponible` varchar(20) CHARACTER SET utf8 NOT NULL,
  `elicuota` varchar(8) CHARACTER SET utf8 NOT NULL,
  `impuesto` varchar(20) CHARACTER SET utf8 NOT NULL,
  `iretenido` varchar(20) CHARACTER SET utf8 NOT NULL,
  `isrlretenido` varchar(20) CHARACTER SET utf8 NOT NULL,
  `oretenciones` varchar(20) CHARACTER SET utf8 NOT NULL,
  `conceptoret` varchar(500) CHARACTER SET utf8 NOT NULL,
  `contribuyente` varchar(80) CHARACTER SET utf8 NOT NULL,
  `rif` varchar(20) CHARACTER SET utf8 NOT NULL,
  `fiva` varchar(12) CHARACTER SET utf8 NOT NULL,
  `facturaaft` varchar(12) CHARACTER SET utf8 NOT NULL,
  `tiretenido` varchar(20) CHARACTER SET utf8 NOT NULL,
  `ncontrol` varchar(10) CHARACTER SET utf8 NOT NULL,
  `tmtfactura` varchar(20) CHARACTER SET utf8 NOT NULL,
  `fechaop` varchar(40) CHARACTER SET utf8 NOT NULL,
  `resta` varchar(20) CHARACTER SET utf8 NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mtFactura_dbl` double NOT NULL,
  `cexentas_dbl` double NOT NULL,
  `bimponible_dbl` double NOT NULL,
  `impuesto_dbl` double NOT NULL,
  `iretenido_dbl` double NOT NULL,
  `ejefis` int(11) NOT NULL,
  `ejefismes` int(11) NOT NULL,
  `iduser` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `caunumop` (`caunumop`),
  KEY `ivanumop` (`ivanumop`),
  KEY `ejefis` (`ejefis`),
  KEY `ejefismes` (`ejefismes`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impuesto_retencion_det`
--

LOCK TABLES `impuesto_retencion_det` WRITE;
/*!40000 ALTER TABLE `impuesto_retencion_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `impuesto_retencion_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `islr_retencion`
--

DROP TABLE IF EXISTS `islr_retencion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `islr_retencion` (
  `id_islr_retencion` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_islr_retencion`),
  KEY `rif` (`benef_rif_ci`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `islr_retencion`
--

LOCK TABLES `islr_retencion` WRITE;
/*!40000 ALTER TABLE `islr_retencion` DISABLE KEYS */;
/*!40000 ALTER TABLE `islr_retencion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `islr_retencion_det`
--

DROP TABLE IF EXISTS `islr_retencion_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `islr_retencion_det` (
  `id_islr_retencion_det` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_islr_retencion` bigint(20) NOT NULL,
  `id_causado` bigint(20) NOT NULL,
  `fecha_fact` date NOT NULL,
  `num_fact` varchar(32) CHARACTER SET utf8 NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8 NOT NULL,
  `id_compr` bigint(20) NOT NULL COMMENT 'num_control\n',
  `total_fact` decimal(20,2) NOT NULL,
  `base_imponible` decimal(20,2) NOT NULL,
  `gravable_bs` decimal(20,2) NOT NULL,
  `islr_porc_ret` decimal(20,2) NOT NULL,
  `islr_retenido_bs` decimal(20,2) NOT NULL,
  `observacion` varchar(512) CHARACTER SET utf8 NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_islr_retencion_det`),
  KEY `caunumop` (`id_causado`),
  KEY `islr_numop` (`id_islr_retencion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `islr_retencion_det`
--

LOCK TABLES `islr_retencion_det` WRITE;
/*!40000 ALTER TABLE `islr_retencion_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `islr_retencion_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iva_aplicado`
--

DROP TABLE IF EXISTS `iva_aplicado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iva_aplicado` (
  `id_iva_aplicado` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_part_ppto` bigint(20) NOT NULL,
  `valor_porc` decimal(20,2) NOT NULL,
  PRIMARY KEY (`id_iva_aplicado`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci COMMENT='Guarda una correlacion del porcentaje aplicado para el IVA, con la respectiva partida de presupuesto.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iva_aplicado`
--

LOCK TABLES `iva_aplicado` WRITE;
/*!40000 ALTER TABLE `iva_aplicado` DISABLE KEYS */;
INSERT INTO `iva_aplicado` VALUES (4,3,12.00);
/*!40000 ALTER TABLE `iva_aplicado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iva_retencion`
--

DROP TABLE IF EXISTS `iva_retencion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iva_retencion` (
  `id_iva_retencion` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_iva_retencion`),
  KEY `rif` (`benef_rif_ci`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iva_retencion`
--

LOCK TABLES `iva_retencion` WRITE;
/*!40000 ALTER TABLE `iva_retencion` DISABLE KEYS */;
/*!40000 ALTER TABLE `iva_retencion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iva_retencion_det`
--

DROP TABLE IF EXISTS `iva_retencion_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iva_retencion_det` (
  `id_iva_retencion_det` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_iva_retencion` bigint(20) NOT NULL,
  `id_causado` bigint(20) NOT NULL,
  `fecha_fact` date NOT NULL,
  `num_fact` varchar(32) CHARACTER SET utf8 NOT NULL,
  `num_control` varchar(32) COLLATE utf8_spanish_ci DEFAULT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8 NOT NULL,
  `id_compr` bigint(20) NOT NULL COMMENT 'num_control\n',
  `ndebito` varchar(32) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Num. de nota credito',
  `ncredito` varchar(32) COLLATE utf8_spanish_ci NOT NULL COMMENT 'Num. de nota credito',
  `transaccion` char(1) CHARACTER SET utf8 NOT NULL,
  `factura_aft` varchar(32) COLLATE utf8_spanish_ci NOT NULL,
  `total_fact` decimal(20,2) NOT NULL,
  `exento` decimal(20,2) NOT NULL,
  `base_imponible` decimal(20,2) NOT NULL,
  `iva_porc_aplic` decimal(20,2) NOT NULL,
  `iva_bs` decimal(20,2) NOT NULL,
  `iva_retenido` decimal(20,2) NOT NULL,
  `observacion` varchar(512) CHARACTER SET utf8 NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `anulado_sn` char(1) CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_iva_retencion_det`),
  KEY `caunumop` (`id_causado`),
  KEY `ivanumop` (`id_iva_retencion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iva_retencion_det`
--

LOCK TABLES `iva_retencion_det` WRITE;
/*!40000 ALTER TABLE `iva_retencion_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `iva_retencion_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `map_next_id`
--

DROP TABLE IF EXISTS `map_next_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `map_next_id` (
  `clave` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT 'Los primeros cuatro (4) digitos deben corresponder con el ejercicio fiscal, es decir el año fiscal',
  `valor` bigint(20) NOT NULL COMMENT 'Normalmente deberia corresponder a actualizaciones, que generen incrementos unitarios, y este valorgenerado podria ser utilizado como clave (indice) primario o no, en otras tablas, este seria el next id.',
  `id_user` bigint(20) NOT NULL,
  PRIMARY KEY (`clave`),
  UNIQUE KEY `cuesec` (`clave`),
  KEY `cuenta` (`clave`),
  KEY `ejefis` (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci COMMENT='Para llevar la cuenta del ultimo Id generado asociado a una ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `map_next_id`
--

LOCK TABLES `map_next_id` WRITE;
/*!40000 ALTER TABLE `map_next_id` DISABLE KEYS */;
/*!40000 ALTER TABLE `map_next_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neg_pri`
--

DROP TABLE IF EXISTS `neg_pri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neg_pri` (
  `id_neg_pri` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_neg_pri`),
  KEY `rif` (`benef_rif_ci`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neg_pri`
--

LOCK TABLES `neg_pri` WRITE;
/*!40000 ALTER TABLE `neg_pri` DISABLE KEYS */;
/*!40000 ALTER TABLE `neg_pri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neg_pri_det`
--

DROP TABLE IF EXISTS `neg_pri_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neg_pri_det` (
  `id_neg_pri_det` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_neg_pri` bigint(20) NOT NULL,
  `id_causado` bigint(20) NOT NULL,
  `fecha_fact` date NOT NULL,
  `num_fact` varchar(32) CHARACTER SET utf8 NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8 NOT NULL,
  `id_compr` bigint(20) NOT NULL COMMENT 'num_control\n',
  `total_fact` decimal(20,2) NOT NULL,
  `base_imponible` decimal(20,2) NOT NULL,
  `gravable_bs` decimal(20,2) NOT NULL,
  `retenido_bs` decimal(20,2) NOT NULL,
  `observacion` varchar(512) CHARACTER SET utf8 NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_neg_pri_det`),
  KEY `caunumop` (`id_causado`),
  KEY `neg_pri` (`id_neg_pri`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neg_pri_det`
--

LOCK TABLES `neg_pri_det` WRITE;
/*!40000 ALTER TABLE `neg_pri_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `neg_pri_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orden_pago`
--

DROP TABLE IF EXISTS `orden_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orden_pago` (
  `id_orden_pago` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `id_cuenta` bigint(20) NOT NULL DEFAULT '0',
  `num_x_cuenta` bigint(20) NOT NULL DEFAULT '0',
  `num_x_pag` bigint(20) NOT NULL DEFAULT '0',
  `benef_razonsocial` varchar(128) NOT NULL,
  `benef_rif_ci` varchar(32) NOT NULL,
  `fecha_pago` date NOT NULL,
  `observacion` varchar(512) NOT NULL,
  `banco` varchar(128) NOT NULL,
  `cuenta` varchar(20) NOT NULL,
  `cheque` varchar(20) NOT NULL,
  `fecha_cheque` date NOT NULL,
  `endosable_sn` char(1) NOT NULL,
  `total_bs` decimal(20,2) NOT NULL,
  `apagar_bs` decimal(20,2) NOT NULL,
  `iva_bs` decimal(20,2) NOT NULL,
  `resta_bs` decimal(20,2) NOT NULL,
  `iva_ret_bs` decimal(20,2) NOT NULL,
  `islr_ret_bs` decimal(20,2) NOT NULL,
  `imp_mun_ret_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `neg_pri_ret_bs` decimal(20,2) NOT NULL DEFAULT '0.00',
  `otras_ret_bs` decimal(20,2) NOT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_orden_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orden_pago`
--

LOCK TABLES `orden_pago` WRITE;
/*!40000 ALTER TABLE `orden_pago` DISABLE KEYS */;
/*!40000 ALTER TABLE `orden_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenpago_aux_report`
--

DROP TABLE IF EXISTS `ordenpago_aux_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordenpago_aux_report` (
  `codpresu` varchar(30) CHARACTER SET latin1 NOT NULL,
  `partida` varchar(200) CHARACTER SET latin1 NOT NULL,
  `total` double NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordenpago_aux_report`
--

LOCK TABLES `ordenpago_aux_report` WRITE;
/*!40000 ALTER TABLE `ordenpago_aux_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordenpago_aux_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenpago_det`
--

DROP TABLE IF EXISTS `ordenpago_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordenpago_det` (
  `numpago` int(11) NOT NULL,
  `numcompr` int(11) NOT NULL,
  `cantpro` varchar(3) CHARACTER SET utf8 NOT NULL,
  `descpro` varchar(30) CHARACTER SET utf8 NOT NULL,
  `punitario` varchar(20) CHARACTER SET utf8 NOT NULL,
  `stotal` varchar(20) CHARACTER SET utf8 NOT NULL,
  `codpresu` varchar(30) CHARACTER SET utf8 NOT NULL,
  `partida` varchar(81) CHARACTER SET utf8 NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tipocompr` varchar(2) CHARACTER SET utf8 NOT NULL,
  `numcau` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  KEY `numpago` (`numpago`),
  KEY `tipocompr` (`tipocompr`),
  KEY `numcau` (`numcau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordenpago_det`
--

LOCK TABLES `ordenpago_det` WRITE;
/*!40000 ALTER TABLE `ordenpago_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordenpago_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordenpago_rpt_summary`
--

DROP TABLE IF EXISTS `ordenpago_rpt_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordenpago_rpt_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL,
  `anu` char(1) CHARACTER SET utf8 NOT NULL,
  `fecha` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `razonsocial` varchar(80) COLLATE utf8_spanish_ci NOT NULL,
  `rif` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `total` double NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordenpago_rpt_summary`
--

LOCK TABLES `ordenpago_rpt_summary` WRITE;
/*!40000 ALTER TABLE `ordenpago_rpt_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordenpago_rpt_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otras_ret`
--

DROP TABLE IF EXISTS `otras_ret`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otras_ret` (
  `id_otras_ret` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_otras_ret`),
  KEY `rif` (`benef_rif_ci`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otras_ret`
--

LOCK TABLES `otras_ret` WRITE;
/*!40000 ALTER TABLE `otras_ret` DISABLE KEYS */;
/*!40000 ALTER TABLE `otras_ret` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otras_ret_det`
--

DROP TABLE IF EXISTS `otras_ret_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otras_ret_det` (
  `id_otras_ret_det` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_otras_ret` bigint(20) NOT NULL,
  `id_causado` bigint(20) NOT NULL,
  `fecha_fact` date NOT NULL,
  `num_fact` varchar(32) CHARACTER SET utf8 NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8 NOT NULL,
  `id_compr` bigint(20) NOT NULL COMMENT 'num_control\n',
  `total_fact` decimal(20,2) NOT NULL,
  `base_imponible` decimal(20,2) NOT NULL,
  `gravable_bs` decimal(20,2) NOT NULL,
  `retenido_bs` decimal(20,2) NOT NULL,
  `observacion` varchar(512) CHARACTER SET utf8 NOT NULL,
  `ejefis` date NOT NULL COMMENT 'Conformado por el numero de año en formato de cuatro (4) digitos',
  `benef_razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `benef_rif_ci` varchar(32) CHARACTER SET utf8 NOT NULL,
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_otras_ret_det`),
  KEY `caunumop` (`id_causado`),
  KEY `oret` (`id_otras_ret`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otras_ret_det`
--

LOCK TABLES `otras_ret_det` WRITE;
/*!40000 ALTER TABLE `otras_ret_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `otras_ret_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presupe`
--

DROP TABLE IF EXISTS `presupe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `presupe` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(32) NOT NULL,
  `partida` varchar(256) NOT NULL,
  `monto_ini` decimal(20,2) NOT NULL DEFAULT '0.00',
  `monto` double(20,2) NOT NULL,
  `registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint(20) DEFAULT NULL,
  `ejefismes` bigint(20) DEFAULT NULL,
  `fchsession` date DEFAULT NULL,
  `idsession` bigint(20) DEFAULT NULL,
  `iduser` bigint(20) DEFAULT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  `cod_contable` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cod_ejefis` (`codigo`,`ejefis`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presupe`
--

LOCK TABLES `presupe` WRITE;
/*!40000 ALTER TABLE `presupe` DISABLE KEYS */;
INSERT INTO `presupe` VALUES (1,'00.00.00.00.000.00.00.10','PARTIDA PRUEBA 1',0.00,0.00,'2018-07-24 12:12:44',2018,201804,'2018-04-29',1,1,'N',''),(2,'00.00.00.00.000.00.00.20','PARTIDA PRUEBA 2',0.00,0.00,'2018-07-24 12:12:44',2018,201804,'2018-04-29',1,1,'N',''),(3,'00.00.00.00.000.00.00.30','PARTIDA PRUEBA 3',0.00,0.00,'2018-07-24 12:12:44',2018,201804,'2018-04-29',1,1,'N','');
/*!40000 ALTER TABLE `presupe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presupi`
--

DROP TABLE IF EXISTS `presupi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `presupi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(32) NOT NULL,
  `partida` varchar(256) NOT NULL,
  `monto_ini` decimal(20,2) NOT NULL DEFAULT '0.00',
  `monto` double(20,2) NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint(20) DEFAULT NULL,
  `ejefismes` bigint(20) DEFAULT NULL,
  `fchsession` date DEFAULT NULL,
  `idsession` bigint(20) DEFAULT NULL,
  `iduser` bigint(20) DEFAULT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  `cod_contable` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presupi`
--

LOCK TABLES `presupi` WRITE;
/*!40000 ALTER TABLE `presupi` DISABLE KEYS */;
INSERT INTO `presupi` VALUES (1,'3.01.02.07.00','Patente de industria y comercio',0.00,0.00,'2017-01-01 11:12:23',2017,201701,NULL,NULL,1,'N',NULL),(2,'3.01.02.08.00','Patente de vehiculo',0.00,0.00,'2017-01-01 11:12:23',2017,201701,NULL,NULL,1,'N',NULL),(3,'3.01.02.09.00','Propaganda comercial',0.00,0.00,'2017-01-01 11:12:23',2017,201701,NULL,NULL,1,'N',NULL),(4,'3.01.02.10.00','Espectaculos publicos',0.00,0.00,'2017-11-07 20:15:58',2017,201701,NULL,NULL,1,'N',NULL),(5,'3.01.02.11.00','Apuestas licitas',0.00,0.00,'2017-01-01 11:12:23',2017,201701,NULL,NULL,1,'N',NULL),(6,'3.01.02.12.00','Deudas morosas',0.00,0.00,'2017-01-01 11:12:23',2017,201701,NULL,NULL,1,'N',NULL),(7,'3.01.02.99.00','Otros impuestos indirectos',0.00,0.00,'2017-01-01 11:12:23',2017,201701,NULL,NULL,1,'N',NULL),(8,'3.01.03.48.00','Permisos municipales',0.00,0.00,'2017-01-01 11:12:23',2017,201701,NULL,NULL,1,'N',NULL),(9,'3.01.03.49.00','Certificaciones y solvencias',0.00,0.00,'2017-01-15 21:59:07',2017,201701,NULL,NULL,1,'N',NULL),(10,'3.01.03.54.00','Aseo domiciliario',0.00,0.00,'2017-01-15 21:59:07',2017,201701,NULL,NULL,1,'N',NULL),(11,'3.01.03.56.00','Mercado',0.00,0.00,'2017-01-15 21:59:07',2017,201701,NULL,NULL,1,'N',NULL),(12,'3.01.03.57.00','Cementerio',0.00,0.00,'2017-01-15 21:59:07',2017,201701,NULL,NULL,1,'N',NULL),(13,'3.01.03.59.00','Deudas morosas por tasas',0.00,0.00,'2017-01-15 21:59:08',2017,201701,NULL,NULL,1,'N',NULL),(14,'3.01.03.99.00','Otros tipos de tasas',0.00,0.00,'2017-01-15 21:59:08',2017,201701,NULL,NULL,1,'N',NULL),(15,'3.01.11.01.00','Intereses moratorios',0.00,0.00,'2017-01-15 21:59:08',2017,201701,NULL,NULL,1,'N',NULL),(16,'3.01.11.02.00','Reparos fiscales',0.00,0.00,'2017-01-15 21:59:09',2017,201701,NULL,NULL,1,'N',NULL),(17,'3.01.11.03.00','Sanciones fiscales',0.00,0.00,'2017-01-15 21:59:09',2017,201701,NULL,NULL,1,'N',NULL),(18,'3.01.11.08.00','Multas y recargos',0.00,0.00,'2017-01-15 21:59:09',2017,201701,NULL,NULL,1,'N',NULL),(19,'3.01.99.01.00','Otros ingresos ordinarios',0.00,0.00,'2017-01-15 21:59:09',2017,201701,NULL,NULL,1,'N',NULL),(20,'3.02.99.01.00','Otros ingresos extraordinarios',0.00,0.00,'2017-01-15 21:59:10',2017,201701,NULL,NULL,1,'N',NULL),(21,'3.05.03.01.02','Situado Municipal',0.00,0.00,'2017-01-15 21:59:10',2017,201701,NULL,NULL,1,'N',NULL),(22,'3.05.08.02.00','Fondo de Compensacion Interterritorial Municipal',0.00,0.00,'2017-01-15 21:59:10',2017,201701,NULL,NULL,1,'N',NULL),(23,'3.06.01.01.00','Venta y/o desincorporacion de tierras y terrenos',0.00,0.00,'2017-01-15 21:59:10',2017,201701,NULL,NULL,1,'N',NULL),(24,'3.01.02.03.10','Impuesto sobre expedicion al publico de especies alcoholica',0.00,0.00,'2017-01-15 21:59:11',2017,201701,NULL,NULL,1,'N',NULL),(25,'3.01.02.05.00','Inmuebles urbanos',0.00,0.00,'2017-01-15 21:59:11',2017,201701,NULL,NULL,1,'N',NULL),(26,'3.01.02.10.00','Espectaculos publicos',0.00,0.00,'2017-11-07 20:15:58',2017,201701,NULL,NULL,1,'N',NULL),(27,'3.01.02.10.00','Espectaculos publicos ???',0.00,0.00,'2017-11-07 20:15:58',2017,201701,NULL,NULL,1,'N',NULL),(28,'3.01.02.10.00','Espectaculos publicos',0.00,0.00,'2017-11-07 20:15:58',2017,201701,NULL,NULL,1,'N',NULL),(29,'3.01.02.10.00','Espectaculos publicos ',0.00,0.00,'2017-11-07 20:15:58',2017,201701,NULL,NULL,1,'N',NULL);
/*!40000 ALTER TABLE `presupi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tmppptoadicional`
--

DROP TABLE IF EXISTS `tmppptoadicional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmppptoadicional` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(32) NOT NULL,
  `partida` varchar(256) NOT NULL,
  `monto_ini` decimal(20,2) NOT NULL DEFAULT '0.00',
  `monto` double(20,2) NOT NULL,
  `registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint(20) DEFAULT NULL,
  `ejefismes` bigint(20) DEFAULT NULL,
  `fchsession` date DEFAULT NULL,
  `idsession` bigint(20) DEFAULT NULL,
  `iduser` bigint(20) DEFAULT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  `cod_contable` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cod_ejefis` (`codigo`,`ejefis`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tmppptoadicional`
--

LOCK TABLES `tmppptoadicional` WRITE;
/*!40000 ALTER TABLE `tmppptoadicional` DISABLE KEYS */;
/*!40000 ALTER TABLE `tmppptoadicional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trasp_part_rpt_summary`
--

DROP TABLE IF EXISTS `trasp_part_rpt_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trasp_part_rpt_summary` (
  `id_trasp_rpt` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ppto_egr_ing` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `referencia` varchar(128) CHARACTER SET utf8 NOT NULL,
  `fecha` date NOT NULL,
  `monto` decimal(20,2) NOT NULL,
  `concepto` varchar(512) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id_trasp_rpt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trasp_part_rpt_summary`
--

LOCK TABLES `trasp_part_rpt_summary` WRITE;
/*!40000 ALTER TABLE `trasp_part_rpt_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `trasp_part_rpt_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trasppartidas`
--

DROP TABLE IF EXISTS `trasppartidas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trasppartidas` (
  `id_trasp` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ppto_egr_ing` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `referencia` varchar(128) CHARACTER SET utf8 NOT NULL,
  `fecha` date NOT NULL,
  `monto` decimal(20,2) NOT NULL,
  `concepto` varchar(512) CHARACTER SET utf8 NOT NULL,
  `ejefis` bigint(20) NOT NULL,
  `ejefismes` bigint(20) NOT NULL,
  `anulado_sn` char(1) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id_trasp`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trasppartidas`
--

LOCK TABLES `trasppartidas` WRITE;
/*!40000 ALTER TABLE `trasppartidas` DISABLE KEYS */;
/*!40000 ALTER TABLE `trasppartidas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trasppartidas_det`
--

DROP TABLE IF EXISTS `trasppartidas_det`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trasppartidas_det` (
  `id_trasp_det` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `id_trasp` bigint(20) NOT NULL,
  `ppto_egr_ing` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `tipo_ori_dest` char(1) COLLATE utf8_spanish_ci NOT NULL,
  `codpresu` varchar(32) CHARACTER SET utf8 NOT NULL,
  `partida` varchar(256) CHARACTER SET utf8 NOT NULL,
  `monto` decimal(20,2) NOT NULL,
  PRIMARY KEY (`id_trasp_det`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trasppartidas_det`
--

LOCK TABLES `trasppartidas_det` WRITE;
/*!40000 ALTER TABLE `trasppartidas_det` DISABLE KEYS */;
/*!40000 ALTER TABLE `trasppartidas_det` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidad_tributaria_aplic`
--

DROP TABLE IF EXISTS `unidad_tributaria_aplic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidad_tributaria_aplic` (
  `id_unidad_tributaria_aplic` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `descripcion` varchar(128) CHARACTER SET utf8 NOT NULL,
  `fecha_vigencia` date NOT NULL,
  `valor_bs` decimal(20,2) NOT NULL COMMENT 'EL valor como representacion del porcentaje de la taza aplicada, ej. si la taza es de 12%, el valor almacenado será 12,00',
  PRIMARY KEY (`id_unidad_tributaria_aplic`),
  KEY `descripcion` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidad_tributaria_aplic`
--

LOCK TABLES `unidad_tributaria_aplic` WRITE;
/*!40000 ALTER TABLE `unidad_tributaria_aplic` DISABLE KEYS */;
/*!40000 ALTER TABLE `unidad_tributaria_aplic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_menu_track`
--

DROP TABLE IF EXISTS `user_menu_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_menu_track` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `iduser` bigint(20) NOT NULL,
  `class` varchar(64) COLLATE utf8_spanish_ci NOT NULL,
  `op` varchar(16) CHARACTER SET utf8 NOT NULL,
  `obs` varchar(64) COLLATE utf8_spanish_ci NOT NULL,
  `ejefis` bigint(20) NOT NULL,
  `ejefismes` bigint(20) NOT NULL,
  `userdatetime` datetime DEFAULT NULL,
  `servdatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=402 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_menu_track`
--

LOCK TABLES `user_menu_track` WRITE;
/*!40000 ALTER TABLE `user_menu_track` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_menu_track` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_menu_track_summary`
--

DROP TABLE IF EXISTS `user_menu_track_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_menu_track_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user` varchar(12) COLLATE utf8_spanish_ci NOT NULL,
  `mnuclass` varchar(64) COLLATE utf8_spanish_ci NOT NULL,
  `op` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `obs` varchar(128) CHARACTER SET utf8 NOT NULL,
  `userdatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `servdatetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_menu_track_summary`
--

LOCK TABLES `user_menu_track_summary` WRITE;
/*!40000 ALTER TABLE `user_menu_track_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_menu_track_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id_user` bigint(20) NOT NULL AUTO_INCREMENT,
  `user` varchar(16) NOT NULL,
  `pass` binary(16) DEFAULT NULL,
  `nombre` varchar(32) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'ADMIN',NULL,'ADMINISTRADOR (SUPER USUARIO)',1,1,1,1,1,1,1,1,'true',NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-24  8:15:04
