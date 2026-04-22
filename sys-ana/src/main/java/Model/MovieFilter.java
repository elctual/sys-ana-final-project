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
public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getIsWatched() {
        return isWatched;
    }

    public void setIsWatched(Boolean isWatched) {
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
