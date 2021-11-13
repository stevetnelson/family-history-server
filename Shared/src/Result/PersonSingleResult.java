package Result;

import java.util.Objects;

/**
 * Class that holds the information returned to the user after the specific person command
 */
public class PersonSingleResult extends Result{
    /**
     * username associated with a person as a String
     */
    private String associatedUsername = new String();
    /**
     * personID associated with a person as a String
     */
    private String personID = new String();
    /**
     * first name associated with a person as a String
     */
    private String firstName = new String();
    /**
     * last name associated with a person as a String
     */
    private String lastName = new String();
    /**
     * gender associated with a person as a char
     */
    private String gender;
    /**
     * fatherID associated with a person as a String (optional)
     */
    private String fatherID = new String();
    /**
     * motherID associated with a person as a String (optional)
     */
    private String motherID = new String();
    /**
     * username associated with a person as a String (optional)
     */
    private String spouseID = new String();


    /**
     * Constructor of the result of the person command
     * @param success whether the command was successful or not as a boolean
     * @param associatedUsername username associated with the person as a String
     * @param personID personID of the person as a String
     * @param firstName first name of the person as a String
     * @param lastName last name of the person as a String
     * @param gender gender of the person as a String
     */
    public PersonSingleResult(String message, boolean success, String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        super(message, success);
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Constructor of the error
     * @param message message of the error
     * @param success whether the command was successful or not as a boolean
     */
    public PersonSingleResult(String message, boolean success) {
        super(message, success);
    }

    /**
     * Return the username associated with the person
     * @return associated username as a String
     */
    public String getAssociatedUsername() {
        return associatedUsername;
    }

    /**
     * Return personID of the person
     * @return personID as a String
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Return first name of the person
     * @return first name as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Return last name of the person
     * @return last name as a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Return gender of the person
     * @return gender as a char
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set fatherID of the person
     * @param fatherID fatherID of the person as a String (optional)
     */
    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    /**
     * Set motherID of the person
     * @param motherID motherID of the person as a String (optional)
     */
    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    /**
     * Set spouseID of the person
     * @param spouseID spouseID of the person as a String (optional)
     */
    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonSingleResult that = (PersonSingleResult) o;
        return Objects.equals(associatedUsername, that.associatedUsername) && Objects.equals(personID, that.personID) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(gender, that.gender) && Objects.equals(fatherID, that.fatherID) && Objects.equals(motherID, that.motherID) && Objects.equals(spouseID, that.spouseID);
    }
}
