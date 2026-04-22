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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MovieDAO {
    public MovieDAO(){}
    
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>(); 
        String sql = "SELECT m.*, " +
            "d.PersonID as dir_id, d.FirstName as dir_fn, d.LastName as dir_ln, d.DateOfBirth as dir_dob, d.Nationality as dir_nat, " +
            "la.PersonID as la_id, la.FirstName as la_fn, la.LastName as la_ln, la.DateOfBirth as la_dob, la.Nationality as la_nat, " +
            "sa.PersonID as sa_id, sa.FirstName as sa_fn, sa.LastName as sa_ln, sa.DateOfBirth as sa_dob, sa.Nationality as sa_nat " +
            "FROM Movies m " +
            "LEFT JOIN Person d ON m.DirectorId = d.PersonID " +
            "LEFT JOIN Person la ON m.LeadingActorId = la.PersonID " +
            "LEFT JOIN Person sa ON m.SupportingActorId = sa.PersonID";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Movie movie = mapResultSetToMovie(rs);
                movies.add(movie);
            }

        } catch (SQLException e) {
            System.err.println("getAllMovies error: " + e.getMessage());
            e.printStackTrace();
        }

        return movies; 
    }
    
    public Movie mapResultSetToMovie(ResultSet rs) throws SQLException {
    
        Person director = null;
        int dirId = rs.getInt("dir_id"); 
        if (!rs.wasNull()) { 
            java.sql.Date dirDob = rs.getDate("dir_dob");
            director = new Person(
                dirId,
                rs.getString("dir_fn"),
                rs.getString("dir_ln"),
                (dirDob != null) ? dirDob.toLocalDate() : null,
                rs.getString("dir_nat")
            );
        }

        Person leadingActor = null;
        int laId = rs.getInt("la_id");
        if (!rs.wasNull()) {
            java.sql.Date laDob = rs.getDate("la_dob");
            leadingActor = new Person(
                laId,
                rs.getString("la_fn"),
                rs.getString("la_ln"),
                (laDob != null) ? laDob.toLocalDate() : null,
                rs.getString("la_nat")
            );
        }

        Person supportingActor = null;
        int saId = rs.getInt("sa_id");
        if (!rs.wasNull()) {
            java.sql.Date saDob = rs.getDate("sa_dob");
            supportingActor = new Person(
                saId,
                rs.getString("sa_fn"),
                rs.getString("sa_ln"),
                (saDob != null) ? saDob.toLocalDate() : null,
                rs.getString("sa_nat")
            );
        }

        java.sql.Date releaseSqlDate = rs.getDate("ReleaseDate"); 
        LocalDate parsedReleaseDate = (releaseSqlDate != null) ? releaseSqlDate.toLocalDate() : null;

        Movie m = new Movie(
            rs.getInt("MovieID"),              
            rs.getString("Title"),             
            parsedReleaseDate,                 
            rs.getString("Language"),          
            rs.getString("CountryOfOrigin"),   
            rs.getString("Genre"),             
            director,                          
            leadingActor,                      
            supportingActor,                   
            rs.getString("About"),             
            rs.getBoolean("IsWatched"),        
            rs.getInt("Rating"),               
            rs.getString("Comments"),          
            rs.getString("Poster"),            
            rs.getBoolean("ParentalRestriction") 
        );

        return m;
    }

    public Movie getMovieById(int id) {
        String sql = "SELECT m.*, " +
                    "d.PersonID as dir_id, d.FirstName as dir_fn, d.LastName as dir_ln, d.DateOfBirth as dir_dob, d.Nationality as dir_nat, " +
                    "la.PersonID as la_id, la.FirstName as la_fn, la.LastName as la_ln, la.DateOfBirth as la_dob, la.Nationality as la_nat, " +
                    "sa.PersonID as sa_id, sa.FirstName as sa_fn, sa.LastName as sa_ln, sa.DateOfBirth as sa_dob, sa.Nationality as sa_nat " +
                    "FROM Movies m " +
                    "LEFT JOIN Person d ON m.DirectorId = d.PersonID " +
                    "LEFT JOIN Person la ON m.LeadingActorId = la.PersonID " +
                    "LEFT JOIN Person sa ON m.SupportingActorId = sa.PersonID " +
                     "WHERE m.MovieID = ?";

        Movie movie = null;
         try (Connection conn = DatabaseManager.getInstance().getConnection(); 
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        movie = mapResultSetToMovie(rs); 
                    }
                }

            } catch (SQLException e) {
                System.out.println("getMovieByID error" + e.getMessage());
                e.printStackTrace();
            }
        return movie;
    }

    //returns true if success
    public boolean addMovie(Movie m) {

        String sql = "INSERT INTO Movies (MovieID, Title, ReleaseDate, Language, CountryOfOrigin, Genre, DirectorId, IsWatched, LeadingActorId, SupportingActorId, Rating, About) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, m.getMovieID());
            pstmt.setString(2, m.getTitle());
            pstmt.setDate(3, java.sql.Date.valueOf(m.getReleaseDate())); 
            pstmt.setString(4, m.getLanguage());
            pstmt.setString(5, m.getCountryOfOrigin());
            pstmt.setString(6, m.getGenre());

            pstmt.setInt(7, m.getDirector().getPersonId()); 

            pstmt.setBoolean(8, m.isWatched());

            pstmt.setInt(9, m.getLeadingActor().getPersonId());
            pstmt.setInt(10, m.getSupportingActor().getPersonId());
            pstmt.setInt(11, m.getRating());
            pstmt.setString(12, m.getAbout());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; 

        } catch (SQLException e) {
            System.out.println("Could not add movie " + e.getMessage());
        }
        return false;

    }

    //returns true if success
    public boolean updateMovie(Movie m) {
        String sql = "UPDATE Movies SET Title = ?, ReleaseDate = ?, Language = ?, CountryOfOrigin = ?, "
                   + "Genre = ?, DirectorId = ?, IsWatched = ?, LeadingActorId = ?, "
                   + "SupportingActorId = ?, Rating = ?, About = ? "
                   + "WHERE MovieID = ?"; 
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, m.getTitle());
            pstmt.setDate(2, java.sql.Date.valueOf(m.getReleaseDate()));
            pstmt.setString(3, m.getLanguage());
            pstmt.setString(4, m.getCountryOfOrigin());
            pstmt.setString(5, m.getGenre());
            pstmt.setInt(6, m.getDirector().getPersonId());
            pstmt.setBoolean(7, m.isWatched());
            pstmt.setInt(8, m.getLeadingActor().getPersonId());
            pstmt.setInt(9, m.getSupportingActor().getPersonId());
            pstmt.setInt(10, m.getRating());
            pstmt.setString(11, m.getAbout());

            pstmt.setInt(12, m.getMovieID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; 
        } catch (SQLException e) {
            System.out.println("Could not update movie: " + e.getMessage());
            return false;
        }
    }

    // if success returns true
    public boolean deleteMovie(int id) {
        String sql = "DELETE FROM movies WHERE MovieID="+id+";";
        try(Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0; 
        }catch(SQLException e){
            System.out.println("Could not delete movie" + e.getMessage());
        }
        return false; 
    }

    public List<Movie> searchMovies(MovieFilter filter) {
        List<Movie> movies = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT m.*, " +
            "d.PersonID as dir_id, d.FirstName as dir_fn, d.LastName as dir_ln, d.DateOfBirth as dir_dob, d.Nationality as dir_nat, " +
            "la.PersonID as la_id, la.FirstName as la_fn, la.LastName as la_ln, la.DateOfBirth as la_dob, la.Nationality as la_nat, " +
            "sa.PersonID as sa_id, sa.FirstName as sa_fn, sa.LastName as sa_ln, sa.DateOfBirth as sa_dob, sa.Nationality as sa_nat " +
            "FROM Movies m " +
            "LEFT JOIN Person d  ON m.DirectorId        = d.PersonID " +
            "LEFT JOIN Person la ON m.LeadingActorId    = la.PersonID " +
            "LEFT JOIN Person sa ON m.SupportingActorId = sa.PersonID " +
            "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (filter.getGenre() != null && !filter.getGenre().isEmpty()) {
            sql.append("AND m.Genre = ? ");
            params.add(filter.getGenre());
        }

        if (filter.getDirectorName() != null && !filter.getDirectorName().isEmpty()) {
            sql.append("AND CONCAT(d.FirstName, ' ', d.LastName) LIKE ? ");
            params.add("%" + filter.getDirectorName() + "%");
        }

        if (filter.getReleaseYear() > 0) {
            sql.append("AND YEAR(m.ReleaseDate) = ? ");
            params.add(filter.getReleaseYear());
        }

        if (filter.getLanguage() != null && !filter.getLanguage().isEmpty()) {
            sql.append("AND m.Language = ? ");
            params.add(filter.getLanguage());
        }

        if (filter.getIsWatched() != null) {
            sql.append("AND m.IsWatched = ? ");
            params.add(filter.getIsWatched());
        }

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movies.add(mapResultSetToMovie(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("searchMovies error: " + e.getMessage());
            e.printStackTrace();
        }

        return movies;
    }

    public List<Movie> getUnrestrictedMovies() {
        List<Movie> movies = new ArrayList<>();

        String sql = "SELECT m.*, " +
                     "d.PersonID as dir_id, d.FirstName as dir_fn, d.LastName as dir_ln, d.DateOfBirth as dir_dob, d.Nationality as dir_nat, " +
                     "la.PersonID as la_id, la.FirstName as la_fn, la.LastName as la_ln, la.DateOfBirth as la_dob, la.Nationality as la_nat, " +
                     "sa.PersonID as sa_id, sa.FirstName as sa_fn, sa.LastName as sa_ln, sa.DateOfBirth as sa_dob, sa.Nationality as sa_nat " +
                     "FROM Movies m " +
                     "LEFT JOIN Person d  ON m.DirectorId        = d.PersonID " +
                     "LEFT JOIN Person la ON m.LeadingActorId    = la.PersonID " +
                     "LEFT JOIN Person sa ON m.SupportingActorId = sa.PersonID " +
                     "WHERE m.ParentalRestriction = FALSE";

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                movies.add(mapResultSetToMovie(rs));
            }

        } catch (SQLException e) {
            System.err.println("getUnrestrictedMovies error: " + e.getMessage());
            e.printStackTrace();
        }

        return movies;
    }
    
    public boolean markWatched(int movieId, boolean watched) {
        String sql = "UPDATE Movies SET IsWatched = ? WHERE MovieID = ?";
        try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setBoolean(1, watched);
            ps.setInt(2, movieId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("error mark watched"+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean rateMovie(int movieId, int rating) {
        String sql = "UPDATE Movies SET Rating = ? WHERE MovieID = ?";
        try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, rating);
            ps.setInt(2, movieId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("rate movie error "+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean addComment(int movieId, String comment) {
        String sql = "UPDATE Movies SET Comments = ? WHERE MovieID = ?";
        try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, comment);
            ps.setInt(2, movieId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("add comment error "+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteComment(int movieId) {
        String sql = "UPDATE Movies SET Comments = NULL WHERE MovieID = ?";
        try (PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, movieId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("error delete comment"+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}