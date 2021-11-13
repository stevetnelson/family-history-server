package Service;

import Dao.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.Result;

import java.sql.Connection;
import java.util.UUID;

/**
 * Class that handles the logic of clearing and repopulating the database
 */
public class LoadService {
    /**
     * Clears the database then repopulates with new data
     * @param r request object as a LoadRequest object
     * @return result of the command as a Result object
     */
    public Result load(LoadRequest r) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            userDao.clearUserTable();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            authTokenDao.clearAuthTokenTable();
            for (User user : r.getMyUsers()) {
                if (user != null && user.getPassword() != null && user.getEmail() != null && user.getUsername() != null &&
                        user.getFirstName() != null && user.getLastName() != null && user.getGender() != null && user.getPersonID() != null &&
                        (user.getGender().toLowerCase().equals("m") || user.getGender().toLowerCase().equals("f"))) {
                    userDao.insertUser(user);
                    authTokenDao.insertAuthToken(new AuthToken(UUID.randomUUID().toString(), user.getUsername()));
                }
                else {
                    db.closeConnection(false);
                    return new Result("Error: Missing or invalid data members", false);
                }
            }
            PersonDao personDao = new PersonDao(conn);
            personDao.clearPersonTable();
            for (Person person : r.getMyPersons()) {
                if (person != null && person.getAssociatedUsername() != null && person.getPersonID() != null &&
                        person.getFirstName() != null && person.getLastName() != null && person.getGender() != null &&
                        (person.getGender().toLowerCase().equals("m") || person.getGender().toLowerCase().equals("f"))) {
                    personDao.insertPerson(person);
                }
                else {
                    db.closeConnection(false);
                    return new Result("Error: Missing or invalid data members", false);
                }

            }
            EventDao eventDao = new EventDao(conn);
            eventDao.clearEventTable();
            for (Event event : r.getMyEvents()) {
                if (event != null && event.getUsername() != null && event.getEventID() != null && event.getPersonID() != null &&
                        event.getCountry() != null && event.getCity() != null && event.getEventType() != null) {
                    eventDao.insertEvent(event);
                }
                else {
                    db.closeConnection(false);
                    return new Result("Error: Missing or invalid data members", false);
                }
            }
            db.closeConnection(true);
            String retString = "Successfully added " + r.getMyUsers().size() + " users, " + r.getMyPersons().size() + " persons, and " + r.getMyEvents().size() + " events to the database.";
            return new Result(retString, true);
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
