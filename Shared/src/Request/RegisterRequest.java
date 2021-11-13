package Request;


/**
 * Class holding the information from the user to carry out the Register command
 */
public class RegisterRequest {
    /**
     * username of user to register as a String
     */
    private String username = new String();
    /**
     * password of user to register as a String
     */
    private String password = new String();
    /**
     * email of user to register as a String
     */
    private String email = new String();
    /**
     * first name of user to register as a String
     */
    private String firstName = new String();
    /**
     * last name of user to register as a String
     */
    private String lastName = new String();
    /**
     * gender of user to register as a char
     */
    private String gender;

    /**
     * Constructor of a register request
     * @param username username of the user to register as a String
     * @param password password of the user to register
     * @param email email of the user to register as a String
     * @param firstName first name of the user to register as a String
     * @param lastName last name of the user to register as a String
     * @param gender gender of the user to register as a char
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Returns username of the person to register
     * @return username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns password of the person to register
     * @return password as a String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns email of the person to register
     * @return email as a String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns first name of the person to register
     * @return first name as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns last name of the person to register
     * @return last name as a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns gender of the person to register
     * @return gender as a char
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender to be lowercase
     */
    public void setGenderLowercase() {
        gender = gender.toLowerCase();
    }
}
