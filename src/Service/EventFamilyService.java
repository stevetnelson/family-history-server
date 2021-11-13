package Service;

import Dao.*;
import Model.AuthToken;
import Result.EventFamilyResult;
import Result.Result;
import Result.EventSingleResult;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that handles the logic of querying for many events
 */
public class EventFamilyService {
    /**
     * Queries database for events
     * @param authToken authToken as a String
     * @return result of the command as an EventFamilyResult object
     */
    public Result eventFamily(String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            AuthToken associatedAuthToken = authTokenDao.findWithAuthToken(authToken);
            EventDao eventDao = new EventDao(conn);
            ArrayList<EventSingleResult> eventList = eventDao.findEventList(associatedAuthToken.getUsername());
            EventFamilyResult eventResult = new EventFamilyResult(null, true, eventList);
            db.closeConnection(false);
            return eventResult;
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
