package carrental;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminPanel extends JFrame {
    private CarDAO carDAO;
    private JTable carTable;
    private DefaultTableModel model;

    public AdminPanel(CarDAO carDAO) {
        this.carDAO = carDAO;
        setTitle("Admin Panel - Car Management");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new DefaultTableModel(new String[]{"ID", "Brand", "Model", "Price", "Status"}, 0);
        carTable = new JTable(model);

        JButton addBtn = new JButton("Add Car");
        JButton removeBtn = new JButton("Remove Car");
        JButton backBtn = new JButton("Back to Menu");

        addBtn.addActionListener(e -> addCar());
        removeBtn.addActionListener(e -> removeCar());
        backBtn.addActionListener(e -> {
            new MainMenuGUI(carDAO);
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(addBtn);
        panel.add(removeBtn);
        panel.add(backBtn);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(carTable), BorderLayout.CENTER);

        loadAllCars();
        setVisible(true);
    }

    private void loadAllCars() {
        model.setRowCount(0);
        List<Car> cars = carDAO.getAllCars();
        for (Car car : cars) {
            model.addRow(new Object[]{
                car.getCarId(), car.getBrand(), car.getModel(), car.getPricePerDay(), car.isAvailable() ? "Available" : "Rented"
            });
        }
    }

    private void addCar() {
        JTextField idField = new JTextField();
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField priceField = new JTextField();

        Object[] fields = {
            "Car ID:", idField,
            "Brand:", brandField,
            "Model:", modelField,
            "Price/Day:", priceField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add New Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText();
                String brand = brandField.getText();
                String model = modelField.getText();
                double price = Double.parseDouble(priceField.getText());

                Car car = new Car(id, brand, model, price, true);
                if (carDAO.addCar(car)) {
                    System.out.println("[OK] Car added: " + id + " - " + brand + " " + model + " | Rs" + price);
                    JOptionPane.showMessageDialog(this, "Car added successfully.");
                    loadAllCars();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add car.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeCar() {
        String carId = JOptionPane.showInputDialog(this, "Enter Car ID to remove:");
        if (carId != null && !carId.trim().isEmpty()) {
            if (carDAO.removeCar(carId)) {
                System.out.println("[OK] Car removed: " + carId);
                JOptionPane.showMessageDialog(this, "Car removed successfully.");
                loadAllCars();
            } else {
                JOptionPane.showMessageDialog(this, "Car ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}