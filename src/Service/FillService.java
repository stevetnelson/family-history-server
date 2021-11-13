package Service;

import Dao.*;
import Model.User;
import Result.Result;

import java.sql.Connection;

/**
 * Class that handles the logic of filling database with ancestors of a user
 */
public class FillService {
    /**
     * Populates the server with family members of a specific user
     * @param username request object as a FillRequest object
     * @return result of the command as a FillResult object
     */
    public Result fill(String username, int generations) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            User user = userDao.find(username);
            PersonDao personDao = new PersonDao(conn);
            personDao.clearPersonTableUser(username);
            EventDao eventDao = new EventDao(conn);
            eventDao.clearEventTableUser(username);
            PopulateGenerations.populateUserGenerations(conn, user, generations);
            db.closeConnection(true);
            int numAdded = recurCount(generations, 1);
            String retString = "Successfully added " + numAdded + " persons " + "and " + numAdded * 3 + " events to the database.";
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
    private int recurCount(int generation, int multiplier) {
        int sum;
        if (generation > 0) {
            sum = recurCount(generation - 1, multiplier * 2);
            return multiplier + sum;
        }
        else {
            return multiplier;
        }
    }
}
