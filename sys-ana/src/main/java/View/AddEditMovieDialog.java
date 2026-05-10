package View;

import Controller.MovieController;
import Model.Movie;
import Model.Person;
import Model.PersonDAO;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

public class AddEditMovieDialog extends JDialog {

    private final MovieController movieController;
    private final Movie existing; 

    private JTextField txtId, txtTitle, txtLanguage, txtCountry, txtAbout, txtPoster;
    private JComboBox<String> cmbGenre;
    private JSpinner spnYear, spnRating;
    private JCheckBox chkWatched, chkRestricted;
    private JComboBox<PersonItem> cmbDirector, cmbLeadActor, cmbSupportActor;

    public AddEditMovieDialog(JFrame parent, boolean modal, Movie movie, MovieController mc) {
        super(parent, modal);
        this.existing = movie;
        this.movieController = mc;
        setTitle(movie == null ? "Add Movie" : "Edit Movie");
        buildUI();
        if (movie != null) populate(movie);
        pack();
        setLocationRelativeTo(parent);
    }

    private void buildUI() {
        JPanel p = new JPanel(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
        c.insets = new java.awt.Insets(4, 4, 4, 4);
        c.fill = java.awt.GridBagConstraints.HORIZONTAL;

        // Load persons for combo boxes
        PersonDAO pdao = new PersonDAO();
        List<Person> persons = pdao.getAllPersons();
        PersonItem[] personItems = new PersonItem[persons.size() + 1];
        personItems[0] = new PersonItem(null);
        for (int i = 0; i < persons.size(); i++) personItems[i + 1] = new PersonItem(persons.get(i));

        txtId        = new JTextField(6);
        txtTitle     = new JTextField(20);
        txtLanguage  = new JTextField(15);
        txtCountry   = new JTextField(15);
        txtAbout     = new JTextField(30);
        txtPoster    = new JTextField(20);
        cmbGenre     = new JComboBox<>(new String[]{"Action","Comedy","Drama","Horror","Sci-Fi","Thriller","Animation","Romance","Documentary","Other"});
        spnYear      = new JSpinner(new SpinnerNumberModel(2024, 1900, 2100, 1));
        spnRating    = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        chkWatched   = new JCheckBox("Watched");
        chkRestricted= new JCheckBox("Parental Restriction");
        cmbDirector  = new JComboBox<>(personItems);
        cmbLeadActor = new JComboBox<>(personItems);
        cmbSupportActor = new JComboBox<>(personItems);

        int row = 0;
        addRow(p, c, row++, "Movie ID:",         txtId);
        addRow(p, c, row++, "Title:",             txtTitle);
        addRow(p, c, row++, "Release Year:",      spnYear);
        addRow(p, c, row++, "Language:",          txtLanguage);
        addRow(p, c, row++, "Country of Origin:", txtCountry);
        addRow(p, c, row++, "Genre:",             cmbGenre);
        addRow(p, c, row++, "Director:",          cmbDirector);
        addRow(p, c, row++, "Leading Actor:",     cmbLeadActor);
        addRow(p, c, row++, "Supporting Actor:",  cmbSupportActor);
        addRow(p, c, row++, "About:",             txtAbout);
        addRow(p, c, row++, "Poster URL/Path:",   txtPoster);
        addRow(p, c, row++, "Rating (0-10):",     spnRating);

        c.gridx = 0; c.gridy = row; c.gridwidth = 2;
        JPanel checks = new JPanel();
        checks.add(chkWatched);
        checks.add(chkRestricted);
        p.add(checks, c);
        row++;

        c.gridx = 0; c.gridy = row; c.gridwidth = 2;
        JButton btnSave   = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        JPanel btns = new JPanel();
        btns.add(btnSave);
        btns.add(btnCancel);
        p.add(btns, c);

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());

        setContentPane(new JScrollPane(p));
    }

    private void addRow(JPanel p, java.awt.GridBagConstraints c, int row, String label, JComponent field) {
        c.gridx = 0; c.gridy = row; c.gridwidth = 1; c.weightx = 0;
        p.add(new JLabel(label), c);
        c.gridx = 1; c.weightx = 1.0;
        p.add(field, c);
    }

    private void populate(Movie m) {
        txtId.setText(String.valueOf(m.getMovieID()));
        txtId.setEditable(false);
        txtTitle.setText(m.getTitle());
        if (m.getReleaseDate() != null) spnYear.setValue(m.getReleaseDate().getYear());
        txtLanguage.setText(m.getLanguage());
        txtCountry.setText(m.getCountryOfOrigin());
        txtAbout.setText(m.getAbout());
        txtPoster.setText(m.getPoster() != null ? m.getPoster() : "");
        spnRating.setValue(m.getRating());
        chkWatched.setSelected(m.isWatched());
        chkRestricted.setSelected(m.isParentalRestriction());
        if (m.getGenre() != null) cmbGenre.setSelectedItem(m.getGenre());
        selectPerson(cmbDirector, m.getDirector());
        selectPerson(cmbLeadActor, m.getLeadingActor());
        selectPerson(cmbSupportActor, m.getSupportingActor());
    }

    private void selectPerson(JComboBox<PersonItem> cmb, Person p) {
        if (p == null) { cmb.setSelectedIndex(0); return; }
        for (int i = 0; i < cmb.getItemCount(); i++) {
            PersonItem item = cmb.getItemAt(i);
            if (item.person != null && item.person.getPersonId() == p.getPersonId()) {
                cmb.setSelectedIndex(i);
                return;
            }
        }
    }

    private void save() {
        try {
            String title = txtTitle.getText().trim();
            if (title.isEmpty()) { JOptionPane.showMessageDialog(this,"Title is required."); return; }

            int year = (int) spnYear.getValue();
            LocalDate releaseDate = LocalDate.of(year, 1, 1);

            Person director = ((PersonItem) cmbDirector.getSelectedItem()).person;
            Person lead     = ((PersonItem) cmbLeadActor.getSelectedItem()).person;
            Person support  = ((PersonItem) cmbSupportActor.getSelectedItem()).person;

            if (director == null || lead == null || support == null) {
                JOptionPane.showMessageDialog(this, "Please select Director, Leading Actor, and Supporting Actor.");
                return;
            }

            int movieId = existing != null ? existing.getMovieID() : Integer.parseInt(txtId.getText().trim());

            Movie m = new Movie(
                movieId, title, releaseDate,
                txtLanguage.getText().trim(),
                txtCountry.getText().trim(),
                (String) cmbGenre.getSelectedItem(),
                director, lead, support,
                txtAbout.getText().trim(),
                chkWatched.isSelected(),
                (int) spnRating.getValue(),
                existing != null ? existing.getComments() : null,
                txtPoster.getText().trim(),
                chkRestricted.isSelected()
            );

            boolean ok = existing == null ? movieController.addMovie(m) : movieController.updateMovie(m);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Saved successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save. Check the ID/fields.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Movie ID must be a valid number.");
        }
    }

    // Wrapper so Person shows a nice name in combo boxes.
    private static class PersonItem {
        final Person person;
        PersonItem(Person p) { this.person = p; }
        @Override public String toString() {
            if (person == null) return "-- None --";
            return person.getFirstName() + " " + person.getLastName();
        }
    }
}
