--------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           10.1.35-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win32
-- HeidiSQL Versão:              11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Copiando estrutura do banco de dados para crud_manager
CREATE DATABASE IF NOT EXISTS `crud_manager` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `crud_manager`;

-- Copiando estrutura para tabela crud_manager.ceo
CREATE TABLE IF NOT EXISTS `ceos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cpf` varchar(11) NOT NULL,
  `rg` varchar(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `ceo_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `ceo_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `companies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `role` varchar(128) NOT NULL,
  `start` date NOT NULL,
  `end` date DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `companies_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Copiando estrutura para tabela crud_manager.posts
CREATE TABLE IF NOT EXISTS `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `post_date` date NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  `sexo` enum('M','F') DEFAULT NULL,
  `email` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

INSERT IGNORE INTO `ceos` (`id`, `cpf`, `rg`, `user_id`, `company_id`) VALUES
	(1, '12345678900', 'RG123', 1, 1),
	(2, '98765432100', 'RG456', 5, 2),
	(3, '11122233300', 'RG789', 4, 3);

INSERT IGNORE INTO `companies` (`id`, `name`, `role`, `start`, `end`, `user_id`) VALUES
	(1, 'Empresa A', 'CEO da Empresa A', '2023-06-07', NULL, 1),
	(2, 'Empresa B', 'CEO da Empresa B', '2023-06-07', NULL, 2),
	(3, 'Empresa C', 'CEO da Empresa C', '2023-06-07', NULL, 3);

INSERT IGNORE INTO `posts` (`id`, `content`, `post_date`, `user_id`) VALUES
	(1, 'Olá a todos! Sou o usuário Emerson e estou compartilhando minha primeira postagem.', '2023-06-07', 1),
	(2, 'Oi pessoal! Aqui está a postagem da usuária Luiza. Espero que gostem!', '2023-06-07', 2),
	(3, 'Bom dia! Venho trazer algumas novidades como a usuária Elenice.', '2023-06-07', 3),
	(4, 'Olá, pessoal! Sou o usuário Noé e estou animado para participar desta comunidade.', '2023-06-07', 4),
	(5, 'Oi galera! A usuária Rosânia está aqui para compartilhar suas experiências.', '2023-06-07', 5),
	(6, 'E aí, pessoal! Mais uma postagem da usuária Rosânia para vocês.', '2023-06-07', 5),
	(7, 'Olá de novo! Aqui está mais uma postagem da usuária Rosânia. Espero que gostem!', '2023-06-07', 5),
	(8, 'Boa noite a todos! A usuária Rosânia está aqui para desejar uma ótima noite.', '2023-06-07', 5);

INSERT IGNORE INTO `users` (`id`, `nome`, `sexo`, `email`) VALUES
	(1, 'Emerson Carvalho', 'M', 'emerson@mail.com'),
	(2, 'Luiza Carvalho', 'F', 'lu@mail.com'),
	(3, 'Elenice Carvalho', 'F', 'le@mail.com'),
	(4, 'Noé Carvalho', 'M', 'noe@mail.com'),
	(5, 'Rosânia Carvalho', 'F', 'ro@mail.com');


/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
