-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 14, 2019 at 08:01 PM
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
-- Table structure for table `addlocker_request`
--

CREATE TABLE `addlocker_request` (
  `id` int(11) NOT NULL,
  `customer` int(11) NOT NULL,
  `request_date` date DEFAULT NULL,
  `approved_date` date DEFAULT NULL,
  `expiry_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `addlocker_request`
--

INSERT INTO `addlocker_request` (`id`, `customer`, `request_date`, `approved_date`, `expiry_date`) VALUES
(8, 1, '2019-03-14', '2019-03-14', '2020-03-14'),
(9, 3, '2019-03-14', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nature` varchar(255) NOT NULL,
  `balance` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `nature`, `balance`) VALUES
(1, 'Deepak', 'Deepak', 'customer', 15000),
(2, 'banker', 'banker', 'banker', 0),
(3, 'ishant', 'ishant', 'customer', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `addlocker_request`
--
ALTER TABLE `addlocker_request`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `customer_2` (`customer`),
  ADD KEY `customer` (`customer`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `addlocker_request`
--
ALTER TABLE `addlocker_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `addlocker_request`
--
ALTER TABLE `addlocker_request`
  ADD CONSTRAINT `addlocker_request_ibfk_1` FOREIGN KEY (`customer`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
