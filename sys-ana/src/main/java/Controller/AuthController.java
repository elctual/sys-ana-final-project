/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author elifa
 */
import Model.*;
import View.*;
public class AuthController {

    public static final int DEFAULT_USER_TYPE = 1;
    DatabaseManager dbm;
    UserDAO userDAO = new UserDAO();
    
    public AuthController() {
       this.dbm = DatabaseManager.getInstance(); 
    }

    public boolean login(String username, String password) {
        
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }

        boolean isValid = userDAO.validateUser(username, password);

        if (isValid) {
            System.out.println("login success" + username);
            return true;
        } else {
            System.out.println("login error");
            return false;
        }
    }
    
     public void handleLogin(String username, String password, LoginFrame loginFrame) {
        User user = userDAO.getUserByCredentials(username, password);

        if (user == null) {
            loginFrame.showError("Invalid username or password.");
            return;
        }

        if (user.isParent()) {
            new Type1MainFrame().setVisible(true);
        } else {
            new Type2MainFrame().setVisible(true);
        }

        loginFrame.dispose();
    }
     
    public boolean register(String username, String password, String email) {
            //public User(int userId, String username, String password, String email, int userType)

        User user = new User(0, username, password, email, DEFAULT_USER_TYPE);
        userDAO.createUser(user);
        boolean isCreated = userDAO.createUser(user);

        if (isCreated) {
            System.out.println("user is now on db " + username);
        } else {
            System.out.println("user can not be created in db ");
        }

        return isCreated;
    }
}