package Test;

import Dao.*;
import Model.AuthToken;
import Model.Event;
import Result.EventSingleResult;
import Result.Result;
import Service.ClearService;
import Service.EventSingleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventSingleServiceTest {
    EventSingleService eventSingleService;
    Event testEvent;
    @BeforeEach
    public void setUp() throws DataAccessException
    {
        testEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        ClearService clearService = new ClearService();
        clearService.clear();
        eventSingleService = new EventSingleService();
    }

    @Test
    public void getEvent() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        EventDao eventDao = new EventDao(conn);
        eventDao.insertEvent(testEvent);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        authTokenDao.insertAuthToken(new AuthToken("123", "Gale"));
        db.closeConnection(true);
        Result result = eventSingleService.eventSingle(testEvent.getEventID(), "123");
        assertNotNull(result);
        assertEquals(result.getClass(), EventSingleResult.class);
        assertNull(result.getMessage());
    }

    @Test
    public void wrongEventID() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        EventDao eventDao = new EventDao(conn);
        eventDao.insertEvent(testEvent);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        authTokenDao.insertAuthToken(new AuthToken("123", "Gale"));
        db.closeConnection(true);
        Result result = eventSingleService.eventSingle(testEvent.getEventID() + "a", "123");
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Error: Event does not exist");
    }

    @Test
    public void wrongAuthCode() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        EventDao eventDao = new EventDao(conn);
        eventDao.insertEvent(testEvent);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        authTokenDao.insertAuthToken(new AuthToken("123", "Gale"));
        authTokenDao.insertAuthToken(new AuthToken("456", "User"));
        db.closeConnection(true);
        Result result = eventSingleService.eventSingle(testEvent.getEventID(), "456");
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Error: Requested event does not belong to this user");
    }


}
