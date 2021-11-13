package Test;

import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDao;
import Model.User;
import Request.RegisterRequest;
import Result.Result;
import Service.ClearService;
import Service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Result.LoginRegisterResult;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    RegisterService registerService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
        registerService = new RegisterService();
    }

    @Test
    public void loginCorrectly() throws DataAccessException {
        Database db = new Database();
        RegisterRequest registerRequest = new RegisterRequest("stnelson", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M");
        Result result = registerService.register(registerRequest);
        assertNotNull(result);
        assertEquals(result.getClass(), LoginRegisterResult.class);
        assertNull(result.getMessage());
    }

    @Test
    public void loginTaken() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();
        UserDao userDao = new UserDao(conn);
        userDao.insertUser(new User("stnelson", "84606", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M"));
        db.closeConnection(true);
        RegisterRequest registerRequest = new RegisterRequest("stnelson", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M");
        Result result = registerService.register(registerRequest);
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Error: Username already taken by another user");
    }
}
