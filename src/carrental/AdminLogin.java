package carrental;

import javax.swing.*;
import java.awt.*;

public class AdminLogin extends JFrame {
    public AdminLogin(CarDAO carDAO) {
        setTitle("Admin Login");
        setSize(350, 200);
        setLayout(new GridLayout(4, 1));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set consistent icon for all frames
        ImageIcon icon = new ImageIcon("assets/Car Renting.png");
        setIconImage(icon.getImage());

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.equals("Cardealer") && password.equals("Carrental123")) {
                System.out.println("[OK] Admin login successful: " + username);
                JOptionPane.showMessageDialog(this, "Login Successful!");
                AdminPanel panel = new AdminPanel(carDAO);
                panel.setIconImage(icon.getImage()); // ensure icon consistency
                dispose();
            } else {
                System.out.println("[ERROR] Failed login attempt for: " + username);
                JOptionPane.showMessageDialog(this, "Invalid Credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(loginBtn);

        setVisible(true);
    }
}