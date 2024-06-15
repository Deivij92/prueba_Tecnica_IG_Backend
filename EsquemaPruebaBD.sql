-- MySQL Script generated by MySQL Workbench
-- Fri Jun 14 23:00:33 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema igtest
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema igtest
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `igtest` DEFAULT CHARACTER SET utf8mb4 ;
USE `igtest` ;

-- -----------------------------------------------------
-- Table `igtest`.`info_cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `igtest`.`info_cliente` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `apellidos` VARCHAR(255) NOT NULL,
  `ciudad` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `numero_documento` VARCHAR(255) NOT NULL,
  `residencia` VARCHAR(255) NOT NULL,
  `telefono` VARCHAR(255) NOT NULL,
  `tipo_documento` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_brp2lfi0kb1j4vifi3oxj018u` (`numero_documento` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 34
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `igtest`.`info_laboral`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `igtest`.`info_laboral` (
  `fecha_vinculacion` DATE NOT NULL,
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `id_cliente` BIGINT(20) NOT NULL,
  `cargo` VARCHAR(255) NOT NULL,
  `direccion` VARCHAR(255) NOT NULL,
  `nit_empresa` VARCHAR(255) NOT NULL,
  `nombre_empresa` VARCHAR(255) NOT NULL,
  `telefono` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_27532feyg3yt0drcv6tq0q2tm` (`nit_empresa` ASC) VISIBLE,
  INDEX `FKi2n0o2r6s0edd8wssw791vn2a` (`id_cliente` ASC) VISIBLE,
  CONSTRAINT `FKi2n0o2r6s0edd8wssw791vn2a`
    FOREIGN KEY (`id_cliente`)
    REFERENCES `igtest`.`info_cliente` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `igtest`.`info_referencias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `igtest`.`info_referencias` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ciudad` VARCHAR(255) NOT NULL,
  `nombres_apellidos` VARCHAR(255) NOT NULL,
  `numero_documento` VARCHAR(255) NOT NULL,
  `parentezco` VARCHAR(255) NOT NULL,
  `residencia` VARCHAR(255) NOT NULL,
  `telefono` VARCHAR(255) NOT NULL,
  `tipo_documento` VARCHAR(255) NOT NULL,
  `id_cliente` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_p7heysf1wuqe8kyaj4u1gg1w0` (`numero_documento` ASC) VISIBLE,
  INDEX `FK6qtkf14s66vgq2q2w3wku24n4` (`id_cliente` ASC) VISIBLE,
  CONSTRAINT `FK6qtkf14s66vgq2q2w3wku24n4`
    FOREIGN KEY (`id_cliente`)
    REFERENCES `igtest`.`info_cliente` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `igtest`.`linea_credito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `igtest`.`linea_credito` (
  `plazo_maximo` INT(11) NOT NULL,
  `valor_maximo` DOUBLE NOT NULL,
  `valor_minimo` DOUBLE NOT NULL,
  `id_linea_credito` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nombre_linea_credito` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_linea_credito`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `igtest`.`solicitud_prestamo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `igtest`.`solicitud_prestamo` (
  `id_prestamo` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descripcion_credito` VARCHAR(255) NOT NULL,
  `estado_solicitud` VARCHAR(255) NULL DEFAULT NULL,
  `fecha_desembolso` DATE NOT NULL,
  `monto` DOUBLE NOT NULL,
  `plazo` INT(11) NOT NULL,
  `id_cliente` BIGINT(20) NOT NULL,
  `linea_credito_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id_prestamo`),
  INDEX `FK4sxsi5qs5x72n010feetew3bm` (`id_cliente` ASC) VISIBLE,
  INDEX `FKgw6map6rev8lgfylld5px0bc3` (`linea_credito_id` ASC) VISIBLE,
  CONSTRAINT `FK4sxsi5qs5x72n010feetew3bm`
    FOREIGN KEY (`id_cliente`)
    REFERENCES `igtest`.`info_cliente` (`id`),
  CONSTRAINT `FKgw6map6rev8lgfylld5px0bc3`
    FOREIGN KEY (`linea_credito_id`)
    REFERENCES `igtest`.`linea_credito` (`id_linea_credito`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
