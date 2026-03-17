package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Metodoak.Konexioa;
import POJOAK3.Jokalaria;

public class JokalariaDAO {

    // ===============================
    // LORTU JOKALARIAK TALDEAREN ARABERA
    // ===============================
    public List<Jokalaria> getJokalariakByTaldea(String taldea) {

        List<Jokalaria> lista = new ArrayList<>();

        String sql = """
                SELECT NANa, Izen_abizena, Dortsala, Posizioa, Jaiotze_data, taldea
                FROM jokalaria
                WHERE taldea = ?
                ORDER BY Dortsala ASC
                """;

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, taldea);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Jokalaria j = new Jokalaria();

                j.setNana(rs.getString("NANa"));
                j.setIzenAbizena(rs.getString("Izen_abizena"));
                j.setDortsala(rs.getInt("Dortsala"));
                j.setPosizioa(rs.getString("Posizioa"));
                j.setJaiotzeData(rs.getDate("Jaiotze_data"));
                j.setTaldea(rs.getString("taldea"));

                lista.add(j);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ===============================
    // TXERTATU JOKALARIA
    // ===============================
    public boolean insertarJokalaria(Jokalaria j) {

        String sql = """
                INSERT INTO jokalaria 
                (NANa, Izen_abizena, Dortsala, Posizioa, Jaiotze_data, taldea)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, j.getNana());
            pstmt.setString(2, j.getIzenAbizena());
            pstmt.setInt(3, j.getDortsala());
            pstmt.setString(4, j.getPosizioa());
            pstmt.setDate(5, j.getJaiotzeData());
            pstmt.setString(6, j.getTaldea());

            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===============================
    // EZABATU JOKALARIA
    // ===============================
    public boolean eliminarJokalaria(String nana) {

        String sql = "DELETE FROM jokalaria WHERE NANa = ?";

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