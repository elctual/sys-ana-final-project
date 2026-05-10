package View;

import Controller.MovieController;
import Controller.UserController;
import Controller.WatchlistController;
import Model.Movie;
import Model.User;
import java.util.List;
import javax.swing.*;


public class Type2MainFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Type2MainFrame.class.getName());

    private final User currentUser;
    private final MovieController     movieController     = new MovieController();
    private final UserController      userController      = new UserController();
    private final WatchlistController watchlistController = new WatchlistController();

    private JTable tblMovies;
    private JTextField txtSearch, txtYear;
    private JComboBox<String> cmbGenre;

    public Type2MainFrame(User user) {
        this.currentUser = user;
        initComponents();
        loadMovies();
        setLocationRelativeTo(null);
        setTitle("MovieCritics – Welcome, " + user.getUsername());
    }

    private void loadMovies() {
        refreshMovieTable(movieController.getUnrestrictedMovies());
    }

    private void refreshMovieTable(List<Movie> movies) {
        String[] cols = {"ID", "Title", "Genre", "Year", "Director", "Rating", "Watched"};
        Object[][] data = new Object[movies.size()][cols.length];
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            data[i][0] = m.getMovieID();
            data[i][1] = m.getTitle();
            data[i][2] = m.getGenre();
            data[i][3] = m.getReleaseDate() != null ? m.getReleaseDate().getYear() : "";
            data[i][4] = m.getDirector() != null ? m.getDirector().getFirstName() + " " + m.getDirector().getLastName() : "";
            data[i][5] = m.getRating();
            data[i][6] = m.isWatched() ? "Yes" : "No";
        }
        tblMovies.setModel(new javax.swing.table.DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
    }

    private Movie getSelectedMovie() {
        int row = tblMovies.getSelectedRow();
        if (row == -1) { showMessage("Please select a movie first."); return null; }
        int movieId = (int) tblMovies.getModel().getValueAt(row, 0);
        return movieController.getMovieByID(movieId);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // ─── toolbar ──────────────────────────────────────────────────────────
        JLabel lblSearch = new JLabel("Search:");
        txtSearch = new JTextField(14);
        JLabel lblYear = new JLabel("Year:");
        txtYear = new JTextField(5);
        JLabel lblGenre = new JLabel("Genre:");
        cmbGenre = new JComboBox<>(new String[]{"All","Action","Comedy","Drama","Horror","Sci-Fi","Thriller","Animation"});
        JButton btnSearch = new JButton("Search");

        // movie table 
        tblMovies = new JTable();
        JScrollPane scroll = new JScrollPane(tblMovies);

        // action buttons
        JButton btnMarkWatched   = new JButton("Mark Watched / Unwatched");
        JButton btnRate          = new JButton("Rate Movie");
        JButton btnComment       = new JButton("Add / View Comment");
        JButton btnAddWatchlist  = new JButton("Add to Watchlist");
        JButton btnMyWatchlist   = new JButton("My Watchlist");
        JButton btnProgress      = new JButton("Track Progress");
        JButton btnViewDetails   = new JButton("View Details");
        JButton btnFamilyRatings = new JButton("Family Ratings");
        JButton btnLogout        = new JButton("Logout");

        // listeners 
        btnSearch.addActionListener(e -> search());
        txtSearch.addActionListener(e -> search());

        btnMarkWatched.addActionListener(e -> {
            Movie m = getSelectedMovie();
            if (m == null) return;
            boolean newVal = !m.isWatched();
            boolean ok = movieController.markWatched(m.getMovieID(), newVal);
            showMessage(ok ? "Marked as " + (newVal ? "watched." : "not watched.") : "Failed.");
            loadMovies();
        });

        btnRate.addActionListener(e -> {
            Movie m = getSelectedMovie();
            if (m == null) return;
            String input = JOptionPane.showInputDialog(this, "Enter rating (1-10):", m.getRating());
            if (input == null) return;
            try {
                int r = Integer.parseInt(input.trim());
                if (r < 1 || r > 10) throw new NumberFormatException();
                boolean ok = movieController.rateMovie(m.getMovieID(), r);
                showMessage(ok ? "Rating saved." : "Failed.");
                loadMovies();
            } catch (NumberFormatException ex) {
                showMessage("Enter a number between 1 and 10.");
            }
        });

        btnComment.addActionListener(e -> {
            Movie m = getSelectedMovie();
            if (m == null) return;
            String current = m.getComments() != null ? m.getComments() : "";
            JTextArea ta = new JTextArea(current, 4, 30);
            int res = JOptionPane.showConfirmDialog(this,
                    new JScrollPane(ta), "Comment for \"" + m.getTitle() + "\"",
                    JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                boolean ok = movieController.addComment(m.getMovieID(), ta.getText().trim());
                showMessage(ok ? "Comment saved." : "Failed.");
                loadMovies();
            }
        });

        btnAddWatchlist.addActionListener(e -> {
            Movie m = getSelectedMovie();
            if (m == null) return;
            boolean ok = watchlistController.addToWatchlist(currentUser.getUserId(), m.getMovieID());
            showMessage(ok ? "Added to your watchlist." : "Failed (already in list?).");
        });

        btnMyWatchlist.addActionListener(e -> {
            System.out.println("Current user ID: " + currentUser.getUserId()); // bunu ekle
            List<Movie> wl = watchlistController.getWatchlist(currentUser.getUserId());
            System.out.println("Watchlist size: " + wl.size()); // bunu ekle
            WatchlistDialog dlg = new WatchlistDialog(this, true, wl, watchlistController, currentUser.getUserId());
            dlg.setVisible(true);
        });

        btnProgress.addActionListener(e -> showProgress());

        btnViewDetails.addActionListener(e -> {
            Movie m = getSelectedMovie();
            if (m == null) return;
            new MovieDetailDialog(this, true, m, userController).setVisible(true);
        });

        btnFamilyRatings.addActionListener(e -> {
            Movie m = getSelectedMovie();
            if (m == null) return;
            showMessage("Rating for \"" + m.getTitle() + "\": " + m.getRating() + " / 10");
        });

        btnLogout.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) { new LoginFrame().setVisible(true); dispose(); }
        });

        // ─── layout ───────────────────────────────────────────────────────────
        JPanel toolbar = new JPanel();
        toolbar.add(lblSearch); toolbar.add(txtSearch);
        toolbar.add(lblYear);   toolbar.add(txtYear);
        toolbar.add(lblGenre);  toolbar.add(cmbGenre);
        toolbar.add(btnSearch);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        for (JButton b : new JButton[]{btnMarkWatched, btnRate, btnComment,
                btnAddWatchlist, btnMyWatchlist, btnProgress,
                btnViewDetails, btnFamilyRatings, btnLogout}) {
            b.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, b.getPreferredSize().height));
            sidePanel.add(b);
            sidePanel.add(Box.createVerticalStrut(4));
        }

        JPanel center = new JPanel(new java.awt.BorderLayout());
        center.add(toolbar, java.awt.BorderLayout.NORTH);
        center.add(scroll,  java.awt.BorderLayout.CENTER);

        setLayout(new java.awt.BorderLayout());
        add(sidePanel, java.awt.BorderLayout.WEST);
        add(center,    java.awt.BorderLayout.CENTER);
        setSize(800, 500);
    }

    private void search() {
        String keyword = txtSearch.getText().trim();
        String genre = (String) cmbGenre.getSelectedItem();
        int year = 0;
        try {
            String ys = txtYear.getText().trim();
            if (!ys.isEmpty()) year = Integer.parseInt(ys);
        } catch (NumberFormatException e) {
            showMessage("Enter a valid year."); return;
        }
        List<Movie> results = movieController.searchMovies(keyword, genre, year);
        // Filter out restricted movies for children
        results.removeIf(Movie::isParentalRestriction);
        refreshMovieTable(results);
    }

    private void showProgress() {
        List<Movie> allUnrestricted = movieController.getUnrestrictedMovies();
        long watched = allUnrestricted.stream().filter(Movie::isWatched).count();
        List<User> users = userController.getAllUsers();

        StringBuilder sb = new StringBuilder("<html><b>Your Progress</b><br>");
        sb.append("Movies available to you: ").append(allUnrestricted.size()).append("<br>");
        sb.append("Watched (family-wide): ").append(watched).append("<br><br>");
        sb.append("<b>All Family Members:</b><br>");
        for (User u : users) {
            sb.append("- ").append(u.getUsername())
              .append(" (Type ").append(u.getUserType()).append(")<br>");
        }
        sb.append("</html>");
        JOptionPane.showMessageDialog(this, sb.toString(), "Progress", JOptionPane.INFORMATION_MESSAGE);
    }
}
