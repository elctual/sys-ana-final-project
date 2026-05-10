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
import java.sql.*;
import java.util.*;

public class AnalyticsController {

    public List<Movie> getMostWatchedMovies() {
        MovieDAO dao = new MovieDAO();
        MovieFilter f = new MovieFilter();
        f.setIsWatched(true);
        return dao.searchMovies(f);
    }

    public Map<Movie, Double> getAverageRatings() {
        Map<Movie, Double> result = new LinkedHashMap<>();
        MovieDAO dao = new MovieDAO();
        for (Movie m : dao.getAllMovies()) {
            result.put(m, (double) m.getRating());
        }
        return result;
    }

    public Map<User, Integer> getWatchProgressPerUser() {
        Map<User, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT u.UserId, u.Username, u.Password, u.Email, u.UserType, " +
                     "COUNT(w.MovieId) AS cnt " +
                     "FROM `User` u " +
                     "LEFT JOIN Watchlist w ON u.UserId = w.UserId " +
                     "GROUP BY u.UserId";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User(
                    rs.getInt("UserId"),
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Email"),
                    rs.getInt("UserType")
                );
                result.put(u, rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            System.err.println("Analytics error: " + e.getMessage());
        }
        return result;
    }
}
