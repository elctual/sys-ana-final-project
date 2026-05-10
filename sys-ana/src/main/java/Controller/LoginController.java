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
import View.LoginFrame;
import View.Type1MainFrame;
import View.Type2MainFrame;

public class LoginController {

    private final LoginFrame view;
    private final UserController userController = new UserController();

    public LoginController(LoginFrame view) {
        this.view = view;
        view.setLoginAction(this::handleLogin);
    }

    private void handleLogin(String username, String password) {
        if (username.isBlank() || password.isBlank()) {
            view.showError("Username and password are required.");
            return;
        }
        User user = userController.login(username, password);
        if (user == null) {
            view.showError("Invalid username or password.");
            return;
        }
        view.dispose();
        if (user.getUserType() == 1) {           // Parent / Adult
            new Type1MainFrame(user).setVisible(true);
        } else {                                  // Child
            new Type2MainFrame(user).setVisible(true);
        }
    }
}
