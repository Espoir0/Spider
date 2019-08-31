CREATE TABLE `user` (
  `name` varchar(30) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `location` varchar(30) DEFAULT NULL,
  `bussiness` varchar(30) DEFAULT NULL,
  `employment` varchar(30) DEFAULT NULL,
  `postion` varchar(30) DEFAULT NULL,
  `education` varchar(30) DEFAULT NULL,
  `education_extra` varchar(30) DEFAULT NULL,
  `suppose` int(11) DEFAULT NULL,
  `thanks` int(11) DEFAULT NULL,
  `question` int(11) DEFAULT NULL,
  `answer` int(11) DEFAULT NULL,
  `article` int(11) DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
  `bewatched` int(11) DEFAULT NULL
) DEFAULT CHARSET=utf8;

CREATE TABLE `userurl` (
  `url` varchar(20) NOT NULL,
  PRIMARY KEY (`url`)
) DEFAULT CHARSET=utf8;