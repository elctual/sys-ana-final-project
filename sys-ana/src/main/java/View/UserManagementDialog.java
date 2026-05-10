package View;

import Controller.UserController;
import Model.User;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Parent-only: manage all user accounts (Function 3).
 */
public class UserManagementDialog extends JDialog {

    private final UserController userController;
    private JTable tblUsers;
    private DefaultTableModel tableModel;

    public UserManagementDialog(JFrame parent, boolean modal, UserController uc) {
        super(parent, modal);
        this.userController = uc;
        setTitle("Manage Users");
        buildUI();
        loadUsers();
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }

    private void buildUI() {
        String[] cols = {"ID", "Username", "Email", "Type (1=Parent,2=Child)"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblUsers = new JTable(tableModel);

        JButton btnAdd    = new JButton("Add User");
        JButton btnEdit   = new JButton("Edit User");
        JButton btnDelete = new JButton("Delete User");
        JButton btnResetPw= new JButton("Reset Password");
        JButton btnClose  = new JButton("Close");

        btnAdd.addActionListener(e -> addUser());
        btnEdit.addActionListener(e -> editUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnResetPw.addActionListener(e -> resetPassword());
        btnClose.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnResetPw);
        btnPanel.add(btnClose);

        setLayout(new java.awt.BorderLayout());
        add(new JScrollPane(tblUsers), java.awt.BorderLayout.CENTER);
        add(btnPanel, java.awt.BorderLayout.SOUTH);
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        for (User u : userController.getAllUsers()) {
            tableModel.addRow(new Object[]{u.getUserId(), u.getUsername(), u.getEmail(), u.getUserType()});
        }
    }

    private User getSelected() {
        int row = tblUsers.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a user first."); return null; }
        int id = (int) tableModel.getValueAt(row, 0);
        return userController.getUserById(id);
    }

    private void addUser() {
        JTextField txtUser = new JTextField(15);
        JPasswordField txtPass = new JPasswordField(15);
        JTextField txtEmail = new JTextField(20);
        JComboBox<String> cmbType = new JComboBox<>(new String[]{"1 – Parent/Adult", "2 – Child"});

        JPanel form = new JPanel(new java.awt.GridLayout(0, 2, 4, 4));
        form.add(new JLabel("Username:")); form.add(txtUser);
        form.add(new JLabel("Password:")); form.add(txtPass);
        form.add(new JLabel("Email:"));    form.add(txtEmail);
        form.add(new JLabel("Type:"));     form.add(cmbType);

        int res = JOptionPane.showConfirmDialog(this, form, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (res != JOptionPane.OK_OPTION) return;

        User u = new User(0, txtUser.getText().trim(),
                new String(txtPass.getPassword()),
                txtEmail.getText().trim(),
                cmbType.getSelectedIndex() + 1);
        boolean ok = userController.createUser(u);
        JOptionPane.showMessageDialog(this, ok ? "User created." : "Failed to create user.");
        loadUsers();
    }

    private void editUser() {
        User u = getSelected();
        if (u == null) return;

        JTextField txtUser  = new JTextField(u.getUsername(), 15);
        JTextField txtEmail = new JTextField(u.getEmail() != null ? u.getEmail() : "", 20);
        JComboBox<String> cmbType = new JComboBox<>(new String[]{"1 – Parent/Adult", "2 – Child"});
        cmbType.setSelectedIndex(u.getUserType() - 1);

        JPanel form = new JPanel(new java.awt.GridLayout(0, 2, 4, 4));
        form.add(new JLabel("Username:")); form.add(txtUser);
        form.add(new JLabel("Email:"));    form.add(txtEmail);
        form.add(new JLabel("Type:"));     form.add(cmbType);

        int res = JOptionPane.showConfirmDialog(this, form, "Edit User", JOptionPane.OK_CANCEL_OPTION);
        if (res != JOptionPane.OK_OPTION) return;

        u.setUsername(txtUser.getText().trim());
        u.setEmail(txtEmail.getText().trim());
        u.setUserType(cmbType.getSelectedIndex() + 1);
        boolean ok = userController.updateUser(u);
        JOptionPane.showMessageDialog(this, ok ? "User updated." : "Failed to update user.");
        loadUsers();
    }

    private void deleteUser() {
        User u = getSelected();
        if (u == null) return;
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete user \"" + u.getUsername() + "\"?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = userController.deleteUser(u.getUserId());
            JOptionPane.showMessageDialog(this, ok ? "User deleted." : "Failed.");
            loadUsers();
        }
    }

    private void resetPassword() {
        User u = getSelected();
        if (u == null) return;
        JPasswordField pf = new JPasswordField(15);
        int res = JOptionPane.showConfirmDialog(this,
                new Object[]{"New password for \"" + u.getUsername() + "\":", pf},
                "Reset Password", JOptionPane.OK_CANCEL_OPTION);
        if (res != JOptionPane.OK_OPTION) return;
        String newPw = new String(pf.getPassword()).trim();
        if (newPw.isEmpty()) { JOptionPane.showMessageDialog(this, "Password cannot be empty."); return; }
        boolean ok = userController.resetPassword(u.getUserId(), newPw);
        JOptionPane.showMessageDialog(this, ok ? "Password reset." : "Failed.");
    }
}
