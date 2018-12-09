-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 30, 2018 at 09:27 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `brickmanagment`
--

-- --------------------------------------------------------

--
-- Table structure for table `bricks`
--

CREATE TABLE IF NOT EXISTS `bricks` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `klinid` int(11) NOT NULL,
  `bricks_qty` int(11) NOT NULL,
  `date` varchar(200) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE IF NOT EXISTS `employee` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(200) NOT NULL,
  `emp_age` varchar(200) NOT NULL,
  `emp_cell` varchar(200) NOT NULL,
  `emp_addr` varchar(200) NOT NULL,
  `klinid` int(11) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `klin`
--

CREATE TABLE IF NOT EXISTS `klin` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `klin_name` varchar(200) NOT NULL,
  `userId` int(11) NOT NULL,
  `klin_addr` varchar(200) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `purchaseorder`
--

CREATE TABLE IF NOT EXISTS `purchaseorder` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `raw_mat_id` int(11) NOT NULL,
  `p_date` varchar(200) NOT NULL,
  `p_price` varchar(200) NOT NULL,
  `p_p_qty` int(11) NOT NULL,
  `p_old_qty` int(11) NOT NULL,
  `p_current_qty` int(11) NOT NULL,
  `klinid` int(11) NOT NULL,
  `p_by` int(11) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `rawmaterial`
--

CREATE TABLE IF NOT EXISTS `rawmaterial` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `mat_name` varchar(200) NOT NULL,
  `mat_type` varchar(200) NOT NULL,
  `klinid` int(11) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `salesorder`
--

CREATE TABLE IF NOT EXISTS `salesorder` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `s_brick_qty` int(11) NOT NULL,
  `s_price` int(11) NOT NULL,
  `s_date` varchar(200) NOT NULL,
  `s_by` varchar(200) NOT NULL,
  `s_to` varchar(200) NOT NULL,
  `klinid` int(11) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(200) NOT NULL,
  `user_mobile` varchar(200) NOT NULL,
  `user_pass` varchar(200) NOT NULL,
  `user_type` varchar(200) NOT NULL,
  `user_valid` int(11) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Id`, `username`, `user_mobile`, `user_pass`, `user_type`, `user_valid`) VALUES
(1, 'alimirza00', '03224379402', 'abc', 'user', 1);

-- --------------------------------------------------------

--
-- Table structure for table `weather_rep`
--

CREATE TABLE IF NOT EXISTS `weather_rep` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `w_value` int(11) NOT NULL,
  `w_degree` varchar(200) NOT NULL,
  `w_date` varchar(200) NOT NULL,
  `w_day` varchar(200) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
