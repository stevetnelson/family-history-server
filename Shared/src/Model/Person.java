package Model;

import java.util.Objects;

/**
 * Class used to hold Person information
 */
public class Person {
    /**
     * username associated with a specific person as a String
     */
    private String associatedUsername = new String();
    /**
     * personID of the person as a String
     */
    private String personID = new String();
    /**
     * first name of the person as a String
     */
    private String firstName = new String();
    /**
     * last name of the person as a String
     */
    private String lastName = new String();
    /**
     * gender of the person as a String
     */
    private String gender;
    /**
     * fatherID of the person as a String (optional)
     */
    private String fatherID = new String();
    /**
     * motherID of the person as a String (optional)
     */
    private String motherID = new String();
    /**
     * spouse of the person as a String (optional)
     */
    private String spouseID = new String();

    /**
     * Constructor to create a Person object
     * @param associatedUsername username associated with a specific person as a String
     * @param personID personID of the person as a String
     * @param firstName first name of the person as a String
     * @param lastName last name of the person as a String
     * @param gender gender of the person as a char
     * @param fatherID id of the father as a string (optional)
     * @param motherID id of the mother as a string (optional)
     * @param spouseID id of the spouse as a string (optional)
     */
    public Person(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public Person(String associatedUsername, String personID, String firstName, String lastName, String gender) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Return username associated with the person
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
     * Return id of the person's father
     * @return father's id as a String
     */
    public String getFatherID() {
        return fatherID;
    }

    /**
     * Return id of the person's mother
     * @return mother's id as a String
     */
    public String getMotherID() {
        return motherID;
    }

    /**
     * Return id of the person's spouse
     * @return spouse's id as a String
     */
    public String getSpouseID() {
        return spouseID;
    }

    /**
     * Sets Father's ID (Optional)
     */
    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    /**
     * Sets Mother's ID (Optional)
     */
    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    /**
     * Sets Spouse's ID (Optional)
     */
    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(associatedUsername, person.associatedUsername) && Objects.equals(personID, person.personID) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(gender, person.gender) && Objects.equals(fatherID, person.fatherID) && Objects.equals(motherID, person.motherID) && Objects.equals(spouseID, person.spouseID);
    }
}
