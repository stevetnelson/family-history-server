package Service;

import Dao.*;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginRegisterResult;
import Result.Result;

import java.sql.Connection;
import java.util.UUID;

/**
 * Class that handles the logic of logging a user in
 */
public class LoginService {
    /**
     * Logs into the server
     * @param r request object as a LoginRequest object
     * @return result of the command as a LoginRegisterResult object
     */
    public Result login(LoginRequest r) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            User user = userDao.find(r.getUsername());
            if (user == null) {
                db.closeConnection(false);
                return new Result("Error: User does not exist", false);
            }
            if (!r.getPassword().equals(user.getPassword())) {
                db.closeConnection(false);
                return new Result("Error: Password is incorrect", false);
            }
            AuthToken authToken = new AuthToken(UUID.randomUUID().toString(), r.getUsername());
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            authTokenDao.insertAuthToken(authToken);
            LoginRegisterResult loginResult = new LoginRegisterResult(null, true, authToken.getAuthToken(), r.getUsername(), user.getPersonID());
            db.closeConnection(true);
            return loginResult;
        }
        catch (DataAccessException e) {
            try {db.closeConnection(false);}
            catch (DataAccessException d) {
                System.out.println(d.getMessage());
            }
            return new LoginRegisterResult(e.getMessage(), false);
        }
    }
}
