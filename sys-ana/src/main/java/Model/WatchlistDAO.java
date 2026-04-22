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

public class WatchlistDAO {
    
    private DatabaseManager dbm;

    public WatchlistDAO(DatabaseManager dbm) {
        this.dbm = DatabaseManager.getInstance();;
    }

   public boolean addToWatchlist(int userId, int movieId) {
        String sql = "INSERT INTO Watchlist (UserId, MovieId, AddedDate) VALUES (?, ?, CURRENT_DATE)";
        try (PreparedStatement ps = dbm.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, movieId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("could not add to watchlist "+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromWatchlist(int userId, int movieId) {
        String sql = "DELETE FROM Watchlist WHERE UserId = ? AND MovieId = ?";
        try (PreparedStatement ps = dbm.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, movieId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("could not remove from watchlist "+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Movie> getWatchlistByUser(int userId) {
        List<Movie> movies = new ArrayList<>();
        MovieDAO movieDAO = new MovieDAO();
        String sql = """
                SELECT m.*,
                       d.PersonID as dir_id, d.FirstName as dir_fn, d.LastName as dir_ln,
                       d.DateOfBirth as dir_dob, d.Nationality as dir_nat,
                       la.PersonID as la_id, la.FirstName as la_fn, la.LastName as la_ln,
                       la.DateOfBirth as la_dob, la.Nationality as la_nat,
                       sa.PersonID as sa_id, sa.FirstName as sa_fn, sa.LastName as sa_ln,
                       sa.DateOfBirth as sa_dob, sa.Nationality as sa_nat
                FROM Watchlist w
                JOIN Movies m ON w.MovieId = m.MovieID
                LEFT JOIN Person d  ON m.DirectorId        = d.PersonID
                LEFT JOIN Person la ON m.LeadingActorId     = la.PersonID
                LEFT JOIN Person sa ON m.SupportingActorId  = sa.PersonID
                WHERE w.UserId = ?
                ORDER BY w.AddedDate DESC
                """;
        try (PreparedStatement ps = dbm.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                movies.add(movieDAO.mapResultSetToMovie(rs));
            }
        } catch (SQLException e) {
            System.out.println("could not get watchlist by user "+ e.getMessage());
            e.printStackTrace();
        }
        return movies;
    }
}
