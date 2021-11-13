package Model;

import java.util.Objects;

/**
 * Class used to hold User information
 */
public class User {
    /**
     * username of the user as a string
     */
    private String username = new String();
    /**
     * personID of the user as a string
     */
    private String personID = new String();
    /**
     * password of the user as a string
     */
    private String password = new String();
    /**
     * email of the user as a string
     */
    private String email = new String();
    /**
     * first name of the user as a string
     */
    private String firstName = new String();
    /**
     * last name of the user as a string
     */
    private String lastName = new String();
    /**
     * gender of the user as a char
     */
    private String gender;

    /**
     * Constructor to create a User object
     * @param username username of the user as a string
     * @param personID personID of the user as a string
     * @param password password of the user as a string
     * @param email email of the user as a string
     * @param firstName first name of the user as a string
     * @param lastName last name of the user as a string
     * @param gender gender of the user as a String
     */
    public User(String username, String personID, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.personID = personID;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Return username of the user
     * @return username as a string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return personID of the user
     * @return personID as a string
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Return password of the user
     * @return password as a string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Return email of the user
     * @return email as a string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Return first name of the user
     * @return first name as a string
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Return last name of the user
     * @return last name as a string
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Return gender of the user
     * @return gender as a char
     */
    public String getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(personID, user.personID) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(gender, user.gender);
    }
}
