package Request;

/**
 * Class holding the information from the user to carry out the login command
 */
public class LoginRequest {
    /**
     * username to login with as a String
     */
    private String username = new String();
    /**
     * password to login with as a String
     */
    private String password = new String();

    /**
     * Constructor for a login request
     * @param username username to login with as a String
     * @param password password to login with as a String
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns username of the request
     * @return username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns password of the request
     * @return password as a String
     */
    public String getPassword() {
        return password;
    }
}
