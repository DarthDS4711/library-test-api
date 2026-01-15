/* 1. Tabla CATEGORY */
CREATE TABLE `category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* 2. Tabla STUDENT */
CREATE TABLE `student` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50),
  `student_number` BIGINT NOT NULL, /* NUMBER(10,0) cabe en INT, pero BIGINT es más seguro */
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* 3. Tabla BOOK */
CREATE TABLE `book` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(50),
  `name` VARCHAR(50),
  `category_id` BIGINT,
  PRIMARY KEY (`id`),
  KEY `idx_book_category` (`category_id`),
  CONSTRAINT `FK_BOOK_CATEGORY` FOREIGN KEY (`category_id`) 
    REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* 4. Tabla INVENTARY (Mantenido el nombre original del script) */
CREATE TABLE `inventary` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `number_avalable` INT NOT NULL,
  `book_id` BIGINT,
  PRIMARY KEY (`id`),
  KEY `idx_inventary_book` (`book_id`),
  CONSTRAINT `FK_INVENTARY_BOOK` FOREIGN KEY (`book_id`) 
    REFERENCES `book` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* 5. Tabla BOOK_LOAN */
CREATE TABLE `book_loan` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `student_id` BIGINT,
  `is_returned` TINYINT(1) NOT NULL DEFAULT 0,
  `init_loan_date` DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
  `finished_loan_date` DATETIME(6),
  `commentaries` VARCHAR(50),
  `book_id` BIGINT, /* Nota: En tu script original no había FK explícito para esto, pero la columna existe */
  PRIMARY KEY (`id`),
  KEY `idx_loan_student` (`student_id`),
  CONSTRAINT `FK_LOAN_STUDENT` FOREIGN KEY (`student_id`) 
    REFERENCES `student` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


¡Claro! Para que los datos se inserten correctamente, es fundamental respetar el orden de las llaves foráneas (primero las categorías y estudiantes, luego los libros y finalmente los préstamos).

Puedes añadir este bloque al final de tu archivo init.sql. He incluido datos de prueba para que puedas verificar el funcionamiento de las tablas de inmediato:

SQL

/* 6. INSERCIÓN DE DATOS DE PRUEBA */

-- Insertar Categorías
INSERT INTO `category` (`name`) VALUES 
('Programación'), 
('Bases de Datos'), 
('Terror'), 
('Ciencia Ficción');

-- Insertar Estudiantes
INSERT INTO `student` (`name`, `student_number`) VALUES 
('Darth Vader', 1001), 
('Luke Skywalker', 1002), 
('Leia Organa', 1003);

-- Insertar Libros
INSERT INTO `book` (`description`, `name`, `category_id`) VALUES 
('Guía de Java para principiantes', 'Java Head First', 1),
('Conceptos de Spring Boot 2.7', 'Spring in Action', 1),
('Domina SQL y MySQL', 'SQL Mastery', 2),
('Relatos de naves espaciales', 'Star Wars: A New Hope', 4);

-- Insertar Inventario
INSERT INTO `inventary` (`number_avalable`, `book_id`) VALUES 
(5, 1), -- 5 copias de Java Head First
(2, 2), -- 2 copias de Spring in Action
(10, 3), -- 10 copias de SQL Mastery
(1, 4); -- 1 copia de Star Wars

-- Insertar Préstamos (Book Loans)
INSERT INTO `book_loan` (`student_id`, `book_id`, `is_returned`, `commentaries`) VALUES 
(1, 1, 0, 'El estudiante prometió no usar el lado oscuro'),
(2, 4, 1, 'Devuelto a tiempo para el entrenamiento Jedi');