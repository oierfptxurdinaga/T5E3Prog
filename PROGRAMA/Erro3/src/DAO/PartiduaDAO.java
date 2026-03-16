package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Metodoak.Konexioa;
import POJOAK3.Partidua;

public class PartiduaDAO {
	
    // ===============================
    // LORTU PARTIDUAK GUZTIAK
    // ===============================

    public List<Partidua> getPartiduakGuztiak() {
        List<Partidua> lista = new ArrayList<>();
        String sql = """
                SELECT id_auto, kod_partidua, denboraldia, Data, Ordua, 
                       Golak_lokala, Golak_kanpokoak, Zelaia, 
                       Talde_lokala, Kampoko_taldea, epailea1, epailea2
                FROM partidua
                """;

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Partidua p = new Partidua();
                p.setIdAuto(rs.getInt("id_auto"));
                p.setKodPartidua(rs.getString("kod_partidua"));
                p.setDenboraldia(rs.getString("denboraldia"));
                p.setData(rs.getDate("Data"));
                p.setOrdua(rs.getTime("Ordua"));
                p.setGolakLokala((Integer) rs.getObject("Golak_lokala"));
                p.setGolakKanpokoak((Integer) rs.getObject("Golak_kanpokoak"));
                p.setZelaia(rs.getString("Zelaia"));
                p.setTaldeLokala(rs.getString("Talde_lokala"));
                p.setKampokoTaldea(rs.getString("Kampoko_taldea"));
                p.setEpailea1(rs.getString("epailea1"));
                p.setEpailea2(rs.getString("epailea2"));
                lista.add(p);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // ===============================
    // TXERTATU PARTIDUA
    // ===============================
    
    public boolean insertarPartidua(Partidua p) {
        String sql = """
                INSERT INTO partidua 
                (kod_partidua, denboraldia, Data, Ordua, Golak_lokala, Golak_kanpokoak, 
                 Zelaia, Talde_lokala, Kampoko_taldea, epailea1, epailea2)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = Konexioa.getKonexioa();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getKodPartidua());
            pstmt.setString(2, p.getDenboraldia());
            pstmt.setDate(3, p.getData());
            pstmt.setTime(4, p.getOrdua());
            pstmt.setObject(5, p.getGolakLokala());
            pstmt.setObject(6, p.getGolakKanpokoak());
            pstmt.setString(7, p.getZelaia());
            pstmt.setString(8, p.getTaldeLokala());
            pstmt.setString(9, p.getKampokoTaldea());
            pstmt.setString(10, p.getEpailea1());
            pstmt.setString(11, p.getEpailea2());

            pstmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}