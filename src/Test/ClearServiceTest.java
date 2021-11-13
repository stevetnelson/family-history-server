package Test;

import Dao.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Service.ClearService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;


public class ClearServiceTest {
    private ClearService clearService;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        ClearService clear = new ClearService();
        clear.clear();
    }

    @Test
    public void clearAuthTokenAndEvent() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        AuthToken authToken= new AuthToken("testToken", "testUser");
        authTokenDao.insertAuthToken(authToken);
        EventDao eventDao = new EventDao(conn);
        Event event = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        eventDao.insertEvent(event);
        Event eventRes = eventDao.findEvent(event.getEventID());
        assertNotNull(eventRes);
        AuthToken authTokenRes = authTokenDao.findWithAuthToken(authToken.getAuthToken());
        assertNotNull(authTokenRes);
        db.closeConnection(true);
        clearService = new ClearService();
        clearService.clear();
        db = new Database();
        conn = db.getConnection();
        authTokenDao = new AuthTokenDao(conn);
        authTokenRes = authTokenDao.findWithAuthToken(authToken.getAuthToken());
        assertNull(authTokenRes);
        eventDao = new EventDao(conn);
        eventRes = eventDao.findEvent(event.getEventID());
        assertNull(eventRes);
        db.closeConnection(true);
    }

    @Test
    public void clearPersonAndUser() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        UserDao userDao = new UserDao(conn);
        User user= new User("stnelson", "84606", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M");
        userDao.insertUser(user);
        PersonDao personDao = new PersonDao(conn);
        Person person = new Person("JeanValJean", "24601", "Jean",
                "Val Jean", "M", "", "", "");
        personDao.insertPerson(person);
        User userRes = userDao.find(user.getUsername());
        assertNotNull(userRes);
        Person personRes = personDao.find(person.getPersonID());
        assertNotNull(personRes);
        db.closeConnection(true);
        clearService = new ClearService();
        clearService.clear();
        db = new Database();
        conn = db.getConnection();
        userDao = new UserDao(conn);
        userRes = userDao.find(user.getUsername());
        assertNull(userRes);
        personDao = new PersonDao(conn);
        personRes = personDao.find(person.getPersonID());
        assertNull(personRes);
        db.closeConnection(true);
    }

}
