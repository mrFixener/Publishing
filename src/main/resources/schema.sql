-- Server version: 5.0.51

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `publishing`
--

-- --------------------------------------------------------

--
-- Table structure for table `author`
--
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `author` (
  `id` bigint(20) NOT NULL auto_increment,
  `firstname` varchar(128) NOT NULL,
  `lastname` varchar(128) NOT NULL,
  `sex` varchar(6) NOT NULL,
  `birthdate` date NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Table structure for table `book`
--

CREATE TABLE IF NOT EXISTS `book` (
  `id` bigint(20) NOT NULL auto_increment,
  `title` varchar(256) NOT NULL,
  `isbn` varchar(128) NOT NULL,
  `genre` varchar(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Table structure for table `book_authors`
--

CREATE TABLE IF NOT EXISTS `book_authors` (
  `book_id` bigint(20) NOT NULL,
  `authors_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`book_id`,`authors_id`),
  KEY `authors_id` (`authors_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `book_authors_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_authors_ibfk_2` FOREIGN KEY (`authors_id`) REFERENCES `author` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Table structure for table `reward`
--

CREATE TABLE IF NOT EXISTS `reward` (
  `id` bigint(20) NOT NULL auto_increment,
  `year` int(11) NOT NULL,
  `title` varchar(256) NOT NULL,
  `id_author` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `id_author` (`id_author`),
  CONSTRAINT `reward_ibfk_1` FOREIGN KEY (`id_author`) REFERENCES `author` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=34 ;

SET FOREIGN_KEY_CHECKS = 1;