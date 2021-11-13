package Test;

import Dao.*;
import Model.AuthToken;
import Model.Person;
import Result.Result;
import Service.ClearService;
import Service.PersonFamilyService;
import org.junit.jupiter.api.Test;
import Result.PersonSingleResult;
import Result.PersonFamilyResult;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PersonFamilyServiceTest {
    @Test
    public void retrieveMultiple() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
        Person testPerson = new Person("JeanValJean", "24601", "Jean",
                "Val Jean", "M", "", "", "");
        PersonSingleResult testPersonRes1 = new PersonSingleResult(null, true, "JeanValJean", "24601", "Jean",
                "Val Jean", "M", null, null, null);
        Person testPerson2 = new Person("JeanValJean", "246", "J",
                "VJ", "M", "", "", "");
        PersonSingleResult testPersonRes2 = new PersonSingleResult(null, true, "JeanValJean", "246", "J",
                "VJ", "M", null, null, null);

        Database db = new Database();
        Connection conn = db.getConnection();
        AuthTokenDao aDao = new AuthTokenDao(conn);
        aDao.insertAuthToken(new AuthToken("732", "JeanValJean"));
        PersonDao pDao = new PersonDao(conn);
        pDao.insertPerson(testPerson);
        pDao.insertPerson(testPerson2);
        db.closeConnection(true);
        ArrayList<PersonSingleResult> testArray = new ArrayList();
        testArray.add(testPersonRes1);
        testArray.add(testPersonRes2);
        PersonFamilyResult personFamilyResult = new PersonFamilyResult(null, true, testArray);
        PersonFamilyService personFamilyService = new PersonFamilyService();
        Result compareTest = personFamilyService.personFamily("732");
        assertEquals(compareTest.getClass(), PersonFamilyResult.class);
        assertNull(compareTest.getMessage());
        assertEquals(personFamilyResult.getMessage(), compareTest.getMessage());
    }

    @Test
    public void retrieveSingle() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
        Person testPerson = new Person("JeanValJean", "24601", "Jean",
                "Val Jean", "M", "", "", "");
        PersonSingleResult testPersonRes1 = new PersonSingleResult(null, true, "JeanValJean", "24601", "Jean",
                "Val Jean", "M", null, null, null);

        Database db = new Database();
        Connection conn = db.getConnection();
        AuthTokenDao aDao = new AuthTokenDao(conn);
        aDao.insertAuthToken(new AuthToken("732", "JeanValJean"));
        PersonDao pDao = new PersonDao(conn);
        pDao.insertPerson(testPerson);
        db.closeConnection(true);
        ArrayList<PersonSingleResult> testArray = new ArrayList();
        testArray.add(testPersonRes1);
        PersonFamilyResult personFamilyResult = new PersonFamilyResult(null, true, testArray);
        PersonFamilyService personFamilyService = new PersonFamilyService();
        Result compareTest = personFamilyService.personFamily("732");
        assertEquals(compareTest.getClass(), PersonFamilyResult.class);
        assertNull(compareTest.getMessage());
        assertEquals(personFamilyResult.getMessage(), compareTest.getMessage());
    }
}
