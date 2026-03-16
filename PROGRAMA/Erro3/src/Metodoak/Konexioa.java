package Metodoak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Konexioa {
    private static final String URL = "jdbc:mysql://localhost:3306/eskubaloi";
    private static final String ERABILTZAILEA = "root";
    private static final String PASAHITZA = "";

    public static Connection getKonexioa() {
        Connection konexioa = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            konexioa = DriverManager.getConnection(URL, ERABILTZAILEA, PASAHITZA);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL Driver-a ez da aurkitu.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errorea datu-basearekin konektatzean: " + e.getMessage());
        }
        return konexioa;
    }
}