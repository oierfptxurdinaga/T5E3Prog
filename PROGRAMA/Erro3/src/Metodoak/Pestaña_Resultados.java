package Metodoak;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Pestaña_Resultados extends JPanel {

    private JComboBox<String> comboTemporada;
    private JTable tabla;
    private DefaultTableModel modelo;
    private Image imagenFondo; // Variable para almacenar la imagen de fondo

    public Pestaña_Resultados() {
        // Cargamos la imagen de fondo. Asegúrate de que la ruta es correcta.
        imagenFondo = new ImageIcon("Imagenes16K/Fondo.jpg").getImage();
        
        setLayout(null);
        inicializarComponentes();
    }	

    // Sobreescribimos el método paintComponent para dibujar la imagen de fondo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            // Dibuja la imagen ocupando todo el ancho y alto del panel
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

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
        
        // --- INICIO DE LA TRANSPARENCIA DE LA TABLA ---
        tabla.setOpaque(false); // Hace transparente la tabla
        
        // Hace transparentes las celdas de la tabla para ver el fondo
        ((DefaultTableCellRenderer)tabla.getDefaultRenderer(Object.class)).setOpaque(false);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 700, 350);
        
        // Hace transparente el contenedor del scroll y su área de visualización
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        // --- FIN DE LA TRANSPARENCIA DE LA TABLA ---

        add(scroll);
        
        btnFiltrar.addActionListener(e -> cargarClasificacion());
    }

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