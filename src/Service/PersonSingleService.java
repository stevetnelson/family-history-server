package Service;

import Dao.*;
import Model.AuthToken;
import Model.Person;
import Result.Result;
import Result.PersonSingleResult;
import java.sql.Connection;

/**
 * Class that handles the logic of querying for a specific person
 */
public class PersonSingleService {
    /**
     * Queries the database for a specific person
     * @param personID string object of the PersonID
     * @return result of the command as a PersonSingleResult object
     */
    public Result personSingle(String personID, String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            PersonDao personDao = new PersonDao(conn);
            Person person = personDao.find(personID);
            if (person == null) {
                db.closeConnection(false);
                return new Result("Error: Person does not exist", false);
            }
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            AuthToken associatedAuthToken = authTokenDao.findWithAuthToken(authToken);
            if (!person.getAssociatedUsername().equals(associatedAuthToken.getUsername())) {
                db.closeConnection(false);
                return new Result("Error: Requested person does not belong to this user", false);
            }
            PersonSingleResult personSingleResult = new PersonSingleResult(null, true, person.getAssociatedUsername(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender(), person.getFatherID(), person.getMotherID(), person.getSpouseID());
            if (person.getFatherID() != null) {
                if (person.getFatherID().equals("")) { personSingleResult.setFatherID(null); }
            }
            if (person.getMotherID() != null) {
                if (person.getMotherID().equals("")) { personSingleResult.setMotherID(null); }
            }
            if (person.getSpouseID() != null) {
                if (person.getSpouseID().equals("")) { personSingleResult.setSpouseID(null); }
            }
            db.closeConnection(false);
            return personSingleResult;
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
