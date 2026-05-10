/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author elifa
 */

import Model.User;
import Model.UserDAO;
import java.util.List;

public class UserController {

    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        return userDAO.getUserByCredentials(username, password);
    }

    public boolean createUser(User user) {
        return userDAO.createUser(user);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    public boolean resetPassword(int userId, String newPassword) {
        return userDAO.updatePassword(userId, newPassword);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
}
