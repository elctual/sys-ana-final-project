package View;

import Controller.WatchlistController;
import Model.Movie;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WatchlistDialog extends JDialog {

    private final WatchlistController wc;
    private final int userId;
    private List<Movie> movies;
    private DefaultTableModel model;
    private JTable table;

    public WatchlistDialog(JFrame parent, boolean modal, List<Movie> movies,
                           WatchlistController wc, int userId) {
        super(parent, modal);
        this.wc     = wc;
        this.userId = userId;
        this.movies = movies;
        setTitle("My Watchlist");
        buildUI();
        setSize(500, 350);
        setLocationRelativeTo(parent);
    }

    private void buildUI() {
        String[] cols = {"ID", "Title", "Genre", "Year", "Watched"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        fillTable();

        JButton btnRemove = new JButton("Remove from Watchlist");
        JButton btnClose  = new JButton("Close");

        btnRemove.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select a movie first."); return; }
            int movieId = (int) model.getValueAt(row, 0);
            boolean ok = wc.removeFromWatchlist(userId, movieId);
            JOptionPane.showMessageDialog(this, ok ? "Removed." : "Failed.");
            // Refresh
            this.movies = wc.getWatchlist(userId);
            model.setRowCount(0);
            fillTable();
        });
        btnClose.addActionListener(e -> dispose());

        JPanel btns = new JPanel();
        btns.add(btnRemove);
        btns.add(btnClose);

        setLayout(new java.awt.BorderLayout());
        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);
        add(btns, java.awt.BorderLayout.SOUTH);
    }

    private void fillTable() {
        for (Movie m : movies) {
            model.addRow(new Object[]{
                m.getMovieID(), m.getTitle(), m.getGenre(),
                m.getReleaseDate() != null ? m.getReleaseDate().getYear() : "",
                m.isWatched() ? "Yes" : "No"
            });
        }
    }
}
