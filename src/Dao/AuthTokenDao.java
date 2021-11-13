package Dao;

import Model.AuthToken;

import java.sql.*;

/**
 * Class used to access and edit AuthToken table
 */
public class AuthTokenDao {
    private final Connection conn;

    public AuthTokenDao(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Inserts an authToken into the authToken table
     * @param authToken unique authToken object for a user's login session as an AuthToken
     */
    public void insertAuthToken(AuthToken authToken) throws DataAccessException {
        String sql = "INSERT INTO AuthTokens (AuthToken, Username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Gets a authToken object associated with an authToken from the AuthToken table
     * @param authToken authToken used to find a specific username
     * @return authToken object associated with given authToken as an authToken
     */
    public AuthToken findWithAuthToken(String authToken) throws DataAccessException {
        AuthToken authTokenModel;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE authToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authTokenModel = new AuthToken(rs.getString("AuthToken"), rs.getString("Username"));
                return authTokenModel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding AuthToken");
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
     * Clear out entire AuthToken table
     */
    public void clearAuthTokenTable() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM AuthTokens";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
