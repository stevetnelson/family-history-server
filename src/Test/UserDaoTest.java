package Test;

import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Model.User;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;


public class UserDaoTest {
    private Database db;
    private User testUser;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        db = new Database();
        testUser = new User("stnelson", "84606", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M");
        Connection conn = db.getConnection();
        db.clearTables("Users");
        uDao = new UserDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insertUser(testUser);
        User compareTest = uDao.find(testUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(testUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insertUser(testUser);
        assertThrows(DataAccessException.class, ()-> uDao.insertUser(testUser));
    }

    @Test
    public void retrievePass() throws DataAccessException {
        uDao.insertUser(testUser);
        User testUser2 = new User("stnels", "84600", "mypassword",
                "nelson.stevent@hotmail.com", "Steve", "Nels", "M");
        uDao.insertUser(testUser2);
        User compareTest = uDao.find(testUser.getUsername());
        User compareTest2 = uDao.find(testUser2.getUsername());
        assertNotNull(compareTest);
        assertNotNull(compareTest2);
        assertEquals(testUser, compareTest);
        assertEquals(testUser2, compareTest2);
    }

    @Test
    public void retrieveFail() throws DataAccessException {
        uDao.insertUser(testUser);
        User compareTest = uDao.find(testUser.getUsername());
        assertEquals(testUser, compareTest);
        assertThrows(DataAccessException.class, ()-> uDao.insertUser(testUser));
        User compareTester = uDao.find(testUser.getUsername() + "a");
        assertNull(compareTester);
    }

    @Test
    public void clearPass() throws DataAccessException {
        uDao.insertUser(testUser);
        uDao.clearUserTable();
        User compareTest = uDao.find(testUser.getUsername());
        assertNull(compareTest);
    }
}
