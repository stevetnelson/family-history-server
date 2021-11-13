package Test;

import Dao.*;
import Model.AuthToken;
import Model.Person;
import Result.Result;
import Service.ClearService;
import Service.PersonSingleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Result.PersonSingleResult;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonSingleServiceTest {
    PersonSingleService personSingleService;
    Person testPerson;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        testPerson = new Person("JeanValJean", "24601", "Jean",
                "Val Jean", "M", "", "", "");
        ClearService clearService = new ClearService();
        clearService.clear();
        personSingleService = new PersonSingleService();
    }

    @Test
    public void getEvent() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        PersonDao personDao = new PersonDao(conn);
        personDao.insertPerson(testPerson);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        authTokenDao.insertAuthToken(new AuthToken("123", "JeanValJean"));
        db.closeConnection(true);
        Result result = personSingleService.personSingle(testPerson.getPersonID(), "123");
        assertNotNull(result);
        assertEquals(result.getClass(), PersonSingleResult.class);
        assertNull(result.getMessage());
    }

    @Test
    public void wrongEventID() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        PersonDao personDao = new PersonDao(conn);
        personDao.insertPerson(testPerson);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        authTokenDao.insertAuthToken(new AuthToken("123", "JeanValJean"));
        db.closeConnection(true);
        Result result = personSingleService.personSingle(testPerson.getPersonID() + "a", "123");
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Error: Person does not exist");
    }

    @Test
    public void wrongAuthCode() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        PersonDao personDao = new PersonDao(conn);
        personDao.insertPerson(testPerson);
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        authTokenDao.insertAuthToken(new AuthToken("123", "JeanValJean"));
        authTokenDao.insertAuthToken(new AuthToken("456", "User"));
        db.closeConnection(true);
        Result result = personSingleService.personSingle(testPerson.getPersonID(), "456");
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Error: Requested person does not belong to this user");
    }
}
