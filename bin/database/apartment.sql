-- Create the database
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
    isForced INT DEFAULT 1,
    status INT DEFAULT 0,
    amount INT DEFAULT 0,
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
('038201000001', 'John Doe', '123-456-7890'),
('038201000002', 'Alice Smith', '987-654-3210'),
('038201000003', 'Bruce Wayne', '555-123-4567');

-- Insert sample data into Account
INSERT INTO Account (residentID, username, password, type) VALUES
('038201000001', 'johndoe', 'password123', 'user'),
('038201000002', 'alicesmith', 'password456', 'user'),
('038201000003', 'admin', 'adminpass', 'admin');

-- Insert sample data into Apartment
INSERT INTO Apartment (id, ownerID, vehicleAmount, electric, water, area) VALUES
('APT001', '038201000001', 2, 500, 300, 1200),
('APT002', '038201000002', 1, 700, 400, 1500),
('APT003', '038201000003', 3, 800, 600, 2000);

-- Insert sample data into Fee
INSERT INTO Fee (apartmentID, typeFee, isForced, status, amount) VALUES
('APT001', 'Living Expenses', 1, 1, 1000),
('APT002', 'Water', 1, 0, 500),
('APT003', 'Electricity', 1, 1, 1500);

-- Insert sample data into Relationship
INSERT INTO Relationship (id, ownerID, relationship) VALUES
('038201000002', '038201000001', 'Spouse'),
('038201000003', '038201000002', 'Sibling'),
('038201000001', '038201000003', 'Parent');

-- Insert sample data into Vehicle
INSERT INTO Vehicle (vehicleID, apartmentID, type) VALUES
('VEH123', 'APT001', 'Car'),
('VEH124', 'APT001', 'Bike'),
('VEH125', 'APT002', 'Scooter'),
('VEH126', 'APT003', 'Truck'),
('VEH127', 'APT003', 'Motorcycle'),
('VEH128', 'APT003', 'Bicycle');
