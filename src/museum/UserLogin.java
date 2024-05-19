package museum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserLogin extends JFrame implements ActionListener {

    private JLabel usernameLabel = new JLabel("Username");
    private JTextField usernameTextField = new JTextField(20);
    private JLabel passwordLabel = new JLabel("Password");
    private JTextField passwordField = new JPasswordField(20);
    private JButton loginButton = new JButton("login");
    private JButton exitButton = new JButton("exit");

    private Database db = Database.getInstance();

    public UserLogin(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(this);
        exitButton.addActionListener(this);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(loginButton);
        buttonsPanel.add(exitButton);

        mainPanel.add(usernamePanel, BorderLayout.NORTH);
        mainPanel.add(passwordPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.setSize(400, 300);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            login(usernameTextField.getText(), passwordField.getText());
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    public void login(String username, String password) {
        try {
            String query = "select * from employees where Username = '"
                    + username + "' and Password = '" + password + "'";
            ResultSet result = db.getStatement().executeQuery(query);
            if (result.next()) {
                int adminVerification = result.getInt("Id");
                new FrameSelector("Panel selector", adminVerification);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - Error inserting data");
        }
    }
}
