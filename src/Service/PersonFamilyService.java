package Service;

import Dao.AuthTokenDao;
import Dao.DataAccessException;
import Dao.Database;
import Dao.PersonDao;
import Model.AuthToken;
import Result.PersonFamilyResult;
import Result.PersonSingleResult;
import Result.Result;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that handles the logic of querying for many people
 */
public class PersonFamilyService {
    /**
     * Queries database for people
     * @return result of the command as a PersonFamilyResult object
     */
    public Result personFamily(String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            AuthToken associatedAuthToken = authTokenDao.findWithAuthToken(authToken);
            PersonDao personDao = new PersonDao(conn);
            ArrayList<PersonSingleResult> personList = personDao.findPersonList(associatedAuthToken.getUsername());
            PersonFamilyResult personResult = new PersonFamilyResult(null, true, personList);
            db.closeConnection(false);
            return personResult;
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
