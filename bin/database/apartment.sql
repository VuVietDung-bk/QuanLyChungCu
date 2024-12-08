-- Create the database
CREATE DATABASE ApartmentManagement;
USE ApartmentManagement;

-- Create the Resident table
CREATE TABLE Resident (
    id CHAR(12) PRIMARY KEY, -- Fixed 12-digit ID
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50)
);

-- Create the Account table
CREATE TABLE Account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    residentID CHAR(12),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    type VARCHAR(50) DEFAULT 'user',
    FOREIGN KEY (residentID) REFERENCES Resident(id)
);

-- Create the Apartment table
CREATE TABLE Apartment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    ownerID INT,
    vehicleAmount INT,
    electric INT,
    water INT,
    area INT NOT NULL,
    FOREIGN KEY (ownerID) REFERENCES Account(id)
);

-- Create the Fee table
CREATE TABLE Fee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    apartmentID INT,
    typeFee ENUM('Living Expense', 'Vehicle', 'Water', 'Electricity', 'Volunteer') DEFAULT 'Living Expense',
    isForced INT DEFAULT 1,
    status INT DEFAULT 0,
    amount INT DEFAULT 0,
    FOREIGN KEY (apartmentID) REFERENCES Apartment(id)
);

-- Create the Relationship table
CREATE TABLE Relationship (
    id CHAR(12),
    ownerID CHAR(12),
    relationship ENUM('Dad', 'Mom', 'Son', 'Daughter', 'Grandparent', 'Sibling') NOT NULL,
    FOREIGN KEY (id) REFERENCES Resident(id),
    FOREIGN KEY (ownerID) REFERENCES Resident(id)
);

-- Create the Vehicle table
CREATE TABLE Vehicle (
    vehicleID VARCHAR(50) PRIMARY KEY,
    apartmentID INT,
    type VARCHAR(50) NOT NULL,
    FOREIGN KEY (apartmentID) REFERENCES Apartment(id)
);

-- Insert sample data into Resident
INSERT INTO Resident (id, name, phone) VALUES
('03810201901', 'John Doe', '123-456-7890'),
('03820201902', 'Jane Doe', '987-654-3210'),
('03810201903', 'Mike Doe', '555-123-4567'),
('03810201904', 'Sarah Doe', '444-555-6666'),
('03820201905', 'Emma Doe', '111-222-3333');

-- Insert sample data into Account (one account per apartment + admin account)
INSERT INTO Account (residentID, username, password, type) VALUES
('03810201901', 'john_doe', 'password123', 'user'),
('03820201902', 'jane_doe', 'password456', 'user'),
('03810201903', 'admin', 'admin123', 'admin'); -- Admin account linked to a resident

-- Insert sample data into Apartment
INSERT INTO Apartment (ownerID, vehicleAmount, electric, water, area) VALUES
(1, 2, 500, 300, 1200),
(2, 1, 700, 400, 1500);

-- Insert sample data into Fee
INSERT INTO Fee (apartmentID, typeFee, isForced, status, amount) VALUES
(1, 'Living Expense', 1, 0, 1000),
(1, 'Vehicle', 1, 0, 200),
(1, 'Water', 1, 0, 300),
(1, 'Electricity', 1, 0, 400),
(1, 'Volunteer', 0, 0, 50),
(2, 'Living Expense', 1, 0, 1200),
(2, 'Vehicle', 1, 0, 100),
(2, 'Water', 1, 0, 400),
(2, 'Electricity', 1, 0, 500),
(2, 'Volunteer', 0, 0, 75);

-- Insert sample data into Relationship
INSERT INTO Relationship (id, ownerID, relationship) VALUES
('03820201902', '03810201901', 'Mom'),
('03810201903', '03810201901', 'Son'),
('03810201904', '03810201901', 'Daughter'),
('03820201905', '03810201901', 'Grandparent');

-- Insert sample data into Vehicle
INSERT INTO Vehicle (vehicleID, apartmentID, type) VALUES
('VEH001', 1, 'Car'),
('VEH002', 1, 'Bike'),
('VEH003', 2, 'Scooter');

-- Query data to verify
SELECT * FROM Resident;
SELECT * FROM Account;
SELECT * FROM Apartment;
SELECT * FROM Fee;
SELECT * FROM Relationship;
SELECT * FROM Vehicle;

-- Validation query for vehicle count consistency
SELECT a.id AS ApartmentID, a.vehicleAmount, COUNT(v.vehicleID) AS VehicleCount
FROM Apartment a
LEFT JOIN Vehicle v ON a.id = v.apartmentID
GROUP BY a.id, a.vehicleAmount
HAVING a.vehicleAmount != COUNT(v.vehicleID);
