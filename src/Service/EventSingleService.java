package Service;

import Dao.*;
import Model.AuthToken;
import Model.Event;
import Result.EventSingleResult;
import Result.Result;

import java.sql.Connection;

/**
 * Class that handles the logic of querying for a specific event
 */
public class EventSingleService {
    /**
     * Queries the database for a specific event
     * @param eventID eventID as a String
     * @param authToken as a String
     * @return result of the command as an EventSingleResult object
     */
    public Result eventSingle(String eventID, String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            EventDao eventDao = new EventDao(conn);
            Event event = eventDao.findEvent(eventID);
            if (event == null) {
                db.closeConnection(false);
                return new Result("Error: Event does not exist", false);
            }
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            AuthToken associatedAuthToken = authTokenDao.findWithAuthToken(authToken);
            if (!event.getUsername().equals(associatedAuthToken.getUsername())) {
                db.closeConnection(false);
                return new Result("Error: Requested event does not belong to this user", false);
            }
            EventSingleResult eventSingleResult = new EventSingleResult(null, true, event.getUsername(), event.getEventID(), event.getPersonID(), event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear());
            db.closeConnection(false);
            return eventSingleResult;
        }
        catch (DataAccessException e) {
            try {db.closeConnection(false);}
            catch (DataAccessException d) {
                System.out.println(d.getMessage());
            }
            return new Result(e.getMessage(), false);
        }
    }
}
