package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Metodoak.Konexioa;
import POJOAK3.Epailea;

public class EpaileaDAO {

    // ===============================
    // LORTU EPAILE GUZTIAK
    // ===============================
    public List<Epailea> getEpaileakGuztiak() {
        List<Epailea> lista = new ArrayList<>();
        String sql = "SELECT NANa, Izen_abizena, Maila FROM epailea";

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Epailea e = new Epailea();
                e.setNana(rs.getString("NANa"));
                e.setIzenAbizena(rs.getString("Izen_abizena"));
                e.setMaila(rs.getString("Maila"));
                lista.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // ===============================
    // TXERTATU EPAILEA
    // ===============================
    public boolean insertarEpailea(Epailea e) {
        String sql = """
                INSERT INTO epailea (NANa, Izen_abizena, Maila)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, e.getNana());
            pstmt.setString(2, e.getIzenAbizena());
            pstmt.setString(3, e.getMaila());

            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // ===============================
    // EZABATU EPAILEA
    // ===============================
    public boolean eliminarEpailea(String nana) {
        String sql = "DELETE FROM epailea WHERE NANa = ?";

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nana);
            int filasAffected = pstmt.executeUpdate();
            return filasAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}