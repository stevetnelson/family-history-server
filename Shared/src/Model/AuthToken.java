package Model;

import java.util.Objects;

/**
 * Class used to hold authToken information
 */
public class AuthToken {
    /**
     * Unique authToken as a String
     */
    private String authToken;
    /**
     * Boolean that stores validity of the authToken request
     */
    private String username;

    /**
     * Constructor to create AuthToken object
     * @param authToken authToken to be stored
     * @param username validity to be stored
     */
    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    /**
     * Returns a username
     * @return username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the authToken value
     * @return the authToken as a string
     */
    public String getAuthToken() {
        return authToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(authToken, authToken1.authToken) && Objects.equals(username, authToken1.username);
    }
}
