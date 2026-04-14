package Metodoak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Datu-basearekin konexioa ezartzeko ardura duen klasea.
 * MySQL datu-base baterako sarbide-datuak (URL-a, erabiltzailea eta pasahitza) kudeatzen ditu.
 */

public class Konexioa {
	
	/**
     * MySQL datu-basearekin (eskubaloi) konexio bat sortzen eta itzultzen du.
     * Gidaria (Driver) aurkitzen ez bada edo konexio-errore bat gertatzen bada,
     * erabiltzaileari mezu bat erakusten dio abisu gisa.
     *
     * @return Datu-basearekiko konexio objektua ({@link Connection}). Errore bat badago, null itzuliko du.
     */
	
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