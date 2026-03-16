package Metodoak;

import javax.swing.*;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Traspaso_Y_Confirmacion {

    public static boolean realizarTraspaso(Component parent, int jugadorId, String equipoDestino) {
        
        String nuevoDorsalStr = JOptionPane.showInputDialog(parent, "Sartu jokalariaren dortsal berria:");
        if (nuevoDorsalStr == null || nuevoDorsalStr.trim().isEmpty()) return false;
        
        int nuevoDorsal;
        try {
            nuevoDorsal = Integer.parseInt(nuevoDorsalStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parent, "Dortsala zenbaki bat izan behar da.");
            return false;
        }

        try (Connection conn = Konexioa.getKonexioa()) {
            if (conn == null) return false;

            // 1. Konprobatu dortsala okupatuta dagoen
            String checkSql = "SELECT count(*) FROM jokalariak WHERE taldea = ? AND dortsala = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, equipoDestino);
            checkStmt.setInt(2, nuevoDorsal);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(parent, "Dorsal hori okupatuta dago talde horretan.");
                return false;
            }

            // 2. Eguneratu jokalaria
            String updateSql = "UPDATE jokalariak SET taldea = ?, dortsala = ? WHERE id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, equipoDestino);
            updateStmt.setInt(2, nuevoDorsal);
            updateStmt.setInt(3, jugadorId); 
            
            int filas = updateStmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(parent, "Transferentzia behar bezala burutu da.");
                return true;
            } else {
                JOptionPane.showMessageDialog(parent, "Ez da jokalaria aurkitu.");
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parent, "Errorea transferentzian: " + ex.getMessage());
            return false;
        }
    }
}