-- MySQL dump 10.9
--
-- Host: localhost    Database: lintrasearch
-- ------------------------------------------------------
-- Server version	4.1.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dokumente`
--

DROP TABLE IF EXISTS `dokumente`;
CREATE TABLE `dokumente` (
  `docID` bigint(20) NOT NULL auto_increment,
  `dateiname` varchar(255) NOT NULL default '',
  `vonIP` varchar(15) default NULL,
  `vonBenutzer` varchar(100) NOT NULL default '',
  `inhaltText` longtext NOT NULL,
  `inhaltBinaer` longtext,
  `contentType` varchar(255) NOT NULL default '',
  `pfad` int(11) default NULL,
  PRIMARY KEY  (`docID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Table structure for table `pfade`
--

DROP TABLE IF EXISTS `pfade`;
CREATE TABLE `pfade` (
  `pid` int(11) NOT NULL auto_increment,
  `pfad` text,
  PRIMARY KEY  (`pid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

