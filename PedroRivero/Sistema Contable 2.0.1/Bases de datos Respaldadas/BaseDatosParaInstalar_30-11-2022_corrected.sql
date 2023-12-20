CREATE DATABASE /*!32312 IF NOT EXISTS*/ `sistemacontable` /*!40100 DEFAULT CHARACTER SET utf8 */;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


SET GLOBAL FOREIGN_KEY_CHECKS=0;

USE `sistemacontable`;

--
-- Table structure for table `mh_auxiliar`
--

DROP TABLE IF EXISTS `mh_auxiliar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mh_auxiliar` (
  `emp_id` varchar(100) NOT NULL,
  `cta_id` varchar(100) NOT NULL,
  `aux_nombreCta` varchar(100) NOT NULL,
  `aux_tipoCta` varchar(15) NOT NULL,
  `aux_condicion` varchar(3) NOT NULL,
  `aux_id` varchar(100) NOT NULL,
  `aux_nombreAux` varchar(100) NOT NULL,
  PRIMARY KEY (`emp_id`,`cta_id`,`aux_id`),
  KEY `aux_nombreCta` (`aux_nombreCta`,`aux_tipoCta`,`aux_condicion`,`aux_nombreAux`),
  KEY `aux_id` (`aux_id`),
  KEY `cta_id` (`cta_id`),
  CONSTRAINT `mh_auxiliar_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `mh_empresas` (`emp_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mh_auxiliar_ibfk_2` FOREIGN KEY (`cta_id`) REFERENCES `mh_cuentas` (`cta_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mh_auxiliar`
--

LOCK TABLES `mh_auxiliar` WRITE;
/*!40000 ALTER TABLE `mh_auxiliar` DISABLE KEYS */;
INSERT INTO `mh_auxiliar` VALUES ('001','1','ACTIVOS','De Titulo','No','',''),('003','1','ACTIVOS','De Titulo','No','',''),('100','1','ACTIVOS','De Titulo','No','',''),('001','1150','ANTICIPO DE COMPRAS','De Movimiento','No','',''),('003','1150','ANTICIPO DE COMPRAS','De Movimiento','No','',''),('001','1110','BANCO DE VENEZUELA','De Movimiento','No','',''),('003','1110','BANCO DE VENEZUELA','De Movimiento','No','',''),('100','1110','CAJA','De Movimiento','No','',''),('001','1190','CARGOS DIFERIDOS','De Movimiento','No','',''),('003','1190','CARGOS DIFERIDOS','De Movimiento','No','',''),('100','1190','CARGOS POR IVA','De Movimiento','No','',''),('001','11','CIRCULANTE','De Titulo','No','',''),('001','21','CIRCULANTE','De Titulo','No','',''),('100','11','CIRCULANTE','De Titulo','No','',''),('003','11','CIRCULANTE','De Totales','No','',''),('003','21','CIRCULANTE','De Totales','No','',''),('100','21','CIRCULANTE','De Totales','No','',''),('003','4100','COMPRAS','De Movimiento','No','',''),('100','4200','COMPRAS','De Movimiento','No','',''),('001','4','COSTOS','De Titulo','No','',''),('003','4','COSTOS','De Titulo','No','',''),('001','4000','COSTOS DE VENTAS','De Movimiento','No','',''),('003','4000','COSTOS DE VENTAS','De Movimiento','No','',''),('100','4100','COSTOS DE VENTAS','De Movimiento','No','',''),('100','4','COSTOS DE VENTAS','De Titulo','No','',''),('100','2190','CREDITO POR IVA','De Movimiento','No','',''),('100','1120','CUENTAS POR COBRAR','De Movimiento','No','',''),('001','1120','CUENTAS POR COBRAR','De Movimiento','Si','',''),('003','1120','CUENTAS POR COBRAR','De Movimiento','Si','',''),('001','1120','CUENTAS POR COBRAR','De Movimiento','Si','1121','CUENTAS POR COBRAR CLIENTES'),('003','1120','CUENTAS POR COBRAR','De Movimiento','Si','1121','CUENTAS POR COBRAR CLIENTES'),('001','1120','CUENTAS POR COBRAR','De Movimiento','Si','1123','OTRAS CUENTAS POR COBRAR'),('003','1120','CUENTAS POR COBRAR','De Movimiento','Si','1123','OTRAS CUENTAS POR COBRAR'),('001','1120','CUENTAS POR COBRAR','De Movimiento','Si','1122','P Y M POLLOS C A'),('003','1120','CUENTAS POR COBRAR','De Movimiento','Si','1122','P Y M POLLOS C.A.'),('001','2120','CUENTAS POR PAGAR','De Movimiento','Si','',''),('003','2120','CUENTAS POR PAGAR','De Movimiento','Si','',''),('100','2120','CUENTAS POR PAGAR','De Movimiento','Si','',''),('001','2120','CUENTAS POR PAGAR','De Movimiento','Si','2121','PROVEEDORES'),('003','2120','CUENTAS POR PAGAR','De Movimiento','Si','2121','PROVEEDORES'),('100','2120','CUENTAS POR PAGAR','De Movimiento','Si','2121','PROVEEDORES'),('001','2120','CUENTAS POR PAGAR','De Movimiento','Si','2122','SOCIOS'),('003','2120','CUENTAS POR PAGAR','De Movimiento','Si','2122','SOCIOS'),('100','2120','CUENTAS POR PAGAR','De Movimiento','Si','2122','SOCIOS'),('100','2730','DEFICIT','De Movimiento','No','',''),('001','1321','DEPRECIACION ACUMULADA','De Movimiento','No','',''),('001','1331','DEPRECIACION ACUMULADA','De Movimiento','No','',''),('001','1341','DEPRECIACION ACUMULADA','De Movimiento','No','',''),('003','1321','DEPRECIACION ACUMULADA','De Movimiento','No','',''),('003','1331','DEPRECIACION ACUMULADA','De Movimiento','No','',''),('003','1341','DEPRECIACION ACUMULADA','De Movimiento','No','',''),('100','1341','DEPRECIACION ACUMULADA','De Movimiento','No','',''),('100','1390','DEPRECIACION ACUMULADA','De Movimiento','No','',''),('001','1330','EQUIPOS DE COMPUTACION','De Movimiento','No','',''),('003','1330','EQUIPOS DE COMPUTACION','De Movimiento','No','',''),('001','9000','GANANCIAS Y PERDIDAS','De Movimiento','No','',''),('003','9000','GANANCIAS Y PERDIDAS','De Totales','No','',''),('100','9000','GANANCIAS Y PERDIDAS','De Totales','No','',''),('001','5','GASTOS','De Titulo','No','',''),('003','5','GASTOS','De Titulo','No','',''),('100','5','GASTOS','De Titulo','No','',''),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','',''),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5002','ARRENDAMIENTO Y CONDOMINIO'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5013','BOLSAS Y MATERIAL DE EMPAQUE'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5008','CONTABILIDAD'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5004','ELECTRICIDAD'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5016','FLETES'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5006','GASTOS DE DEPRECIACION'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5005','GASTOS DE VEHICULOS'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5009','GASTOS DIVERSOS'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5015','HIDROCENTRO'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5010','IMPUESTOS MUNICIPALES'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5012','MANTENIMIENTO Y REPARACION'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5014','PRESTACIONES SOCIALES'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5003','PUBLICIDAD'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5017','RETIRO SOCIOS'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5001','SUELDOS Y SALARIOS'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5011','SUMINISTROS DE OFICINA'),('100','5000','GASTOS DE ADMINISTRACION','De Totales','Si','5007','TELEFONO'),('100','4300','GASTOS DE IMPORTACION','De Movimiento','No','',''),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','',''),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','',''),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5014','ANTIGUEDAD'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5014','ANTIGUEDAD'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5015','BONOS'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5015','BONOS'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5002','COMISIONES'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5002','COMISIONES'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5012','COMISIONES Y CARGOS BANCARIOS'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5012','COMISIONES Y CARGOS BANCARIOS'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5011','EGRESOS VARIOS'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5011','EGRESOS VARIOS'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5007','FLETES'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5007','FLETES'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5010','GASTOS DE DEPRECIACION'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5010','GASTOS DE DEPRECIACION'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5003','GASTOS DE ELECTRICIDAD'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5003','GASTOS DE ELECTRICIDAD'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5005','GASTOS DE GESTIONES'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5005','GASTOS DE GESTIONES'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5006','GASTOS DE VEHICULOS'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5006','GASTOS DE VEHICULOS'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5001','HONORARIOS OPERACIONALES'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5001','HONORARIOS PROFESIONALES'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5008','REPARACION Y MANTENIMIENTO'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5008','REPARACION Y MANTENIMIENTO'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5004','RETIRO SOCIOS'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5004','RETIRO SOCIOS'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5013','SUELDOS Y SALARIOS'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5013','SUELDOS Y SALARIOS'),('001','5000','GASTOS OPERACIONALES','De Movimiento','Si','5009','SUMINISTROS DE OFICINA'),('003','5000','GASTOS OPERACIONALES','De Movimiento','Si','5009','SUMINISTROS DE OFICINA'),('001','2140','IMPUESTO S L RENTA P PAGAR','De Movimiento','No','',''),('003','2140','IMPUESTO S/L RENTA P/PAGAR','De Movimiento','No','',''),('100','2140','IMPUESTO SL RENTA P PAGAR','De Movimiento','No','',''),('100','2130','IMPUESTO SOBRE LA RENTA POR PAGAR','De Movimiento','No','',''),('001','1160','IMPUESTOS RETENIDOS','De Movimiento','No','',''),('003','1160','IMPUESTOS RETENIDOS','De Movimiento','No','',''),('001','3','INGRESOS','De Titulo','No','',''),('003','3','INGRESOS','De Titulo','No','',''),('100','3','INGRESOS','De Totales','No','',''),('001','7000','INGRESOS INTERESES CT CT','De Movimiento','No','',''),('003','7000','INGRESOS INTERESES CT. CT.','De Movimiento','No','',''),('004','1','INICIALIZAR CUENTA','De Movimiento','No','',''),('004','2','INICIALIZAR CUENTA DOS','De Movimiento','No','',''),('001','6000','INTERESES BANCARIOS','De Movimiento','No','',''),('003','6000','INTERESES BANCARIOS','De Movimiento','No','',''),('100','1130','INVENTARIO DE MERCANCIAS','De Movimiento','No','',''),('100','1200','INVERSIONES','De Movimiento','No','',''),('001','1200','INVERSIONES','De Movimiento','Si','',''),('003','1200','INVERSIONES','De Movimiento','Si','',''),('001','1200','INVERSIONES','De Movimiento','Si','1210','GRANJAS P Y M POLLOS C A'),('003','1200','INVERSIONES','De Movimiento','Si','1210','GRANJAS P Y M POLLOS C.A.'),('100','2191','IVA POR PAGAR','De Movimiento','No','',''),('100','1310','MOBILIARIO','De Movimiento','No','',''),('001','1340','MOBILIARIO DE OFICINA','De Movimiento','No','',''),('003','1340','MOBILIARIO DE OFICINA','De Movimiento','No','',''),('100','1340','MOBILIARIO DE OFICINA','De Movimiento','No','',''),('001','2110','OBLIGACIONES BANCARIAS','De Movimiento','No','',''),('003','2110','OBLIGACIONES BANCARIAS','De Movimiento','No','',''),('100','2110','OBLIGACIONES BANCARIAS','De Movimiento','No','',''),('001','1320','OFICINAS','De Movimiento','No','',''),('003','1320','OFICINAS','De Movimiento','No','',''),('001','2','PASIVOS','De Titulo','No','',''),('003','2','PASIVOS','De Titulo','No','',''),('100','2','PASIVOS','De Titulo','No','',''),('001','2700','PATRIMONIO','De Movimiento','No','',''),('003','2700','PATRIMONIO','De Movimiento','No','',''),('100','2700','PATRIMONIO','De Movimiento','No','',''),('001','27','PATRIMONIO','De Titulo','No','',''),('003','27','PATRIMONIO','De Titulo','No','',''),('100','27','PATRIMONIO','De Titulo','No','',''),('001','270','PATRIMONIO','De Totales','No','',''),('003','270','PATRIMONIO','De Totales','No','',''),('100','270','PATRIMONIO','De Totales','No','',''),('100','13','PROPIEDADES Y EQUIPOS','De Titulo','No','',''),('004','4','PRUEBA AUX DOS','De Movimiento','Si','',''),('004','4','PRUEBA AUX DOS','De Movimiento','Si','41','AUXILIAR INICIALIZADO'),('004','3','PRUEBA CON AUXILIAR','De Movimiento','Si','',''),('004','3','PRUEBA CON AUXILIAR','De Movimiento','Si','31','INICIALIZAR AUX'),('004','3','PRUEBA CON AUXILIAR','De Movimiento','Si','32','INICIALIZAR AUX UNO'),('001','2720','RESERVA LEGAL','De Movimiento','No','',''),('003','2720','RESERVA LEGAL','De Movimiento','No','',''),('100','2720','RESERVA LEGAL','De Movimiento','No','',''),('100','2710','UTILIDAD NO DISTRIBUIDAS','De Movimiento','No','',''),('001','2710','UTILIDADES NO DISTRIBUIDAS','De Movimiento','No','',''),('003','2710','UTILIDADES NO DISTRIBUIDAS','De Movimiento','No','',''),('100','3000','VENTAS','De Movimiento','No','',''),('001','3000','VENTAS','De Movimiento','Si','',''),('003','3000','VENTAS','De Movimiento','Si','',''),('001','3000','VENTAS','De Movimiento','Si','3100','AVES'),('003','3000','VENTAS','De Movimiento','Si','3100','AVES'),('003','3000','VENTAS','De Movimiento','Si','3110','SERVICIOS TECNICOS'),('001','3000','VENTAS','De Movimiento','Si','3110','SOCIOS TECNICOS');
/*!40000 ALTER TABLE `mh_auxiliar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mh_comprobante`
--

DROP TABLE IF EXISTS `mh_comprobante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mh_comprobante` (
  `emp_id` varchar(100) NOT NULL,
  `cta_id` varchar(100) NOT NULL,
  `aux_id` varchar(100) NOT NULL,
  `comp_id` int(11) NOT NULL DEFAULT 0,
  `comp_fecha` date NOT NULL,
  `comp_concepto` varchar(50) NOT NULL,
  `comp_debe` double DEFAULT 0,
  `comp_haber` double NOT NULL DEFAULT 0,
  `comp_linea` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`emp_id`,`cta_id`,`aux_id`,`comp_linea`),
  KEY `comp_linea` (`comp_linea`),
  KEY `aux_id` (`aux_id`),
  KEY `cta_id` (`cta_id`),
  CONSTRAINT `mh_comprobante_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `mh_auxiliar` (`emp_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mh_comprobante_ibfk_2` FOREIGN KEY (`aux_id`) REFERENCES `mh_auxiliar` (`aux_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mh_comprobante_ibfk_3` FOREIGN KEY (`cta_id`) REFERENCES `mh_auxiliar` (`cta_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mh_comprobante`
--

LOCK TABLES `mh_comprobante` WRITE;
/*!40000 ALTER TABLE `mh_comprobante` DISABLE KEYS */;
INSERT INTO `mh_comprobante` VALUES ('001','1200','1210',0,'2017-01-23','Comprobante Inicial',0,0,117),('001','1200','1210',1,'2016-01-31','PRUEBA 1',0,5000,142),
('001','2120','2121',0,'2017-01-23','Comprobante Inicial',0,0,118),
('001','2120','2122',0,'2017-01-23','Comprobante Inicial',0,0,119),
('003','1110','',1,'2005-01-31','ASIENTO DE APERTURA',206630390,0,176),
('003','1110','',2,'2005-01-31','CHEQ. # 1',0,2314500,188),
('003','1110','',2,'2005-01-31','CHEQ. # 2',0,4021786,189),
('003','1110','',2,'2005-01-31','CHEQ. # 3',0,1150000,190),('003','1110','',2,'2005-01-31','CHEQ. # 4',0,2288000,191),('003','1110','',3,'2005-01-31','CHEQ. # 5',0,1207000,193),('003','1110','',4,'2005-01-31','CHEQ. # 6',0,270000,198),('003','1110','',4,'2005-01-31','CHEQ. # 7',0,400000,199),('003','1110','',4,'2005-01-31','CHEQ. # 7',0,350000,200),('003','1110','',4,'2005-01-31','CHEQ. # 8',0,1300000,201),('003','1110','',4,'2005-01-31','CHEQ. # 9',0,2300000,202),('003','1110','',4,'2005-01-31','CHEQ. # 10',0,5000000,203),('003','1110','',4,'2005-01-31','CHEQ. # 11',0,500000,204),('003','1110','',4,'2005-01-31','CHEQ. # 12',0,200000,205),('003','1110','',4,'2005-01-31','CHEQ. # 13',0,700000,206),('003','1110','',4,'2005-01-31','CHEQ. # 14',0,4000000,207),('003','1110','',4,'2005-01-31','CHEQ. # 15',0,850000,208),('003','1120','1122',2,'2005-01-31','CARGOS A PY M POLLOS',9774286,0,187),('003','1120','1123',3,'2005-01-31','CARGOS A PY M POLLOS',1207000,0,192),('003','1200','1210',1,'2005-01-31','ASIENTO DE APERTURA',25000000,0,177),('003','1340','',1,'2005-01-31','ASIENTO DE APERTURA',8166808,0,178),('003','1341','',1,'2005-01-31','ASIENTO DE APERTURA',0,1149253,179),('003','2110','',1,'2005-01-31','ASIENTO DE APERTURA',0,70000000,180),('003','2120','2121',2,'2005-02-28','PAGO A PROVEEDORES',102436800,0,39),('003','2120','2121',1,'2005-01-31','ASIENTO DE APERTURA',0,81152211,181),('003','2120','2122',1,'2005-01-31','ASIENTO DE APERTURA',0,20000000,182),('003','2140','',1,'2005-01-31','ASIENTO DE APERTURA',0,5624360,183),('003','2700','',1,'2005-01-31','ASIENTO DE APERTURA',0,30000000,184),('003','2710','',1,'2005-01-31','ASIENTO DE APERTURA',0,30150306,185),('003','2720','',1,'2005-01-31','ASIENTO DE APERTURA',0,1721068,186),('003','5000','5001',4,'2005-01-31','PAGOS S/ CHEQUES',14650000,0,194),('003','5000','5004',4,'2005-01-31','PAGOS S/ CHEQUES',620000,0,195),('003','5000','5005',4,'2005-01-31','PAGOS S/ CHEQUES',200000,0,196),('003','5000','5006',4,'2005-01-31','PAGOS S/ CHEQUES',400000,0,197),('004','3','31',0,'2016-01-01','Comprobante Inicial',0,0,155),('004','3','32',0,'2016-01-01','Comprobante Inicial',0,0,156),('004','4','41',0,'2016-01-01','Comprobante Inicial',0,0,157),('100','2120','2121',0,'2010-01-01','Comprobante Inicial',0,0,161),('100','2120','2121',1,'2010-01-31','Mi primer asiento',0,81152211,169),('100','2120','2122',0,'2010-01-01','Comprobante Inicial',0,0,162),('100','2120','2122',1,'2010-01-31','Mi primer asiento',0,20000000,170);
/*!40000 ALTER TABLE `mh_comprobante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mh_concepto`
--

DROP TABLE IF EXISTS `mh_concepto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mh_concepto` (
  `emp_id` varchar(100) NOT NULL,
  `conc_id` int(11) NOT NULL AUTO_INCREMENT,
  `conc_descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`conc_id`),
  KEY `emp_id` (`emp_id`),
  CONSTRAINT `mh_concepto_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `mh_empresas` (`emp_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mh_concepto`
--

LOCK TABLES `mh_concepto` WRITE;
/*!40000 ALTER TABLE `mh_concepto` DISABLE KEYS */;
INSERT INTO `mh_concepto` VALUES ('100',1,'Mi primer concepto'),('100',2,'Mi primer asiento'),('003',3,'ASIENTO DE APERTURA'),('003',4,'INGRESOS DEL MES'),('003',5,'DEP. # '),('003',6,'DEPOSITOS DEL MES'),('003',7,'CHEQ. # '),('003',8,'CARGOS A PY M POLLOS'),('003',9,'PAGOS S/ CHEQUES'),('003',10,'COMPRAS DEL MES'),('003',11,'PAGO A PROVEEDORES'),('003',12,'P Y M POLLOS'),('003',13,'TRANSFERENCIAS A PY M POLLOS'),('003',14,'CHEQUES NO IDENTIFICADOS'),('003',15,'COMPRAS AVICENTRO AVES'),('003',16,'CANCELACION DE PROVEEDORES'),('003',17,'COMPRAS S CHEQUES');
/*!40000 ALTER TABLE `mh_concepto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mh_cuentas`
--

DROP TABLE IF EXISTS `mh_cuentas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mh_cuentas` (
  `cta_id` varchar(100) NOT NULL,
  PRIMARY KEY (`cta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mh_cuentas`
--

LOCK TABLES `mh_cuentas` WRITE;
/*!40000 ALTER TABLE `mh_cuentas` DISABLE KEYS */;
INSERT INTO `mh_cuentas` VALUES ('1'),('1000'),('11'),('1110'),('1120'),('1130'),('1150'),('1160'),('1190'),('1200'),('1240'),('13'),('1310'),('1320'),('1321'),('1330'),('1331'),('1340'),('1341'),('1390'),('2'),('2000'),('21'),('2110'),('2120'),('2130'),('2140'),('2190'),('2191'),('27'),('270'),('2700'),('2710'),('2720'),('2730'),('3'),('3000'),('4'),('4000'),('4100'),('4200'),('4300'),('5'),('5000'),('6000'),('7000'),('9000');
/*!40000 ALTER TABLE `mh_cuentas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mh_empresas`
--

DROP TABLE IF EXISTS `mh_empresas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mh_empresas` (
  `emp_id` varchar(100) NOT NULL,
  `emp_nombre` varchar(100) NOT NULL,
  `emp_rif` varchar(20) NOT NULL,
  `emp_inicioFiscal` date NOT NULL,
  `emp_finFiscal` date NOT NULL,
  `emp_fecha` date NOT NULL,
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mh_empresas`
--

LOCK TABLES `mh_empresas` WRITE;
/*!40000 ALTER TABLE `mh_empresas` DISABLE KEYS */;
INSERT INTO `mh_empresas` VALUES ('001','Joel Prueba 1','000000-0','2016-01-01','2016-12-31','2016-09-20'),('002','Cadenas','11111111','2015-01-01','2015-12-31','2016-09-20'),('003','TECNO ASESORES AVICOLA, C.A.','J-310225536-4','2005-01-01','2005-12-31','2016-09-26'),('004','Joel Prueba 2','000000-1','2016-01-01','2016-12-31','2017-03-11'),('100','TEREARTE S.R.L.','J-31000033-1','2010-01-01','2010-12-31','2016-09-20'),('995','LLLLLL','999999','2005-05-01','2005-12-31','2017-01-16');
/*!40000 ALTER TABLE `mh_empresas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'sistemacontable'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-30 22:19:33

SET GLOBAL FOREIGN_KEY_CHECKS=1;
