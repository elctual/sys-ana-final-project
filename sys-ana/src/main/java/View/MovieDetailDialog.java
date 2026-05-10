package View;

import Controller.UserController;
import Model.Movie;
import Model.User;
import java.util.List;
import javax.swing.*;

public class MovieDetailDialog extends JDialog {

    public MovieDetailDialog(JFrame parent, boolean modal, Movie movie, UserController uc) {
        super(parent, modal);
        setTitle("Movie Details – " + movie.getTitle());
        buildUI(movie, uc);
        pack();
        setLocationRelativeTo(parent);
    }

    private void buildUI(Movie movie, UserController uc) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        p.add(bold("Title: ")           .append(safe(movie.getTitle()))           .toLabel());
        p.add(bold("Genre: ")           .append(safe(movie.getGenre()))           .toLabel());
        p.add(bold("Release Year: ")    .append(movie.getReleaseDate() != null ? String.valueOf(movie.getReleaseDate().getYear()) : "N/A").toLabel());
        p.add(bold("Language: ")        .append(safe(movie.getLanguage()))        .toLabel());
        p.add(bold("Country: ")         .append(safe(movie.getCountryOfOrigin())) .toLabel());
        p.add(bold("Director: ")        .append(personName(movie.getDirector()))  .toLabel());
        p.add(bold("Leading Actor: ")   .append(personName(movie.getLeadingActor()))   .toLabel());
        p.add(bold("Supporting Actor: ").append(personName(movie.getSupportingActor())).toLabel());
        p.add(bold("Watched: ")         .append(movie.isWatched() ? "Yes" : "No").toLabel());
        p.add(bold("Rating: ")          .append(String.valueOf(movie.getRating())).toLabel());
        p.add(bold("Parental Restriction: ").append(movie.isParentalRestriction() ? "Yes" : "No").toLabel());

        // About
        p.add(new JLabel(" "));
        JLabel aboutLabel = new JLabel("<html><b>About:</b><br>" + safe(movie.getAbout()) + "</html>");
        aboutLabel.setMaximumSize(new java.awt.Dimension(500, 200));
        p.add(aboutLabel);

        // Comments
        p.add(new JLabel(" "));
        p.add(new JLabel("<html><b>Comments:</b> " + safe(movie.getComments()) + "</html>"));

        // Family ratings (Function 13) – show all users' view
        p.add(new JLabel(" "));
        p.add(new JLabel("<html><b>=== Family Ratings ===</b></html>"));
        List<User> users = uc.getAllUsers();
        // In this simplified model, the rating is per-movie not per-user.
        // We show the single stored rating as "community rating".
        p.add(new JLabel("Shared rating for this movie: " + movie.getRating() + " / 10"));
        p.add(new JLabel("Total family members: " + users.size()));

        JButton close = new JButton("Close");
        close.addActionListener(e -> dispose());
        JPanel btns = new JPanel();
        btns.add(close);
        p.add(btns);

        setContentPane(new JScrollPane(p));
    }

    private String safe(String s) { return s != null ? s : "N/A"; }
    private String personName(Model.Person p) {
        return p != null ? p.getFirstName() + " " + p.getLastName() : "N/A";
    }

    private SB bold(String s) { return new SB("<html><b>" + s + "</b>"); }

    private static class SB {
        private final StringBuilder sb;
        SB(String s) { sb = new StringBuilder(s); }
        SB append(String s) { sb.append(s); return this; }
        JLabel toLabel() { return new JLabel(sb + "</html>"); }
    }
}
