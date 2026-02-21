package carrental;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerPanel extends JFrame {
    private CarDAO carDAO;
    private JTable carTable;
    private DefaultTableModel model;
    private Customer customer;

    public CustomerPanel(CarDAO carDAO) {
        this.carDAO = carDAO;
        setTitle("Customer Panel - Book Car");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new DefaultTableModel(new String[]{"ID", "Brand", "Model", "Price"}, 0);
        carTable = new JTable(model);

        JTextField nameField = new JTextField();
        Object[] namePrompt = {"Enter your name:", nameField};
        int ok = JOptionPane.showConfirmDialog(this, namePrompt, "Customer Info", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            this.customer = new Customer("CUS" + (int)(Math.random() * 1000), name);
        } else {
            dispose();
            return;
        }

        JButton bookBtn = new JButton("Book Selected Car");
        JButton backBtn = new JButton("Back to Menu");

        bookBtn.addActionListener(e -> bookSelectedCar());
        backBtn.addActionListener(e -> {
            new MainMenuGUI(carDAO);
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(bookBtn);
        panel.add(backBtn);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(carTable), BorderLayout.CENTER);

        loadAvailableCars();
        setVisible(true);
    }

    private void loadAvailableCars() {
        model.setRowCount(0);
        List<Car> cars = carDAO.getAllAvailableCars();
        for (Car car : cars) {
            model.addRow(new Object[]{
                car.getCarId(), car.getBrand(), car.getModel(), car.getPricePerDay()
            });
        }
    }

    private void bookSelectedCar() {
        int row = carTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a car to book.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 1: Ask how many days
        String daysStr = JOptionPane.showInputDialog(this, "Enter number of days to rent:");
        if (daysStr == null || daysStr.trim().isEmpty()) return;
        int days;
        try {
            days = Integer.parseInt(daysStr.trim());
            if (days <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of days.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 2: Retrieve selected car data from table
        String carId = (String) model.getValueAt(row, 0);
        String brand = (String) model.getValueAt(row, 1);
        String modelName = (String) model.getValueAt(row, 2);
        double price = (double) model.getValueAt(row, 3);

        double totalCost = days * price;

        boolean success = carDAO.rentCar(carId);
        if (success) {
            // Terminal Output
            System.out.println("\n========= BOOKING RECEIPT =========");
            System.out.println("Customer ID   : " + customer.getUserId());
            System.out.println("Customer Name : " + customer.getName());
            System.out.println("Car ID        : " + carId);
            System.out.println("Car           : " + brand + " " + modelName);
            System.out.println("Price/Day     : Rs" + price);
            System.out.println("Days Booked   : " + days);
            System.out.println("Total Cost    : Rs" + totalCost);
            System.out.println("Status        : Rental Confirmed");
            System.out.println("===================================\n");

            // GUI Receipt
            JTextArea receipt = new JTextArea();
            receipt.setText("--- Booking Receipt ---\n");
            receipt.append("Customer ID   : " + customer.getUserId() + "\n");
            receipt.append("Customer Name : " + customer.getName() + "\n");
            receipt.append("Car ID        : " + carId + "\n");
            receipt.append("Car           : " + brand + " " + modelName + "\n");
            receipt.append("Price/Day     : Rs" + price + "\n");
            receipt.append("Days Booked   : " + days + "\n");
            receipt.append("Total Cost    : Rs" + totalCost + "\n");
            receipt.append("Status        : Rental Confirmed\n");

            JOptionPane.showMessageDialog(this, new JScrollPane(receipt), "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
            loadAvailableCars();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to book car.", "Error", JOptionPane.ERROR_MESSAGE);
 }
}
}