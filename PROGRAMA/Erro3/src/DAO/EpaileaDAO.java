package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Metodoak.Konexioa;
import POJOAK3.Epailea;

/**
 * Epaileekin erlazionatutako datu-base eragiketak kudeatzen dituen DAO klasea.
 * <p>
 * Klase honek epaileen informazioa lortzeko, txertatzeko eta ezabatzeko
 * funtzioak eskaintzen ditu.
 * </p>
 */
public class EpaileaDAO {

    /**
     * Datu-baseko epaile guztien zerrenda lortzen du.
     *
     * @return Epaileen {@link List} bat, edo zerrenda huts bat errorea gertatuz gero
     */
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

    /**
     * Epaile berri bat datu-basean txertatzen du.
     *
     * @param e Txertatu nahi den {@link Epailea} objektua
     * @return {@code true} txertaketa arrakastatsua bada, bestela {@code false}
     */
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

    /**
     * NANaren bidez epaile bat datu-basetik ezabatzen du.
     *
     * @param nana Ezabatu nahi den epailearen NANa
     * @return {@code true} epailea ezabatu bada, bestela {@code false}
     */
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