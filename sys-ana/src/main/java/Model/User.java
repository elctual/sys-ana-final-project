/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author elifa
 */
import javax.swing.JFrame;

public class User {

    protected int userId;
    protected String username;
    protected String password;
    protected String email;
    protected int userType;

    public User() {
    }

    public User(int userId, String username, String password, String email, int userType) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getUserType() { return userType; }
    public void setUserType(int userType) { this.userType = userType; }

    public JFrame getMainFrame() {
        return new JFrame(); 
    }
}
