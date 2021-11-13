package Service;

import Dao.*;
import Handler.JsonSerializer;
import Model.Event;
import Model.Person;
import Model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class PopulateGenerations {
    Random random = new Random();
    ArrayList<String> boyNames = new ArrayList<>();
    ArrayList<String> girlNames = new ArrayList<>();
    ArrayList<String> lastNames = new ArrayList<>();

    private void createLists() {
        try {
            Scanner scanner = new Scanner(new File("json/mnames.json"));
            String boyString = scanner.useDelimiter("\\A").next();
            scanner.close();
            scanner = new Scanner(new File("json/fnames.json"));
            String girlString = scanner.useDelimiter("\\A").next();
            scanner.close();
            scanner = new Scanner(new File("json/snames.json"));
            String lastNameString = scanner.useDelimiter("\\A").next();
            scanner.close();

            boyNames = JsonSerializer.deserialize(boyString, NameHolder.class).getData();
            girlNames = JsonSerializer.deserialize(girlString, NameHolder.class).getData();
            lastNames = JsonSerializer.deserialize(lastNameString, NameHolder.class).getData();
        }
        catch (FileNotFoundException e) {}
    }

    public static void populateUserGenerations(Connection conn, User user, int generations) throws DataAccessException {
        PopulateGenerations populateGenerations = new PopulateGenerations();
        populateGenerations.createLists();
        PersonDao personDao = new PersonDao(conn);
        String[] parents = populateUserRecur(conn, personDao, user.getUsername(), 2000, generations, populateGenerations.boyNames, populateGenerations.girlNames, populateGenerations.lastNames);
        Person person = new Person(user.getUsername(), user.getPersonID(), user.getFirstName(), user.getLastName(), user.getGender());
        person.setFatherID(parents[1]);
        person.setMotherID(parents[0]);
        personDao.insertPerson(person);
        createEvents(user.getPersonID(), user.getUsername(), 2020, conn);
    }

    private static String[] populateUserRecur (Connection conn, PersonDao personDao, String associatedUsername, int year, int remainingGen, ArrayList<String> boyNames, ArrayList<String> girlNames, ArrayList<String> lastNames) throws DataAccessException {
        String[] retArray;
        if (remainingGen > 1) {
            String[] parents1 = populateUserRecur(conn, personDao, associatedUsername, year-20, remainingGen-1, boyNames, girlNames, lastNames);
            String[] parents2 = populateUserRecur(conn, personDao, associatedUsername, year-20, remainingGen-1, boyNames, girlNames, lastNames);
            Person man = new Person(associatedUsername, UUID.randomUUID().toString(), boyNames.get(37 * year % 100), lastNames.get(37 * year % 100), "m");
            Person woman = new Person(associatedUsername, UUID.randomUUID().toString(), girlNames.get(37 * year % 100), lastNames.get(37 * year % 100), "f", parents2[1], parents2[0], man.getPersonID());
            man.setMotherID(parents1[0]);
            man.setFatherID(parents1[1]);
            man.setSpouseID(woman.getPersonID());
            personDao.insertPerson(man);
            personDao.insertPerson(woman);
            createEvents(man.getPersonID(), man.getAssociatedUsername(), year, conn);
            createEvents(woman.getPersonID(), woman.getAssociatedUsername(), year, conn);
            retArray = new String[2];
            retArray[0] = woman.getPersonID();
            retArray[1] = man.getPersonID();
        }
        else {
            Person man = new Person(associatedUsername, UUID.randomUUID().toString(), boyNames.get(37 * year % 100), lastNames.get(37 * year % 100), "m");
            Person woman = new Person(associatedUsername, UUID.randomUUID().toString(), girlNames.get(37 * year % 100), lastNames.get(37 * year % 100), "f");
            man.setSpouseID(woman.getPersonID());
            woman.setSpouseID(man.getPersonID());
            personDao.insertPerson(man);
            personDao.insertPerson(woman);
            createEvents(man.getPersonID(), man.getAssociatedUsername(), year, conn);
            createEvents(woman.getPersonID(), woman.getAssociatedUsername(), year, conn);
            retArray = new String[2];
            retArray[0] = woman.getPersonID();
            retArray[1] = man.getPersonID();
        }
        return retArray;
    }

    private static void createEvents (String personID, String associatedUsername, int year, Connection conn) {
        Event birth = new Event(UUID.randomUUID().toString(), associatedUsername, personID, 59.26f, 17.89f, "Sweden", "Stockholm", "birth", year-40);
        Event marriage = new Event(UUID.randomUUID().toString(), associatedUsername, personID, 59.26f, 17.89f, "Sweden", "Stockholm", "marriage", year - 20);
        Event death = new Event(UUID.randomUUID().toString(), associatedUsername, personID, 59.26f, 17.89f, "Sweden", "Stockholm", "death", year);
        try {
            EventDao eventDao = new EventDao(conn);
            eventDao.insertEvent(birth);
            eventDao.insertEvent(marriage);
            eventDao.insertEvent(death);
        }
        catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }


}
