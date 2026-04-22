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
    
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public Person getLeadingActor() {
        return leadingActor;
    }

    public void setLeadingActor(Person leadingActor) {
        this.leadingActor = leadingActor;
    }

    public Person getSupportingActor() {
        return supportingActor;
    }

    public void setSupportingActor(Person supportingActor) {
        this.supportingActor = supportingActor;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setWatched(boolean watched) {
        isWatched = watched;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public boolean isParentalRestriction() {
        return parentalRestriction;
    }

    public void setParentalRestriction(boolean parentalRestriction) {
        this.parentalRestriction = parentalRestriction;
    }
}
