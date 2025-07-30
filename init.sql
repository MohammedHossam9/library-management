-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS library_db;

-- Use the database
USE library_db;

-- Create tables (Hibernate will handle this, but we can add any initial data here)
-- The tables will be created by Hibernate's hbm2ddl.auto=update setting 