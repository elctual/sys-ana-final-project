/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author elifa
 */
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDAO {
    
    public boolean addPerson(Person person) {
        String sql = "INSERT INTO Person (PersonID, FirstName, LastName, DateOfBirth, Nationality) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, person.getPersonId());
            ps.setString(2, person.getFirstName());
            ps.setString(3, person.getLastName());
            ps.setDate(4, person.getDateOfBirth() != null ? java.sql.Date.valueOf(person.getDateOfBirth()) : null);
            ps.setString(5, person.getNationality());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("add person error"+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Person getPersonById(int personId) {
        String sql = "SELECT * FROM Person WHERE PersonID = ?";
        try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToPerson(rs);
            }
        } catch (SQLException e) {
            System.out.println("Get person by ID error "+ e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM Person";
        try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                persons.add(mapResultSetToPerson(rs));
            }
        } catch (SQLException e) {
            System.out.println("get all persons error"+ e.getMessage());
            e.printStackTrace();
        }
        return persons;
    }

    public boolean updatePerson(Person person) {
        String sql = "UPDATE Person SET FirstName = ?, LastName = ?, DateOfBirth = ?, Nationality = ? WHERE PersonID = ?";
        try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setDate(3, person.getDateOfBirth() != null ? java.sql.Date.valueOf(person.getDateOfBirth()) : null);
            ps.setString(4, person.getNationality());
            ps.setInt(5, person.getPersonId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("update person error"+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePerson(int personId) {
        String sql = "DELETE FROM Person WHERE PersonID = ?";
        try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, personId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("delete person error "+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Person mapResultSetToPerson(ResultSet rs) throws SQLException {
        java.sql.Date dob = rs.getDate("DateOfBirth");
        return new Person(
            rs.getInt("PersonID"),
            rs.getString("FirstName"),
            rs.getString("LastName"),
            dob != null ? dob.toLocalDate() : null,
            rs.getString("Nationality")
        );
    }
}
