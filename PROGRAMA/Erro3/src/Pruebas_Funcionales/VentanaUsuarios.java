package Pruebas_Funcionales;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// POJOAK eta DAOAK
import POJOAK3.Jokalaria;
import DAO.JokalariaDAO;
import POJOAK3.Entrenatzailea;
import DAO.EntrenatzaileaDAO;

import Metodoak.Cerrar_Sesion;
import Metodoak.Konexioa;
import Metodoak.Panel_Pestaña_Tarjetas_Equipos;
import Metodoak.Pestaña_Resultados;

import java.awt.*;
import java.util.List;

public class VentanaUsuarios extends JFrame {

    private JTextField txtNombre, txtApellido, txtEdad, txtCorreo, txtTelefono, txtUsuario;
    private JLabel lblBienvenida;
    
    // Taldeen zerrenda globala datu-baserako
    private final String[] ZERRENDA_TALDEAK = { "Aloña Mendi", "Amezti Zarautz", "Berango Urduliz", "Irauli Bosteko", "Kukullaga", "San Adrian" };

    public VentanaUsuarios() {
        setTitle("Ventana de Usuario");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Hasiera", crearPanelInicio());
        pestañas.addTab("Taldeak Ikusi", Panel_Pestaña_Tarjetas_Equipos.crearPanelEquipos());
        pestañas.addTab("Jokalariak", crearPanelDeLosJugadores()); // Datu-basera konektatuta
        pestañas.addTab("Entrenatzaileak", crearPanelDeLosEntrenadores()); // POJO eta DAO erabiliz konektatuta
        pestañas.addTab("Emaitzak", new Pestaña_Resultados());
        pestañas.addTab("Perfila", crearPanelPerfil());
        pestañas.addTab("Saioa Amaitu", Cerrar_Sesion.crearPanel(this));

        getContentPane().add(pestañas);
    }

    // =======================
    // PESTAÑA INICIO
    // =======================
    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(null);

        lblBienvenida = new JLabel("¡Bienvenido!", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblBienvenida.setBounds(0, 200, 780, 40);
        panel.add(lblBienvenida);

        return panel;
    }

    // =======================
    // PESTAÑA PERFIL
    // =======================
    private JPanel crearPanelPerfil() {
        JPanel panel = new JPanel(null);

        JLabel lblInfo = new JLabel("Profilaren informazioa");
        lblInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblInfo.setBounds(250, 20, 300, 25);
        panel.add(lblInfo);

        txtNombre = crearCampo(panel, "Emaila:", 60);
        txtApellido = crearCampo(panel, "Izena:", 100);
        txtEdad = crearCampo(panel, "Abizena1:", 140);
        txtCorreo = crearCampo(panel, "Abizena2:", 180);
        txtTelefono = crearCampo(panel, "Jaioteguna:", 220);
        txtUsuario = crearCampo(panel, "Erabiltzaile izena:", 260);

        return panel;
    }

    private JTextField crearCampo(JPanel panel, String etiqueta, int y) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lbl.setBounds(100, y, 140, 25);
        panel.add(lbl);

        JTextField txt = new JTextField();
        txt.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txt.setEditable(false);
        txt.setBackground(Color.LIGHT_GRAY);
        txt.setBounds(280, y, 264, 25);
        panel.add(txt);

        return txt;
    }

    // =======================
    // RELLENAR DATOS USUARIO
    // =======================
    public void setDatosUsuario(String[] datosUsuario) {
        lblBienvenida.setText("¡Ongi Etorri, " + datosUsuario[5] + "!");
        txtNombre.setText(datosUsuario[0]);
        txtApellido.setText(datosUsuario[1]);
        txtEdad.setText(datosUsuario[2]);
        txtCorreo.setText(datosUsuario[3]);
        txtTelefono.setText(datosUsuario[4]);
        txtUsuario.setText(datosUsuario[5]);
    }


    // ==========================================
    // PESTAÑA JUGADORES (CONECTADA A MYSQL)
    // ==========================================
    private JPanel crearPanelDeLosJugadores() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTaldea = new JLabel("Aukeratu taldea: ");
        lblTaldea.setFont(new Font("Arial", Font.BOLD, 14));
        
        JComboBox<String> cbEquipos = new JComboBox<>(ZERRENDA_TALDEAK);
        cbEquipos.setFont(new Font("Arial", Font.PLAIN, 14));
        
        panelTop.add(lblTaldea);
        panelTop.add(cbEquipos);

        String[] columnas = {"NANa", "Izen-abizenak", "Dortsala", "Posizioa", "Jaiotze data"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tablaJugadores = new JTable(modelo);
        tablaJugadores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaJugadores.setRowHeight(25);
        tablaJugadores.getTableHeader().setReorderingAllowed(false);
        tablaJugadores.getTableHeader().setResizingAllowed(false);
        tablaJugadores.removeColumn(tablaJugadores.getColumnModel().getColumn(0)); // Ocultar columna NANa
        
        JScrollPane scroll = new JScrollPane(tablaJugadores);

        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        cbEquipos.addActionListener(e -> {
            String taldeAukeratua = (String) cbEquipos.getSelectedItem();
            cargarJugadoresPorEquipo(taldeAukeratua, modelo);
        });

        cargarJugadoresPorEquipo((String) cbEquipos.getSelectedItem(), modelo);

        return panel;
    }

    private void cargarJugadoresPorEquipo(String taldea, DefaultTableModel modelo) {
        modelo.setRowCount(0);

        try {
            JokalariaDAO dao = new JokalariaDAO();
            List<Jokalaria> jugadores = dao.getJokalariakByTaldea(taldea);

            for (Jokalaria j : jugadores) {
                Object[] fila = {
                    j.getNana(),
                    j.getIzenAbizena(),
                    j.getDortsala(),
                    j.getPosizioa(),
                    j.getJaiotzeData()
                };
                modelo.addRow(fila);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errorea jokalariak kargatzean: " + ex.getMessage());
        }
    }

    // ==========================================
    // PESTAÑA ENTRENADORES (CONECTADA A MYSQL CON DAO)
    // ==========================================
    private JPanel crearPanelDeLosEntrenadores() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTaldea = new JLabel("Aukeratu taldea: ");
        lblTaldea.setFont(new Font("Arial", Font.BOLD, 14));
        
        JComboBox<String> cbEquipos = new JComboBox<>(ZERRENDA_TALDEAK);
        cbEquipos.setFont(new Font("Arial", Font.PLAIN, 14));
        
        panelTop.add(lblTaldea);
        panelTop.add(cbEquipos);

        String[] columnas = {"NANa", "Izen-abizenak", "Titulazioa"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tablaEntrenadores = new JTable(modelo);
        tablaEntrenadores.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaEntrenadores.setRowHeight(25);
        tablaEntrenadores.getTableHeader().setReorderingAllowed(false);
        tablaEntrenadores.getTableHeader().setResizingAllowed(false);
        tablaEntrenadores.removeColumn(tablaEntrenadores.getColumnModel().getColumn(0)); // Ocultar columna NANa visualmente
        
        JScrollPane scroll = new JScrollPane(tablaEntrenadores);

        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        cbEquipos.addActionListener(e -> {
            String taldeAukeratua = (String) cbEquipos.getSelectedItem();
            cargarEntrenadoresPorEquipo(taldeAukeratua, modelo);
        });

        cargarEntrenadoresPorEquipo((String) cbEquipos.getSelectedItem(), modelo);

        return panel;
    }

    private void cargarEntrenadoresPorEquipo(String taldea, DefaultTableModel modelo) {
        modelo.setRowCount(0);

        try {
            EntrenatzaileaDAO dao = new EntrenatzaileaDAO();
            List<Entrenatzailea> entrenadores = dao.getEntrenatzaileakByTaldea(taldea);

            for (Entrenatzailea e : entrenadores) {
                Object[] fila = {
                    e.getNana(),           
                    e.getIzenAbizena(),
                    e.getTitulazioa()
                };
                modelo.addRow(fila); 
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errorea entrenatzaileak kargatzean: " + ex.getMessage());
        }
    }

	
}
