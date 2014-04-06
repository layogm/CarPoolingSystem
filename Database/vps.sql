-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 30, 2013 at 09:10 PM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `vps`
--

-- --------------------------------------------------------

--
-- Table structure for table `source_details`
--

CREATE TABLE IF NOT EXISTS `source_details` (
  `location` varchar(40) NOT NULL,
  `distance` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `source_details`
--

INSERT INTO `source_details` (`location`, `distance`) VALUES
('27 Blk Trilokpuri', 31.4),
('Adarsh Nagar', 7.8),
('Ahata Kedara', 16.1),
('AIIMS', 29.3),
('Alipur', 11),
('Amar Colony', 35),
('Ambedkar Nagar', 38.9),
('Anand Parbat', 17.5),
('Anand Vihar', 31.8),
('Anaz Mundi', 19.6),
('Andha Mugal', 10.6),
('Ashok Nagar', 17.9),
('Ashok Vihar', 10.9),
('Auchandi Border', 14.9),
('Azadpur', 9.3),
('Badarpur', 41.6),
('Balli maran', 22.1),
('Bapa Nagar', 28.8),
('Bara Hindu Rao', 15.2),
('Bawana', 11.5),
('Bela Road', 21.9),
('Bhajan Pura', 17.7),
('Bharat Nagar', 13.5),
('Boat Club', 29.1),
('Chanakya Puri', 27.6),
('Chandini Mahal', 23),
('Chandni Chowk', 21.1),
('Chitranjan Park', 35.1),
('Church Mission', 20.9),
('Civil Lines', 15.5),
('Connaught Place', 19),
('Dabri', 22.3),
('Darya Ganj', 22.2),
('DBG Road', 23.1),
('Defence Colony', 32.9),
('Delhi Cantt.', 21.5),
('Dhaula Kuan', 23.4),
('Dwarka', 21.5),
('East Kidwai Nagar', 30),
('G.T.B. Nagar', 11.4),
('Gamri', 17.7),
('Gandhi Nagar', 24.9),
('Garthi', 11.5),
('Gazipur', 31.3),
('Gita Colony', 25.2),
('Gokul Puri', 19.4),
('Gole Market', 18.6),
('Govt. Qtr. Dev Nagar', 16.6),
('Greater Kailash', 35.8),
('Gul Mohar Park', 30.6),
('Gulabi Bagh', 14.4),
('Gurgaon', 47),
('Hari Nagar', 6.7),
('Harsh Vihar, Mandoli', 24.5),
('Hauz Khaz', 31.6),
('Hauz Quazi', 21.6),
('Hz. Nizammudin', 30.4),
('I.P. Estate', 28),
('Inder Lok', 12.2),
('Inderpuri', 19.5),
('Jaffer pur', 38.5),
('Jahangirpuri', 7),
('Jama Masjid', 20.8),
('Janakpuri', 17.9),
('Jangpura', 32.1),
('Jheel', 25.6),
('Kaka Nagar', 29.5),
('Kalkaji', 37.6),
('Kalyanpuri', 32.1),
('Kamla Market', 17.3),
('Kanjhawala', 15.9),
('Kapashera', 36.2),
('Karawal Nagar', 21),
('Karkardooma', 28.8),
('Karol Bagh', 18.1),
('Kashmere Gate', 20.1),
('Keshav Puram', 10.2),
('Khanpur', 40.6),
('Khayala', 13.3),
('Khazuri Khas', 17.5),
('Kingsway Camp', 11.4),
('Kirti Nagar', 15),
('Kotwali', 35.5),
('Krishna Nagar', 26.8),
('Lahori Gate', 20.1),
('Lajpat Nagar', 34),
('Lal kaun', 16.6),
('Laxmi Nagar', 26),
('LNJP Hospita', 24.2),
('Lodhi Colony', 31.9),
('Madangir', 39.3),
('Madipur', 11.9),
('Majnu Ka Tila', 15.7),
('Malcha Marg', 25.9),
('Malviya Nagar', 33.3),
('Mandawali Fazad', 12.2),
('Mandi House', 26.6),
('Mangolpuri', 9),
('Mansarover Garden', 17.5),
('Mansarover Park', 26.5),
('Matiyala', 19.5),
('Maurice Nagar', 13.1),
('Mayapuri', 18.3),
('Mayur Vihar I & II', 31.3),
('Meera Bagh', 11.8),
('Mehrauli', 35.5),
('Miawali Nagar', 11.2),
('MIG Flats', 18.7),
('Model Town', 10.8),
('Mori Gate', 20),
('Moti Nagar', 13.9),
('Mukherjee Nagar', 13),
('Nabi Karim', 22.7),
('Nai Sarak', 21.3),
('Najaf garh', 31.5),
('Nanak Pura', 25),
('Nand Nagri', 22.6),
('Nangloi', 12.2),
('Naraina', 18.8),
('Narela', 16.5),
('Nehru Place', 36.5),
('New Ashok Nagar', 32.3),
('New Friends Colony', 32.8),
('New Shahdara', 18.2),
('North Avenue', 21.3),
('Okhla', 36.9),
('Old Sheelampur', 22.9),
('Pahar Ganj', 18),
('Palam Colony', 27.9),
('Palam Village', 22.1),
('Panchasheel', 32.6),
('Panckuian Road', 17.6),
('Parliament Street', 19.3),
('Paschim Vihar', 12.2),
('Patel Nagar', 16.9),
('Patpargang', 28.2),
('Pitampura', 7.1),
('Pragati Vihar', 31.2),
('Prasad Nagar', 18.3),
('Prashant Vihar', 4.9),
('Preet Vihar', 29.2),
('Punjabi Bagh', 13.6),
('Pusa Road', 18.3),
('Pushp Vihar', 39.2),
('R.K. Puram', 27.7),
('R.M.L Hospital', 21.1),
('Rabindra Nagar', 30.6),
('Raghu Nagar', 22.2),
('Raghubir Nagar', 16.4),
('Raja Garden', 15.3),
('Rajender Nagar', 18.3),
('Rajouri Garden', 16.4),
('Rana Pratap Bagh', 11.5),
('Rani Bagh', 8.9),
('Red Fort', 20.4),
('Rohini', 3.5),
('Roop Nagar', 13.3),
('Sadar Bazar', 15.5),
('Sainik Farm', 38.4),
('Saket', 40.7),
('Samalakha', 34.2),
('Sameypur-Bodli', 4),
('Sangam Park', 11.3),
('Sangam Vihar', 41.6),
('Sangtrashan', 17.2),
('Sant Ngar', 11.4),
('Sarai Kale Khan', 31.6),
('Sarai Rohilla', 16.8),
('Saraswati Vihar', 8.1),
('Sarita Vihar', 38.8),
('Seelampur', 22.9),
('Seema Puri', 25.2),
('Shahdra', 20.7),
('Shahganj', 16.9),
('Shakarpur', 27.9),
('Shakti Nagar', 13.1),
('Shalimar Bagh', 7.2),
('Shidipura', 15.5),
('Sita Ram Bazar', 23.9),
('South Avenue', 24),
('Subzi mandi', 14.7),
('Sucheeta Kriplani Hospital', 18.4),
('Sukhdev Vihar', 35.8),
('Sultanpuri', 11.2),
('Sunder Nagri', 23.5),
('Sunlight Colony', 31.7),
('Tikri Border', 24.5),
('Tilak Nagar', 14.5),
('Tis Hazari', 19.8),
('Town Hall', 20.7),
('Trilokpuri', 31.4),
('Tuglak Road', 25.4),
('Turkman Gate', 23.9),
('Uttam Nagar', 16.6),
('Vasant Kunj', 34),
('Vasant Vihar', 28.2),
('Vijay Nagar', 12.3),
('Vikas Puri', 15.5),
('Vinay Nagar', 28.4),
('Vivek Vihar', 28.3),
('Wazirpur', 10.6),
('Welcome Colony', 24.7),
('West Kidwai Nagar', 29.4),
('Yamuna Bazar', 24.9),
('Yamuna Vihar', 19);

-- --------------------------------------------------------

--
-- Table structure for table `travellers`
--

CREATE TABLE IF NOT EXISTS `travellers` (
  `tID` varchar(15) NOT NULL,
  `source` varchar(50) NOT NULL,
  `time` time NOT NULL,
  `vehicleOwnerID` varchar(15) NOT NULL DEFAULT 'No',
  `moneyShare` int(5) NOT NULL DEFAULT '0',
  `vehicleNumber` varchar(15) NOT NULL,
  PRIMARY KEY (`tID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `travellers`
--

INSERT INTO `travellers` (`tID`, `source`, `time`, `vehicleOwnerID`, `moneyShare`, `vehicleNumber`) VALUES
('02', 'Ahata Kedara', '09:00:00', 'No', 0, ''),
('03', 'Alipur', '08:00:00', 'No', 0, ''),
('05', 'Ambedkar Nagar', '11:00:00', 'No', 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `uID` varchar(15) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `category` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `genderPreference` varchar(50) NOT NULL,
  `phone` bigint(15) NOT NULL,
  `password` varchar(40) NOT NULL,
  `source` varchar(30) NOT NULL,
  `destination` varchar(30) NOT NULL,
  `SecurityQ` varchar(100) NOT NULL,
  `SecurityA` varchar(50) NOT NULL,
  `vehicle_no` varchar(100) NOT NULL,
  `vehicle_mileage` varchar(50) NOT NULL,
  PRIMARY KEY (`uID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uID`, `name`, `gender`, `category`, `email`, `genderPreference`, `phone`, `password`, `source`, `destination`, `SecurityQ`, `SecurityA`, `vehicle_no`, `vehicle_mileage`) VALUES
('01', 'hello', 'female', 'taveller', 'drufznkj', 'kk', 123, 'password', 'dsource', 'ddest', 'securityq', 'securitya', '', ''),
('02', 'abc', 'female', '', '', 'all', 0, '', '', '', '', '', '', ''),
('03', 'abc2', 'male', '', '', '', 0, '', '', '', '', '', '', ''),
('09', 'abc3', 'male', '', '', '', 0, '', '', '', '', '', '', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
