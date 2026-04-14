package Metodoak;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Denboraldiko emaitzak eta taldeen sailkapena erakusten dituen panela pertsonalizatua.
 * Atzeko planoko irudi bat onartzen du eta taula garden bat erabiltzen du interfaze erakargarriagoa lortzeko.
 
 * Pestaña_Resultados klasearen eraikitzailea.
 * Atzeko planoko irudia kargatzen du eta paneleko osagaiak hasieratzen ditu.

     * Panelaren marrazketa-metodoa gainidazten du, atzeko planoan irudi bat (Fondo.jpg)
     * marrazteko panelaren dimentsio osora egokituz.
     *
     * @param g Marrazketa testuingurua ({@link Graphics}).
     * 
     * 
     * Interfazeko elementuak (etiketak, goitibeherako zerrendak, botoiak eta taula)
     * sortu, konfiguratu eta panelean kokatzen ditu.
     * 
     * Aukeratutako denboraldiaren arabera (adibidez, 2024/25), datu-baseko taula 
     * espezifikotik (adibidez, sailkapena_24_25) sailkapeneko datuak irakurtzen ditu.
     * Ondoren, datuak taulan bistaratzen ditu, puntuen eta gol-diferentziaren arabera ordenatuta.
     */


public class Pestaña_Resultados extends JPanel {

    private JComboBox<String> comboTemporada;
    private JTable tabla;
    private DefaultTableModel modelo;
    private Image imagenFondo;

    public Pestaña_Resultados() {
        imagenFondo = new ImageIcon("Imagenes16K/Fondo.jpg").getImage();
        
        setLayout(null);
        inicializarComponentes();
    }	
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Interfazeko elementuak (etiketak, goitibeherako zerrendak, botoiak eta taula)
     * sortu, konfiguratu eta panelean kokatzen ditu.
     */
    
    private void inicializarComponentes() {
        JLabel lblTemporada = new JLabel("Demboraldia:");
        lblTemporada.setBounds(20, 20, 100, 25);
        add(lblTemporada);

        String[] temporadas = { "2024/25", "2025/26" };
        comboTemporada = new JComboBox<>(temporadas);
        comboTemporada.setBounds(120, 20, 150, 25);
        add(comboTemporada);

        JButton btnFiltrar = new JButton("Filtrar Sailkapena");
        btnFiltrar.setBounds(300, 20, 150, 25);
        add(btnFiltrar);

        String[] columnas = {"Pos", "Taldea", "Puntuak", "JP", "IrP", "BerP", "GaP", "AG", "KG"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tabla = new JTable(modelo);
        
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        tabla.setRowHeight(25);
        
        tabla.setOpaque(false);
        
        ((DefaultTableCellRenderer)tabla.getDefaultRenderer(Object.class)).setOpaque(false);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 700, 350);
        
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        add(scroll);
        
        btnFiltrar.addActionListener(e -> cargarClasificacion());
    }

    /**
     * Aukeratutako denboraldiaren arabera (adibidez, 2024/25), datu-baseko taula 
     * espezifikotik (adibidez, sailkapena_24_25) sailkapeneko datuak irakurtzen ditu.
     * Ondoren, datuak taulan bistaratzen ditu, puntuen eta gol-diferentziaren arabera ordenatuta.
     */
    
    private void cargarClasificacion() {
        String temporadaStr = (String) comboTemporada.getSelectedItem();
        String nombreTabla = "sailkapena_" + temporadaStr.substring(2).replace("/", "_"); 

        modelo.setRowCount(0); 

        try (Connection conn = Konexioa.getKonexioa()) {
            if (conn == null) return;

            String sql = "SELECT * FROM " + nombreTabla + " ORDER BY puntuak DESC, (AG-KG) DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            int pos = 1;
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    pos++,
                    rs.getString("taldea"),
                    rs.getInt("puntuak"),
                    rs.getInt("JP"),
                    rs.getInt("IrP"),
                    rs.getInt("BerP"),
                    rs.getInt("GaP"),
                    rs.getInt("AG"),
                    rs.getInt("KG")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errorea sailkapena kargatzean: " + ex.getMessage());
        }
    }
}