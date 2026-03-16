package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Metodoak.Konexioa;
import POJOAK3.Entrenatzailea;

public class EntrenatzaileaDAO {

    // ===============================
    // LORTU ENTRENATZAILEAK TALDEAREN ARABERA
    // ===============================
    public List<Entrenatzailea> getEntrenatzaileakByTaldea(String taldea) {
        List<Entrenatzailea> lista = new ArrayList<>();
        String sql = """
                SELECT NANa, Izen_abizena, Titulazioa, taldea
                FROM entrenatzailea
                WHERE taldea = ?
                """;

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, taldea);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Entrenatzailea e = new Entrenatzailea();
                e.setNana(rs.getString("NANa"));
                e.setIzenAbizena(rs.getString("Izen_abizena"));
                e.setTitulazioa(rs.getString("Titulazioa"));
                e.setTaldea(rs.getString("taldea"));
                lista.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // ===============================
    // TXERTATU ENTRENATZAILEA
    // ===============================
    public boolean insertarEntrenatzailea(Entrenatzailea e) {
        String sql = """
                INSERT INTO entrenatzailea (NANa, Izen_abizena, Titulazioa, taldea)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, e.getNana());
            pstmt.setString(2, e.getIzenAbizena());
            pstmt.setString(3, e.getTitulazioa());
            pstmt.setString(4, e.getTaldea());

            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}