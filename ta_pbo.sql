-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 02, 2017 at 10:36 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.5.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ta_pbo`
--

-- --------------------------------------------------------

--
-- Table structure for table `db_barang`
--

CREATE TABLE `db_barang` (
  `code_barang` int(11) NOT NULL,
  `nama_barang` varchar(300) NOT NULL,
  `jenis_barang` enum('Obat_Dalam','Obat_Luar') NOT NULL,
  `jumlah` int(11) NOT NULL,
  `deskripsi_barang` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `db_barang`
--

INSERT INTO `db_barang` (`code_barang`, `nama_barang`, `jenis_barang`, `jumlah`, `deskripsi_barang`) VALUES
(7, 'Promax', 'Obat_Dalam', 200, 'Buat yang sakit Perut'),
(8, 'Feminax', 'Obat_Dalam', 200, 'Buat Perempuan yang Dilep'),
(9, 'Obat Merah', 'Obat_Luar', 300, 'buat yang luka Luar'),
(10, 'pusing', 'Obat_Dalam', 181, 'bisa diminum');

-- --------------------------------------------------------

--
-- Table structure for table `db_boking`
--

CREATE TABLE `db_boking` (
  `code_boking` int(11) NOT NULL,
  `code_user` int(11) NOT NULL,
  `code_barang` int(11) NOT NULL,
  `jumlah_barang` int(11) NOT NULL,
  `tgl_boking` date NOT NULL,
  `verifikasi` enum('Diterima','Tidak Diterima') NOT NULL,
  `deskripsi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `db_boking`
--

INSERT INTO `db_boking` (`code_boking`, `code_user`, `code_barang`, `jumlah_barang`, `tgl_boking`, `verifikasi`, `deskripsi`) VALUES
(12, 15, 10, 2, '2017-12-02', 'Tidak Diterima', 'ok makasih'),
(13, 16, 10, 19, '2017-12-02', 'Tidak Diterima', 'tolong dibanyakin obatnya');

-- --------------------------------------------------------

--
-- Table structure for table `db_jabatan`
--

CREATE TABLE `db_jabatan` (
  `code_jabatan` int(11) NOT NULL,
  `nama_jabatan` varchar(50) NOT NULL,
  `deskrisi_jabatan` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `db_jabatan`
--

INSERT INTO `db_jabatan` (`code_jabatan`, `nama_jabatan`, `deskrisi_jabatan`) VALUES
(1, 'admin', 'Can Edit, Update, Delete'),
(2, 'member', 'Read Only');

-- --------------------------------------------------------

--
-- Table structure for table `db_user`
--

CREATE TABLE `db_user` (
  `code_user` int(11) NOT NULL,
  `code_jabatan` int(11) NOT NULL,
  `nama_user` varchar(300) NOT NULL,
  `umur` int(11) NOT NULL,
  `gender` enum('L','P') NOT NULL,
  `username` varchar(150) NOT NULL,
  `password` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `db_user`
--

INSERT INTO `db_user` (`code_user`, `code_jabatan`, `nama_user`, `umur`, `gender`, `username`, `password`) VALUES
(1, 1, 'ChaVaZaSRL', 17, 'L', 'admin', 'irc'),
(15, 2, 'utet', 16, 'P', 'utet', 'utet'),
(16, 2, 'Ariel ', 17, 'L', 'dravity', 'dravity');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `db_barang`
--
ALTER TABLE `db_barang`
  ADD PRIMARY KEY (`code_barang`);

--
-- Indexes for table `db_boking`
--
ALTER TABLE `db_boking`
  ADD PRIMARY KEY (`code_boking`),
  ADD KEY `code_user` (`code_user`),
  ADD KEY `code_barang` (`code_barang`);

--
-- Indexes for table `db_jabatan`
--
ALTER TABLE `db_jabatan`
  ADD PRIMARY KEY (`code_jabatan`);

--
-- Indexes for table `db_user`
--
ALTER TABLE `db_user`
  ADD PRIMARY KEY (`code_user`),
  ADD KEY `code_jabatan` (`code_jabatan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `db_barang`
--
ALTER TABLE `db_barang`
  MODIFY `code_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `db_boking`
--
ALTER TABLE `db_boking`
  MODIFY `code_boking` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `db_jabatan`
--
ALTER TABLE `db_jabatan`
  MODIFY `code_jabatan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `db_user`
--
ALTER TABLE `db_user`
  MODIFY `code_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `db_boking`
--
ALTER TABLE `db_boking`
  ADD CONSTRAINT `db_boking_ibfk_1` FOREIGN KEY (`code_user`) REFERENCES `db_user` (`code_user`),
  ADD CONSTRAINT `db_boking_ibfk_2` FOREIGN KEY (`code_barang`) REFERENCES `db_barang` (`code_barang`);

--
-- Constraints for table `db_user`
--
ALTER TABLE `db_user`
  ADD CONSTRAINT `db_user_ibfk_1` FOREIGN KEY (`code_jabatan`) REFERENCES `db_jabatan` (`code_jabatan`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
