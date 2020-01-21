-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: db:3306
-- Generation Time: Jan 21, 2020 at 08:51 AM
-- Server version: 8.0.19
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rdsdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `dataobject`
--

CREATE TABLE `dataobject` (
  `id` int NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int DEFAULT NULL,
  `datacontent` varchar(255) DEFAULT NULL,
  `edited_by` varchar(255) DEFAULT NULL,
  `maxlifecyclelevel` int DEFAULT NULL,
  `validtill` date DEFAULT NULL,
  `datatype` int DEFAULT NULL,
  `lifecycle` int DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `dataobject`
--

INSERT INTO `dataobject` (`id`, `created_date`, `version`, `datacontent`, `edited_by`, `maxlifecyclelevel`, `validtill`, `datatype`, `lifecycle`) VALUES
(1, '2020-01-20', 0, 'Content', 'Roman', 3, '2020-01-01', 1, 1),
(2, '2020-01-20', 0, 'Content', 'Roman', 3, '2020-01-01', 1, 1),
(3, '2020-01-20', 0, 'Content', 'Roman', 3, '2020-01-09', 1, 1),
(4, '2020-01-20', 0, 'Content', 'Roman', 3, '2020-01-07', 2, 2),
(5, '2020-01-20', 0, 'Content', 'Roman', 3, '2020-01-08', 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `datatype`
--

CREATE TABLE `datatype` (
  `id` int NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `datatype`
--

INSERT INTO `datatype` (`id`, `created_date`, `version`, `name`) VALUES
(1, '2020-01-20', 0, 'Web'),
(2, '2020-01-17', 1, 'Desktop'),
(3, '2020-01-20', 0, 'Mobile');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(6),
(6),
(6);

-- --------------------------------------------------------

--
-- Table structure for table `lifecycle`
--

CREATE TABLE `lifecycle` (
  `id` int NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int DEFAULT NULL,
  `level` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `lifecycle`
--

INSERT INTO `lifecycle` (`id`, `created_date`, `version`, `level`, `name`) VALUES
(1, '2020-01-20', 1, 1, 'First'),
(2, '2020-01-17', 1, 2, 'Second'),
(3, '2020-01-01', 1, 3, 'Third');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dataobject`
--
ALTER TABLE `dataobject`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj0o3gwq2nmrvfmws6ak02s2gf` (`datatype`),
  ADD KEY `FKssmq4hc4ejd8gxfruuemqvu04` (`lifecycle`);

--
-- Indexes for table `datatype`
--
ALTER TABLE `datatype`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lifecycle`
--
ALTER TABLE `lifecycle`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
