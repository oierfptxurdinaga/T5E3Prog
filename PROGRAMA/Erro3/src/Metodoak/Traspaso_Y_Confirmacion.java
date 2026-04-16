package Metodoak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Jokalariak talde batetik bestera eskualdatzeko (traspasatzeko) eta dortsal berria 
 * esleitzeko prozesua kudeatzen duen interfaze grafikoaren klasea.
 */
public class Traspaso_Y_Confirmacion {

    /**
     * Sisteman eskuragarri dauden taldeen izenak gordetzen dituen array finkoa.
     * Hautatze-zerrendetan (ComboBox) jatorrizko eta helburu taldeak erakusteko erabiltzen da.
     */
    private static final String[] ZERRENDA_TALDEAK = {
            "Aloña Mendi", "Amezti Zarautz", "Berango Urduliz", "Irauli Bosteko", "Kukullaga", "San Adrian"
    };

    /**
     * ComboBox batean jokalari baten informazioa (NANa, izena eta dortsala)
     * gordetzeko eta bistaratze egokia egiteko klase laguntzailea.
     */
    private static class JugadorComboItem {
        String nana;
        String nombre;
        int dorsal;

        /**
         * JugadorComboItem objektu berri bat sortzen du.
         *
         * @param nana Jokalariaren NANa.
         * @param nombre Jokalariaren izen-abizenak.
         * @param dorsal Jokalariaren dortsala.
         */
        public JugadorComboItem(String nana, String nombre, int dorsal) {
            this.nana = nana;
            this.nombre = nombre;
            this.dorsal = dorsal;
        }
        
        /**
         * Jokalariaren informazioa testu moduan itzultzen du ComboBox-ean erakusteko.
         *
         * @return Jokalariaren izena eta dortsala.
         */
        @Override
        public String toString() {
            return nombre + " (Dortsala: " + dorsal + ")";
        }
    }

    /**
     * Traspasoak egiteko osagai guztiak (jatorrizko taldea, jokalaria, helburu taldea, 
     * dortsal berria eta botoia) dituen panela sortzen du. Panelean atzeko planoko irudia 
     * duen klase pertsonalizatu bat erabiltzen da.
     *
     * @return Traspasoen interfaze osoa jasotzen duen {@link JPanel} objektua.
     */
    public static JPanel crearPanelTraspaso() {
    	PanelConFondo panel = new PanelConFondo("Imagenes16K/TEXTURES/Fondo.jpg", new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> cbEquipoOrigen = new JComboBox<>(ZERRENDA_TALDEAK);
        JComboBox<JugadorComboItem> cbJugadores = new JComboBox<>();
        JComboBox<String> cbEquipoDestino = new JComboBox<>(ZERRENDA_TALDEAK);
        JTextField txtDorsal = new JTextField();

        panel.add(new JLabel("Jatorrizko Taldea (Equipo de origen):"));
        panel.add(cbEquipoOrigen);

        panel.add(new JLabel("Aukeratu Jokalaria (Selecciona el Jugador):"));
        panel.add(cbJugadores);

        panel.add(new JLabel("Helburu Taldea (Equipo de destino):"));
        panel.add(cbEquipoDestino);

        panel.add(new JLabel("Dortsal Berria (Nuevo Dorsal):"));
        panel.add(txtDorsal);

        JButton btnTraspasar = new JButton("Traspasatu");
        panel.add(new JLabel(""));
        panel.add(btnTraspasar);

        ActionListener cargarJugadoresAction = e -> {
            String equipoOrigen = (String) cbEquipoOrigen.getSelectedItem();
            cbJugadores.removeAllItems();
            txtDorsal.setText("");

            if (equipoOrigen == null) return;

            try (Connection conn = Konexioa.getKonexioa()) {
                if (conn == null) return;
                String sql = "SELECT NANa, Izen_abizena, Dortsala FROM jokalaria WHERE taldea = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, equipoOrigen);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cbJugadores.addItem(new JugadorComboItem(
                            rs.getString("NANa"),
                            rs.getString("Izen_abizena"),
                            rs.getInt("Dortsala")
                    ));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "Errorea jokalariak kargatzean: " + ex.getMessage());
            }
        };

        cbEquipoOrigen.addActionListener(cargarJugadoresAction);

        cbJugadores.addActionListener(e -> {
            JugadorComboItem seleccionado = (JugadorComboItem) cbJugadores.getSelectedItem();
            if (seleccionado != null) {
                txtDorsal.setText(String.valueOf(seleccionado.dorsal));
            }
        });

        cargarJugadoresAction.actionPerformed(null);

        btnTraspasar.addActionListener(e -> {
            JugadorComboItem jokalaria = (JugadorComboItem) cbJugadores.getSelectedItem();
            String equipoDestino = (String) cbEquipoDestino.getSelectedItem();
            String equipoOrigen = (String) cbEquipoOrigen.getSelectedItem();
            String nuevoDorsalStr = txtDorsal.getText();

            if (jokalaria == null) {
                JOptionPane.showMessageDialog(panel, "Aukeratu jokalari bat mesedez.");
                return;
            }

            if (equipoOrigen.equals(equipoDestino)) {
                JOptionPane.showMessageDialog(panel, "Jatorrizko eta helburu taldeak ezin dira berdinak izan.");
                return;
            }

            int nuevoDorsal;
            try {
                nuevoDorsal = Integer.parseInt(nuevoDorsalStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Dortsala zenbaki bat izan behar da.");
                return;
            }

            try (Connection conn = Konexioa.getKonexioa()) {
                if (conn == null) return;

                String checkSql = "SELECT count(*) FROM jokalaria WHERE taldea = ? AND Dortsala = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, equipoDestino);
                checkStmt.setInt(2, nuevoDorsal);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(panel, "Dortsal hori (" + nuevoDorsal + ") okupatuta dago " + equipoDestino + " taldean. Mesedez, aldatu dortsala.");
                    return;
                }

                String updateSql = "UPDATE jokalaria SET taldea = ?, Dortsala = ? WHERE NANa = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, equipoDestino);
                updateStmt.setInt(2, nuevoDorsal);
                updateStmt.setString(3, jokalaria.nana);

                int filas = updateStmt.executeUpdate();
                if (filas > 0) {
                    try {
                         String updatePertsona = "UPDATE pertsona SET taldea = ? WHERE NANa = ?";
                         PreparedStatement pstmtP = conn.prepareStatement(updatePertsona);
                         pstmtP.setString(1, equipoDestino);
                         pstmtP.setString(2, jokalaria.nana);
                         pstmtP.executeUpdate();
                    } catch (SQLException ignore) { 
                    }

                    JOptionPane.showMessageDialog(panel, "Traspasoa behar bezala burutu da!");
                    cargarJugadoresAction.actionPerformed(null);
                } else {
                    JOptionPane.showMessageDialog(panel, "Ez da jokalaria aurkitu datu-basean.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(panel, "Errorea traspasoan: " + ex.getMessage());
            }
        });

        return panel;
    }
    
    /**
     * Atzeko planoan irudi bat erakusten duen JPanel pertsonalizatua.
     */
    static class PanelConFondo extends JPanel {
        private Image imagen;

        /**
         * PanelConFondo klasearen eraikitzailea.
         *
         * @param rutaImagen Atzeko plano gisa erabiliko den irudiaren kokalekua (ruta).
         * @param layout Panelak erabiliko duen diseinu-kudeatzailea (LayoutManager).
         */
        public PanelConFondo(String rutaImagen, LayoutManager layout) {
            super(layout);
            imagen = new ImageIcon(rutaImagen).getImage();
        }

        /**
         * Metodo hau gainidatziz atzeko irudia marrazten da panelaren tamaina osora egokituz.
         *
         * @param g Marrazketa testuingurua ({@link Graphics}).
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}