-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 23, 2023 at 05:03 PM
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
-- Database: `isiweekloanservices`
--
CREATE DATABASE IF NOT EXISTS `isiweekloanservices` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `isiweekloanservices`;

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(256) NOT NULL,
  `description` varchar(512) NOT NULL,
  `email` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL,
  `phone1` varchar(32) NOT NULL,
  `phone2` varchar(32) DEFAULT NULL,
  `primary_contact` varchar(256) NOT NULL,
  `taxidnumber` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `company`:
--

-- --------------------------------------------------------

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
CREATE TABLE IF NOT EXISTS `country` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `country`:
--

-- --------------------------------------------------------

--
-- Table structure for table `criminal_record`
--

DROP TABLE IF EXISTS `criminal_record`;
CREATE TABLE IF NOT EXISTS `criminal_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `criminal_record`:
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_person` bigint NOT NULL,
  `credit_score` bigint NOT NULL,
  `max_loan_amount` decimal(2,0) NOT NULL,
  `observations` varchar(256) NOT NULL,
  `person` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_fk0` (`id_person`),
  KEY `FKihbint30ismdy1hy7or8yx221` (`person`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `customer`:
--   `id_person`
--       `person` -> `id`
--   `person`
--       `person` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `departament`
--

DROP TABLE IF EXISTS `departament`;
CREATE TABLE IF NOT EXISTS `departament` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `departament`:
--

-- --------------------------------------------------------

--
-- Table structure for table `doc_type`
--

DROP TABLE IF EXISTS `doc_type`;
CREATE TABLE IF NOT EXISTS `doc_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `doc_type`:
--

-- --------------------------------------------------------

--
-- Table structure for table `email_notification`
--

DROP TABLE IF EXISTS `email_notification`;
CREATE TABLE IF NOT EXISTS `email_notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_person` bigint NOT NULL,
  `id_loan_contract` bigint NOT NULL,
  `subject` varchar(256) NOT NULL,
  `sent_at` varchar(256) NOT NULL,
  `body` varchar(1024) NOT NULL,
  `date_sent` datetime NOT NULL,
  `loan_contract` bigint NOT NULL,
  `person` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `email_notification_fk0` (`id_person`),
  KEY `email_notification_fk1` (`id_loan_contract`),
  KEY `FKlblurvljbhgsl2r2k0blnu5ma` (`loan_contract`),
  KEY `FK7a21y7ycyp32uvm2o776eeyoi` (`person`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `email_notification`:
--   `id_person`
--       `person` -> `id`
--   `id_loan_contract`
--       `loan_contract` -> `id`
--   `person`
--       `person` -> `id`
--   `loan_contract`
--       `loan_contract` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `id` bigint NOT NULL,
  `id_person` bigint NOT NULL,
  `id_employee_status` bigint NOT NULL,
  `id_Job_title` bigint NOT NULL,
  `id_department` bigint NOT NULL,
  `id_manager` bigint NOT NULL,
  `date_of_hire` date NOT NULL,
  `salary` decimal(2,0) NOT NULL,
  `benefits` varchar(512) NOT NULL,
  `contact_information` varchar(512) NOT NULL,
  `education` varchar(512) NOT NULL,
  `skills` varchar(512) NOT NULL,
  `performance_reviews` varchar(1024) NOT NULL,
  `department` bigint NOT NULL,
  `employee_status` bigint NOT NULL,
  `job_title` bigint NOT NULL,
  `manager` bigint NOT NULL,
  `person` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_fk0` (`id_person`),
  KEY `employee_fk1` (`id_employee_status`),
  KEY `employee_fk2` (`id_Job_title`),
  KEY `employee_fk3` (`id_department`),
  KEY `employee_fk4` (`id_manager`),
  KEY `FK4c78tog7snd0jkn3l8nw45s8` (`department`),
  KEY `FK3b6nha46997yh4kt1fxgrtnva` (`employee_status`),
  KEY `FKe6vgkubudbkculd0p3j6aa9v3` (`job_title`),
  KEY `FK250pv8c6bvqi85do66wnpjkrw` (`manager`),
  KEY `FKherextttrn6l7tlg2qsf51dut` (`person`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `employee`:
--   `id_person`
--       `person` -> `id`
--   `id_employee_status`
--       `employee_status` -> `id`
--   `id_Job_title`
--       `job_title` -> `id`
--   `id_department`
--       `departament` -> `id`
--   `id_manager`
--       `person` -> `id`
--   `manager`
--       `person` -> `id`
--   `employee_status`
--       `employee_status` -> `id`
--   `department`
--       `departament` -> `id`
--   `job_title`
--       `job_title` -> `id`
--   `person`
--       `person` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee_status`
--

DROP TABLE IF EXISTS `employee_status`;
CREATE TABLE IF NOT EXISTS `employee_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `employee_status`:
--

-- --------------------------------------------------------

--
-- Table structure for table `job_title`
--

DROP TABLE IF EXISTS `job_title`;
CREATE TABLE IF NOT EXISTS `job_title` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `job_title`:
--

-- --------------------------------------------------------

--
-- Table structure for table `loan_collector`
--

DROP TABLE IF EXISTS `loan_collector`;
CREATE TABLE IF NOT EXISTS `loan_collector` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_person` bigint NOT NULL,
  `id_lc_status` bigint NOT NULL,
  `lc_status` bigint NOT NULL,
  `person` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `loan_collector_fk0` (`id_lc_status`),
  KEY `FKlvh848axh9xc825aavjgx9r9` (`lc_status`),
  KEY `FK2gi1p301pff7sbuntanta5okp` (`person`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `loan_collector`:
--   `person`
--       `person` -> `id`
--   `lc_status`
--       `loan_collector_status` -> `id`
--   `id_lc_status`
--       `loan_collector_status` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `loan_collector_status`
--

DROP TABLE IF EXISTS `loan_collector_status`;
CREATE TABLE IF NOT EXISTS `loan_collector_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `loan_collector_status`:
--

-- --------------------------------------------------------

--
-- Table structure for table `loan_contract`
--

DROP TABLE IF EXISTS `loan_contract`;
CREATE TABLE IF NOT EXISTS `loan_contract` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_customer` bigint NOT NULL,
  `id_loan_status` bigint NOT NULL,
  `id_loan_type` bigint NOT NULL,
  `loan_amount` decimal(2,0) NOT NULL,
  `interest_rate` decimal(2,0) NOT NULL,
  `loan_term` bigint NOT NULL,
  `payment` decimal(2,0) NOT NULL,
  `date` date NOT NULL,
  `date_of_maturity` date NOT NULL,
  `loan_purpose` bigint NOT NULL,
  `collateral` varchar(1024) NOT NULL,
  `prepayment_penalty` decimal(2,0) NOT NULL,
  `default_interest_rate` decimal(2,0) NOT NULL,
  `customer` bigint NOT NULL,
  `loan_status` bigint NOT NULL,
  `loan_type` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `loan_contract_fk0` (`id_customer`),
  KEY `loan_contract_fk1` (`id_loan_status`),
  KEY `loan_contract_fk2` (`id_loan_type`),
  KEY `FKqfugtokar71ofmyv1tlmyqt2i` (`customer`),
  KEY `FKd6hppujs9f5b5fym6fdh73nnn` (`loan_status`),
  KEY `FK9sq53r8dwhlxoc0syx96e1aj3` (`loan_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `loan_contract`:
--   `loan_type`
--       `loan_type` -> `id`
--   `loan_status`
--       `loan_status` -> `id`
--   `customer`
--       `customer` -> `id`
--   `id_customer`
--       `customer` -> `id`
--   `id_loan_status`
--       `loan_status` -> `id`
--   `id_loan_type`
--       `loan_type` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `loan_status`
--

DROP TABLE IF EXISTS `loan_status`;
CREATE TABLE IF NOT EXISTS `loan_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `loan_status`:
--

-- --------------------------------------------------------

--
-- Table structure for table `loan_type`
--

DROP TABLE IF EXISTS `loan_type`;
CREATE TABLE IF NOT EXISTS `loan_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `loan_type`:
--

-- --------------------------------------------------------

--
-- Table structure for table `marital_status`
--

DROP TABLE IF EXISTS `marital_status`;
CREATE TABLE IF NOT EXISTS `marital_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `marital_status`:
--

-- --------------------------------------------------------

--
-- Table structure for table `payment_datails`
--

DROP TABLE IF EXISTS `payment_datails`;
CREATE TABLE IF NOT EXISTS `payment_datails` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_loan_contract` bigint NOT NULL,
  `payment_date` date NOT NULL,
  `payment_amount` decimal(2,0) NOT NULL,
  `id_payment_type` bigint NOT NULL,
  `id_payment_status` bigint NOT NULL,
  `notes` varchar(1024) NOT NULL,
  `loan_contract` bigint NOT NULL,
  `payment_status` bigint NOT NULL,
  `payment_type` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `payment_datails_fk0` (`id_loan_contract`),
  KEY `payment_datails_fk1` (`id_payment_type`),
  KEY `payment_datails_fk2` (`id_payment_status`),
  KEY `FKh27yv1n02j54egelxvvyhp2j0` (`loan_contract`),
  KEY `FKrrrs50vhtjnwwpihe3kve5qlb` (`payment_status`),
  KEY `FKqgcl1ybw76mfqv6y11n88m3su` (`payment_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `payment_datails`:
--   `loan_contract`
--       `loan_contract` -> `id`
--   `payment_type`
--       `payment_type` -> `id`
--   `payment_status`
--       `payment_status` -> `id`
--   `id_loan_contract`
--       `loan_contract` -> `id`
--   `id_payment_type`
--       `payment_type` -> `id`
--   `id_payment_status`
--       `payment_status` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `payment_status`
--

DROP TABLE IF EXISTS `payment_status`;
CREATE TABLE IF NOT EXISTS `payment_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `payment_status`:
--

-- --------------------------------------------------------

--
-- Table structure for table `payment_type`
--

DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE IF NOT EXISTS `payment_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` varchar(512) NOT NULL,
  `processing_fee` varchar(512) DEFAULT NULL,
  `supported_currencies` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `payment_type`:
--

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `identification_document` varchar(16) NOT NULL,
  `id_doc_type` bigint NOT NULL,
  `id_country` bigint NOT NULL,
  `id_criminal_record` bigint NOT NULL,
  `id_marital_status` bigint NOT NULL,
  `email` varchar(32) NOT NULL,
  `first_name` varchar(64) NOT NULL,
  `last_name` varchar(64) NOT NULL,
  `phone_number1` varchar(16) NOT NULL,
  `phone_number2` varchar(16) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` char(1) NOT NULL,
  `address` varchar(256) NOT NULL,
  `city` varchar(64) NOT NULL,
  `state` varchar(64) NOT NULL,
  `observations` varchar(256) NOT NULL,
  `occupation` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `country` bigint NOT NULL,
  `criminal_record` bigint NOT NULL,
  `doc_type` bigint NOT NULL,
  `marital_status` bigint NOT NULL,
  `company` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `person_fk0` (`id_doc_type`),
  KEY `person_fk1` (`id_country`),
  KEY `person_fk2` (`id_criminal_record`),
  KEY `person_fk3` (`id_marital_status`),
  KEY `FK9c3l0gnmefwwjik0lwj8lqou9` (`country`),
  KEY `FKd12cm22rrf0rw3e9p44yd4xb8` (`criminal_record`),
  KEY `FK64gpon64oxailw0q0t1v04xv1` (`doc_type`),
  KEY `FK4n3rjr6h2a9n21cjh0juk8gv8` (`marital_status`),
  KEY `FKbpbwe3hfsg98qqo3luvktsq69` (`company`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `person`:
--   `marital_status`
--       `marital_status` -> `id`
--   `doc_type`
--       `doc_type` -> `id`
--   `country`
--       `country` -> `id`
--   `company`
--       `company` -> `id`
--   `criminal_record`
--       `criminal_record` -> `id`
--   `id_doc_type`
--       `doc_type` -> `id`
--   `id_country`
--       `country` -> `id`
--   `id_criminal_record`
--       `criminal_record` -> `id`
--   `id_marital_status`
--       `marital_status` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `phone_notification`
--

DROP TABLE IF EXISTS `phone_notification`;
CREATE TABLE IF NOT EXISTS `phone_notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_person` bigint NOT NULL,
  `id_loan_contract` bigint NOT NULL,
  `subject` varchar(256) NOT NULL,
  `sent_at` varchar(256) NOT NULL,
  `body` varchar(1024) NOT NULL,
  `date_of_call` datetime NOT NULL,
  `loan_contract` bigint NOT NULL,
  `person` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `phone_notification_fk0` (`id_person`),
  KEY `phone_notification_fk1` (`id_loan_contract`),
  KEY `FKbpelqbgveomrtbndf9abqlubo` (`loan_contract`),
  KEY `FKb3m7uswtin12ggoqxfu7n674b` (`person`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `phone_notification`:
--   `person`
--       `person` -> `id`
--   `loan_contract`
--       `loan_contract` -> `id`
--   `id_person`
--       `person` -> `id`
--   `id_loan_contract`
--       `loan_contract` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `whatsapp_notification`
--

DROP TABLE IF EXISTS `whatsapp_notification`;
CREATE TABLE IF NOT EXISTS `whatsapp_notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_person` bigint NOT NULL,
  `id_loan_contract` bigint NOT NULL,
  `subject` varchar(256) NOT NULL,
  `sent_at` varchar(256) NOT NULL,
  `body` varchar(1024) NOT NULL,
  `date_sent` datetime NOT NULL,
  `loan_contract` bigint NOT NULL,
  `person` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `whatsapp_notification_fk0` (`id_person`),
  KEY `whatsapp_notification_fk1` (`id_loan_contract`),
  KEY `FKpdlt4jj7kqulvm5sqehbvmv6b` (`loan_contract`),
  KEY `FKduic4xx5ynrhjqknln4tmjjrx` (`person`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONSHIPS FOR TABLE `whatsapp_notification`:
--   `person`
--       `person` -> `id`
--   `loan_contract`
--       `loan_contract` -> `id`
--   `id_person`
--       `person` -> `id`
--   `id_loan_contract`
--       `loan_contract` -> `id`
--

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_fk0` FOREIGN KEY (`id_person`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FKihbint30ismdy1hy7or8yx221` FOREIGN KEY (`person`) REFERENCES `person` (`id`);

--
-- Constraints for table `email_notification`
--
ALTER TABLE `email_notification`
  ADD CONSTRAINT `email_notification_fk0` FOREIGN KEY (`id_person`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `email_notification_fk1` FOREIGN KEY (`id_loan_contract`) REFERENCES `loan_contract` (`id`),
  ADD CONSTRAINT `FK7a21y7ycyp32uvm2o776eeyoi` FOREIGN KEY (`person`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FKlblurvljbhgsl2r2k0blnu5ma` FOREIGN KEY (`loan_contract`) REFERENCES `loan_contract` (`id`);

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_fk0` FOREIGN KEY (`id_person`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `employee_fk1` FOREIGN KEY (`id_employee_status`) REFERENCES `employee_status` (`id`),
  ADD CONSTRAINT `employee_fk2` FOREIGN KEY (`id_Job_title`) REFERENCES `job_title` (`id`),
  ADD CONSTRAINT `employee_fk3` FOREIGN KEY (`id_department`) REFERENCES `departament` (`id`),
  ADD CONSTRAINT `employee_fk4` FOREIGN KEY (`id_manager`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FK250pv8c6bvqi85do66wnpjkrw` FOREIGN KEY (`manager`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FK3b6nha46997yh4kt1fxgrtnva` FOREIGN KEY (`employee_status`) REFERENCES `employee_status` (`id`),
  ADD CONSTRAINT `FK4c78tog7snd0jkn3l8nw45s8` FOREIGN KEY (`department`) REFERENCES `departament` (`id`),
  ADD CONSTRAINT `FKe6vgkubudbkculd0p3j6aa9v3` FOREIGN KEY (`job_title`) REFERENCES `job_title` (`id`),
  ADD CONSTRAINT `FKherextttrn6l7tlg2qsf51dut` FOREIGN KEY (`person`) REFERENCES `person` (`id`);

--
-- Constraints for table `loan_collector`
--
ALTER TABLE `loan_collector`
  ADD CONSTRAINT `FK2gi1p301pff7sbuntanta5okp` FOREIGN KEY (`person`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FKlvh848axh9xc825aavjgx9r9` FOREIGN KEY (`lc_status`) REFERENCES `loan_collector_status` (`id`),
  ADD CONSTRAINT `loan_collector_fk0` FOREIGN KEY (`id_lc_status`) REFERENCES `loan_collector_status` (`id`);

--
-- Constraints for table `loan_contract`
--
ALTER TABLE `loan_contract`
  ADD CONSTRAINT `FK9sq53r8dwhlxoc0syx96e1aj3` FOREIGN KEY (`loan_type`) REFERENCES `loan_type` (`id`),
  ADD CONSTRAINT `FKd6hppujs9f5b5fym6fdh73nnn` FOREIGN KEY (`loan_status`) REFERENCES `loan_status` (`id`),
  ADD CONSTRAINT `FKqfugtokar71ofmyv1tlmyqt2i` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `loan_contract_fk0` FOREIGN KEY (`id_customer`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `loan_contract_fk1` FOREIGN KEY (`id_loan_status`) REFERENCES `loan_status` (`id`),
  ADD CONSTRAINT `loan_contract_fk2` FOREIGN KEY (`id_loan_type`) REFERENCES `loan_type` (`id`);

--
-- Constraints for table `payment_datails`
--
ALTER TABLE `payment_datails`
  ADD CONSTRAINT `FKh27yv1n02j54egelxvvyhp2j0` FOREIGN KEY (`loan_contract`) REFERENCES `loan_contract` (`id`),
  ADD CONSTRAINT `FKqgcl1ybw76mfqv6y11n88m3su` FOREIGN KEY (`payment_type`) REFERENCES `payment_type` (`id`),
  ADD CONSTRAINT `FKrrrs50vhtjnwwpihe3kve5qlb` FOREIGN KEY (`payment_status`) REFERENCES `payment_status` (`id`),
  ADD CONSTRAINT `payment_datails_fk0` FOREIGN KEY (`id_loan_contract`) REFERENCES `loan_contract` (`id`),
  ADD CONSTRAINT `payment_datails_fk1` FOREIGN KEY (`id_payment_type`) REFERENCES `payment_type` (`id`),
  ADD CONSTRAINT `payment_datails_fk2` FOREIGN KEY (`id_payment_status`) REFERENCES `payment_status` (`id`);

--
-- Constraints for table `person`
--
ALTER TABLE `person`
  ADD CONSTRAINT `FK4n3rjr6h2a9n21cjh0juk8gv8` FOREIGN KEY (`marital_status`) REFERENCES `marital_status` (`id`),
  ADD CONSTRAINT `FK64gpon64oxailw0q0t1v04xv1` FOREIGN KEY (`doc_type`) REFERENCES `doc_type` (`id`),
  ADD CONSTRAINT `FK9c3l0gnmefwwjik0lwj8lqou9` FOREIGN KEY (`country`) REFERENCES `country` (`id`),
  ADD CONSTRAINT `FKbpbwe3hfsg98qqo3luvktsq69` FOREIGN KEY (`company`) REFERENCES `company` (`id`),
  ADD CONSTRAINT `FKd12cm22rrf0rw3e9p44yd4xb8` FOREIGN KEY (`criminal_record`) REFERENCES `criminal_record` (`id`),
  ADD CONSTRAINT `person_fk0` FOREIGN KEY (`id_doc_type`) REFERENCES `doc_type` (`id`),
  ADD CONSTRAINT `person_fk1` FOREIGN KEY (`id_country`) REFERENCES `country` (`id`),
  ADD CONSTRAINT `person_fk2` FOREIGN KEY (`id_criminal_record`) REFERENCES `criminal_record` (`id`),
  ADD CONSTRAINT `person_fk3` FOREIGN KEY (`id_marital_status`) REFERENCES `marital_status` (`id`);

--
-- Constraints for table `phone_notification`
--
ALTER TABLE `phone_notification`
  ADD CONSTRAINT `FKb3m7uswtin12ggoqxfu7n674b` FOREIGN KEY (`person`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FKbpelqbgveomrtbndf9abqlubo` FOREIGN KEY (`loan_contract`) REFERENCES `loan_contract` (`id`),
  ADD CONSTRAINT `phone_notification_fk0` FOREIGN KEY (`id_person`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `phone_notification_fk1` FOREIGN KEY (`id_loan_contract`) REFERENCES `loan_contract` (`id`);

--
-- Constraints for table `whatsapp_notification`
--
ALTER TABLE `whatsapp_notification`
  ADD CONSTRAINT `FKduic4xx5ynrhjqknln4tmjjrx` FOREIGN KEY (`person`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FKpdlt4jj7kqulvm5sqehbvmv6b` FOREIGN KEY (`loan_contract`) REFERENCES `loan_contract` (`id`),
  ADD CONSTRAINT `whatsapp_notification_fk0` FOREIGN KEY (`id_person`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `whatsapp_notification_fk1` FOREIGN KEY (`id_loan_contract`) REFERENCES `loan_contract` (`id`);
SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
