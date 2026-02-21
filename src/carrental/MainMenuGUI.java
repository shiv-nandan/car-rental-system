package carrental;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {
    private CarDAO carDAO;

    public MainMenuGUI(CarDAO carDAO) {
        this.carDAO = carDAO;
        setTitle("Car Rental Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("assets/Car Renting.png");
        setIconImage(icon.getImage());

        JLabel title = new JLabel("Welcome to Car Rental System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton adminBtn = new JButton("Admin Login");
        JButton customerBtn = new JButton("Customer Portal");
        JButton exitBtn = new JButton("Exit");

        adminBtn.addActionListener(e -> {
            AdminLogin login = new AdminLogin(carDAO);
            login.setIconImage(icon.getImage());
            dispose();
        });

        customerBtn.addActionListener(e -> {
            CustomerPanel panel = new CustomerPanel(carDAO);
            panel.setIconImage(icon.getImage());
            dispose();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        buttonPanel.add(adminBtn);
        buttonPanel.add(customerBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
