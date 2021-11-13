package Service;

import Dao.*;
import Result.Result;

import java.sql.Connection;

/**
 * Class that handles the logic of clearing the database
 */
public class ClearService {
    /**
     * Clear the entire database
     * @return result of clearing database as a ClearResult object
     */
    public Result clear() {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            EventDao eventDao = new EventDao(conn);
            PersonDao personDao = new PersonDao(conn);
            UserDao userDao = new UserDao(conn);
            authTokenDao.clearAuthTokenTable();
            eventDao.clearEventTable();
            personDao.clearPersonTable();
            userDao.clearUserTable();
            db.closeConnection(true);
            Result clearResult = new Result("Clear succeeded.", true);
            return clearResult;
        }
        catch (DataAccessException e) {
            try {db.closeConnection(false);}
            catch (DataAccessException d) {
                System.out.println(d.getMessage());
            }
            Result result = new Result(e.getMessage(), false);
            return result;
        }
    }
}
