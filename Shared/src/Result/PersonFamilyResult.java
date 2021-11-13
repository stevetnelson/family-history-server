package Result;

import Model.Person;

import java.util.ArrayList;

/**
 * Class that holds the information returned to the user after the person command
 */
public class PersonFamilyResult extends Result{
    /**
     * List of Person Objects found to return
     */
    private ArrayList<PersonSingleResult> data;

    /**
     * Constructor of the error
     * @param message message of the error
     * @param success whether the command was successful or not as a boolean
     */
    public PersonFamilyResult(String message, boolean success) {
        super(message, success);
    }

    /**
     * Constructor of the result of the person command
     * @param success whether the command was successful or not as a boolean
     * @param data result of the person command as an list of Event objects
     */
    public PersonFamilyResult(String message, boolean success, ArrayList<PersonSingleResult> data) {
        super(message, success);
        this.data = data;
    }

    /**
     * Return list of Person objects
     * @return person list as a list
     */
    public ArrayList<PersonSingleResult> getData() {
        return data;
    }
}
