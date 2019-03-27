-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 26, 2019 at 05:31 PM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `miniproject`
--

-- --------------------------------------------------------

--
-- Table structure for table `access_locker`
--

CREATE TABLE `access_locker` (
  `id` int(11) NOT NULL,
  `locker_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `permission` int(1) NOT NULL,
  `charges` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `addlocker_request`
--

CREATE TABLE `addlocker_request` (
  `id` int(11) NOT NULL,
  `customer` int(11) NOT NULL,
  `request_date` date DEFAULT NULL,
  `approved_date` date DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `cancelled_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `addlocker_request`
--

INSERT INTO `addlocker_request` (`id`, `customer`, `request_date`, `approved_date`, `expiry_date`, `cancelled_date`) VALUES
(1, 152901, '2019-03-24', '2019-03-24', '2020-03-24', NULL),
(2, 152903, '2019-03-24', '2019-03-24', '2020-03-24', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `locker`
--

CREATE TABLE `locker` (
  `id` int(11) NOT NULL,
  `customer` int(11) DEFAULT NULL,
  `locker_key` int(15) NOT NULL,
  `available` varchar(20) NOT NULL DEFAULT 'yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `locker`
--

INSERT INTO `locker` (`id`, `customer`, `locker_key`, `available`) VALUES
(2, 152901, 10002, 'no'),
(14524, 152903, 10001, 'no'),
(14526, NULL, 10003, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(30) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nature` varchar(255) NOT NULL,
  `balance` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact` varchar(18) NOT NULL,
  `address` text NOT NULL,
  `accounttype` varchar(255) NOT NULL,
  `is_active` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `nature`, `balance`, `name`, `contact`, `address`, `accounttype`, `is_active`) VALUES
(152900, '152900', 'Deepak@123', 'customer', 13999, 'Deepak', '8377083727', '923', 'Saving', 1),
(152901, '152901', 'Kaju@123', 'customer', 50000, 'Kajal', '8377083727', 'South Extension', 'Saving', 1),
(152902, 'banker', 'banker', 'banker', 0, '', '', '', '', 0),
(152903, '152903', 'Shakir@123', 'customer', 11730, 'Shakir', '7501452484', 'Batla House', 'Saving', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `access_locker`
--
ALTER TABLE `access_locker`
  ADD PRIMARY KEY (`id`),
  ADD KEY `locker_id_2` (`locker_id`);

--
-- Indexes for table `addlocker_request`
--
ALTER TABLE `addlocker_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_3` (`customer`);

--
-- Indexes for table `locker`
--
ALTER TABLE `locker`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `customer` (`customer`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `access_locker`
--
ALTER TABLE `access_locker`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `addlocker_request`
--
ALTER TABLE `addlocker_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `locker`
--
ALTER TABLE `locker`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14527;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=152904;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `access_locker`
--
ALTER TABLE `access_locker`
  ADD CONSTRAINT `access_locker_ibfk_1` FOREIGN KEY (`locker_id`) REFERENCES `locker` (`id`);

--
-- Constraints for table `addlocker_request`
--
ALTER TABLE `addlocker_request`
  ADD CONSTRAINT `addlocker_request_ibfk_1` FOREIGN KEY (`customer`) REFERENCES `users` (`id`);

--
-- Constraints for table `locker`
--
ALTER TABLE `locker`
  ADD CONSTRAINT `locker_ibfk_1` FOREIGN KEY (`customer`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
