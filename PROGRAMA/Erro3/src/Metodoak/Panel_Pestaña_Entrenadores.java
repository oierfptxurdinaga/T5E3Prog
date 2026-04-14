package Metodoak;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Entrenatzaileen informazioa eta bakoitzari dagokion taldeko jokalariak 
 * bistaratzen dituen interfaze grafikoaren (GUI) panela kudeatzen duen klasea.
 */

public class Panel_Pestaña_Entrenadores {

	/**
     * Entrenatzaileen ikuspegia osatzen duen panela (JPanel) sortzen du.
     * Bertan entrenatzaileen datuak jasotzen dituen taula bat eta, talde bat hautatzean, 
     * jokalariak bistaratzen dituen zerrenda bat integratzen dira.
     *
     * @return Eraikitako eta osagaiz betetako {@link JPanel} objektua.
     */
	
    public static JPanel crearPanelDeLosEntrenadores() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = { "Taldea", "Izen-Abizenak", "Titulazioa" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        cargarEntrenadoresDesdeBD(modelo);

        JTable tabla = new JTable(modelo);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaTaldea = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaTaldea);
        scrollLista.setPreferredSize(new Dimension(260, 0));
        scrollLista.setBorder(BorderFactory.createTitledBorder("Taldeko Jokalariak"));

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    String taldeIzena = (String) modelo.getValueAt(fila, 0);
                    cargarJugadoresDelEquipo(taldeIzena, modeloLista);
                }
            }
        });

        panel.add(new JLabel("Entrenatzaileak", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);
        panel.add(scrollLista, BorderLayout.EAST);

        return panel;
    }

    /**
     * Datu-basetik entrenatzaileen datuak kontsultatzen ditu (taldea, izen-abizenak 
     * eta titulazioa) eta taularen modeloan (DefaultTableModel) kargatzen ditu.
     *
     * @param modelo Datuak bistaratzeko erabiliko den taularen eredua ({@link DefaultTableModel}).
     */
    
    private static void cargarEntrenadoresDesdeBD(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        Connection conn = Konexioa.getKonexioa();
        if (conn == null) return;

        try {
            String sql = "SELECT taldea, Izen_abizena, Titulazioa FROM entrenatzailea ORDER BY taldea ASC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("taldea"),
                    rs.getString("Izen_abizena"),
                    rs.getString("Titulazioa")
                });
            }
            rs.close();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errorea entrenatzaileak kargatzean: " + ex.getMessage());
        }
    }
    
    /**
     * Emandako talde baten jokalariak datu-basetik kontsultatzen ditu (dortsala eta 
     * izen-abizenak) eta zerrendaren modeloan kargatzen ditu.
     *
     * @param taldea Kontsultatu nahi den taldearen izena.
     * @param modeloLista Jokalariak bistaratuko diren zerrendaren eredua ({@link DefaultListModel}).
     */
    
    private static void cargarJugadoresDelEquipo(String taldea, DefaultListModel<String> modeloLista) {
        modeloLista.clear(); // Limpiar la lista

        Connection conn = Konexioa.getKonexioa();
        if (conn == null) return;

        try {
            String sql = "SELECT Izen_abizena, Dortsala FROM jokalaria WHERE taldea = ? ORDER BY Dortsala ASC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, taldea);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String jugadorInfo = rs.getInt("Dortsala") + " - " + rs.getString("Izen_abizena");
                modeloLista.addElement(jugadorInfo);
            }
            rs.close();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errorea taldeko jokalariak kargatzean: " + ex.getMessage());
        }
    }
}