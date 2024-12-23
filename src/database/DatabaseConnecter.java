package database;

import application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseConnecter {

	private static final String URL = "jdbc:mysql://localhost:3306/apartmentmanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "provnYT2005";
    
    public static Connection getConnection() throws DatabaseConnectionException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            if (connection != null && !connection.isClosed()) {
                return connection;
            } else {
                throw new DatabaseConnectionException("Can't connect to database.");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error occurs while trying to connect to database: " + e.getMessage(), e);
        }
    }
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error occurs while closing connection: " + e.getMessage());
            }
        }
    }
    
    public static class DatabaseConnectionException extends Exception {

		private static final long serialVersionUID = 1L;

		public DatabaseConnectionException(String message) {
            super(message);
        }

        public DatabaseConnectionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    public static Account getUserAccount(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnecter.getConnection();

            if (connection != null) {
                String query = "SELECT * FROM account WHERE username = ? AND password = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, username.trim());
                statement.setString(2, password.trim());

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int accountId = resultSet.getInt("id");
                    String accountName = resultSet.getString("username");
                    String accountPassword = resultSet.getString("password");
                    String ownerID = resultSet.getString("residentID");
                    String type = resultSet.getString("type");
                    return new Account(accountName, accountPassword, ownerID, type);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (DatabaseConnecter.DatabaseConnectionException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) DatabaseConnecter.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static boolean setPassword(String id, String newPassword) {
    	Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnecter.getConnection();

            if (connection != null) {
                String query = "UPDATE account SET password = ? WHERE residentID = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, newPassword.trim());
                statement.setString(2, id);

                int affectedRows = statement.executeUpdate();

                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        } catch (DatabaseConnecter.DatabaseConnectionException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) DatabaseConnecter.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public static boolean setUsername(String id, String newUsername) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement updateStatement = null;

        try {
            connection = DatabaseConnecter.getConnection();

            if (connection != null) {
                // Check if the username already exists
                String checkQuery = "SELECT COUNT(*) FROM account WHERE username = ?";
                checkStatement = connection.prepareStatement(checkQuery);
                checkStatement.setString(1, newUsername.trim());
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // Username is already in use
                    return false;
                }

                // Update the username
                String updateQuery = "UPDATE account SET username = ? WHERE residentID = ?";
                updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, newUsername.trim());
                updateStatement.setString(2, id);

                int affectedRows = updateStatement.executeUpdate();

                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        } catch (DatabaseConnecter.DatabaseConnectionException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (checkStatement != null) checkStatement.close();
                if (updateStatement != null) updateStatement.close();
                if (connection != null) DatabaseConnecter.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

	
    public static ObservableList<Resident> getResidentsData() {
        String query = "SELECT id, name, phone FROM Resident";
        Connection connection = null;
        ObservableList<Resident> residentsData = FXCollections.observableArrayList();

        try {
            connection = getConnection(); 
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                Resident resident = new Resident(id, name, phone);
                residentsData.add(resident);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return residentsData;
    }

    public static ObservableList<Apartment> getApartmentsData() {
        String query = "SELECT id, ownerID, vehicleAmount, electric, water, area FROM Apartment";
        Connection connection = null;
        ObservableList<Apartment> apartmentsData = FXCollections.observableArrayList();

        try {
            connection = getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String ownerID = resultSet.getString("ownerID");
                int vehicleAmount = resultSet.getInt("vehicleAmount");
                int electric = resultSet.getInt("electric");
                int water = resultSet.getInt("water");
                int area = resultSet.getInt("area");
                Apartment apartment = new Apartment(id, ownerID, vehicleAmount, electric, water, area);
                apartmentsData.add(apartment);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return apartmentsData;
    }
    
    public static ObservableList<Relationship> getRelationshipsData() {
        String query = "SELECT id, ownerID, relationship FROM Relationship";
        Connection connection = null;
        ObservableList<Relationship> relationshipsData = FXCollections.observableArrayList();

        try {
            connection = getConnection(); 
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String ownerID = resultSet.getString("ownerID");
                String relationship = resultSet.getString("relationship");
                Relationship rel = new Relationship(ownerID, id, relationship);
                relationshipsData.add(rel);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return relationshipsData;
    }

    public static ObservableList<Vehicle> getVehiclesData() {
        String query = "SELECT vehicleID, apartmentID, type FROM Vehicle";
        Connection connection = null;
        ObservableList<Vehicle> vehiclesData = FXCollections.observableArrayList();

        try {
            connection = getConnection(); 
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String vehicleID = resultSet.getString("vehicleID");
                String apartmentID = resultSet.getString("apartmentID");
                String type = resultSet.getString("type");
                Vehicle vehicle = new Vehicle(apartmentID, vehicleID, type);
                vehiclesData.add(vehicle);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection); 
        }

        return vehiclesData;
    }

    public static ObservableList<Account> getAccountsData() {
        String query = "SELECT residentID, username, password, type FROM Account";
        Connection connection = null;
        ObservableList<Account> accountsData = FXCollections.observableArrayList();

        try {
            connection = getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String residentID = resultSet.getString("residentID");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String type = resultSet.getString("type");
                Account account = new Account(username, password, residentID, type);
                accountsData.add(account);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection); 
        }

        return accountsData;
    }
    
    public static ObservableList<Fee> getAllFees() {
        String query = "SELECT * FROM Fee";
        Connection connection = null;
        ObservableList<Fee> feeList = FXCollections.observableArrayList();

        try {
            connection = getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String apartmentID = resultSet.getString("apartmentID");
                String typeFee = resultSet.getString("typeFee");
                int isForced = resultSet.getInt("isForced");
                int status = resultSet.getInt("status");
                int amount = resultSet.getInt("amount");
                Date fromDate = resultSet.getDate("fromDate");
                Date toDate = resultSet.getDate("toDate");

                String isForcedText = isForced == 1 ? "Bắt buộc" : "Không bắt buộc";
                String statusText = status == 1 ? "Đã thanh toán" : status == 2 ? "Quá hạn" : "Chưa thanh toán";

                Fee fee = new Fee(apartmentID, typeFee, isForcedText, statusText, amount, fromDate, toDate);
                feeList.add(fee);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return feeList;
    }

    
    public static Resident getResidentByOwnerID(String ownerID) {
        String query = "SELECT Resident.id, Resident.name, Resident.phone " +
                       "FROM Resident " +
                       "INNER JOIN Apartment ON Resident.id = Apartment.ownerID " +
                       "WHERE Apartment.ownerID = ?";
        Connection connection = null;
        Resident resident = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ownerID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                resident = new Resident(id, name, phone);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return resident;
    }


    public static String getResidentNameById(String residentId) {
        String query = "SELECT name FROM Resident WHERE id = ?";
        Connection connection = null;
        String residentName = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, residentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                residentName = resultSet.getString("name");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection); 
        }

        return residentName;
    }
    
    public static boolean deleteApartmentById(String apartmentId) {
        String deleteFeeQuery = "DELETE FROM Fee WHERE apartmentID = ?";
        String deleteVehicleQuery = "DELETE FROM Vehicle WHERE apartmentID = ?";
        String selectOwnerQuery = "SELECT ownerID FROM Apartment WHERE id = ?";
        String selectRelatedResidentsQuery = "SELECT id FROM Relationship WHERE ownerID = ? OR id = ?";
        String deleteAccountQuery = "DELETE FROM Account WHERE residentID = ?";
        String deleteRelationshipQuery = "DELETE FROM Relationship WHERE id = ? OR ownerID = ?";
        String deleteResidentQuery = "DELETE FROM Resident WHERE id = ?";
        String deleteApartmentQuery = "DELETE FROM Apartment WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isDeleted = false;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            // Step 1: Retrieve the ownerID of the apartment
            String ownerID = null;
            preparedStatement = connection.prepareStatement(selectOwnerQuery);
            preparedStatement.setString(1, apartmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ownerID = resultSet.getString("ownerID");
            }
            resultSet.close();
            preparedStatement.close();

            if (ownerID == null) {
                System.err.println("No Apartment found with the provided ID.");
                return false; // Abort if the apartment does not exist
            }

            // Step 2: Delete all related Fees
            preparedStatement = connection.prepareStatement(deleteFeeQuery);
            preparedStatement.setString(1, apartmentId);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            // Step 3: Delete all related Vehicles
            preparedStatement = connection.prepareStatement(deleteVehicleQuery);
            preparedStatement.setString(1, apartmentId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
            // Step 4: Delete the Apartment itself
            preparedStatement = connection.prepareStatement(deleteApartmentQuery);
            preparedStatement.setString(1, apartmentId);
            int rowsAffected = preparedStatement.executeUpdate();
            isDeleted = rowsAffected > 0;
            preparedStatement.close();

            // Step 5: Delete related Accounts
            preparedStatement = connection.prepareStatement(deleteAccountQuery);
            preparedStatement.setString(1, ownerID);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            // Step 6: Delete related Relationships
            preparedStatement = connection.prepareStatement(deleteRelationshipQuery);
            preparedStatement.setString(1, ownerID);
            preparedStatement.setString(2, ownerID);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            // Step 7: Find and delete all Residents involved in relationships
            preparedStatement = connection.prepareStatement(selectRelatedResidentsQuery);
            preparedStatement.setString(1, ownerID);
            preparedStatement.setString(2, ownerID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String residentID = resultSet.getString("id");

                // Delete each Resident
                PreparedStatement deleteResidentStatement = connection.prepareStatement(deleteResidentQuery);
                deleteResidentStatement.setString(1, residentID);
                deleteResidentStatement.executeUpdate();
                deleteResidentStatement.close();
            }
            resultSet.close();
            preparedStatement.close();
            
            connection.commit();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback changes in case of errors
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            closeConnection(connection);
        }

        return isDeleted;
    }

    public static boolean deleteResident(String residentID) {
        String query = "DELETE FROM Resident WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isDeleted = false;

        try {
            connection = getConnection(); 
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, residentID);

            int rowsAffected = preparedStatement.executeUpdate();
            isDeleted = rowsAffected > 0;

        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isDeleted;
    }
    
    public static boolean deleteFee(String apartmentID, String typeFee, int isForced, int status, int amount) {
        String query = "DELETE FROM Fee WHERE apartmentID = ? AND typeFee = ? AND isForced = ? AND status = ? AND amount = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection(); 
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, apartmentID);
            preparedStatement.setString(2, typeFee);
            preparedStatement.setInt(3, isForced);
            preparedStatement.setInt(4, status);
            preparedStatement.setInt(5, amount);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            return false; 
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean deleteVehicle(String vehicleID) {
        String query = "DELETE FROM Vehicle WHERE vehicleID = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isDeleted = false;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, vehicleID);

            int rowsAffected = preparedStatement.executeUpdate();
            isDeleted = rowsAffected > 0;

        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isDeleted;
    }

    public static boolean insertApartment(String aptID, String newAptOwnerID, int vehicleAmount, int electric, int water, int area) {
        String query = "INSERT INTO Apartment (id, ownerID, vehicleAmount, electric, water, area) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        boolean isInserted = false;

        try {
            connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, aptID);
            preparedStatement.setString(2, newAptOwnerID);
            preparedStatement.setInt(3, vehicleAmount);
            preparedStatement.setInt(4, electric);
            preparedStatement.setInt(5, water);
            preparedStatement.setInt(6, area);

            int rowsAffected = preparedStatement.executeUpdate();
            isInserted = rowsAffected > 0;

            preparedStatement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection); 
        }

        return isInserted;
    }

    public static boolean insertResident(String id, String name, String phone) {
        String query = "INSERT INTO Resident (id, name, phone) VALUES (?, ?, ?)";
        Connection connection = null;
        boolean isInserted = false;

        try {
            connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id); 
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, phone);

            int rowsAffected = preparedStatement.executeUpdate();
            isInserted = rowsAffected > 0;

            preparedStatement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection); 
        }

        return isInserted;
    }
    
    public static int insertAccount(String residentID, String username, String password, String type) {
        String query = "INSERT INTO Account (residentID, username, password, type) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        int newAccountId = -1;

        try {
            connection = getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, residentID);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, type);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    newAccountId = generatedKeys.getInt(1); 
                }
                generatedKeys.close();
            }

            preparedStatement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return newAccountId;
    }
    
    public static boolean insertRelationship(String residentID, String ownerID, String relationship) {
        String query = "INSERT INTO Relationship (id, ownerID, relationship) VALUES (?, ?, ?)";
        Connection connection = null;
        boolean isInserted = false;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, residentID);
            preparedStatement.setString(2, ownerID);
            preparedStatement.setString(3, relationship);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                isInserted = true;
            }

            preparedStatement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return isInserted;
    }

    public static boolean insertVehicle(String vehicleID, String apartmentID, String type) {
        String query = "INSERT INTO Vehicle (vehicleID, apartmentID, type) VALUES (?, ?, ?)";
        Connection connection = null;
        boolean isInserted = false;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicleID);
            preparedStatement.setString(2, apartmentID);
            preparedStatement.setString(3, type);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                isInserted = true;
            }

            preparedStatement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return isInserted;
    }
    
    public static boolean insertFee(String apartmentID, String typeFee, int isForced, int status, int amount, Date fromDate, Date toDate) {
        String query = "INSERT INTO Fee (apartmentID, typeFee, isForced, status, amount, fromDate, toDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, apartmentID);
            preparedStatement.setString(2, typeFee);
            preparedStatement.setInt(3, isForced);
            preparedStatement.setInt(4, status);
            preparedStatement.setInt(5, amount);
            preparedStatement.setDate(6, fromDate); 
            preparedStatement.setDate(7, toDate);  

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void updateVehicleCountForApartments() {
        String countQuery = "SELECT apartmentID, COUNT(*) AS vehicleCount FROM Vehicle GROUP BY apartmentID";
        String updateQuery = "UPDATE Apartment SET vehicleAmount = ? WHERE id = ?";
        Connection connection = null;

        try {
            connection = getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(countQuery);

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            while (resultSet.next()) {
                String apartmentID = resultSet.getString("apartmentID");
                int vehicleCount = resultSet.getInt("vehicleCount");

                preparedStatement.setInt(1, vehicleCount);   
                preparedStatement.setString(2, apartmentID); 

                preparedStatement.executeUpdate();
            }

            preparedStatement.close();
            resultSet.close();
            statement.close();

        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    public static boolean updateFeeStatusToPaid(String apartmentID, String typeFee, int isForced, int amount) {
        String query = "UPDATE Fee SET status = 1 WHERE apartmentID = ? AND typeFee = ? AND isForced = ? AND amount = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isUpdated = false;

        try {
            connection = getConnection(); 
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, apartmentID);
            preparedStatement.setString(2, typeFee);
            preparedStatement.setInt(3, isForced);
            preparedStatement.setInt(4, amount);

            int rowsAffected = preparedStatement.executeUpdate();
            isUpdated = rowsAffected > 0;

        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isUpdated;
    }
    
    public static boolean updateElecWater(String aptID, int electric, int water) {
        String query = "UPDATE Apartment SET electric = ?, water = ? WHERE id = ?";
        Connection connection = null;
        boolean isUpdated = false;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, electric);
            preparedStatement.setInt(2, water);
            preparedStatement.setString(3, aptID);

            int rowsAffected = preparedStatement.executeUpdate();
            isUpdated = rowsAffected > 0;

            preparedStatement.close();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return isUpdated;
    }

    
    public static int updateExpiredFees() {
        String query = "UPDATE Fee SET status = 2 WHERE toDate < CURRENT_DATE AND status != 2";

        Connection connection = null;
        Statement statement = null;
        int rowsAffected = 0;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            rowsAffected = statement.executeUpdate(query);

        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsAffected;
    }

}