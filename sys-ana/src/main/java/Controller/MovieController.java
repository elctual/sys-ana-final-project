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
import java.util.List;

public class MovieController {

    private final MovieDAO movieDAO = new MovieDAO();

    public List<Movie> getAllMovies() {
        return movieDAO.getAllMovies();
    }

    public List<Movie> getUnrestrictedMovies() {
        return movieDAO.getUnrestrictedMovies();
    }

    public Movie getMovieByID(int id) {
        return movieDAO.getMovieById(id);
    }

    public boolean addMovie(Movie m) {
        return movieDAO.addMovie(m);
    }

    public boolean updateMovie(Movie m) {
        return movieDAO.updateMovie(m);
    }

    public boolean removeMovie(int movieId) {
        return movieDAO.deleteMovie(movieId);
    }

    public boolean setParentalRestriction(int movieId, boolean restricted) {
        Movie m = movieDAO.getMovieById(movieId);
        if (m == null) return false;
        m.setParentalRestriction(restricted);
        return movieDAO.updateMovie(m);
    }

    public boolean markWatched(int movieId, boolean watched) {
        return movieDAO.markWatched(movieId, watched);
    }

    public boolean rateMovie(int movieId, int rating) {
        return movieDAO.rateMovie(movieId, rating);
    }

    public boolean addComment(int movieId, String comment) {
        return movieDAO.addComment(movieId, comment);
    }

    public boolean moderateComment(int movieId) {
        return movieDAO.deleteComment(movieId);
    }

    public List<Movie> searchMovies(String directorKeyword, String genre, int year) {
        MovieFilter filter = new MovieFilter();
        if (directorKeyword != null && !directorKeyword.isEmpty()) {
            filter.setDirectorName(directorKeyword);
        }
        if (genre != null && !genre.equals("All")) {
            filter.setGenre(genre);
        }
        if (year > 0) {
            filter.setReleaseYear(year);
        }
        return movieDAO.searchMovies(filter);
    }
}
