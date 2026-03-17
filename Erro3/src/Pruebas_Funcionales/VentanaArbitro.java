package Pruebas_Funcionales;

import javax.swing.*;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import Metodoak.Cerrar_Sesion;
import Metodoak.Panel_Pestaña_Tarjetas_Equipos;
import Metodoak.Konexioa;

public class VentanaArbitro extends JFrame {

    public VentanaArbitro() {
        setTitle("Panel del Arbitro");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Orri Nagusia", crearPanelPrincipal());
        pestañas.addTab("Taldeak Ikusi", Panel_Pestaña_Tarjetas_Equipos.crearPanelEquipos());
        
        JPanel panelCerrarSesion = Cerrar_Sesion.crearPanel(this);
        pestañas.addTab("Saioa Itxi", panelCerrarSesion);

        add(pestañas);
    }

    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel lblTemporada = new JLabel("Denboraldia:");
        lblTemporada.setBounds(50, 50, 100, 25);
        panel.add(lblTemporada);

        String[] temporadas = {"2024/25", "2025/26"};
        JComboBox<String> comboTemporada = new JComboBox<>(temporadas);
        comboTemporada.setBounds(150, 50, 150, 25);
        panel.add(comboTemporada);

        JLabel lblJornada = new JLabel("Jardunaldia:");
        lblJornada.setBounds(400, 50, 100, 25);
        panel.add(lblJornada);

        Integer[] jornadas = new Integer[38];
        for (int i = 0; i < 38; i++) jornadas[i] = i + 1;
        JComboBox<Integer> comboJornada = new JComboBox<>(jornadas);
        comboJornada.setBounds(500, 50, 100, 25);
        panel.add(comboJornada);

        String[] equipos = {"Aloña Mendi", "Amezti Zarautz", "Berango Urduliz", "Irauli Bosteko", "Kukullaga", "San Adrian"};

        JLabel lblLocal = new JLabel("Talde Lokala:");
        lblLocal.setBounds(50, 150, 100, 25);
        panel.add(lblLocal);

        JComboBox<String> comboLocal = new JComboBox<>(equipos);
        comboLocal.setBounds(150, 150, 150, 25);
        panel.add(comboLocal);

        JTextField txtGolesLocal = new JTextField();
        txtGolesLocal.setBounds(320, 150, 50, 25);
        panel.add(txtGolesLocal);

        JLabel lblVisitante = new JLabel("Kanpoko Taldea:");
        lblVisitante.setBounds(400, 150, 100, 25);
        panel.add(lblVisitante);

        JComboBox<String> comboVisitante = new JComboBox<>(equipos);
        comboVisitante.setBounds(500, 150, 150, 25);
        panel.add(comboVisitante);

        JTextField txtGolesVisitante = new JTextField();
        txtGolesVisitante.setBounds(670, 150, 50, 25);
        panel.add(txtGolesVisitante);

        JButton btnGuardar = new JButton("Gorde Emaitza");
        btnGuardar.setBounds(300, 250, 200, 40);
        panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            String temporada = (String) comboTemporada.getSelectedItem();
            int jornada = (int) comboJornada.getSelectedItem();
            String local = (String) comboLocal.getSelectedItem();
            String visitante = (String) comboVisitante.getSelectedItem();

            if (local.equals(visitante)) {
                JOptionPane.showMessageDialog(panel, "Talde bera ezin da bi aldiz aukeratu.");
                return;
            }

            try {
                int golesLocal = Integer.parseInt(txtGolesLocal.getText());
                int golesVisitante = Integer.parseInt(txtGolesVisitante.getText());

                try (Connection conn = Konexioa.getKonexioa()) {
                    if (conn == null) return;

                    // 1. Insertar o actualizar
                    String sql = "INSERT INTO partidua (denboraldia, jardunaldia, Talde_lokala, Golak_lokala, Kampoko_taldea, Golak_kanpokoak) " +
                                 "VALUES (?, ?, ?, ?, ?, ?) " +
                                 "ON DUPLICATE KEY UPDATE Golak_lokala = ?, Golak_kanpokoak = ?";
                    
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, temporada);
                    pstmt.setInt(2, jornada);
                    pstmt.setString(3, local);
                    pstmt.setInt(4, golesLocal);
                    pstmt.setString(5, visitante);
                    pstmt.setInt(6, golesVisitante);
                    pstmt.setInt(7, golesLocal);
                    pstmt.setInt(8, golesVisitante);
                    pstmt.executeUpdate();

                    // 2. Llamar al Procedimiento (Ej: "2024/25" -> "24-25")
                    String temporadaProc = temporada.substring(2).replace("/", "-"); 
                    CallableStatement cstmt = conn.prepareCall("{CALL ActualizarClasificacion(?)}");
                    cstmt.setString(1, temporadaProc);
                    cstmt.execute();

                    JOptionPane.showMessageDialog(panel, "Behar bezala gorde da emaitza eta sailkapena eguneratu da.");
                    txtGolesLocal.setText("");
                    txtGolesVisitante.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Errorea datu-basean gordetzean: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Golak zenbakiak izan behar dira.");
            }
        });

        return panel;
    }
}