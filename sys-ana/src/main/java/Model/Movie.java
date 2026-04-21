package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author elifa
 */
import java.time.LocalDate;
public class Movie {
    private int movieID;
    private String title;
    private LocalDate releaseDate;
    private String language;
    private String countryOfOrigin;
    private String genre;
    private Person director;
    private Person leadingActor;
    private Person supportingActor;
    private String about;
    private boolean isWatched;
    private int rating;
    private String comments;
    private String poster;
    private boolean parentalRestriction;
    
    public Movie(int movieID, String title, LocalDate releaseDate, String language, 
                 String countryOfOrigin, String genre, Person director, Person leadingActor, 
                 Person supportingActor, String about, boolean isWatched, int rating, 
                 String comments, String poster, boolean parentalRestriction) {
        this.movieID = movieID;
        this.title = title;
        this.releaseDate = releaseDate;
        this.language = language;
        this.countryOfOrigin = countryOfOrigin;
        this.genre = genre;
        this.director = director;
        this.leadingActor = leadingActor;
        this.supportingActor = supportingActor;
        this.about = about;
        this.isWatched = isWatched;
        this.rating = rating;
        this.comments = comments;
        this.poster = poster;
        this.parentalRestriction = parentalRestriction;
    }
}
