package Test;

import Dao.DataAccessException;
import Dao.Database;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.Result;
import Service.ClearService;
import Service.LoadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoadServiceTest {
    LoadService loadService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
        loadService = new LoadService();
    }

    @Test
    public void fillCorrectly() throws DataAccessException {
        Database db = new Database();
        ArrayList<Person> person = new ArrayList<>();
        ArrayList<User> user = new ArrayList<>();
        ArrayList<Event> event = new ArrayList<>();
        person.add(new Person("JeanValJean", "24601", "Jean",
                "Val Jean", "M", "", "", ""));
        user.add(new User("stnelson", "84606", "mypass",
                "nelson.stevent@gmail.com", "Steven", "Nelson", "M"));
        event.add(new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016));
        LoadRequest loadRequest = new LoadRequest(user, person, event);
        Result result = loadService.load(loadRequest);
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Successfully added 1 users, 1 persons, and 1 events to the database.");
    }

    @Test
    public void fillPartially() throws DataAccessException {
        Database db = new Database();
        ArrayList<Person> person = new ArrayList<>();
        ArrayList<User> user = new ArrayList<>();
        ArrayList<Event> event = new ArrayList<>();
        person.add(new Person("JeanValJean", "24601", "Jean",
                "Val Jean", "M", "", "", ""));
        LoadRequest loadRequest = new LoadRequest(user, person, event);
        Result result = loadService.load(loadRequest);
        assertNotNull(result);
        assertEquals(result.getClass(), Result.class);
        assertEquals(result.getMessage(), "Successfully added 0 users, 1 persons, and 0 events to the database.");
    }
}
