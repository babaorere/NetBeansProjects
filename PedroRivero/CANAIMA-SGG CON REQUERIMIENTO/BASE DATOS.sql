/*
SQLyog Ultimate v10.42 
MySQL - 5.1.36-community-log : Database - capip2022
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`capip_2023` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `capip_2023`;

/*Table structure for table `ava_efe_aux_report` */

DROP TABLE IF EXISTS `ava_efe_aux_report`;

CREATE TABLE `ava_efe_aux_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codpresu` varchar(32) CHARACTER SET latin1 NOT NULL,
  `partida` varchar(256) CHARACTER SET latin1 NOT NULL,
  `total` decimal(20,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`),
  KEY `iduser` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `ava_efe_aux_report` */

/*Table structure for table `ava_efe_rpt_summary` */

DROP TABLE IF EXISTS `ava_efe_rpt_summary`;

CREATE TABLE `ava_efe_rpt_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL,
  `fecha` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8 NOT NULL,
  `total` decimal(20,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `ava_efe_rpt_summary` */

/*Table structure for table `avance_efectivo` */

DROP TABLE IF EXISTS `avance_efectivo`;

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

/*Data for the table `avance_efectivo` */

/*Table structure for table `banco_saldo_anual` */

DROP TABLE IF EXISTS `banco_saldo_anual`;

CREATE TABLE `banco_saldo_anual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) NOT NULL,
  `id_session` bigint(20) NOT NULL,
  `id_banco` bigint(20) NOT NULL,
  `date_session` datetime NOT NULL,
  `ejefis` date NOT NULL,
  `saldoi` decimal(20,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `banco_saldo_anual` */

insert  into `banco_saldo_anual`(`id`,`id_user`,`id_session`,`id_banco`,`date_session`,`ejefis`,`saldoi`) values (1,1,1,1,'2023-01-02 15:19:54','2023-01-02',1000.00),(2,1,1,2,'2023-01-02 15:20:15','2023-01-02',2000.00);

/*Table structure for table `bancos` */

DROP TABLE IF EXISTS `bancos`;

CREATE TABLE `bancos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cuenta` varchar(20) NOT NULL,
  `banco` varchar(30) NOT NULL,
  `saldoi` decimal(50,2) NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` int(11) NOT NULL,
  `ejefismes` int(11) NOT NULL,
  `iduser` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `bancos` */

insert  into `bancos`(`id`,`cuenta`,`banco`,`saldoi`,`registro`,`ejefis`,`ejefismes`,`iduser`) values (1,'11111111111111111111','BANCO DE VENEZUELA',1000.00,'2023-01-02 15:19:54',2023,202301,1),(2,'22222222222222222222','BANCO BANESCO',2000.00,'2023-01-02 15:20:15',2023,202301,1);

/*Table structure for table `bancos_ch_anu` */

DROP TABLE IF EXISTS `bancos_ch_anu`;

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

/*Data for the table `bancos_ch_anu` */

/*Table structure for table `bancos_ch_anu_aux_rpt` */

DROP TABLE IF EXISTS `bancos_ch_anu_aux_rpt`;

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

/*Data for the table `bancos_ch_anu_aux_rpt` */

/*Table structure for table `bancos_cheque` */

DROP TABLE IF EXISTS `bancos_cheque`;

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

/*Data for the table `bancos_cheque` */

/*Table structure for table `bancos_operaciones` */

DROP TABLE IF EXISTS `bancos_operaciones`;

CREATE TABLE `bancos_operaciones` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cuenta` varchar(20) CHARACTER SET utf8 NOT NULL,
  `banco` varchar(30) CHARACTER SET utf8 NOT NULL,
  `descripcion` varchar(80) CHARACTER SET utf8 NOT NULL,
  `fecha` varchar(10) CHARACTER SET utf8 NOT NULL,
  `tipo_operacion` varchar(2) CHARACTER SET utf8 NOT NULL,
  `soporte_o_cheque` varchar(20) CHARACTER SET utf8 NOT NULL,
  `conciliado` varchar(11) CHARACTER SET utf8 NOT NULL,
  `debe` decimal(20,2) NOT NULL,
  `haber` decimal(20,2) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `bancos_operaciones` */

/*Table structure for table `beneficiario` */

DROP TABLE IF EXISTS `beneficiario`;

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

/*Data for the table `beneficiario` */

insert  into `beneficiario`(`id_beneficiario`,`id_user`,`id_session`,`date_session`,`razonsocial`,`rif_ci`,`domicilio`,`telefonos`,`activo`) values (1,1,1,'2022-09-28 12:17:20','CAPIP SISTEMAS,C.A.','J407111787','CARACAS DISTRITO CAPITAL','0402-993.76.96','S');

/*Table structure for table `beneficiarios` */

DROP TABLE IF EXISTS `beneficiarios`;

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

/*Data for the table `beneficiarios` */

/*Table structure for table `cau_rpt_summary` */

DROP TABLE IF EXISTS `cau_rpt_summary`;

CREATE TABLE `cau_rpt_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL,
  `fecha` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8 NOT NULL,
  `total` decimal(20,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `cau_rpt_summary` */

/*Table structure for table `causado` */

DROP TABLE IF EXISTS `causado`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `causado` */

/*Table structure for table `causado_aux_report` */

DROP TABLE IF EXISTS `causado_aux_report`;

CREATE TABLE `causado_aux_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codpresu` varchar(30) NOT NULL,
  `partida` varchar(100) NOT NULL,
  `total` decimal(20,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `conpresu` (`codpresu`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `causado_aux_report` */

/*Table structure for table `causado_avance_efectivo_nn` */

DROP TABLE IF EXISTS `causado_avance_efectivo_nn`;

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

/*Data for the table `causado_avance_efectivo_nn` */

/*Table structure for table `causado_det` */

DROP TABLE IF EXISTS `causado_det`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `causado_det` */

/*Table structure for table `causado_orden_pago` */

DROP TABLE IF EXISTS `causado_orden_pago`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci COMMENT='Lleva el registro de cada uno de los pagos realizado a un Ca';

/*Data for the table `causado_orden_pago` */

/*Table structure for table `causado_pag_hist` */

DROP TABLE IF EXISTS `causado_pag_hist`;

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

/*Data for the table `causado_pag_hist` */

/*Table structure for table `compr_compra` */

DROP TABLE IF EXISTS `compr_compra`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `compr_compra` */

/*Table structure for table `compr_det` */

DROP TABLE IF EXISTS `compr_det`;

CREATE TABLE `compr_det` (
  `id_compr_det` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_compr` bigint(20) NOT NULL,
  `tipo_compr` char(2) CHARACTER SET utf8 NOT NULL,
  `cantpro` decimal(20,2) NOT NULL,
  `descpro` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `punitario` decimal(20,2) NOT NULL,
  `codpresu` varchar(32) CHARACTER SET utf8 NOT NULL,
  `partida` varchar(256) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id_compr_det`),
  KEY `tipo_y_idcompr` (`tipo_compr`,`id_compr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `compr_det` */

/*Table structure for table `compr_otros` */

DROP TABLE IF EXISTS `compr_otros`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `compr_otros` */

/*Table structure for table `compr_rpt_summary` */

DROP TABLE IF EXISTS `compr_rpt_summary`;

CREATE TABLE `compr_rpt_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL,
  `edo` char(1) CHARACTER SET utf8 NOT NULL,
  `fecha` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `razonsocial` varchar(128) CHARACTER SET utf8 NOT NULL,
  `rif` varchar(32) CHARACTER SET utf8 NOT NULL,
  `total` decimal(20,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `compr_rpt_summary` */

/*Table structure for table `compr_servicio` */

DROP TABLE IF EXISTS `compr_servicio`;

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

/*Data for the table `compr_servicio` */

/*Table structure for table `creditoadicional` */

DROP TABLE IF EXISTS `creditoadicional`;

CREATE TABLE `creditoadicional` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `soporte` varchar(64) NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  `monto` decimal(20,2) NOT NULL DEFAULT '0.00',
  `fecha` date NOT NULL,
  `ejefis` bigint(20) NOT NULL,
  `ejefismes` bigint(20) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `creditoadicional` */

/*Table structure for table `creditoadicional_det` */

DROP TABLE IF EXISTS `creditoadicional_det`;

CREATE TABLE `creditoadicional_det` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(32) NOT NULL,
  `partida` varchar(256) NOT NULL,
  `monto` decimal(20,2) NOT NULL,
  `registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint(20) NOT NULL,
  `ejefismes` bigint(20) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  `id_cre_adi` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `creditoadicional_det` */

/*Table structure for table `creditoadicional_rpt_summary` */

DROP TABLE IF EXISTS `creditoadicional_rpt_summary`;

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

/*Data for the table `creditoadicional_rpt_summary` */

/*Table structure for table `global` */

DROP TABLE IF EXISTS `global`;

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

/*Data for the table `global` */

insert  into `global`(`id_global`,`id_user`,`nombre`,`valor`,`dato`,`fecha`) values (3,0,'ejercicio_fiscal','2023',NULL,NULL),(8,0,'rpt_fecha_hora','102',NULL,NULL),(9,0,'DESC_1','DESC_1',NULL,NULL),(10,0,'DESC_2','ADMINISTRACION',NULL,NULL),(11,0,'DESC_3','DESC_3',NULL,NULL),(12,0,'FUNC_1','FUNC_1',NULL,NULL),(13,0,'FUNC_2','CANAIMA SGG',NULL,NULL),(14,0,'FUNC_3','FUNC_3',NULL,NULL),(15,0,'DESC_4','PRESIDENTE',NULL,NULL),(16,0,'FUNC_4','CANAIMA SGG',NULL,NULL),(17,0,'DESC_5','DESC_5',NULL,NULL),(18,0,'FUNC_5','FUNC_5',NULL,NULL),(19,0,'DESC_6','DESC_6',NULL,NULL),(20,0,'FUNC_6','FUNC_6',NULL,NULL),(21,0,'DESC_7','DESC_7',NULL,NULL),(22,0,'FUNC_7','FUNC_7',NULL,NULL),(23,0,'DESC_8','DESC_8',NULL,NULL),(24,0,'FUNC_8','FUNC_8',NULL,NULL);

/*Table structure for table `histpre` */

DROP TABLE IF EXISTS `histpre`;

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

/*Data for the table `histpre` */

/*Table structure for table `imp_mun` */

DROP TABLE IF EXISTS `imp_mun`;

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

/*Data for the table `imp_mun` */

/*Table structure for table `imp_mun_det` */

DROP TABLE IF EXISTS `imp_mun_det`;

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

/*Data for the table `imp_mun_det` */

/*Table structure for table `impuesto_retencion` */

DROP TABLE IF EXISTS `impuesto_retencion`;

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

/*Data for the table `impuesto_retencion` */

/*Table structure for table `impuesto_retencion_det` */

DROP TABLE IF EXISTS `impuesto_retencion_det`;

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

/*Data for the table `impuesto_retencion_det` */

/*Table structure for table `islr_retencion` */

DROP TABLE IF EXISTS `islr_retencion`;

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

/*Data for the table `islr_retencion` */

/*Table structure for table `islr_retencion_det` */

DROP TABLE IF EXISTS `islr_retencion_det`;

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

/*Data for the table `islr_retencion_det` */

/*Table structure for table `iva_aplicado` */

DROP TABLE IF EXISTS `iva_aplicado`;

CREATE TABLE `iva_aplicado` (
  `id_iva_aplicado` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_part_ppto` bigint(20) NOT NULL,
  `valor_porc` decimal(20,2) NOT NULL,
  PRIMARY KEY (`id_iva_aplicado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci COMMENT='Guarda una correlacion del porcentaje aplicado para el IVA, ';

/*Data for the table `iva_aplicado` */

/*Table structure for table `iva_retencion` */

DROP TABLE IF EXISTS `iva_retencion`;

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

/*Data for the table `iva_retencion` */

/*Table structure for table `iva_retencion_det` */

DROP TABLE IF EXISTS `iva_retencion_det`;

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

/*Data for the table `iva_retencion_det` */

/*Table structure for table `map_next_id` */

DROP TABLE IF EXISTS `map_next_id`;

CREATE TABLE `map_next_id` (
  `clave` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT 'Los primeros cuatro (4) digitos deben corresponder con el ejercicio fiscal, es decir el año fiscal',
  `valor` bigint(20) NOT NULL COMMENT 'Normalmente deberia corresponder a actualizaciones, que generen incrementos unitarios, y este valorgenerado podria ser utilizado como clave (indice) primario o no, en otras tablas, este seria el next id.',
  `id_user` bigint(20) NOT NULL,
  PRIMARY KEY (`clave`),
  UNIQUE KEY `cuesec` (`clave`),
  KEY `cuenta` (`clave`),
  KEY `ejefis` (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci COMMENT='Para llevar la cuenta del ultimo Id generado asociado a una ';

/*Data for the table `map_next_id` */

insert  into `map_next_id`(`clave`,`valor`,`id_user`) values ('2023-1',1,1),('2023-11111111111111111111',0,1),('2023-22222222222222222222',0,1),('2023-OC',0,1);

/*Table structure for table `neg_pri` */

DROP TABLE IF EXISTS `neg_pri`;

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

/*Data for the table `neg_pri` */

/*Table structure for table `neg_pri_det` */

DROP TABLE IF EXISTS `neg_pri_det`;

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

/*Data for the table `neg_pri_det` */

/*Table structure for table `orden_pago` */

DROP TABLE IF EXISTS `orden_pago`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orden_pago` */

/*Table structure for table `ordenpago_aux_report` */

DROP TABLE IF EXISTS `ordenpago_aux_report`;

CREATE TABLE `ordenpago_aux_report` (
  `codpresu` varchar(30) CHARACTER SET latin1 NOT NULL,
  `partida` varchar(200) CHARACTER SET latin1 NOT NULL,
  `total` decimal(20,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `ordenpago_aux_report` */

/*Table structure for table `ordenpago_det` */

DROP TABLE IF EXISTS `ordenpago_det`;

CREATE TABLE `ordenpago_det` (
  `numpago` int(11) NOT NULL,
  `numcompr` int(11) NOT NULL,
  `cantpro` varchar(3) CHARACTER SET utf8 NOT NULL,
  `descpro` varchar(500) CHARACTER SET utf8 NOT NULL DEFAULT '',
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

/*Data for the table `ordenpago_det` */

/*Table structure for table `ordenpago_rpt_summary` */

DROP TABLE IF EXISTS `ordenpago_rpt_summary`;

CREATE TABLE `ordenpago_rpt_summary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL,
  `anu` char(1) CHARACTER SET utf8 NOT NULL,
  `fecha` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `razonsocial` varchar(80) COLLATE utf8_spanish_ci NOT NULL,
  `rif` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `total` decimal(20,2) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idsession` (`idsession`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `ordenpago_rpt_summary` */

/*Table structure for table `otras_ret` */

DROP TABLE IF EXISTS `otras_ret`;

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

/*Data for the table `otras_ret` */

/*Table structure for table `otras_ret_det` */

DROP TABLE IF EXISTS `otras_ret_det`;

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

/*Data for the table `otras_ret_det` */

/*Table structure for table `presupe` */

DROP TABLE IF EXISTS `presupe`;

CREATE TABLE `presupe` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(200) NOT NULL DEFAULT '',
  `partida` varchar(768) NOT NULL DEFAULT '',
  `monto_ini` decimal(20,2) NOT NULL,
  `monto` double(15,2) NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint(20) NOT NULL,
  `ejefismes` bigint(20) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  `anulado_sn` char(3) NOT NULL DEFAULT 'N',
  `cod_contable` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=latin1;

/*Data for the table `presupe` */

insert  into `presupe`(`id`,`codigo`,`partida`,`monto_ini`,`monto`,`registro`,`ejefis`,`ejefismes`,`fchsession`,`idsession`,`iduser`,`anulado_sn`,`cod_contable`) values (197,'11.00.41.40.103.02.01.00','PRUEBA1',300.00,300.00,'2023-01-02 14:59:08',2023,202301,'2023-01-02',1,1,'N','10.23.43.98'),(198,'11.00.52.00.402.01.01.00','PARTIDA2',500.00,500.00,'2023-01-02 15:00:55',2023,202301,'2023-01-02',1,1,'N','40.5.09.76'),(199,'01.03.00.51.403.18.01.00','IMPUESTO AL VALOR AGREGADO',800.00,800.00,'2023-01-02 15:09:21',2023,202301,'2023-01-02',1,1,'N','45.97.343.0');

/*Table structure for table `presupi` */

DROP TABLE IF EXISTS `presupi`;

CREATE TABLE `presupi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(32) NOT NULL,
  `partida` varchar(256) NOT NULL,
  `monto_ini` decimal(20,2) NOT NULL,
  `monto` decimal(20,2) NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis_year` bigint(20) NOT NULL,
  `ejefismes` bigint(20) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  `anulado_sn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `presupi` */

/*Table structure for table `tmppptoadicional` */

DROP TABLE IF EXISTS `tmppptoadicional`;

CREATE TABLE `tmppptoadicional` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(200) NOT NULL DEFAULT '',
  `partida` varchar(768) NOT NULL DEFAULT '',
  `monto_ini` decimal(20,2) NOT NULL,
  `monto` double(15,2) NOT NULL,
  `registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ejefis` bigint(20) NOT NULL,
  `ejefismes` bigint(20) NOT NULL,
  `fchsession` date NOT NULL,
  `idsession` bigint(20) NOT NULL,
  `iduser` bigint(20) NOT NULL,
  `anulado_sn` char(3) NOT NULL DEFAULT 'N',
  `cod_contable` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tmppptoadicional` */

/*Table structure for table `trasp_part_rpt_summary` */

DROP TABLE IF EXISTS `trasp_part_rpt_summary`;

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

/*Data for the table `trasp_part_rpt_summary` */

/*Table structure for table `trasppartidas` */

DROP TABLE IF EXISTS `trasppartidas`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `trasppartidas` */

/*Table structure for table `trasppartidas_det` */

DROP TABLE IF EXISTS `trasppartidas_det`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `trasppartidas_det` */

/*Table structure for table `unidad_tributaria_aplic` */

DROP TABLE IF EXISTS `unidad_tributaria_aplic`;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `unidad_tributaria_aplic` */

insert  into `unidad_tributaria_aplic`(`id_unidad_tributaria_aplic`,`id_user`,`id_session`,`date_session`,`descripcion`,`fecha_vigencia`,`valor_bs`) values (1,1,37,'2022-09-02 15:20:05','UND.T','2022-09-02',5.00);

/*Table structure for table `user_menu_track` */

DROP TABLE IF EXISTS `user_menu_track`;

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Data for the table `user_menu_track` */

insert  into `user_menu_track`(`id`,`iduser`,`class`,`op`,`obs`,`ejefis`,`ejefismes`,`userdatetime`,`servdatetime`) values (1,0,'capipsistema.UserPassIn','','',2023,202301,'2023-01-02 15:17:30','2023-01-02 15:17:32'),(2,1,'capipsistema.FrmPrincipal','','',2023,202301,'2023-01-02 15:17:42','2023-01-02 15:17:42'),(3,1,'configuracion.Configuracion','','',2023,202301,'2023-01-02 15:17:52','2023-01-02 15:17:52'),(4,1,'compromisos.Compromiso','','',2023,202301,'2023-01-02 15:18:26','2023-01-02 15:18:26'),(5,1,'compromisos.PresupeSel','','',2023,202301,'2023-01-02 15:18:47','2023-01-02 15:18:47'),(6,1,'bancos.BancosContabilidad','','',2023,202301,'2023-01-02 15:19:22','2023-01-02 15:19:22'),(7,1,'bancos.BancosCuentasRegistro','','',2023,202301,'2023-01-02 15:19:26','2023-01-02 15:19:26'),(8,1,'capipsistema.FrmPrincipal','','',2023,202301,'2023-01-02 15:20:34','2023-01-02 15:20:34');

/*Table structure for table `user_menu_track_summary` */

DROP TABLE IF EXISTS `user_menu_track_summary`;

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

/*Data for the table `user_menu_track_summary` */

/*Table structure for table `usuario` */

DROP TABLE IF EXISTS `usuario`;

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

/*Data for the table `usuario` */

insert  into `usuario`(`id_user`,`user`,`pass`,`nombre`,`ppto`,`compr`,`cau`,`imp`,`pago`,`banco`,`cfg`,`rpt`,`active`,`lastupdate`) values (1,'ADMIN',NULL,'ADMINISTRADOR (SUPER USUARIO)',1,1,1,1,1,1,1,1,'true',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
