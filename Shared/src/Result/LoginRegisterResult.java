package Result;

/**
 * Class that holds the information returned to the user after the login or register command
 */
public class LoginRegisterResult extends Result{
    /**
     * authToken associated with the attempt as a String
     */
    private String authtoken = new String();
    /**
     * username associated with the attempt as a String
     */
    private String username = new String();
    /**
     * personID associated with the attempt as a String
     */
    private String personID = new String();

    /**
     * Constructor of the result of the Login or Register command
     * @param success whether the command was successful or not as a boolean
     * @param authtoken authToken of the login attempt as a String
     * @param username username of the login attempt as a String
     * @param personID personID of the login attempt as a String
     */
    public LoginRegisterResult(String message, boolean success, String authtoken, String username, String personID) {
        super(message, success);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Constructor of the error
     * @param message message of the error
     * @param success whether the command was successful or not as a boolean
     */
    public LoginRegisterResult(String message, boolean success) {
        super(message, success);
    }

    /**
     * Return authToken of the login or register attempt
     * @return authToken as a String
     */
    public String getAuthtoken() {
        return authtoken;
    }

    /**
     * Return username of the login or register attempt
     * @return username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return personID of the login or register attempt
     * @return personID as a String
     */
    public String getPersonID() {
        return personID;
    }
}
