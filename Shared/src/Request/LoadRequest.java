package Request;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.ArrayList;

/**
 * Class holding the information from the user to carry out the load command
 */
public class LoadRequest {
    /**
     * List of user Objects to add
     */
    ArrayList<User> users;
    /**
     * List of Person Objects to add
     */
    ArrayList<Person> persons;
    /**
     * List of Event Objects to add
     */
    ArrayList<Event> events;

    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public LoadRequest() {
    }

    /**
     * Return list of User objects
     * @return users as a list
     */
    public ArrayList<User> getMyUsers() {
        return users;
    }

    /**
     * Return list of Person objects
     * @return people as a list
     */
    public ArrayList<Person> getMyPersons() {
        return persons;
    }

    /**
     * Return list of Event objects
     * @return events as a list
     */
    public ArrayList<Event> getMyEvents() {
        return events;
    }
}
