package Test;

import Dao.DataAccessException;
import Dao.Database;
import Dao.EventDao;
import Result.EventSingleResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Model.Event;
import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDaoTest {
    private Database db;
    private Event testEvent;
    private EventDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        testEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables("Events");
        //Then we pass that connection to the EventDAO so it can access the database
        eDao = new EventDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        //While insert returns a bool we can't use that to verify that our function actually worked
        //only that it ran without causing an error
        eDao.insertEvent(testEvent);
        //So lets use a find method to get the event that we just put in back out
        Event compareTest = eDao.findEvent(testEvent.getEventID());
        //First lets see if our find found anything at all. If it did then we know that if nothing
        //else something was put into our database, since we cleared it in the beginning
        assertNotNull(compareTest);
        //Now lets make sure that what we put in is exactly the same as what we got out. If this
        //passes then we know that our insert did put something in, and that it didn't change the
        //data in any way
        assertEquals(testEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        eDao.insertEvent(testEvent);
        //but our sql table is set up so that "eventID" must be unique. So trying to insert it
        //again will cause the method to throw an exception
        //Note: This call uses a lambda function. What a lambda function is is beyond the scope
        //of this class. All you need to know is that this line of code runs the code that
        //comes after the "()->" and expects it to throw an instance of the class in the first parameter.
        assertThrows(DataAccessException.class, ()-> eDao.insertEvent(testEvent));
    }

    @Test
    public void retrievePass() throws DataAccessException {
        eDao.insertEvent(testEvent);
        Event testEvent2 = new Event("eventID2", "steve", "myid", (float)13.4, (float)5.90,
                "Canada", "Vancouver", "Birth", 1999);
        eDao.insertEvent(testEvent2);
        Event compareTest = eDao.findEvent(testEvent.getEventID());
        Event compareTest2 = eDao.findEvent(testEvent2.getEventID());
        assertNotNull(compareTest);
        assertNotNull(compareTest2);
        assertEquals(testEvent, compareTest);
        assertEquals(testEvent2, compareTest2);
    }

    @Test
    public void retrievePassMultiple() throws DataAccessException {
        eDao.insertEvent(testEvent);
        Event testEvent2 = new Event("otherId", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "having fun", 2015);
        EventSingleResult testEventRes2 = new EventSingleResult(null, true, "Gale", "otherId", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "having fun", 2015);
        EventSingleResult testEventRes1 = new EventSingleResult(null, true, "Gale", "Biking_123A", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku", "Biking_Around", 2016);
        eDao.insertEvent(testEvent2);
        ArrayList<EventSingleResult> testArray = new ArrayList();
        testArray.add(testEventRes1);
        testArray.add(testEventRes2);
        ArrayList<EventSingleResult> compareTest = eDao.findEventList("Gale");
        assertNotNull(compareTest);
        assertEquals(testArray, compareTest);
    }

    @Test
    public void retrieveFail() throws DataAccessException {
        eDao.insertEvent(testEvent);
        Event compareTest = eDao.findEvent(testEvent.getEventID());
        assertEquals(testEvent, compareTest);
        assertThrows(DataAccessException.class, ()-> eDao.insertEvent(testEvent));
        Event compareTester = eDao.findEvent(testEvent.getEventID() + "a");
        assertNull(compareTester);
    }

    @Test
    public void clearPass() throws DataAccessException {
        eDao.insertEvent(testEvent);
        eDao.clearEventTable();
        Event compareTest = eDao.findEvent(testEvent.getEventID());
        assertNull(compareTest);
    }
}
