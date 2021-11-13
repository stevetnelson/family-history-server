package Dao;

import Model.Person;
import Result.PersonSingleResult;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class used to access and edit Person table
 */
public class PersonDao {
    private final Connection conn;

    public PersonDao(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Inserts a Person into the Person table
     * @param person Person to add to the database
     */
    public void insertPerson(Person person) throws DataAccessException {
        String sql = "INSERT INTO Persons (AssociatedUsername, PersonID, FirstName, LastName, " +
                "Gender, FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getAssociatedUsername());
            stmt.setString(2, person.getPersonID());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Gets an Person object associated with a specific personID
     * @param personID personID used to find a given person
     * @return Person object associated with given personID
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("AssociatedUsername"), rs.getString("PersonID"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * Gets an Person object associated with a specific personID
     * @param associatedUsername personID used to find a given person
     * @return ArrayList object associated with given username
     */
    public ArrayList<PersonSingleResult> findPersonList(String associatedUsername) throws DataAccessException {
        PersonSingleResult person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            ArrayList<PersonSingleResult> myPeople = new ArrayList<>();
            while (rs.next()) {
                person = new PersonSingleResult(null, true, rs.getString("AssociatedUsername"), rs.getString("PersonID"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                if (rs.getString("FatherID") != null) {
                    if (rs.getString("FatherID").equals("")) { person.setFatherID(null); }
                }
                if (rs.getString("MotherID") != null) {
                    if (rs.getString("MotherID").equals("")) { person.setMotherID(null); }
                }
                if (rs.getString("SpouseID") != null) {
                    if (rs.getString("SpouseID").equals("")) { person.setSpouseID(null); }
                }
                myPeople.add(person);
            }
            return myPeople;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding people");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Clear out all people associated with a specific user
     */
    public void clearPersonTableUser(String username) throws DataAccessException {
        String sql = "DELETE FROM Persons WHERE AssociatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing people associated with a user");
        }

    }

    /**
     * Clear out entire Person table
     */
    public void clearPersonTable() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Persons";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
