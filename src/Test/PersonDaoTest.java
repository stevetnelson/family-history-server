package Test;

import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Result.PersonSingleResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Model.Person;
import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PersonDaoTest {
    private Database db;
    private Person testPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        db = new Database();
        testPerson = new Person("JeanValJean", "24601", "Jean",
                "Val Jean", "M", "", "",
                "");
        Connection conn = db.getConnection();
        db.clearTables("Persons");
        pDao = new PersonDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        pDao.insertPerson(testPerson);
        Person compareTest = pDao.find(testPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(testPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDao.insertPerson(testPerson);
        assertThrows(DataAccessException.class, ()-> pDao.insertPerson(testPerson));
    }

    @Test
    public void retrievePassGroup() throws DataAccessException {
        pDao.insertPerson(testPerson);
        Person testPerson2 = new Person("JeanValJean", "246", "J",
                "VJ", "M", "", "",
                "");
        PersonSingleResult testPersonRes2 = new PersonSingleResult(null, true, "JeanValJean", "246", "J",
                "VJ", "M", null, null, null);
        PersonSingleResult testPersonRes1 = new PersonSingleResult(null, true, "JeanValJean", "24601", "Jean",
                "Val Jean", "M", null, null, null);
        pDao.insertPerson(testPerson2);
        ArrayList<PersonSingleResult> testArray = new ArrayList();
        testArray.add(testPersonRes1);
        testArray.add(testPersonRes2);
        ArrayList<PersonSingleResult> compareTest = pDao.findPersonList("JeanValJean");
        assertNotNull(compareTest);
        assertEquals(testArray.get(0), compareTest.get(0));
        assertEquals(testArray.get(1), compareTest.get(1));
        assertEquals(testArray, compareTest);
    }

    @Test
    public void retrievePass() throws DataAccessException {
        pDao.insertPerson(testPerson);
        Person testPerson2 = new Person("JeanVal", "10642", "Val Jean",
                "Jean", "M", "39075", "47157",
                "09361");
        pDao.insertPerson(testPerson2);
        Person compareTest = pDao.find(testPerson.getPersonID());
        Person compareTest2 = pDao.find(testPerson2.getPersonID());
        assertNotNull(compareTest);
        assertNotNull(compareTest2);
        assertEquals(testPerson, compareTest);
        assertEquals(testPerson2, compareTest2);
    }

    @Test
    public void retrieveFail() throws DataAccessException {
        pDao.insertPerson(testPerson);
        Person compareTest = pDao.find(testPerson.getPersonID());
        assertEquals(testPerson, compareTest);
        assertThrows(DataAccessException.class, ()-> pDao.insertPerson(testPerson));
        Person compareTester = pDao.find(testPerson.getPersonID() + "a");
        assertNull(compareTester);
    }

    @Test
    public void clearPass() throws DataAccessException {
        pDao.insertPerson(testPerson);
        pDao.clearPersonTable();
        Person compareTest = pDao.find(testPerson.getPersonID());
        assertNull(compareTest);
    }
}
