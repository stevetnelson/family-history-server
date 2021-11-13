package Result;

import java.util.Objects;

/**
 * Class that holds the information returned to the user after the single event command
 */
public class EventSingleResult extends Result{
    /**
     * username associated with the Event as a String
     */
    private String associatedUsername = new String();
    /**
     * eventID associated with the Event as a String
     */
    private String eventID = new String();
    /**
     * personID associated with the Event as a String
     */
    private String personID = new String();
    /**
     * latitude associated with the Event as a float
     */
    private float latitude;
    /**
     * longitude  associated with the Event as a float
     */
    private float longitude;
    /**
     * country associated with the Event as a String
     */
    private String country = new String();
    /**
     * city associated with the Event as a String
     */
    private String city = new String();
    /**
     * event type associated with the Event as a String
     */
    private String eventType = new String();
    /**
     * year associated with the Event as an int
     */
    private int year;

    /**
     * Constructor of the error
     * @param message message of the error
     * @param success whether the command was successful or not as a boolean
     */
    public EventSingleResult(String message, boolean success) {
        super(message, success);
    }

    /**
     * Constructor of the result of the Event command
     * @param success whether the command was successful or not as a boolean
     * @param associatedUsername username associated with the event as a String
     * @param eventID eventID of the event as a String
     * @param personID personID of the event as a String
     * @param latitude latitude of the event as a float
     * @param longitude longitude of the event as a float
     * @param country country where the event took place as a String
     * @param city city where the event took place as a String
     * @param eventType type of event as a String
     * @param year year of the event as an int
     */
    public EventSingleResult(String message, boolean success, String associatedUsername, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        super(message, success);
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Returns username associated with an event
     * @return associated username as a String
     */
    public String getAssociatedUsername() {
        return associatedUsername;
    }

    /**
     * Returns eventID of an event
     * @return eventID as a String
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Returns personID of the person associated with an event
     * @return personID as a String
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * Returns country where an Event took place
     * @return country as a String
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns city where an Event took place
     * @return city as a String
     */
    public String getCity() {
        return city;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventSingleResult that = (EventSingleResult) o;
        return Float.compare(that.latitude, latitude) == 0 && Float.compare(that.longitude, longitude) == 0 && year == that.year && Objects.equals(associatedUsername, that.associatedUsername) && Objects.equals(eventID, that.eventID) && Objects.equals(personID, that.personID) && Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(eventType, that.eventType);
    }
}
