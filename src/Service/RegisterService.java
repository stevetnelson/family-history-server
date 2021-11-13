package Service;

import Dao.*;
import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.Result;
import Result.LoginRegisterResult;
import java.sql.Connection;
import java.util.UUID;

/**
 * Class that handles the logic of registering a new user
 */
public class RegisterService {
    /**
     * Creates a new user
     * @param r request object as a RegisterRequest object
     * @return result of the command as a RegisterResult object
     */
    public Result register(RegisterRequest r) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            if (userDao.find(r.getUsername()) == null) {
                User newUser = new User(r.getUsername(), UUID.randomUUID().toString(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender());
                userDao.insertUser(newUser);
                AuthToken authToken = new AuthToken(UUID.randomUUID().toString(), r.getUsername());
                AuthTokenDao authTokenDao = new AuthTokenDao(conn);
                authTokenDao.insertAuthToken(authToken);
                LoginRegisterResult registerResult = new LoginRegisterResult(null, true, authToken.getAuthToken(), newUser.getUsername(), newUser.getPersonID());
                PopulateGenerations.populateUserGenerations(conn, newUser, 4);
                db.closeConnection(true);
                return registerResult;
            }
            else {
                Result registerResult = new Result("Error: Username already taken by another user", false);
                db.closeConnection(false);
                return registerResult;
            }
        }
        catch (DataAccessException e) {
            try {db.closeConnection(false);}
            catch (DataAccessException d) {
                System.out.println(d.getMessage());
            }
            return new Result(e.getMessage(), false);
        }
    }


}
