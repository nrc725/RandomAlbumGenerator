-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 06, 2019 at 05:52 PM
-- Server version: 5.7.26
-- PHP Version: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `albums`
--

-- --------------------------------------------------------

--
-- Table structure for table `album_information`
--

DROP TABLE IF EXISTS `album_information`;
CREATE TABLE IF NOT EXISTS `album_information` (
  `Album_Date` date NOT NULL,
  `Artist_Name` text NOT NULL,
  `Album_Name` text NOT NULL,
  `Album_URL` varchar(2083) NOT NULL,
  `Image_URL` varchar(2083) NOT NULL,
  `Redirect_URL` varchar(2083) NOT NULL,
  `Unique_Album_ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Unique_Album_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `album_information`
--

INSERT INTO `album_information` (`Album_Date`, `Artist_Name`, `Album_Name`, `Album_URL`, `Image_URL`, `Redirect_URL`, `Unique_Album_ID`) VALUES
('2019-07-02', 'My Chemical Romance', 'The Black Parade', '0FZK97MXMm5mUQ8mtudjuK', 'https://i.scdn.co/image/767fd98433e0d4ad11bc0e8edf605872bcb7a02c', 'https://open.spotify.com/album/0FZK97MXMm5mUQ8mtudjuK', 1),
('2019-07-03', 'Queens of the Stone Age', '...Like Clockwork', '06S2JBsr4U1Dz3YaenPdVq', 'https://i.scdn.co/image/8230534734ea7dead79f54a303801edc013024a5', 'https://open.spotify.com/album/06S2JBsr4U1Dz3YaenPdVq', 2),
('2019-07-04', 'Muse', 'The Resistance', '0eFHYz8NmK75zSplL5qlfM', 'https://i.scdn.co/image/6e1be3ceda70250c701caee5a16bef205e94bc98', 'https://open.spotify.com/album/0eFHYz8NmK75zSplL5qlfM', 3),
('2019-07-05', 'BROCKHAMPTON', 'Saturation', '67smHJOf5YlFwad6dAlppm', 'https://i.scdn.co/image/b75a102224a64af6a62da941eb1d2b9984dc717f', 'https://open.spotify.com/album/67smHJOf5YlFwad6dAlppm', 4),
('2019-07-06', 'Florence + The Machine', 'How Big, How Blue, How Beautiful', '2btszoya78vyT8fwelmVnz', 'https://i.scdn.co/image/38e16c703f610be6472e686be0d171b4c0993749', 'https://open.spotify.com/album/2btszoya78vyT8fwelmVnz', 5),
('2019-07-07', 'Gorillaz', 'Plastic Beach', '2dIGnmEIy1WZIcZCFSj6i8', 'https://i.scdn.co/image/94c124dd9812e03e8d21de9a05bbee08ad60ed91', 'https://open.spotify.com/album/2dIGnmEIy1WZIcZCFSj6i8', 6),
('2019-07-08', 'The Dear Hunter', 'Act IV: Rebirth in Reprise', '0TCkVVN1AfEtMP3IjVD1Zi', 'https://i.scdn.co/image/9616e45452f52c39340365be976404149149f718', 'https://open.spotify.com/album/0TCkVVN1AfEtMP3IjVD1Zi', 7),
('2019-07-09', 'Thin Lizzy', 'Jailbreak', '2TCSZn8ArTdDhXYhsTNBPm', 'https://i.scdn.co/image/a7edf626b3c25e464b7d11c90080fccf53dedcad', 'https://open.spotify.com/album/2TCSZn8ArTdDhXYhsTNBPm', 8),
('2019-07-10', 'The White Stripes', 'Elephant', '0VXcqDD3sHdOIGtO6oYv3d', 'https://i.scdn.co/image/0a11bb546557b3b02f02903476fe13f52a052ad1', 'https://open.spotify.com/album/0VXcqDD3sHdOIGtO6oYv3d', 9),
('2019-07-11', 'Twenty One Pilots', 'Trench', '621cXqrTSSJi1WqDMSLmbL', 'https://i.scdn.co/image/54a95d9d5c92512b914ac6ac1faa9a67b195cde5', 'https://open.spotify.com/album/621cXqrTSSJi1WqDMSLmbL', 10),
('2019-07-12', 'Childish Gambino', 'Awaken, My Love!', '4xnq1L6P551Qcb9gBXNMK7', 'https://i.scdn.co/image/49c99505c9dd13fb27b6bc82dfa69d0fe58f0193', 'https://open.spotify.com/album/4xnq1L6P551Qcb9gBXNMK7', 11),
('2019-07-13', 'Arctic Monkeys', 'AM', '78bpIziExqiI9qztvNFlQu', 'https://i.scdn.co/image/f513ae2d610d9255913795d4bc28ca33c827b232', 'https://open.spotify.com/album/78bpIziExqiI9qztvNFlQu', 12),
('2019-07-14', 'Fall Out Boy', 'From Under the Cork Tree', '5nkUSlIhtoJZMOUlB0sNCp', 'https://i.scdn.co/image/63a37f32006eab70bd58c892e4098f2a84bb915e', 'https://open.spotify.com/album/5nkUSlIhtoJZMOUlB0sNCp', 13),
('2019-07-15', 'Father John Misty', 'Pure Comedy', '3CoFoDt6zt5EKxmTpOX32b', 'https://i.scdn.co/image/583e15a010fdce0fe18fa56d3272a5f78a4686a8', 'https://open.spotify.com/album/3CoFoDt6zt5EKxmTpOX32b', 14),
('2019-07-16', 'Coheed and Cambria', 'The Unheavenly Creatures', '42S0lDJT9wHKCVaMGgqKdm', 'https://i.scdn.co/image/05ea7b7fa9c12994a0a0259b72dc1df4ca59a67f', 'https://open.spotify.com/album/42S0lDJT9wHKCVaMGgqKdm', 15),
('2019-07-17', 'Fleetwood Mac', 'Rumours', '3nuRoTy7gi52Z4C0negdw1', 'https://i.scdn.co/image/42195211685a0b44f8d8d51b02b2605acb2c5b10', 'https://api.spotify.com/v1/albums/3nuRoTy7gi52Z4C0negdw1', 16),
('2019-07-19', 'Kendrick Lamar', 'To Pimp a Butterfly', '7ycBtnsMtyVbbwTfJwRjSP', 'https://i.scdn.co/image/5d78516b21c1845a1fc3b2bbb94c745998c19146', 'https://open.spotify.com/album/7ycBtnsMtyVbbwTfJwRjSP', 17),
('2019-07-20', 'Foo Fighters', 'Wasting Light', '5lnQLEUiVDkLbFJHXHQu9m', 'https://i.scdn.co/image/13b3c650142254a1c9191a5cdda961ab051a2659', 'https://open.spotify.com/album/5lnQLEUiVDkLbFJHXHQu9m', 18),
('2019-07-21', 'Green Day', '21st Century Breakdown', '1AHZd3C3S8m8fFrhFxyk79', 'https://i.scdn.co/image/674099a68b30a3188ee213cd8ca260fa1daf00e9', 'https://api.spotify.com/v1/albums/1AHZd3C3S8m8fFrhFxyk79', 19),
('2019-07-22', 'Avenged Sevenfold', 'The Stage', '0jwnYwJz6XHNrVAYEclQPd', 'https://i.scdn.co/image/25ebc610b02667ecd62a204fa53d432ef0fce8a5', 'https://open.spotify.com/album/0jwnYwJz6XHNrVAYEclQPd', 20),
('2019-07-18', 'Paramore', 'After Laughter', '1c9Sx7XdXuMptGyfCB6hHs', 'https://i.scdn.co/image/d8296568ae1b856050976111fa892d8db693efd5', 'https://open.spotify.com/album/1c9Sx7XdXuMptGyfCB6hHs', 21),
('2019-07-23', 'Tyler, The Creator', 'IGOR', '5zi7WsKlIiUXv09tbGLKsE', 'https://i.scdn.co/image/bb05317292465b8809b29c00906c1a4b6a226194', 'https://open.spotify.com/album/5zi7WsKlIiUXv09tbGLKsE', 22),
('2019-07-24', 'Toto', 'Toto IV', '62U7xIHcID94o20Of5ea4D ', 'https://i.scdn.co/image/fba60f3ffb0d2ffc6ec078f670541d0c897ff85f', 'https://open.spotify.com/album/62U7xIHcID94o20Of5ea4D', 23),
('2019-07-26', 'Rage Against the Machine', 'Rage Against the Machine', '4Io5vWtmV1rFj4yirKb4y4', 'https://i.scdn.co/image/85236cc12312fac9405206c25bd38479e63a04d6', 'https://open.spotify.com/album/4Io5vWtmV1rFj4yirKb4y4', 24),
('2019-07-25', 'MGMT', 'Little Dark Age', '7GjVWG39IOj4viyWplJV4H', 'https://i.scdn.co/image/9a14bb92d918a4adb70d15f01d14c59cddb7667f', 'https://open.spotify.com/album/7GjVWG39IOj4viyWplJV4H', 25),
('2019-07-27', 'Kanye West', 'The College Dropout', '3ff2p3LnR6V7m6BinwhNaQ', 'https://i.scdn.co/image/aa5b579c2da0dba06b5597127644d3d46f254cdb', 'https://open.spotify.com/album/3ff2p3LnR6V7m6BinwhNaQ', 26),
('2019-07-27', 'Mastodon', 'Emperor of Sand', '1VzmKgEG38fsUBZVe15wuF', 'https://i.scdn.co/image/3bf3bd6a4617dfeb8f5c4f16cd6c74ceeaba5d52', 'https://open.spotify.com/album/1VzmKgEG38fsUBZVe15wuF', 27),
('2019-07-28', 'Panic! At The Disco', 'Too Weird To Live, Too Rare to Die!', '1hxraaWEf3wFnJxADf8Dge', 'https://i.scdn.co/image/54b005998a18976a9fc7f5c867ceb63068e37786', 'https://open.spotify.com/album/1hxraaWEf3wFnJxADf8Dge', 28),
('2019-07-29', 'Royal Blood', 'Royal Blood', '7DJijwZ2wqJrgLpcRMxs3G', 'https://i.scdn.co/image/677b1f3ec3ca3dd72fb67cdb642fc27c34d0b3f1', 'https://open.spotify.com/album/7DJijwZ2wqJrgLpcRMxs3G', 29),
('2019-07-30', 'Run the Jewels', 'Run the Jewels 2', '2lPYlP4eumsjz6LBG8GCbG', 'https://i.scdn.co/image/7faa16835aee312077945f30dc5cd48215c7044a', 'https://open.spotify.com/album/2lPYlP4eumsjz6LBG8GCbG', 30),
('2019-07-31', 'St. Vincent', 'Strange Mercy', '1Lci4bx7JIuCC8pnBNX7ds', 'https://i.scdn.co/image/6ae05d028f5da35c10ca9d31452184cb45eef097', 'https://open.spotify.com/album/1Lci4bx7JIuCC8pnBNX7ds', 31);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
