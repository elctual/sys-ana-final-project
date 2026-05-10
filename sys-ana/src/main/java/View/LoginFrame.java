package View;

import Controller.LoginController;
import java.util.function.BiConsumer;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(LoginFrame.class.getName());
    private javax.swing.JLabel lblError;

    public LoginFrame() {
        initComponents();
        setLocationRelativeTo(null);
        new LoginController(this);
    }

    public void setLoginAction(BiConsumer<String, String> action) {
        jButton1.addActionListener(e ->
            action.accept(jTextField1.getText(), new String(jPasswordField1.getPassword()))
        );
        // also trigger on Enter in password field
        jPasswordField1.addActionListener(e ->
            action.accept(jTextField1.getText(), new String(jPasswordField1.getPassword()))
        );
    }

    public void showError(String s) {
        lblError.setText(s);
        lblError.setForeground(java.awt.Color.RED);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
   private void initComponents() {
    lblError        = new javax.swing.JLabel();
    jLabel1         = new javax.swing.JLabel();
    jLabel2         = new javax.swing.JLabel();
    jLabel3         = new javax.swing.JLabel();
    jTextField1     = new javax.swing.JTextField();
    jPasswordField1 = new javax.swing.JPasswordField();
    jButton1        = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Login – MovieCritics");

    jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48));
    jLabel1.setText("Movie Critics");

    jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16));
    jLabel2.setText("Username");

    jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16));
    jLabel3.setText("Password");

    jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16));
    jTextField1.setPreferredSize(new java.awt.Dimension(220, 32));

    jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 16));
    jPasswordField1.setPreferredSize(new java.awt.Dimension(220, 32));

    jButton1.setFont(new java.awt.Font("Segoe UI", 0, 16));
    jButton1.setText("Login");
    jButton1.setPreferredSize(new java.awt.Dimension(220, 36));

    lblError.setForeground(java.awt.Color.RED);
    lblError.setFont(new java.awt.Font("Segoe UI", 0, 13));
    lblError.setText(" ");

    // Ortada sabit duran iç panel
    JPanel formPanel = new JPanel(new java.awt.GridBagLayout());
    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
    c.insets = new java.awt.Insets(6, 8, 6, 8);

    // Başlık - 2 sütun kaplıyor
    c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
    c.anchor = java.awt.GridBagConstraints.CENTER;
    formPanel.add(jLabel1, c);

    // Boşluk
    c.gridy = 1; c.gridwidth = 2;
    formPanel.add(new JLabel(" "), c);

    // Username label
    c.gridx = 0; c.gridy = 2; c.gridwidth = 1;
    c.anchor = java.awt.GridBagConstraints.EAST;
    formPanel.add(jLabel2, c);

    // Username field
    c.gridx = 1; c.gridy = 2;
    c.anchor = java.awt.GridBagConstraints.WEST;
    formPanel.add(jTextField1, c);

    // Password label
    c.gridx = 0; c.gridy = 3;
    c.anchor = java.awt.GridBagConstraints.EAST;
    formPanel.add(jLabel3, c);

    // Password field
    c.gridx = 1; c.gridy = 3;
    c.anchor = java.awt.GridBagConstraints.WEST;
    formPanel.add(jPasswordField1, c);

    // Login button
    c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
    c.anchor = java.awt.GridBagConstraints.CENTER;
    formPanel.add(jButton1, c);

    // Error label
    c.gridy = 5;
    formPanel.add(lblError, c);

    // Dış panel: her zaman ortalar
    getContentPane().setLayout(new java.awt.GridBagLayout());
    getContentPane().add(formPanel, new java.awt.GridBagConstraints());

    pack();
    setMinimumSize(new java.awt.Dimension(400, 350));
}// </editor-fold>

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
        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel  jLabel1, jLabel2, jLabel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField     jTextField1;
    // End of variables declaration
}
