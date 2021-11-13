package Test;

import Dao.*;
import Model.User;
import Result.Result;
import Service.ClearService;
import Service.FillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    FillService fillService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
        fillService = new FillService();
    }

    @Test
    public void fillCorrectly() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        UserDao userDao = new UserDao(conn);
        userDao.insertUser(new User("stnelson", "84606", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M"));
        db.closeConnection(true);
        Result result = fillService.fill("stnelson", 4);
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Successfully added 31 persons and 93 events to the database.");
    }

    @Test
    public void invalidUsername() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        UserDao userDao = new UserDao(conn);
        userDao.insertUser(new User("stnelson", "84606", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M"));
        userDao.insertUser(new User("stnelso", "84606", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M"));
        db.closeConnection(true);
        Result result = fillService.fill("stnelso", 6);
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Successfully added 127 persons and 381 events to the database.");
    }
}
