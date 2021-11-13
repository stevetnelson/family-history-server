package Dao;

import Model.Event;
import Result.EventSingleResult;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class used to access and edit Event table
 */
public class EventDao {
    private final Connection conn;

    public EventDao(Connection conn)
    {
        this.conn = conn;
    }
    /**
     * Inserts an Event into the Event table
     * @param event Event to add to the database
     */
    public void insertEvent(Event event) throws DataAccessException {
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, (float) event.getLatitude());
            stmt.setFloat(5, (float) event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }


    /**
     * Gets an Event object associated with a specific eventID
     * @param eventID eventID used to find a given event
     * @return Event object associated with given eventID
     */
    public Event findEvent(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * Gets list of Event objects associated with a specific user
     * @param associatedUsername username used to find events
     * @return ArrayList object associated with given username's events
     */
    public ArrayList<EventSingleResult> findEventList(String associatedUsername) throws DataAccessException {
        EventSingleResult event;
        String eventID = "";
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            ArrayList<EventSingleResult> myEvents = new ArrayList<>();
            while (rs.next()) {
                event = new EventSingleResult(null, true, rs.getString("AssociatedUsername"), rs.getString("EventID"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                myEvents.add(event);
            }
            return myEvents;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding events");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Clear out all people associated with a specific user
     */
    public void clearEventTableUser(String username) throws DataAccessException {
        String sql = "DELETE FROM Events WHERE AssociatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing Events associated with a user");
        }

    }

    /**
     * Clear out entire Event table
     */
    public void clearEventTable() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Events";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
