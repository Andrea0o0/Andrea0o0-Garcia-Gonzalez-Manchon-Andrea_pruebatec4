-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 22, 2024 at 10:54 AM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `be_travel`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active_flag` bit(1) DEFAULT NULL,
  `client_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhs7eej4m2orrmr5cfbcrqs8yw` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `active_flag`, `client_id`) VALUES
(1, b'1', NULL),
(2, b'1', NULL),
(3, b'1', NULL),
(4, b'1', NULL),
(5, b'1', NULL),
(6, b'1', NULL),
(7, b'1', NULL),
(8, b'1', NULL),
(10, b'1', NULL),
(11, b'1', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `username` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS `flight`;
CREATE TABLE IF NOT EXISTS `flight` (
  `flight_number` varchar(255) NOT NULL,
  `active_flag` bit(1) DEFAULT NULL,
  `arrival_date` datetime(6) DEFAULT NULL,
  `columns_seat` int DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `reserved_seats` int DEFAULT NULL,
  `rows_seat` int DEFAULT NULL,
  `seat_price` double DEFAULT NULL,
  `seat_type` varchar(255) DEFAULT NULL,
  `seats_number` int DEFAULT NULL,
  `takeoff_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`flight_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `flight`
--

INSERT INTO `flight` (`flight_number`, `active_flag`, `arrival_date`, `columns_seat`, `destination`, `origin`, `price`, `reserved_seats`, `rows_seat`, `seat_price`, `seat_type`, `seats_number`, `takeoff_date`) VALUES
('AIRWAVE226', b'1', '2024-09-12 19:45:00.000000', 6, 'Miami', 'BCN', 450, 0, 36, 15, 'Business', 216, '2024-09-12 12:15:00.000000'),
('AIRWAVE227', b'1', '2024-09-14 19:45:00.000000', 10, 'BCN', 'Madrid', 45, 0, 30, 18, 'Business', 300, '2024-09-14 12:15:00.000000'),
('AIRWAVE228', b'1', '2024-09-12 19:45:00.000000', 6, 'Madrid', 'BCN', 50, 0, 26, 8, 'Business', 156, '2024-09-12 12:15:00.000000'),
('AIRWAVE229', b'1', '2024-09-14 19:45:00.000000', 6, 'BCN', 'Paris', 450, 0, 36, 15, 'First Class', 216, '2024-09-14 12:15:00.000000'),
('AIRWAVE230', b'1', '2024-09-12 19:45:00.000000', 6, 'Paris', 'BCN', 400, 0, 17, 9.5, 'First Class', 102, '2024-09-12 12:15:00.000000'),
('AIRWAVE231', b'1', '2024-09-13 19:45:00.000000', 6, 'Zurich', 'BCN', 100, 0, 26, 8, 'First Class', 156, '2024-09-11 12:15:00.000000'),
('AIRWAVE232', b'1', '2024-09-13 19:45:00.000000', 6, 'Zurich', 'BCN', 100, 0, 36, 15, 'Business', 216, '2024-09-11 12:15:00.000000'),
('AIRWAVE233', b'1', '2024-09-13 19:45:00.000000', 6, 'BCN', 'Zurich', 100, 0, 17, 9.5, 'Business', 102, '2024-09-13 12:15:00.000000'),
('AIRWAVE234', b'1', '2024-09-11 19:45:00.000000', 6, 'BCN', 'Madrid', 1600, 0, 26, 8, 'First Class', 156, '2024-09-11 12:15:00.000000'),
('AIRWAVE235', b'1', '2024-09-11 19:45:00.000000', 6, 'Madrid', 'BCN', 1600, 0, 36, 15, 'First Class', 216, '2024-09-09 12:15:00.000000'),
('AIRWAVE236', b'1', '2024-09-11 19:45:00.000000', 6, 'Miami', 'BCN', 160, 0, 26, 8, 'First Class', 156, '2024-09-11 12:15:00.000000'),
('AIRWAVE237', b'1', '2024-09-11 19:45:00.000000', 10, 'BCN', 'Miami', 160, 0, 30, 18, 'First Class', 300, '2024-09-09 12:15:00.000000'),
('AIRWAVE238', b'1', '2024-09-14 19:45:00.000000', 6, 'Los Angeles', 'Zurich', 160, 0, 36, 15, 'First Class', 216, '2024-09-13 12:15:00.000000'),
('AIRWAVE239', b'1', '2024-09-14 19:45:00.000000', 6, 'Los Angeles', 'BCN', 1670, 0, 34, 8.3, 'First Class', 204, '2024-09-13 12:15:00.000000'),
('AIRWAVE240', b'1', '2024-09-14 19:45:00.000000', 6, 'Tokyo', 'Los Angeles', 1670, 0, 26, 8, 'First Class', 156, '2024-09-13 12:15:00.000000'),
('AIRWAVE241', b'1', '2024-09-14 19:45:00.000000', 6, 'Tokyo', 'Los Angeles', 1670, 0, 36, 15, 'Business', 216, '2024-08-13 12:15:00.000000'),
('AIRWAVE242', b'1', '2024-09-14 19:45:00.000000', 6, 'Tokyo', 'Los Angeles', 1670, 0, 26, 8, 'Business', 156, '2024-07-13 12:15:00.000000'),
('AIRWAVE243', b'1', '2024-09-11 19:45:00.000000', 6, 'BCN', 'Los Angeles', 100, 0, 36, 15, 'Business', 216, '2024-09-11 12:15:00.000000'),
('AIRWAVE244', b'1', '2024-07-14 19:45:00.000000', 6, 'Tokyo', 'Los Angeles', 1670, 0, 17, 9.5, 'Economy', 102, '2024-07-13 12:15:00.000000'),
('AIRWAVE245', b'1', '2024-07-14 19:45:00.000000', 6, 'Tokyo', 'Los Angeles', 150, 0, 17, 9.5, 'Economy', 102, '2024-07-13 12:15:00.000000'),
('AIRWAVE246', b'1', '2024-07-14 19:45:00.000000', 6, 'Tokyo', 'Los Angeles', 1150, 0, 17, 9.5, 'Economy', 102, '2024-07-13 12:15:00.000000'),
('AVIA1234', b'1', '2024-07-10 12:45:00.000000', 6, 'Paris', 'Madrid', 475, 0, 34, 8.3, 'Economy', 204, '2024-07-10 09:30:00.000000'),
('BABU5536', b'1', '2024-02-17 22:10:00.000000', 6, 'Buenos Aires', 'Barcelona', 750, 0, 17, 9.5, 'Economy', 102, '2024-10-02 11:15:00.000000'),
('BCNMIAMI', b'1', '2024-10-14 19:45:00.000000', 6, 'Miami', 'Bcn', 600, 0, 34, 8.3, 'Economy', 204, '2024-09-13 12:15:00.000000'),
('BLUESKY99', b'1', '2024-08-10 20:45:00.000000', 6, 'Paris', 'Amsterdam', 950, 0, 26, 8, 'Business', 156, '2024-08-10 19:00:00.000000'),
('BOBA6567', b'1', '2024-03-15 04:20:00.000000', 6, 'Buenos Aires', 'Bogota', 400, 0, 17, 9.5, 'Economy', 102, '2024-03-01 20:10:00.000000'),
('BOCA4213', b'1', '2024-02-06 10:20:00.000000', 6, 'Cartagena', 'Bogota', 800, 0, 17, 9.5, 'Economy', 102, '2024-01-23 16:00:00.000000'),
('BOMA4442', b'1', '2024-02-25 15:00:00.000000', 6, 'Madrid', 'Bogota', 1150, 0, 26, 8, 'Economy', 156, '2024-10-02 22:00:00.000000'),
('BUBA3369', b'1', '2024-02-23 03:30:00.000000', 10, 'Barcelona', 'Buenos Aires', 1250, 0, 30, 18, 'Business', 300, '2024-12-02 13:20:00.000000'),
('CAME0321', b'1', '2024-02-01 08:45:00.000000', 10, 'Medellin', 'Cartagena', 750, 0, 30, 18, 'Economy', 300, '2024-01-23 17:30:00.000000'),
('COASTAL76', b'1', '2024-08-11 23:00:00.000000', 6, 'Venice', 'Paris', 220, 0, 17, 9.5, 'Economy', 102, '2024-08-11 21:15:00.000000'),
('CRAZY6567', b'1', '2024-02-29 02:30:00.000000', 6, 'Iguazu', 'Bogota', 600, 0, 26, 8, 'Business', 156, '2024-02-15 18:45:00.000000'),
('DREAMFLY88', b'1', '2024-08-12 11:45:00.000000', 6, 'Prague', 'Venice', 1400, 0, 17, 9.5, 'Business', 102, '2024-08-12 08:30:00.000000'),
('FLYERS888', b'1', '2024-08-05 12:45:00.000000', 6, 'Rome', 'Paris', 1350, 0, 17, 9.5, 'Business', 102, '2024-08-05 09:15:00.000000'),
('FLYHIGH777', b'1', '2024-08-01 15:00:00.000000', 10, 'Singapore', 'Sydney', 875, 0, 30, 18, 'Economy', 300, '2024-08-01 09:30:00.000000'),
('FLYTOYOU22', b'1', '2024-08-08 17:00:00.000000', 6, 'Madrid', 'Barcelona', 180, 0, 26, 8, 'Economy', 156, '2024-08-08 15:30:00.000000'),
('JETSET4567', b'1', '2024-07-11 13:20:00.000000', 6, 'New York', 'London', 775, 0, 17, 9.5, 'Economy', 102, '2024-07-11 06:45:00.000000'),
('MEMI9986', b'1', '2024-05-02 19:10:00.000000', 4, 'Miami', 'Medellin', 1150, 0, 14, 12, 'Business', 56, '2024-04-17 07:20:00.000000'),
('NIGHTOWL11', b'1', '2024-08-13 12:15:00.000000', 6, 'Zurich', 'Prague', 180, 0, 17, 9.5, 'Economy', 102, '2024-08-13 10:00:00.000000'),
('RAPID321', b'1', '2024-08-09 18:30:00.000000', 6, 'Amsterdam', 'Madrid', 300, 0, 26, 8, 'Economy', 156, '2024-08-09 16:45:00.000000'),
('RAPID999', b'1', '2024-08-04 08:30:00.000000', 10, 'Paris', 'London', 250, 0, 30, 18, 'Economy', 300, '2024-08-04 06:00:00.000000'),
('ROCKET666', b'1', '2024-09-04 14:30:00.000000', 6, 'Tokyo', 'Singapore', 1600, 0, 36, 15, 'Business', 216, '2024-09-04 08:00:00.000000'),
('SKYHIGH567', b'1', '2024-07-12 10:45:00.000000', 6, 'Los Angeles', 'New York', 800, 0, 17, 9.5, 'Business', 102, '2024-07-12 08:00:00.000000'),
('SKYLINE789', b'1', '2024-07-12 12:30:00.000000', 4, 'Los Angeles', 'New York', 3250, 0, 14, 12, 'Business', 56, '2024-07-12 08:00:00.000000'),
('SPEEDY666', b'1', '2024-08-06 15:45:00.000000', 6, 'Berlin', 'Rome', 450, 0, 26, 8, 'Economy', 156, '2024-08-06 11:30:00.000000'),
('SUNNY456', b'1', '2024-08-03 20:15:00.000000', 6, 'London', 'Dubai', 675, 0, 26, 8, 'Economy', 156, '2024-08-03 14:45:00.000000'),
('SUNNYDAY10', b'1', '2024-05-04 14:45:00.000000', 6, 'Los Angeles', 'Miami', 950, 0, 36, 15, 'Business', 216, '2024-05-04 11:00:00.000000'),
('SUNRISE11', b'1', '2024-05-04 14:45:00.000000', 6, 'Los Angeles', 'Miami', 950, 0, 17, 9.5, 'Economy', 102, '2024-05-04 11:00:00.000000'),
('SUNSET333', b'1', '2024-07-15 08:30:00.000000', 6, 'Sydney', 'Tokyo', 4500, 0, 34, 8.3, 'Business', 204, '2024-07-14 22:00:00.000000'),
('SUNSET345', b'1', '2024-07-30 14:30:00.000000', 10, 'Sydney', 'Tokyo', 2000, 0, 30, 18, 'First Class', 300, '2024-07-30 07:15:00.000000'),
('SWIFT111', b'1', '2024-08-07 18:20:00.000000', 4, 'Barcelona', 'Berlin', 1800, 0, 14, 12, 'Business', 56, '2024-08-07 13:00:00.000000'),
('SWIFT123', b'1', '2024-08-02 18:30:00.000000', 4, 'Dubai', 'Singapore', 2750, 0, 14, 12, 'Business', 56, '2024-08-02 12:00:00.000000'),
('SWISSWING5', b'1', '2024-08-14 14:30:00.000000', 6, 'Vienna', 'Zurich', 1200, 0, 26, 8, 'Business', 156, '2024-08-14 12:45:00.000000'),
('THEY1235', b'1', '2024-02-15 17:30:00.000000', 6, 'Miami', 'Barcelona', 650, 0, 26, 8, 'Economy', 156, '2024-10-02 08:00:00.000000'),
('TIME3369', b'1', '2024-01-17 07:45:00.000000', 6, 'Barcelona', 'Iguazu', 550, 0, 26, 8, 'Economy', 156, '2024-01-02 14:45:00.000000'),
('TRAV9876', b'1', '2024-07-10 15:30:00.000000', 4, 'London', 'Paris', 1000, 0, 14, 12, 'Business', 56, '2024-07-10 14:00:00.000000'),
('WINGMAN88', b'1', '2024-07-13 13:45:00.000000', 6, 'New York', 'Los Angeles', 550, 0, 26, 8, 'Economy', 156, '2024-07-13 10:15:00.000000'),
('WORLDTRAVEL', b'1', '2024-07-10 13:30:00.000000', 6, 'London', 'New York', 1000, 0, 17, 9.5, 'Business', 102, '2024-07-10 11:30:00.000000'),
('ZIPPY654', b'1', '2024-08-11 15:00:00.000000', 6, 'Barcelona', 'Venice', 250, 0, 36, 15, 'Economy', 216, '2024-08-11 12:30:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `flight_booking`
--

DROP TABLE IF EXISTS `flight_booking`;
CREATE TABLE IF NOT EXISTS `flight_booking` (
  `booking_type` bit(1) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `people_number` int DEFAULT NULL,
  `seat_type` varchar(255) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `id` bigint NOT NULL,
  `flight_one_way_id` varchar(255) DEFAULT NULL,
  `flight_return_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjw5106usngquy3i3kuyjpeo30` (`flight_one_way_id`),
  KEY `FKl4am137xbvkcmqgcrt1t2ja6c` (`flight_return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `flight_booking`
--

INSERT INTO `flight_booking` (`booking_type`, `destination`, `origin`, `people_number`, `seat_type`, `total_price`, `id`, `flight_one_way_id`, `flight_return_id`) VALUES
(b'1', 'Paris', 'BCN', 2, 'First Class', 891, 7, 'AIRWAVE230', 'AIRWAVE229'),
(b'1', 'Paris', 'BCN', 2, 'First Class', 891, 8, 'AIRWAVE230', 'AIRWAVE229');

-- --------------------------------------------------------

--
-- Table structure for table `guest`
--

DROP TABLE IF EXISTS `guest`;
CREATE TABLE IF NOT EXISTS `guest` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `hotel_booking_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7sa7shf80gi6fipg043tox14s` (`hotel_booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `guest`
--

INSERT INTO `guest` (`id`, `email`, `last_name`, `name`, `hotel_booking_id`) VALUES
(1, 'andreagarciagonzalez22@gmail.com', 'García', 'Andrea', 11),
(2, NULL, NULL, NULL, 11),
(3, NULL, NULL, NULL, 11),
(4, NULL, NULL, NULL, 11),
(5, NULL, NULL, NULL, 11),
(6, NULL, NULL, NULL, 11);

-- --------------------------------------------------------

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
CREATE TABLE IF NOT EXISTS `hotel` (
  `hotel_code` varchar(255) NOT NULL,
  `active_flag` bit(1) DEFAULT NULL,
  `check_in` varchar(255) DEFAULT NULL,
  `check_out` varchar(255) DEFAULT NULL,
  `description` text,
  `name` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`hotel_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `hotel`
--

INSERT INTO `hotel` (`hotel_code`, `active_flag`, `check_in`, `check_out`, `description`, `name`, `place`) VALUES
('Andrea-TEST', b'1', '3:00 PM - 6:00 PM / 15:00 - 18:00', '8:00 AM - 11:00 AM / 08:00 - 11:00', 'Enjoy a luxurious stay at Oceanfront Miami Resort, offering stunning views of the Atlantic Ocean. Our double rooms feature elegant decor and modern amenities for a comfortable retreat. Indulge in gourmet dining at our seaside restaurant or relax by the pool with a refreshing cocktail. Conveniently located near Miami\'s top attractions, our resort is the perfect choice for your next getaway.', 'Andrea Hotel', 'Miami'),
('AR-0002', b'1', '3:00 PM - 6:00 PM / 15:00 - 18:00', '8:00 AM - 11:00 AM / 08:00 - 11:00', 'Enjoy a luxurious stay at Oceanfront Miami Resort, offering stunning views of the Atlantic Ocean. Our double rooms feature elegant decor and modern amenities for a comfortable retreat. Indulge in gourmet dining at our seaside restaurant or relax by the pool with a refreshing cocktail. Conveniently located near Miami\'s top attractions, our resort is the perfect choice for your next getaway.', 'Oceanfront Miami Resort', 'Miami'),
('AR-0003', b'1', '2:00 PM - 5:00 PM / 14:00 - 17:00', '10:00 AM - 1:00 PM / 10:00 - 13:00', 'Discover the beauty of Miami at Miami Bayfront Hotel, nestled along the picturesque Biscayne Bay. Our triple rooms offer spacious accommodations and modern amenities for a relaxing stay. Indulge in delectable cuisine at our waterfront restaurant or unwind with a massage at the spa. With easy access to Miami\'s beaches and nightlife, our hotel provides the perfect blend of luxury and convenience.', 'Miami Bayfront Hotel', 'Miami'),
('AR-0004', b'1', '2:00 PM - 5:00 PM / 14:00 - 17:00', '10:00 AM - 1:00 PM / 10:00 - 13:00', 'Experience luxury and relaxation at Zurich Classic Luxury, situated in the heart of Zurich. Our single rooms offer modern amenities and sophisticated decor for a comfortable stay. Indulge in gourmet dining at our upscale restaurant or unwind with a cocktail at the rooftop bar. With panoramic views of the city and attentive service, our hotel provides the perfect escape for your next vacation.', 'Baur Lac Hotel Zurich', 'Zurich'),
('AR-0005', b'1', '3:00 PM - 6:00 PM / 15:00 - 18:00', '8:00 AM - 11:00 AM / 08:00 - 11:00', 'Discover the vibrant energy of Geneva at Geneva Downtown Grand, located in the heart of the city\'s bustling downtown district. Our double rooms offer modern amenities and sleek design for a stylish stay. Indulge in gourmet cuisine at our rooftop restaurant or relax with a drink at the chic rooftop bar. With easy access to Geneva\'s cultural attractions and shopping districts, our hotel offers the perfect base for exploring the city.', 'Fairmont Grand Hotel', 'Geneva'),
('GH-0001', b'1', '4:00 PM - 7:00 PM / 16:00 - 19:00', '9:00 AM - 12:00 PM / 09:00 - 12:00', 'Discover modern luxury at Buenos Aires Grand Hyatt, situated in the heart of Buenos Aires. Our single rooms offer contemporary design and upscale amenities for a relaxing stay. Indulge in gourmet cuisine at our restaurant or unwind with a cocktail in the stylish lounge. With a central location and personalized service, our hotel provides the perfect base for exploring Buenos Aires.', 'Buenos Aires Grand Hyatt', 'Buenos Aires'),
('GH-0002', b'1', '3:00 PM - 6:00 PM / 15:00 - 18:00', '10:00 AM - 1:00 PM / 10:00 - 13:00', 'Experience luxury at its finest at Madrid Grand Hyatt, located in the heart of Madrid. Our double rooms offer elegant decor and modern amenities for a comfortable stay. Indulge in exquisite cuisine at our fine dining restaurant or unwind with a cocktail at the bar. With attentive service and a prime location, our hotel provides the perfect retreat in Madrid.', 'Madrid Grand Hyatt', 'Madrid'),
('GH-0003', b'1', '3:00 PM - 6:00 PM / 15:00 - 18:00', '8:00 AM - 11:00 AM / 08:00 - 11:00', 'Discover luxury and sophistication at Grand Hyatt Paris, located in the heart of the iconic City of Light. Our single rooms offer elegant furnishings and modern amenities for a comfortable stay. Indulge in gourmet cuisine at our Michelin-starred restaurant or relax with a glass of champagne at the bar. With easy access to Paris\'s landmarks and shopping districts, our hotel offers the perfect retreat for discerning travelers.', 'Grand Hyatt Paris', 'Paris'),
('HL-0001', b'1', '3:00 PM - 6:00 PM / 15:00 - 18:00', '8:00 AM - 11:00 AM / 08:00 - 11:00', 'Experience elegance and comfort at Barcelona Mandarin, ideally located in the prestigious Passeig de Gràcia. Our single rooms offer modern amenities and sophisticated decor for a relaxing stay. Indulge in culinary delights at our gourmet restaurant or unwind with panoramic views of the city at the rooftop bar. With a central location and impeccable service, our hotel offers the perfect blend of convenience and luxury in Barcelona.', 'Mandarin Oriental', 'Barcelona'),
('HL-0002', b'1', '2:00 PM - 5:00 PM / 14:00 - 17:00', '10:00 AM - 1:00 PM / 10:00 - 13:00', 'Discover modern luxury at Hilton Diagonal Mar Barcelona, situated along the beautiful Mediterranean coast. Our double rooms offer stunning sea views and upscale amenities for a memorable stay. Indulge in gourmet cuisine at our restaurant or relax by the infinity pool with a cocktail. With easy access to Barcelona\'s beaches and attractions, our hotel provides the perfect retreat for discerning travelers.', 'Hilton Diagonal Mar Barcelona', 'Barcelona'),
('HL-0003', b'1', '2:00 PM - 5:00 PM / 14:00 - 17:00', '10:00 AM - 1:00 PM / 10:00 - 13:00', 'Experience the charm of Rome at Hilton Rome, located near the historic city center. Our double rooms offer modern amenities and stylish decor for a comfortable stay. Indulge in Italian cuisine at our restaurant or unwind with a cocktail at the rooftop bar. With easy access to Rome\'s attractions and landmarks, our hotel provides the perfect base for exploring the Eternal City.', 'Hilton Rome', 'Rome'),
('IR-0004', b'1', '2:00 PM - 5:00 PM / 14:00 - 17:00', '10:00 AM - 1:00 PM / 10:00 - 13:00', 'Experience luxury and elegance at Cartagena InterContinental, located in the historic walled city of Cartagena. Our double rooms offer modern amenities and breathtaking views of the Caribbean Sea. Indulge in gourmet cuisine at our restaurant or unwind with a cocktail at the rooftop bar. With a central location and personalized service, our hotel offers the perfect retreat for discerning travelers.', 'Cartagena InterContinental', 'Cartagena'),
('MT-0003', b'1', '3:00 PM - 6:00 PM / 15:00 - 18:00', '8:00 AM - 11:00 AM / 08:00 - 11:00', 'Experience sophistication and luxury at Marriott Hotel Barcelona, located in the heart of the city. Our double rooms feature elegant furnishings and modern amenities for a comfortable stay. Indulge in gourmet dining at our restaurant or unwind with a massage at the spa. With a central location and personalized service, our hotel offers the perfect blend of relaxation and convenience in Barcelona.', 'Marriott Hotel Vela', 'Barcelona'),
('MT-0004', b'1', '4:00 PM - 7:00 PM / 16:00 - 19:00', '9:00 AM - 12:00 PM / 09:00 - 12:00', 'Experience luxury and elegance at Marriott Paris, situated in the heart of the French capital. Our suites offer spacious accommodations and breathtaking views of Paris for a memorable stay. Indulge in gourmet dining at our restaurant or relax with a massage at the spa. With attentive service and a prime location, our hotel offers an unforgettable experience in Paris.', 'Marriott Paris', 'Paris'),
('RC-0001', b'1', '4:00 PM - 7:00 PM / 16:00 - 19:00', '9:00 AM - 12:00 PM / 09:00 - 12:00', 'Experience unparalleled luxury at Grandeur Ritz-Carlton, located in the heart of Buenos Aires. Our single rooms are elegantly appointed with luxurious furnishings and modern amenities. Indulge in gourmet cuisine at our fine dining restaurant or relax with a cocktail in the sophisticated lounge. With attentive service and a prime location, our hotel offers a memorable stay in Buenos Aires.', 'Grandeur Ritz-Carlton', 'Buenos Aires'),
('RC-0002', b'1', '3:00 PM - 6:00 PM / 15:00 - 18:00', '8:00 AM - 11:00 AM / 08:00 - 11:00', 'Discover the epitome of luxury at Ritz-Carlton Medellin, nestled in the vibrant city of Medellin. Our double rooms feature contemporary design and upscale amenities for a lavish retreat. Indulge in culinary delights at our gourmet restaurant or unwind with a massage at the spa. With impeccable service and a central location, our hotel offers an unforgettable experience in Medellin.', 'Ritz-Carlton Medellin', 'Medellin'),
('RC-0003', b'1', '4:00 PM - 7:00 PM / 16:00 - 19:00', '9:00 AM - 12:00 PM / 09:00 - 12:00', 'Experience unparalleled luxury at Ritz-Carlton Santiago, nestled in the bustling capital of Chile. Our suites offer expansive living spaces and panoramic views of the city for a lavish retreat. Indulge in gourmet dining at our acclaimed restaurant or unwind with a massage at the spa. With attentive service and a central location, our hotel provides an unforgettable experience in Santiago.', 'Ritz-Carlton Santiago', 'Santiago'),
('SH-0002', b'1', '2:00 PM - 5:00 PM / 14:00 - 17:00', '10:00 AM - 1:00 PM / 10:00 - 13:00', 'Discover the awe-inspiring beauty of Iguazu Falls at Sheraton Iguazu, located in the heart of the national park. Our double rooms offer breathtaking views of the falls and modern amenities for a comfortable stay. Indulge in gourmet dining at our restaurant or relax by the pool surrounded by lush rainforest. With easy access to the falls and nature trails, our hotel provides the perfect retreat for nature lovers.', 'Iguazu Falls Sheraton', 'Iguazu'),
('SH-0004', b'1', '3:00 PM - 6:00 PM / 15:00 - 18:00', '8:00 AM - 11:00 AM / 08:00 - 11:00', 'Experience comfort and style at Sheraton Madrid, situated in the heart of the Spanish capital. Our multiple rooms offer spacious accommodations and modern amenities for groups and families. Indulge in delectable cuisine at our restaurant or unwind with a cocktail at the bar. With easy access to Madrid\'s attractions and shopping districts, our hotel provides the perfect base for exploring the city.', 'Sheraton Madrid', 'Madrid');

-- --------------------------------------------------------

--
-- Table structure for table `hotel_booking`
--

DROP TABLE IF EXISTS `hotel_booking`;
CREATE TABLE IF NOT EXISTS `hotel_booking` (
  `from_date` date DEFAULT NULL,
  `people_number` int DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `until_date` date DEFAULT NULL,
  `id` bigint NOT NULL,
  `room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmykcvwg0ef1tielowjpm8affm` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `hotel_booking`
--

INSERT INTO `hotel_booking` (`from_date`, `people_number`, `total_price`, `until_date`, `id`, `room_id`) VALUES
('2024-03-01', 1, 700, '2024-03-08', 11, 76);

-- --------------------------------------------------------

--
-- Table structure for table `luggage`
--

DROP TABLE IF EXISTS `luggage`;
CREATE TABLE IF NOT EXISTS `luggage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `invoiced` bit(1) DEFAULT NULL,
  `kg` double DEFAULT NULL,
  `luggage_type` varchar(255) DEFAULT NULL,
  `model` bit(1) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `passenger_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9cy6pmixgdxluao8vc6x6idnj` (`passenger_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `luggage`
--

INSERT INTO `luggage` (`id`, `description`, `invoiced`, `kg`, `luggage_type`, `model`, `price`, `passenger_id`) VALUES
(1, 'Checked suitcase weighing up to 35kg', b'1', 35, 'Checked suitcase weighing up to 35kg', b'1', 26, NULL),
(2, 'Checked suitcase weighing up to 25kg', b'1', 25, 'Checked Baggage', b'1', 14, NULL),
(3, 'Checked suitcase weighing up to 20kg', b'1', 20, 'Checked Baggage', b'1', 11, NULL),
(4, 'Checked suitcase weighing up to 15kg', b'1', 15, 'Checked Baggage', b'1', 10, NULL),
(5, 'Description: All our clients can carry 1 piece of hand luggage (max. 40x20x30 cm) in the cabin for free, as well as purchases made at the airport. Of course, everything must fit under the front seat', b'0', 10, 'Hand Luggage', b'1', 0, NULL),
(6, 'The suitcase in the cabin of max. 10 kg (55x40x20 cm.) travels with you and you must place it in the upper compartment', b'0', 10, 'Hand Luggage', b'1', 10, NULL),
(12, 'Checked suitcase weighing up to 35kg', b'1', 35, 'Checked Baggage', b'0', 26, 6),
(13, 'Checked suitcase weighing up to 35kg', b'1', 35, 'Checked Baggage', b'0', 26, 7);

-- --------------------------------------------------------

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
CREATE TABLE IF NOT EXISTS `passenger` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `identification` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `luggage_price` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `seats_price` double DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `flight_booking_id` bigint DEFAULT NULL,
  `seat_oneway` bigint DEFAULT NULL,
  `seat_return` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_huq6try86a9isn2kno8qe4ys7` (`seat_oneway`),
  UNIQUE KEY `UK_obf0v4wxjmckjxk0s4qexqff2` (`seat_return`),
  KEY `FKk4unw06btj21hqretcigqsyn2` (`flight_booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `passenger`
--

INSERT INTO `passenger` (`id`, `email`, `identification`, `last_name`, `luggage_price`, `name`, `seats_price`, `total_price`, `flight_booking_id`, `seat_oneway`, `seat_return`) VALUES
(6, 'andreagarcia22@gmail.com', '44937010R', 'Garcia', 26, 'Andrea', 15, 891, 7, 7, 8),
(7, 'andreagarcia22@gmail.com', '44937010R', 'Garcia', 26, 'Andrea', 15, 891, 8, 10, 11);

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active_flag` bit(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reserved_date`
--

DROP TABLE IF EXISTS `reserved_date`;
CREATE TABLE IF NOT EXISTS `reserved_date` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6813ngdhmpc1y2n10okhom06o` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `reserved_date`
--

INSERT INTO `reserved_date` (`id`, `date`, `room_id`) VALUES
(54, '2024-03-01', 82),
(55, '2024-03-02', 82),
(56, '2024-03-03', 82),
(57, '2024-03-04', 82),
(58, '2024-03-05', 82),
(59, '2024-03-06', 82),
(78, '2024-03-01', 76),
(79, '2024-03-02', 76),
(80, '2024-03-03', 76),
(81, '2024-03-04', 76),
(82, '2024-03-05', 76),
(83, '2024-03-06', 76),
(84, '2024-03-07', 76),
(85, '2024-03-08', 76),
(87, '2024-03-01', 11),
(88, '2024-03-02', 11),
(89, '2024-03-03', 11),
(90, '2024-03-04', 11),
(91, '2024-03-05', 11),
(92, '2024-03-06', 11),
(93, '2024-03-07', 11),
(94, '2024-03-08', 11);

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active_flag` bit(1) DEFAULT NULL,
  `description` text,
  `from_date` date DEFAULT NULL,
  `hotel_name` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `price_night` double DEFAULT NULL,
  `room_number` varchar(255) DEFAULT NULL,
  `room_type` int DEFAULT NULL,
  `room_type_string` varchar(255) DEFAULT NULL,
  `until_date` date DEFAULT NULL,
  `hotel_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdosq3ww4h9m2osim6o0lugng8` (`hotel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `active_flag`, `description`, `from_date`, `hotel_name`, `place`, `price_night`, `room_number`, `room_type`, `room_type_string`, `until_date`, `hotel_id`) VALUES
(1, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Oceanfront Miami Resort', 'Miami', 100, '101', 1, 'Single', '2024-03-10', 'AR-0002'),
(2, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Oceanfront Miami Resort', 'Miami', 150, '202', 2, 'Double', '2024-03-15', 'AR-0002'),
(3, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Oceanfront Miami Resort', 'Miami', 250, '305', 3, 'Suite', '2024-03-20', 'AR-0002'),
(4, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Oceanfront Miami Resort', 'Miami', 180, '410', 4, 'Family', '2024-03-25', 'AR-0002'),
(5, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Oceanfront Miami Resort', 'Miami', 200, '512', 2, 'Double', '2024-03-10', 'AR-0002'),
(6, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Miami Bayfront Hotel', 'Miami', 120, '101', 1, 'Single', '2024-03-10', 'AR-0003'),
(7, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Miami Bayfront Hotel', 'Miami', 170, '202', 2, 'Double', '2024-03-15', 'AR-0003'),
(8, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Miami Bayfront Hotel', 'Miami', 280, '305', 3, 'Suite', '2024-03-20', 'AR-0003'),
(9, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Miami Bayfront Hotel', 'Miami', 220, '410', 4, 'Family', '2024-03-25', 'AR-0003'),
(10, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Miami Bayfront Hotel', 'Miami', 230, '512', 2, 'Double', '2024-03-10', 'AR-0003'),
(11, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Grandeur Ritz-Carlton', 'Buenos Aires', 110, '101', 1, 'Single', '2024-03-10', 'RC-0001'),
(12, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Grandeur Ritz-Carlton', 'Buenos Aires', 160, '202', 2, 'Double', '2024-03-15', 'RC-0001'),
(13, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Grandeur Ritz-Carlton', 'Buenos Aires', 270, '305', 3, 'Suite', '2024-03-20', 'RC-0001'),
(14, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Grandeur Ritz-Carlton', 'Buenos Aires', 190, '410', 4, 'Family', '2024-03-25', 'RC-0001'),
(15, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Grandeur Ritz-Carlton', 'Buenos Aires', 210, '512', 2, 'Double', '2024-03-10', 'RC-0001'),
(16, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Ritz-Carlton Medellin', 'Medellin', 130, '101', 1, 'Single', '2024-03-10', 'RC-0002'),
(17, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Ritz-Carlton Medellin', 'Medellin', 180, '202', 2, 'Double', '2024-03-15', 'RC-0002'),
(18, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Ritz-Carlton Medellin', 'Medellin', 290, '305', 3, 'Suite', '2024-03-20', 'RC-0002'),
(19, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Ritz-Carlton Medellin', 'Medellin', 230, '410', 4, 'Family', '2024-03-25', 'RC-0002'),
(20, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Ritz-Carlton Medellin', 'Medellin', 210, '512', 2, 'Double', '2024-03-10', 'RC-0002'),
(21, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Madrid Grand Hyatt', 'Madrid', 110, '101', 1, 'Single', '2024-03-10', 'GH-0002'),
(22, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Madrid Grand Hyatt', 'Madrid', 160, '202', 2, 'Double', '2024-03-15', 'GH-0002'),
(23, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Madrid Grand Hyatt', 'Madrid', 260, '305', 3, 'Suite', '2024-03-20', 'GH-0002'),
(24, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Madrid Grand Hyatt', 'Madrid', 200, '410', 4, 'Family', '2024-03-25', 'GH-0002'),
(25, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Madrid Grand Hyatt', 'Madrid', 220, '512', 2, 'Double', '2024-03-10', 'GH-0002'),
(26, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Buenos Aires Grand Hyatt', 'Buenos Aires', 100, '101', 1, 'Single', '2024-03-10', 'GH-0001'),
(27, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Buenos Aires Grand Hyatt', 'Buenos Aires', 150, '202', 2, 'Double', '2024-03-15', 'GH-0001'),
(28, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Buenos Aires Grand Hyatt', 'Buenos Aires', 250, '305', 3, 'Suite', '2024-03-20', 'GH-0001'),
(29, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Buenos Aires Grand Hyatt', 'Buenos Aires', 180, '410', 4, 'Family', '2024-03-25', 'GH-0001'),
(30, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Buenos Aires Grand Hyatt', 'Buenos Aires', 200, '512', 2, 'Double', '2024-03-10', 'GH-0001'),
(31, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Mandarin Oriental', 'Barcelona', 130, '101', 1, 'Single', '2024-03-10', 'HL-0001'),
(32, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Mandarin Oriental', 'Barcelona', 180, '202', 2, 'Double', '2024-03-15', 'HL-0001'),
(33, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Mandarin Oriental', 'Barcelona', 280, '305', 3, 'Suite', '2024-03-20', 'HL-0001'),
(34, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Mandarin Oriental', 'Barcelona', 220, '410', 4, 'Family', '2024-03-25', 'HL-0001'),
(35, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Mandarin Oriental', 'Barcelona', 230, '512', 2, 'Double', '2024-03-10', 'HL-0001'),
(36, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Hilton Diagonal Mar Barcelona', 'Barcelona', 110, '101', 1, 'Single', '2024-03-10', 'HL-0002'),
(37, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Hilton Diagonal Mar Barcelona', 'Barcelona', 160, '202', 2, 'Double', '2024-03-15', 'HL-0002'),
(38, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Hilton Diagonal Mar Barcelona', 'Barcelona', 260, '305', 3, 'Suite', '2024-03-20', 'HL-0002'),
(39, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Hilton Diagonal Mar Barcelona', 'Barcelona', 200, '410', 4, 'Family', '2024-03-25', 'HL-0002'),
(40, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Hilton Diagonal Mar Barcelona', 'Barcelona', 220, '512', 2, 'Double', '2024-03-10', 'HL-0002'),
(41, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Hilton Rome', 'Rome', 120, '101', 1, 'Single', '2024-03-10', 'HL-0003'),
(42, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Hilton Rome', 'Rome', 170, '202', 2, 'Double', '2024-03-15', 'HL-0003'),
(43, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Hilton Rome', 'Rome', 270, '305', 3, 'Suite', '2024-03-20', 'HL-0003'),
(44, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Hilton Rome', 'Rome', 210, '410', 4, 'Family', '2024-03-25', 'HL-0003'),
(45, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Hilton Rome', 'Rome', 240, '512', 2, 'Double', '2024-03-10', 'HL-0003'),
(46, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Marriott Hotel Vela', 'Barcelona', 130, '101', 1, 'Single', '2024-03-10', 'MT-0003'),
(47, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Marriott Hotel Vela', 'Barcelona', 180, '202', 2, 'Double', '2024-03-15', 'MT-0003'),
(48, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Marriott Hotel Vela', 'Barcelona', 280, '305', 3, 'Suite', '2024-03-20', 'MT-0003'),
(49, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Marriott Hotel Vela', 'Barcelona', 220, '410', 4, 'Family', '2024-03-25', 'MT-0003'),
(50, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Marriott Hotel Vela', 'Barcelona', 250, '512', 2, 'Double', '2024-03-10', 'MT-0003'),
(51, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Sheraton Madrid', 'Madrid', 140, '101', 1, 'Single', '2024-03-10', 'SH-0004'),
(52, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Sheraton Madrid', 'Madrid', 190, '202', 2, 'Double', '2024-03-15', 'SH-0004'),
(53, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Sheraton Madrid', 'Madrid', 290, '305', 3, 'Suite', '2024-03-20', 'SH-0004'),
(54, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Sheraton Madrid', 'Madrid', 230, '410', 4, 'Family', '2024-03-25', 'SH-0004'),
(55, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Sheraton Madrid', 'Madrid', 260, '512', 2, 'Double', '2024-03-10', 'SH-0004'),
(56, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Iguazu Falls Sheraton', 'Iguazu', 150, '101', 1, 'Single', '2024-03-10', 'SH-0002'),
(57, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Iguazu Falls Sheraton', 'Iguazu', 200, '202', 2, 'Double', '2024-03-15', 'SH-0002'),
(58, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Iguazu Falls Sheraton', 'Iguazu', 300, '305', 3, 'Suite', '2024-03-20', 'SH-0002'),
(59, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Iguazu Falls Sheraton', 'Iguazu', 240, '410', 4, 'Family', '2024-03-25', 'SH-0002'),
(60, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Iguazu Falls Sheraton', 'Iguazu', 270, '512', 2, 'Double', '2024-03-10', 'SH-0002'),
(76, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Andrea Hotel', 'Miami', 100, '101', 1, 'Single', '2024-03-10', 'Andrea-TEST'),
(77, b'1', 'Spacious double room featuring two queen-sized beds, ideal for couples or small families.', '2024-02-25', 'Andrea Hotel', 'Miami', 150, '202', 2, 'Double', '2024-03-15', 'Andrea-TEST'),
(78, b'1', 'Luxurious suite with a separate living area, king-sized bed, and a private balcony overlooking the ocean.', '2024-03-10', 'Andrea Hotel', 'Miami', 250, '305', 3, 'Familiar', '2024-03-20', 'Andrea-TEST'),
(79, b'1', 'Family-friendly room equipped with a king-sized bed, two bunk beds, and a gaming console for kids.', '2024-03-05', 'Andrea Hotel', 'Miami', 180, '410', 4, 'Familiar', '2024-03-25', 'Andrea-TEST'),
(80, b'1', 'Elegant double room with a plush queen-sized bed, work desk, and a marble bathroom.', '2024-03-01', 'Andrea Hotel', 'Miami', 200, '512', 2, 'Double', '2024-03-10', 'Andrea-TEST'),
(81, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Andrea Hotel', 'Miami', 100, '101', 3, 'Familiar', '2024-03-10', 'Andrea-TEST'),
(82, b'1', 'Cozy single room with a comfortable bed and a view of the city skyline.', '2024-03-01', 'Andrea Hotel', 'Miami', 100, '102', 2, 'Double', '2024-03-10', 'Andrea-TEST');

-- --------------------------------------------------------

--
-- Table structure for table `seat_flight`
--

DROP TABLE IF EXISTS `seat_flight`;
CREATE TABLE IF NOT EXISTS `seat_flight` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `column_seat` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `row_seat` int DEFAULT NULL,
  `seat_string` varchar(255) DEFAULT NULL,
  `flight_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2ahi8tuq5hbhg3reg3n75w202` (`flight_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `seat_flight`
--

INSERT INTO `seat_flight` (`id`, `column_seat`, `price`, `row_seat`, `seat_string`, `flight_id`) VALUES
(7, 6, 9.5, 4, '04F', 'AIRWAVE230'),
(8, 2, 15, 2, '02B', 'AIRWAVE230'),
(10, 6, 9.5, 3, '03F', 'AIRWAVE230'),
(11, 2, 15, 2, '02B', 'AIRWAVE229');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FKhs7eej4m2orrmr5cfbcrqs8yw` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`);

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `FKr1e0j10i9v9i52l6tqfa69nj0` FOREIGN KEY (`id`) REFERENCES `person` (`id`);

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `FKt824vonkbw2cnqvtmscpuodj9` FOREIGN KEY (`id`) REFERENCES `person` (`id`);

--
-- Constraints for table `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD CONSTRAINT `FK4yc2lh9won9mbs3agudk7f89c` FOREIGN KEY (`id`) REFERENCES `booking` (`id`),
  ADD CONSTRAINT `FKjw5106usngquy3i3kuyjpeo30` FOREIGN KEY (`flight_one_way_id`) REFERENCES `flight` (`flight_number`),
  ADD CONSTRAINT `FKl4am137xbvkcmqgcrt1t2ja6c` FOREIGN KEY (`flight_return_id`) REFERENCES `flight` (`flight_number`);

--
-- Constraints for table `guest`
--
ALTER TABLE `guest`
  ADD CONSTRAINT `FK7sa7shf80gi6fipg043tox14s` FOREIGN KEY (`hotel_booking_id`) REFERENCES `hotel_booking` (`id`);

--
-- Constraints for table `hotel_booking`
--
ALTER TABLE `hotel_booking`
  ADD CONSTRAINT `FK82m4re8kmf430pxuu2gj25gir` FOREIGN KEY (`id`) REFERENCES `booking` (`id`),
  ADD CONSTRAINT `FKmykcvwg0ef1tielowjpm8affm` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

--
-- Constraints for table `luggage`
--
ALTER TABLE `luggage`
  ADD CONSTRAINT `FK9cy6pmixgdxluao8vc6x6idnj` FOREIGN KEY (`passenger_id`) REFERENCES `passenger` (`id`);

--
-- Constraints for table `passenger`
--
ALTER TABLE `passenger`
  ADD CONSTRAINT `FKgwfryhy21vey0dhwylceag8pg` FOREIGN KEY (`seat_oneway`) REFERENCES `seat_flight` (`id`),
  ADD CONSTRAINT `FKjsubtubpldyi4aui7r4j3uhlf` FOREIGN KEY (`seat_return`) REFERENCES `seat_flight` (`id`),
  ADD CONSTRAINT `FKk4unw06btj21hqretcigqsyn2` FOREIGN KEY (`flight_booking_id`) REFERENCES `flight_booking` (`id`);

--
-- Constraints for table `reserved_date`
--
ALTER TABLE `reserved_date`
  ADD CONSTRAINT `FK6813ngdhmpc1y2n10okhom06o` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FKdosq3ww4h9m2osim6o0lugng8` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_code`);

--
-- Constraints for table `seat_flight`
--
ALTER TABLE `seat_flight`
  ADD CONSTRAINT `FK2ahi8tuq5hbhg3reg3n75w202` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`flight_number`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
