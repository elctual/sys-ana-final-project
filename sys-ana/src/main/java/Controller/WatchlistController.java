/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author elifa
 */

import Model.DatabaseManager;
import Model.Movie;
import Model.WatchlistDAO;
import java.util.List;

public class WatchlistController {

    private final WatchlistDAO watchlistDAO = new WatchlistDAO(DatabaseManager.getInstance());

    public boolean addToWatchlist(int userId, int movieId) {
        return watchlistDAO.addToWatchlist(userId, movieId);
    }

    public boolean removeFromWatchlist(int userId, int movieId) {
        return watchlistDAO.removeFromWatchlist(userId, movieId);
    }

    public List<Movie> getWatchlist(int userId) {
        return watchlistDAO.getWatchlistByUser(userId);
    }
}
