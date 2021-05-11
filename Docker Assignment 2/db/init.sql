use netflix;
DROP TABLE IF EXISTS `netflix_shows`;
CREATE TABLE `netflix_shows` (
  `show_id` varchar(255) NOT NULL,
  `cast` longtext,
  `country` longtext,
  `date_added` datetime(6) DEFAULT NULL,
  `description` longtext,
  `director` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `listed_in` longtext,
  `rating` varchar(255) DEFAULT NULL,
  `release_year` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`show_id`)
) ENGINE=InnoDB;
