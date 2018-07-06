--
-- Dumping data for table `author`
--

INSERT IGNORE INTO `author` (`id`, `firstname`, `lastname`, `sex`, `birthdate`) VALUES
(1, 'Cay', 'S. Hostmann', 'MALE', '1959-06-16'),
(2, 'Gary', 'Cornell', 'MALE', '1961-09-25'),
(8, 'Test', 'TestTest', 'MALE', '1990-07-26'),
(9, 'TestName', 'TestLastName', 'FEMALE', '1993-07-05');

-- --------------------------------------------------------
--
-- Dumping data for table `book`
--

INSERT IGNORE INTO `book` (`id`, `title`, `isbn`, `genre`) VALUES
(1, 'Scala for the impatient', '978-5-94074-920-2', 'PROGRAMMING'),
(2, 'Core Java for the impatient', '123-456-789', 'PROGRAMMING'),
(3, 'Java. Library professional', '978-5-94074-920-3', 'PROGRAMMING'),
(4, 'Java. Library professional. Volume 2', '978-5-94074-920-4', 'PROGRAMMING');

-- --------------------------------------------------------

--
-- Dumping data for table `book_authors`
--

INSERT IGNORE INTO `book_authors` (`book_id`, `authors_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(3, 2),
(4, 2),
(3, 9),
(4, 9);


--
-- Dumping data for table `reward`
--

INSERT IGNORE INTO `reward` (`id`, `year`, `title`, `id_author`) VALUES
(31, 2016, 'America Award', 2),
(32, 2010, 'Best Author', 1),
(33, 2012, 'Test reward', 8);
