package Test;

import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDao;
import Model.User;
import Request.LoginRequest;
import Result.Result;
import Service.ClearService;
import Service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Result.LoginRegisterResult;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    LoginService loginService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
        loginService = new LoginService();
    }

    @Test
    public void loginCorrectly() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        UserDao userDao = new UserDao(conn);
        userDao.insertUser(new User("stnelson", "84606", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M"));
        db.closeConnection(true);
        Result result = loginService.login(new LoginRequest("stnelson", "mypass"));
        assertNotNull(result);
        assertEquals(result.getClass(), LoginRegisterResult.class);
        assertNull(result.getMessage());
    }

    @Test
    public void loginWrongUsername() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        UserDao userDao = new UserDao(conn);
        userDao.insertUser(new User("stnelson", "84606", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M"));
        db.closeConnection(true);
        Result result = loginService.login(new LoginRequest("stnelson1", "mypass"));
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Error: User does not exist");
    }

    @Test
    public void loginWrongPassword() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        UserDao userDao = new UserDao(conn);
        userDao.insertUser(new User("stnelson", "84606", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M"));
        db.closeConnection(true);
        Result result = loginService.login(new LoginRequest("stnelson", "mypass1"));
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Error: Password is incorrect");
    }



}
