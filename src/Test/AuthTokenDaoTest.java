package Test;

import Dao.DataAccessException;
import Dao.Database;
import Dao.AuthTokenDao;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class AuthTokenDaoTest {
    private Database db;
    private AuthToken testAuth;
    private AuthTokenDao aDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        db = new Database();
        testAuth = new AuthToken("24601", "stevie");
        Connection conn = db.getConnection();
        db.clearTables("Persons");
        aDao = new AuthTokenDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPassAuthToken() throws DataAccessException {
        aDao.insertAuthToken(testAuth);
        AuthToken compareTest = aDao.findWithAuthToken(testAuth.getAuthToken());
        assertNotNull(compareTest);
        assertEquals(testAuth, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        aDao.insertAuthToken(testAuth);
        assertThrows(DataAccessException.class, ()-> aDao.insertAuthToken(testAuth));
    }

    @Test
    public void retrievePass() throws DataAccessException {
        aDao.insertAuthToken(testAuth);
        AuthToken testAuth2 = new AuthToken("24606", "stonk");
        aDao.insertAuthToken(testAuth2);
        AuthToken compareTest = aDao.findWithAuthToken(testAuth.getAuthToken());
        AuthToken compareTest2 = aDao.findWithAuthToken(testAuth2.getAuthToken());
        assertNotNull(compareTest);
        assertNotNull(compareTest2);
        assertEquals(testAuth, compareTest);
        assertEquals(testAuth2, compareTest2);
    }

    @Test
    public void retrieveFail() throws DataAccessException {
        aDao.insertAuthToken(testAuth);
        AuthToken compareTest = aDao.findWithAuthToken(testAuth.getAuthToken());
        assertEquals(testAuth, compareTest);
        assertThrows(DataAccessException.class, ()-> aDao.insertAuthToken(testAuth));
        AuthToken compareTester = aDao.findWithAuthToken(testAuth.getAuthToken() + "a");
        assertNull(compareTester);
    }

    @Test
    public void clearPass() throws DataAccessException {
        aDao.insertAuthToken(testAuth);
        aDao.clearAuthTokenTable();
        AuthToken compareTest = aDao.findWithAuthToken(testAuth.getAuthToken());
        assertNull(compareTest);
    }
}
