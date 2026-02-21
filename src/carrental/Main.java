package carrental;

public class Main {
    public static void main(String[] args) {
        new MainMenuGUI(new CarDAO(DBConnection.getConnection()));
    }
}
