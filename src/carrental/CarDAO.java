package carrental;

import java.sql.*;
import java.util.*;

public class CarDAO {
    private final Connection conn;

    public CarDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Car> getAllAvailableCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE available = true";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(new Car(
                    rs.getString("id"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getDouble("price_per_day"),
                    rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(new Car(
                    rs.getString("id"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getDouble("price_per_day"),
                    rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public boolean addCar(Car car) {
        String sql = "INSERT INTO cars (id, brand, model, price_per_day, available) VALUES (?, ?, ?, ?, true)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, car.getCarId());
            stmt.setString(2, car.getBrand());
            stmt.setString(3, car.getModel());
            stmt.setDouble(4, car.getPricePerDay());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeCar(String id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rentCar(String id) {
        String sql = "UPDATE cars SET available = false WHERE id = ? AND available = true";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}