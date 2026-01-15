/* init.sql */
SET FOREIGN_KEY_CHECKS = 0;

-- Borrar en orden inverso (de lo más dependiente a lo más simple)
DROP TABLE IF EXISTS `book_loan`;
DROP TABLE IF EXISTS `inventary`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `student`;
DROP TABLE IF EXISTS `category`;

SET FOREIGN_KEY_CHECKS = 1;

-- 1. CATEGORY (Sin dependencias)
CREATE TABLE `category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. STUDENT (Sin dependencias)
CREATE TABLE `student` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50),
  `student_number` BIGINT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. BOOK (Depende de category)
CREATE TABLE `book` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(50),
  `name` VARCHAR(50),
  `category_id` BIGINT,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_BOOK_CATEGORY` FOREIGN KEY (`category_id`) 
    REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. INVENTARY (Depende de book)
CREATE TABLE `inventary` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `number_avalable` INT NOT NULL,
  `book_id` BIGINT,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_INVENTARY_BOOK` FOREIGN KEY (`book_id`) 
    REFERENCES `book` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. BOOK_LOAN (Depende de student y book)
CREATE TABLE `book_loan` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_id` BIGINT,
  `book_id` BIGINT,
  `is_returned` TINYINT(1) NOT NULL DEFAULT 0,
  `init_loan_date` DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
  `finished_loan_date` DATETIME(6),
  `commentaries` VARCHAR(50),
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_LOAN_STUDENT` FOREIGN KEY (`student_id`) 
    REFERENCES `student` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_LOAN_BOOK` FOREIGN KEY (`book_id`) 
    REFERENCES `book` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* INSERCIÓN DE DATOS */
INSERT INTO `category` (`name`) VALUES ('Programación'), ('Bases de Datos');
INSERT INTO `student` (`name`, `student_number`) VALUES ('Darth Vader', 1001), ('Luke Skywalker', 1002);
INSERT INTO `book` (`description`, `name`, `category_id`) VALUES ('Java Guide', 'Java Head First', 1), ('SQL Guide', 'SQL Mastery', 2);
INSERT INTO `inventary` (`number_avalable`, `book_id`) VALUES (5, 1), (10, 2);
INSERT INTO `book_loan` (`student_id`, `book_id`, `is_returned`, `commentaries`) VALUES (1, 1, 0, 'No usar el lado oscuro');