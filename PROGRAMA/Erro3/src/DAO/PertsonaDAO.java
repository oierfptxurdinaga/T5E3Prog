package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Metodoak.Konexioa;
import POJOAK3.Pertsona;

/**
 * Pertsonen datu-base eragiketak kudeatzen dituen DAO klasea.
 * <p>
 * Klase honek pertsonen informazioa taldearen edo rolaren arabera bilatzeko,
 * txertatzeko eta ezabatzeko funtzionalitateak eskaintzen ditu.
 * </p>
 */
public class PertsonaDAO {

    /**
     * Talde jakin bateko pertsona guztien zerrenda lortzen du.
     *
     * @param taldea Pertsonak bilatzeko taldearen izena
     * @return Talde horretako {@link Pertsona} objektuen zerrenda
     */
    public List<Pertsona> getPertsonakByTaldea(String taldea) {
        List<Pertsona> lista = new ArrayList<>();
        String sql = """
                SELECT NANa, Izen_abizena, Adina, Helbidea, Tlfn, taldea, Rola 
                FROM pertsona 
                WHERE taldea = ?
                """;

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, taldea);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Pertsona p = new Pertsona();
                p.setNana(rs.getString("NANa"));
                p.setIzenAbizena(rs.getString("Izen_abizena"));
                // Adina balio nulua izan daiteke, horregatik getObject erabiltzen da
                p.setAdina((Integer) rs.getObject("Adina"));
                p.setHelbidea(rs.getString("Helbidea"));
                p.setTlfn(rs.getString("Tlfn"));
                p.setTaldea(rs.getString("taldea"));
                p.setRola(rs.getString("Rola"));
                lista.add(p);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /**
     * Rol jakin bateko pertsona guztien zerrenda lortzen du.
     *
     * @param rola Pertsonen rola (adibidez: jokalaria, entrenatzailea...)
     * @return Rol horretako {@link Pertsona} objektuen zerrenda
     */
    public List<Pertsona> getPertsonakByRola(String rola) {
        List<Pertsona> lista = new ArrayList<>();
        String sql = "SELECT * FROM pertsona WHERE Rola = ?";

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rola);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Pertsona p = new Pertsona();
                p.setNana(rs.getString("NANa"));
                p.setIzenAbizena(rs.getString("Izen_abizena"));
                p.setAdina((Integer) rs.getObject("Adina"));
                p.setHelbidea(rs.getString("Helbidea"));
                p.setTlfn(rs.getString("Tlfn"));
                p.setTaldea(rs.getString("taldea"));
                p.setRola(rs.getString("Rola"));
                lista.add(p);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /**
     * Pertsona berri bat datu-basean txertatzen du.
     *
     * @param p Txertatu nahi den {@link Pertsona} objektua
     * @return {@code true} txertaketa arrakastatsua bada, bestela {@code false}
     */
    public boolean insertarPertsona(Pertsona p) {
        String sql = """
                INSERT INTO pertsona 
                (NANa, Izen_abizena, Adina, Helbidea, Tlfn, taldea, Rola) 
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getNana());
            pstmt.setString(2, p.getIzenAbizena());
            pstmt.setObject(3, p.getAdina()); // balio nulua onartzen du
            pstmt.setString(4, p.getHelbidea());
            pstmt.setString(5, p.getTlfn());
            pstmt.setString(6, p.getTaldea());
            pstmt.setString(7, p.getRola());

            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * NANaren bidez pertsona bat datu-basetik ezabatzen du.
     *
     * @param nana Ezabatu nahi den pertsonaren NANa
     * @return {@code true} pertsona ezabatu bada, bestela {@code false}
     */
    public boolean eliminarPertsona(String nana) {
        String sql = "DELETE FROM pertsona WHERE NANa = ?";

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nana);
            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}