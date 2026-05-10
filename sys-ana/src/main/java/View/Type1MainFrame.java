package View;

import Controller.AnalyticsController;
import Controller.MovieController;
import Controller.UserController;
import Model.Movie;
import Model.User;
import java.util.List;
import javax.swing.*;

public class Type1MainFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Type1MainFrame.class.getName());

    private final User currentUser;
    private final MovieController     movieController;
    private final UserController      userController;
    private final AnalyticsController analyticsController;

    public Type1MainFrame(User user) {
        this.currentUser      = user;
        this.movieController  = new MovieController();
        this.userController   = new UserController();
        this.analyticsController = new AnalyticsController();
        initComponents();
        loadMovies();
        setLocationRelativeTo(null);
        setTitle("MovieCritics – Welcome, " + user.getUsername());
    }

    private void loadMovies() {
        refreshMovieTable(movieController.getAllMovies());
    }

    public void refreshMovieTable(List<Movie> movies) {
        String[] columns = {"ID", "Title", "Genre", "Year", "Director", "Rating", "Watched", "Restricted"};
        Object[][] data = new Object[movies.size()][columns.length];
        for (int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);
            data[i][0] = m.getMovieID();
            data[i][1] = m.getTitle();
            data[i][2] = m.getGenre();
            data[i][3] = m.getReleaseDate() != null ? m.getReleaseDate().getYear() : "";
            data[i][4] = m.getDirector() != null
                    ? m.getDirector().getFirstName() + " " + m.getDirector().getLastName() : "";
            data[i][5] = m.getRating();
            data[i][6] = m.isWatched() ? "Yes" : "No";
            data[i][7] = m.isParentalRestriction() ? "Yes" : "No";
        }
        tblMovies.setModel(new javax.swing.table.DefaultTableModel(data, columns) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        });
    }

    private Movie getSelectedMovie() {
        int row = tblMovies.getSelectedRow();
        if (row == -1) { showMessage("Please select a movie first."); return null; }
        int movieId = (int) tblMovies.getModel().getValueAt(row, 0);
        return movieController.getMovieByID(movieId);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        JScrollPane jScrollPane1 = new JScrollPane();
        tblMovies     = new JTable();
        jLabel1       = new JLabel();
        txtSearch     = new JTextField();
        jLabel2       = new JLabel();
        txtYear       = new JTextField();
        cmbGenre      = new JComboBox<>();
        btnAddMovie   = new JButton();
        btnDeleteMovie= new JButton();
        btnEditMovie  = new JButton();
        btnSetRestriction = new JButton();
        btnModerate   = new JButton();
        btnAnalytics  = new JButton();
        btnManageUsers= new JButton();
        btnViewDetails= new JButton();
        btnLogout     = new JButton();
        jLabel3       = new JLabel();
        btnSearch     = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(tblMovies);

        jLabel1.setText("Search:");
        jLabel2.setText("Year:");
        jLabel3.setText("Genre:");

        cmbGenre.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[]{"All","Action","Comedy","Drama","Horror","Sci-Fi","Thriller","Animation"}));

        btnAddMovie.setText("Add Movie");
        btnDeleteMovie.setText("Delete Movie");
        btnEditMovie.setText("Edit Movie");
        btnSetRestriction.setText("Toggle Restriction");
        btnModerate.setText("Remove Comment");
        btnAnalytics.setText("Family Analytics");
        btnManageUsers.setText("Manage Users");
        btnViewDetails.setText("View Details");
        btnLogout.setText("Logout");
        btnSearch.setText("Search");

        //listeners 
        btnSearch.addActionListener(e -> doSearch());
        txtSearch.addActionListener(e -> doSearch());

        btnAddMovie.addActionListener(e -> {
            new AddEditMovieDialog(this, true, null, movieController).setVisible(true);
            loadMovies();
        });

        btnDeleteMovie.addActionListener(e -> {
            Movie selected = getSelectedMovie();
            if (selected == null) return;
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Delete \"" + selected.getTitle() + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                showMessage(movieController.removeMovie(selected.getMovieID()) ? "Movie deleted." : "Failed.");
                loadMovies();
            }
        });

        btnEditMovie.addActionListener(e -> {
            Movie selected = getSelectedMovie();
            if (selected == null) return;
            new AddEditMovieDialog(this, true, selected, movieController).setVisible(true);
            loadMovies();
        });

        btnSetRestriction.addActionListener(e -> {
            Movie selected = getSelectedMovie();
            if (selected == null) return;
            boolean newVal = !selected.isParentalRestriction();
            showMessage(movieController.setParentalRestriction(selected.getMovieID(), newVal)
                    ? "Restriction " + (newVal ? "enabled." : "disabled.") : "Failed.");
            loadMovies();
        });

        btnModerate.addActionListener(e -> {
            Movie selected = getSelectedMovie();
            if (selected == null) return;
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Delete comment for \"" + selected.getTitle() + "\"?",
                    "Moderate Content", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                showMessage(movieController.moderateComment(selected.getMovieID())
                        ? "Comment removed." : "Failed.");
                loadMovies();
            }
        });

        btnAnalytics.addActionListener(e -> {
            List<Movie>           mostWatched = analyticsController.getMostWatchedMovies();
            java.util.Map<Movie,Double> avgRatings  = analyticsController.getAverageRatings();
            java.util.Map<User, Integer> progress   = analyticsController.getWatchProgressPerUser();

            StringBuilder sb = new StringBuilder();
            sb.append("=== Most Watched (IsWatched=true) ===\n");
            for (Movie m : mostWatched) sb.append("- ").append(m.getTitle()).append("\n");
            sb.append("\n=== Ratings ===\n");
            avgRatings.forEach((m,r) -> sb.append("- ").append(m.getTitle()).append(": ").append(r).append("\n"));
            sb.append("\n=== Watchlist Count Per User ===\n");
            progress.forEach((u,cnt) -> sb.append("- ").append(u.getUsername()).append(": ").append(cnt).append(" movies\n"));

            JTextArea ta = new JTextArea(sb.toString());
            ta.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Family Analytics", JOptionPane.INFORMATION_MESSAGE);
        });

        btnManageUsers.addActionListener(e ->
                new UserManagementDialog(this, true, userController).setVisible(true));

        btnViewDetails.addActionListener(e -> {
            Movie selected = getSelectedMovie();
            if (selected == null) return;
            new MovieDetailDialog(this, true, selected, userController).setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) { new LoginFrame().setVisible(true); dispose(); }
        });

        // layout 
        JPanel toolbar = new JPanel();
        toolbar.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        txtSearch.setPreferredSize(new java.awt.Dimension(200, 32));
        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));

        txtYear.setPreferredSize(new java.awt.Dimension(80, 32));
        txtYear.setFont(new java.awt.Font("Segoe UI", 0, 14));

        cmbGenre.setPreferredSize(new java.awt.Dimension(120, 32));
        cmbGenre.setFont(new java.awt.Font("Segoe UI", 0, 14));

        btnSearch.setPreferredSize(new java.awt.Dimension(90, 32));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14));

        toolbar.add(jLabel1); toolbar.add(txtSearch);
        toolbar.add(jLabel2); toolbar.add(txtYear);
        toolbar.add(jLabel3); toolbar.add(cmbGenre);
        toolbar.add(btnSearch);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        for (JButton b : new JButton[]{btnAddMovie, btnDeleteMovie, btnEditMovie,
                btnSetRestriction, btnModerate, btnAnalytics,
                btnManageUsers, btnViewDetails, btnLogout}) {
            b.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, b.getPreferredSize().height));
            sidePanel.add(b);
            sidePanel.add(Box.createVerticalStrut(4));
        }

        JPanel center = new JPanel(new java.awt.BorderLayout());
        center.add(toolbar,       java.awt.BorderLayout.NORTH);
        center.add(jScrollPane1,  java.awt.BorderLayout.CENTER);

        setLayout(new java.awt.BorderLayout());
        add(sidePanel, java.awt.BorderLayout.WEST);
        add(center,    java.awt.BorderLayout.CENTER);
        setSize(880, 520);
    }

    private void doSearch() {
        String keyword = txtSearch.getText().trim();
        String genre   = (String) cmbGenre.getSelectedItem();
        int year = 0;
        try {
            String ys = txtYear.getText().trim();
            if (!ys.isEmpty()) year = Integer.parseInt(ys);
        } catch (NumberFormatException e) {
            showMessage("Enter a valid year."); return;
        }
        refreshMovieTable(movieController.searchMovies(keyword, genre, year));
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() ->
            new LoginFrame().setVisible(true)
        );
    }

    // Variables
    private JTable    tblMovies;
    private JLabel    jLabel1, jLabel2, jLabel3;
    private JTextField txtSearch, txtYear;
    private JComboBox<String> cmbGenre;
    private JButton   btnAddMovie, btnDeleteMovie, btnEditMovie, btnSetRestriction,
                      btnModerate, btnAnalytics, btnManageUsers, btnViewDetails,
                      btnSearch, btnLogout;
}
