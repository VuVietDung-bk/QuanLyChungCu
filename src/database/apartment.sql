-- Initialize the database
CREATE DATABASE IF NOT EXISTS ApartmentManagement;
USE ApartmentManagement;

-- Create the Resident table
CREATE TABLE Resident (
    id VARCHAR(12) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50)
);

-- Create the Account table
CREATE TABLE Account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    residentID VARCHAR(12),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    type VARCHAR(50) DEFAULT 'user',
    FOREIGN KEY (residentID) REFERENCES Resident(id)
);

-- Create the Apartment table
CREATE TABLE Apartment (
    id VARCHAR(12) PRIMARY KEY,
    ownerID VARCHAR(12),
    vehicleAmount INT DEFAULT 0,
    electric INT DEFAULT 0,
    water INT DEFAULT 0,
    area INT NOT NULL,
    FOREIGN KEY (ownerID) REFERENCES Resident(id)
);

-- Create the Fee table
CREATE TABLE Fee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    apartmentID VARCHAR(12),
    typeFee VARCHAR(255) DEFAULT 'Living Expenses',
    isForced INT DEFAULT 0,
    status INT DEFAULT 0,
    amount INT DEFAULT 0,
    fromDate DATE,
    toDate DATE,
    FOREIGN KEY (apartmentID) REFERENCES Apartment(id)
);

-- Create the Relationship table
CREATE TABLE Relationship (
    id VARCHAR(12),
    ownerID VARCHAR(12),
    relationship VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES Resident(id),
    FOREIGN KEY (ownerID) REFERENCES Resident(id)
);

-- Create the Vehicle table
CREATE TABLE Vehicle (
    vehicleID VARCHAR(50) PRIMARY KEY,
    apartmentID VARCHAR(12),
    type VARCHAR(50) NOT NULL,
    FOREIGN KEY (apartmentID) REFERENCES Apartment(id)
);

-- Insert sample data into Resident
INSERT INTO Resident (id, name, phone) VALUES
('038201000001', 'John Doe', '1234567890'),
('038201000002', 'Jane Smith', '9876543210'),
('038201000003', 'Bruce Wayne', '5551234567');

-- Insert sample data into Account
INSERT INTO Account (residentID, username, password, type) VALUES
('038201000001', 'johndoe', 'password123', 'user'),
('038201000002', 'janesmith', 'password456', 'admin'),
('038201000003', 'batman', 'darkknight', 'user');

-- Insert sample data into Apartment
INSERT INTO Apartment (id, ownerID, vehicleAmount, electric, water, area) VALUES
('APT001', '038201000001', 1, 200, 150, 1000),
('APT002', '038201000002', 2, 300, 250, 1200),
('APT003', '038201000003', 3, 400, 300, 1500);

-- Insert sample data into Fee
INSERT INTO Fee (apartmentID, typeFee, isForced, status, amount, fromDate, toDate) VALUES
('APT001', 'Living Expenses', 1, 0, 1000, '2024-01-01', '2024-12-31'),
('APT002', 'Electricity', 1, 0, 500, '2024-01-01', '2024-12-31'),
('APT003', 'Water', 1, 1, 300, '2024-01-01', '2024-12-31');

-- Insert sample data into Relationship
INSERT INTO Relationship (id, ownerID, relationship) VALUES
('038201000002', '038201000001', 'Spouse'),
('038201000003', '038201000001', 'Child');

-- Insert sample data into Vehicle
INSERT INTO Vehicle (vehicleID, apartmentID, type) VALUES
('VEH001', 'APT001', 'Car'),
('VEH002', 'APT002', 'Bike'),
('VEH003', 'APT003', 'Scooter');

-- Query data to verify the database
SELECT * FROM Resident;
SELECT * FROM Account;
SELECT * FROM Apartment;
SELECT * FROM Fee;
SELECT * FROM Relationship;
SELECT * FROM Vehicle;
