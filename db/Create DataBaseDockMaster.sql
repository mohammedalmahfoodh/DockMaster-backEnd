DROP DATABASE IF EXISTS dock_master;
CREATE DATABASE IF NOT EXISTS dock_master;
USE dock_master;
SET SQL_SAFE_UPDATES=0;
ALTER USER 'root'@'localhost' IDENTIFIED BY 'tyfon';
 SET GLOBAL time_zone = '+00:00';
 SET @@global.time_zone = '+00:00';
