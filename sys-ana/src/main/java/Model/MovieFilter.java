/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author elifa
 */
public class MovieFilter {

    private String genre;
    private String directorName;
    private int releaseYear;
    private String language;
    private Boolean isWatched;

    public MovieFilter() {
    }

    public MovieFilter(String genre, String directorName, int releaseYear, String language, Boolean isWatched) {
        this.genre = genre;
        this.directorName = directorName;
        this.releaseYear = releaseYear;
        this.language = language;
        this.isWatched = isWatched;
    }

    @Override
    public String toString() {
        return "MovieFilter{" +
                "genre='" + genre + '\'' +
                ", directorName='" + directorName + '\'' +
                ", releaseYear=" + releaseYear +
                ", language='" + language + '\'' +
                ", isWatched=" + isWatched +
                '}';
    }
}
