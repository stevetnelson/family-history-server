package Test;

import Dao.AuthTokenDao;
import Dao.DataAccessException;
import Dao.Database;
import Dao.EventDao;
import Model.AuthToken;
import Model.Event;
import Result.EventSingleResult;
import Result.Result;
import Service.ClearService;
import Service.EventFamilyService;
import org.junit.jupiter.api.Test;
import Result.EventFamilyResult;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EventFamilyServiceTest {

    @Test
    public void retrieveMultiple() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
        Event testEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        EventSingleResult testEventRes1 = new EventSingleResult(null, true, "Gale", "Biking_123A", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku", "Biking_Around", 2016);
        Event testEvent2 = new Event("otherId", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "having fun", 2015);
        EventSingleResult testEventRes2 = new EventSingleResult(null, true, "Gale", "otherId", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "having fun", 2015);

        Database db = new Database();
        Connection conn = db.getConnection();
        AuthTokenDao aDao = new AuthTokenDao(conn);
        aDao.insertAuthToken(new AuthToken("732", "Gale"));
        EventDao eDao = new EventDao(conn);
        eDao.insertEvent(testEvent);
        eDao.insertEvent(testEvent2);
        db.closeConnection(true);
        ArrayList<EventSingleResult> testArray = new ArrayList();
        testArray.add(testEventRes1);
        testArray.add(testEventRes2);
        EventFamilyResult eventFamilyResult = new EventFamilyResult(null, true, testArray);
        EventFamilyService eventFamilyService = new EventFamilyService();
        Result compareTest = eventFamilyService.eventFamily("732");
        assertNull(compareTest.getMessage());
        assertEquals(eventFamilyResult.getMessage(), compareTest.getMessage());
    }

    @Test
    public void retrieveSingle() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
        Event testEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        EventSingleResult testEventRes1 = new EventSingleResult(null, true, "Gale", "Biking_123A", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku", "Biking_Around", 2016);

        Database db = new Database();
        Connection conn = db.getConnection();
        AuthTokenDao aDao = new AuthTokenDao(conn);
        aDao.insertAuthToken(new AuthToken("732", "Gale"));
        EventDao eDao = new EventDao(conn);
        eDao.insertEvent(testEvent);
        db.closeConnection(true);
        ArrayList<EventSingleResult> testArray = new ArrayList();
        testArray.add(testEventRes1);
        EventFamilyResult eventFamilyResult = new EventFamilyResult(null, true, testArray);
        EventFamilyService eventFamilyService = new EventFamilyService();
        Result compareTest = eventFamilyService.eventFamily("732");
        assertNull(compareTest.getMessage());
        assertEquals(eventFamilyResult.getMessage(), compareTest.getMessage());
        assertEquals(eventFamilyResult.getClass(), compareTest.getClass());
        assertEquals(eventFamilyResult.getClass(), compareTest.getClass());
    }
}
