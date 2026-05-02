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


public class UserDAO {

    public User getUserByCredentials(String username, String password) {
      String sql = "SELECT * FROM `User` WHERE Username = ? AND Password = ?";
      try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
          ps.setString(1, username);
          ps.setString(2, password);
          ResultSet rs = ps.executeQuery();
          if (rs.next()) {
              return mapResultSetToUser(rs);
          }
      } catch (SQLException e) {
          System.out.println("get user by credentials error"+ e.getMessage());
          e.printStackTrace();
      }
      return null;
  }

  public List<User> getAllUsers() {
      List<User> users = new ArrayList<>();
      String sql = "SELECT * FROM `User`";
      try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql);
           ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
              users.add(mapResultSetToUser(rs));
          }
      } catch (SQLException e) {
          System.out.println("get all users error "+ e.getMessage());
          e.printStackTrace();
      }
      return users;
  }
    public boolean createUser(User user) {
       
        String sql = "INSERT INTO `User` (Username, Password, UserType, Email) VALUES (?, ?, ?, ?)";

       try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getUserType());
            ps.setString(4, user.getEmail());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("create user error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

  public boolean updateUser(User user) {
      String sql = "UPDATE `User` SET Username = ?, Password = ?, UserType = ?, Email = ? WHERE UserId = ?";
      try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
          ps.setString(1, user.getUsername());
          ps.setString(2, user.getPassword());
          ps.setInt(3, user.getUserType());
          ps.setString(4, user.getEmail());
          ps.setInt(5, user.getUserId());
          return ps.executeUpdate() > 0;
      } catch (SQLException e) {
          System.out.println("update user error"+ e.getMessage());
          e.printStackTrace();
          return false;
      }
  }

  public boolean deleteUser(int userId) {
      String sql = "DELETE FROM `User` WHERE UserId = ?";
      try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
          ps.setInt(1, userId);
          return ps.executeUpdate() > 0;
      } catch (SQLException e) {
          System.out.println("delete user error"+ e.getMessage());
          e.printStackTrace();
          return false;
      }
  }

  public boolean updatePassword(int userId, String newPassword) {
      String sql = "UPDATE `User` SET Password = ? WHERE UserId = ?";
      try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
          ps.setString(1, newPassword);
          ps.setInt(2, userId);
          return ps.executeUpdate() > 0;
      } catch (SQLException e) {
          System.out.println("update password error "+ e.getMessage());
          e.printStackTrace();
          return false;
      }
  }
  
  public boolean validateUser(String username, String password) {
        User user = getUserByCredentials(username, password);
        return user != null;
    }

  private User mapResultSetToUser(ResultSet rs) throws SQLException {
      // public User(int userId, String username, String password, String email, int userType) {
      return new User(
          rs.getInt("UserId"),
          rs.getString("Username"),
          rs.getString("Password"),
          rs.getString("Email"),
          rs.getInt("UserType")
      );
  }
}
