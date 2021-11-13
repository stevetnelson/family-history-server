package Dao;

import Model.User;
import java.sql.*;

/**
 * Class used to access and edit User table
 */
public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Inserts an User into the User table
     * @param user User to add to the database
     */
    public void insertUser(User user) throws DataAccessException {
        String sql = "INSERT INTO Users (Username, PersonID, Password, Email, FirstName, " +
                "LastName, Gender) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPersonID());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getFirstName());
            stmt.setString(6, user.getLastName());
            stmt.setString(7, user.getGender());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }


    /**
     * Gets an User object associated with a specific username
     * @param username username used to find a given user
     * @return User object associated with given username
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("PersonID"),
                        rs.getString("Password"), rs.getString("Email"), rs.getString("Firstname"),
                        rs.getString("LastName"), rs.getString("Gender"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding User");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * Clear out entire User table
     */
    public void clearUserTable() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
