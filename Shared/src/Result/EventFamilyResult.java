package Result;

import java.util.ArrayList;

/**
 * Class that holds the information returned to the user after the event command
 */
public class EventFamilyResult extends Result{
    /**
     * List of Event Objects found to return
     */
    private ArrayList<EventSingleResult> data;

    /**
     * Constructor of the error
     * @param message message of the error as a String
     * @param success whether the command was successful or not as a boolean
     */
    public EventFamilyResult(String message, boolean success) {
        super(message, success);
    }

    /**
     * Constructor of the result of the event command
     * @param success whether the command was successful or not as a boolean
     * @param data result of the event command as an list of Event objects
     */
    public EventFamilyResult(String message, boolean success, ArrayList<EventSingleResult> data) {
        super(message, success);
        this.data = data;
    }

    /**
     * Return list of Event objects
     * @return event list as a list
     */
    public ArrayList<EventSingleResult> getData() {
        return data;
    }
}
