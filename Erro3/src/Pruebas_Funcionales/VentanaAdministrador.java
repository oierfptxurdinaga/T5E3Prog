package Pruebas_Funcionales;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Metodoak.Cerrar_Sesion;
import Metodoak.Konexioa;
import Metodoak.Traspaso_Y_Confirmacion;
import Metodoak.AutoNANaGenerator;

import POJOAK3.Pertsona;
import DAO.PertsonaDAO;

public class VentanaAdministrador extends JFrame {

    private JTextArea areaUsuarios;
    private final String ARCHIVO_USUARIOS = "datos.dat";

    private final String[] ZERRENDA_TALDEAK = {"Aloña Mendi", "Amezti Zarautz", "Berango Urduliz", "Irauli Bosteko", "Kukullaga", "San Adrian"};

    public VentanaAdministrador() {
        setTitle("Panel de Administrador");
        setSize(920, 575);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane pestañas = new JTabbedPane();
        
        // Pestañas Locales (datos.dat)
        pestañas.addTab("Erabiltzaileak ikusi", crearPanelUsuarios());
        pestañas.addTab("Erabiltzaile Sortu", crearPanelCrearUsuario());
        pestañas.addTab("Erabiltzaile Ezabatu", crearPanelEliminarUsuario());
        
        // Pestañas Base de Datos (MySQL)
        pestañas.addTab("Jokalari Sortu", crearPanelCrearJugador());
        pestañas.addTab("Jokalari Ezabatu", crearPanelEliminarJugador());
        pestañas.addTab("Jokalariak Aldatu", crearPanelTraspaso());

        //Pestaña de generar XML
        pestañas.addTab("XML Kudeaketa", crearPanelXML());
        
        // Botón de cerrar sesión
        JPanel panelCerrarSesion = Cerrar_Sesion.crearPanel(this);
        pestañas.addTab("Saioa Itxi", panelCerrarSesion);

        add(pestañas);
    }

    // ==========================================
    // 1. VER USUARIOS (LOCAL - datos.dat)
    // ==========================================
    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());
        areaUsuarios = new JTextArea();
        areaUsuarios.setEditable(false);
        panel.add(new JScrollPane(areaUsuarios), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Eguneratu Zerrenda");
        panel.add(btnActualizar, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> cargarUsuariosLocales());
        cargarUsuariosLocales();

        return panel;
    }

    private void cargarUsuariosLocales() {
        areaUsuarios.setText("");
        try {
            if (!Files.exists(Paths.get(ARCHIVO_USUARIOS))) return;
            List<String> lineas = Files.readAllLines(Paths.get(ARCHIVO_USUARIOS));
            for (String linea : lineas) {
                String[] datos = linea.split(";");
                if (datos.length >= 8) {
                    areaUsuarios.append("Izena: " + datos[1] + " | Email: " + datos[4] + 
                                        " | Erabiltzailea: " + datos[5] + " | Rola: " + datos[7] + "\n");
                    areaUsuarios.append("---------------------------------------------------\n");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Errorea erabiltzaileak irakurtzean.");
        }
    }

    // ==========================================
    // 2. CREAR USUARIOS (LOCAL - datos.dat)
    // ==========================================
    private JPanel crearPanelCrearUsuario() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtIzena = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtErabiltzailea = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JComboBox<String> cbRola = new JComboBox<>(new String[]{"ERABILTZAILEA", "ARBITROA", "ADMIN"});

        panel.add(new JLabel("Izena:")); panel.add(txtIzena);
        panel.add(new JLabel("Email:")); panel.add(txtEmail);
        panel.add(new JLabel("Erabiltzaile Izena:")); panel.add(txtErabiltzailea);
        panel.add(new JLabel("Pasahitza:")); panel.add(txtPass);
        panel.add(new JLabel("Rola:")); panel.add(cbRola);

        JButton btnGuardar = new JButton("Gorde Fitxategian");
        panel.add(new JLabel()); panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
                String nuevoID = "ID" + (int)(Math.random()*1000); 
                String linea = nuevoID + ";" + txtIzena.getText() + ";Apellido;30;" + 
                               txtEmail.getText() + ";" + txtErabiltzailea.getText() + ";" + 
                               new String(txtPass.getPassword()) + ";" + cbRola.getSelectedItem();
                bw.write(linea);
                bw.newLine();
                JOptionPane.showMessageDialog(this, "Erabiltzailea fitxategian gorde da!");
                txtIzena.setText(""); txtEmail.setText(""); txtErabiltzailea.setText(""); txtPass.setText("");
                cargarUsuariosLocales();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Errorea gordetzean.");
            }
        });
        return panel;
    }

    // ==========================================
    // 3. ELIMINAR USUARIOS (LOCAL - datos.dat)
    // ==========================================
    private JPanel crearPanelEliminarUsuario() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Erabiltzailea ezabatzeko (Erabiltzaile-izena):"));
        JTextField txtUsuarioEliminar = new JTextField(15);
        panel.add(txtUsuarioEliminar);

        JButton btnEliminar = new JButton("Ezabatu");
        panel.add(btnEliminar);

        btnEliminar.addActionListener(e -> {
            String user = txtUsuarioEliminar.getText();
            try {
                List<String> lineas = Files.readAllLines(Paths.get(ARCHIVO_USUARIOS));
                boolean encontrado = false;
                Iterator<String> it = lineas.iterator();
                
                while (it.hasNext()) {
                    String[] datos = it.next().split(";");
                    if (datos.length >= 8 && datos[5].equals(user)) {
                        it.remove();
                        encontrado = true;
                    }
                }

                if (encontrado) {
                    Files.write(Paths.get(ARCHIVO_USUARIOS), lineas);
                    JOptionPane.showMessageDialog(this, "Erabiltzailea ezabatu da fitxategitik.");
                    cargarUsuariosLocales();
                    txtUsuarioEliminar.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Ez da erabiltzailea aurkitu.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Errorea ezabatzean.");
            }
        });
        return panel;
    }

    // ==========================================
    // 4. CREAR JUGADOR (MySQL - BASE DE DATOS)
    // ==========================================
    private JPanel crearPanelCrearJugador() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtNombre = new JTextField();
        JTextField txtDorsal = new JTextField();
        JTextField txtFechaNac = new JTextField(); 
        txtFechaNac.setToolTipText("UUUU-HH-EE (Adibidez: 2005-04-14)"); 
        JComboBox<String> cbPosicion = new JComboBox<>(new String[]{"Atezaina", "Hegala", "Pibotea", "Zentrokoa", "Albokoa"});
        JComboBox<String> cbEquipo = new JComboBox<>(ZERRENDA_TALDEAK);

        panel.add(new JLabel("Jokalari Izena eta Abizena:")); panel.add(txtNombre);
        panel.add(new JLabel("Dortsala:")); panel.add(txtDorsal);
        panel.add(new JLabel("Posizioa:")); panel.add(cbPosicion);
        panel.add(new JLabel("Jaiotze-data (UUUU-HH-EE):")); panel.add(txtFechaNac); 
        panel.add(new JLabel("Taldea:")); panel.add(cbEquipo);

        JButton btnGuardar = new JButton("Datu-basean Sortu");
        panel.add(new JLabel()); panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try (Connection conn = Konexioa.getKonexioa()) {
                if (conn == null) return;

                // 1. Llamar al método externo para generar el NANa único
                String nanaGenerado = AutoNANaGenerator.generarNANaUnico(conn);

                // 2. Insertar en Pertsona usando el POJO y DAO
                Pertsona pertsona = new Pertsona();
                pertsona.setNana(nanaGenerado);
                pertsona.setIzenAbizena(txtNombre.getText());
                pertsona.setTaldea((String) cbEquipo.getSelectedItem());
                pertsona.setRola("Jokalaria");
                
                PertsonaDAO pertsonaDAO = new PertsonaDAO();
                pertsonaDAO.insertarPertsona(pertsona);

                // 3. Insertar en la tabla jokalaria
                String sqlJokalaria = "INSERT INTO jokalaria (NANa, Izen_abizena, Dortsala, Posizioa, Jaiotze_data, taldea) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmtJ = conn.prepareStatement(sqlJokalaria)) {
                    pstmtJ.setString(1, nanaGenerado);
                    pstmtJ.setString(2, txtNombre.getText());
                    pstmtJ.setInt(3, Integer.parseInt(txtDorsal.getText()));
                    pstmtJ.setString(4, (String) cbPosicion.getSelectedItem());
                    
                    java.sql.Date fechaSql = java.sql.Date.valueOf(txtFechaNac.getText());
                    pstmtJ.setDate(5, fechaSql);
                    
                    pstmtJ.setString(6, (String) cbEquipo.getSelectedItem());
                    pstmtJ.executeUpdate();
                }

                JOptionPane.showMessageDialog(this, "Jokalaria ondo sortu da! NAN esleitua: " + nanaGenerado);
                
                txtNombre.setText(""); 
                txtDorsal.setText("");
                txtFechaNac.setText(""); 

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Dortsala zenbaki bat izan behar da.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Jaiotze-datak formatu egokia izan behar du: UUUU-HH-EE (Adibidez: 2005-04-14).");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errorea datu-basean: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        return panel;
    }

    // ==========================================
    // 5. ELIMINAR JUGADOR (MySQL - BASE DE DATOS)
    // ==========================================
    private JPanel crearPanelEliminarJugador() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Jokalariaren NAN-a ezabatzeko:"));
        JTextField txtNanaJugador = new JTextField(10); 
        panel.add(txtNanaJugador);

        JButton btnEliminar = new JButton("Ezabatu BDtik");
        panel.add(btnEliminar);

        btnEliminar.addActionListener(e -> {
            String nana = txtNanaJugador.getText().trim();
            
            if (nana.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mesedez, sartu NAN bat.");
                return;
            }

            try (Connection conn = Konexioa.getKonexioa()) {
                if (conn == null) return;
                
                // 1. Eliminar de la tabla jokalaria usando el NANa
                String sqlJokalaria = "DELETE FROM jokalaria WHERE NANa = ?";
                try (PreparedStatement pstmtJ = conn.prepareStatement(sqlJokalaria)) {
                    pstmtJ.setString(1, nana);
                    
                    int filas = pstmtJ.executeUpdate();
                    
                    if (filas > 0) {
                        // 2. Si se eliminó de jokalaria, también lo borramos de pertsona usando el DAO
                        PertsonaDAO pertsonaDAO = new PertsonaDAO();
                        pertsonaDAO.eliminarPertsona(nana);
                        
                        JOptionPane.showMessageDialog(this, "Jokalaria (" + nana + ") ezabatu da datu-basetik.");
                        txtNanaJugador.setText(""); 
                    } else {
                        JOptionPane.showMessageDialog(this, "Ez da jokalaria aurkitu datu-basean NAN horrekin.");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errorea ezabatzean: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        return panel;
    }

    // ==========================================
    // 6. TRASPASO DE JUGADORES (MySQL - BASE DE DATOS)
    // ==========================================
    private JPanel crearPanelTraspaso() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Jokalariaren ID:"));
        JTextField txtIdJugador = new JTextField(5);
        panel.add(txtIdJugador);

        panel.add(new JLabel("Talde Berria:"));
        JComboBox<String> cbEquipoDestino = new JComboBox<>(ZERRENDA_TALDEAK);
        panel.add(cbEquipoDestino);

        JButton btnTraspasar = new JButton("Aldatu BD-an");
        panel.add(btnTraspasar);

        btnTraspasar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdJugador.getText());
                String equipoDestino = (String) cbEquipoDestino.getSelectedItem();
                
                boolean exito = Traspaso_Y_Confirmacion.realizarTraspaso(this, id, equipoDestino);
                if (exito) txtIdJugador.setText("");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID-a zenbaki bat izan behar da.");
            }
        });

        return panel;
    }
    
    // ==========================================
    // 7. GENERAR XML (MySQL - BASE DE DATOS) Todavia no esta hecho.
    // ==========================================
    private JPanel crearPanelXML() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));

        JButton btnExportar = new JButton("Datuak XML-ra Esportatu");
        JButton btnImportar = new JButton("Datuak XML-tik Inportatu");

        // Botoiei ekintzak gehitu
        btnExportar.addActionListener(e -> esportatuXML());
        btnImportar.addActionListener(e -> inportatuXML());

        panel.add(btnExportar);
        panel.add(btnImportar);

        // OSO GARRANTZITSUA: jatorrizko kodeak null itzultzen zuen, orain panela itzultzen du
        return panel; 
    }

 // --- METODOS AUXILIARES PARA XML ---
    private void sortuElementua(Document doc, Element parent, String tagName, String textContent) {
        Element el = doc.createElement(tagName);
        el.appendChild(doc.createTextNode(textContent != null ? textContent : ""));
        parent.appendChild(el);
    }

    private String lortuBalioa(Element element, String tagName) {
        NodeList nl = element.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            return nl.item(0).getTextContent().trim();
        }
        return "";
    }

    private void setIntOrNull(PreparedStatement pstmt, int index, String value) throws java.sql.SQLException {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
            pstmt.setNull(index, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(index, Integer.parseInt(value.trim()));
        }
    }

    private void setDateOrNull(PreparedStatement pstmt, int index, String value) throws java.sql.SQLException {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
            pstmt.setNull(index, java.sql.Types.DATE);
        } else {
            pstmt.setDate(index, java.sql.Date.valueOf(value.trim()));
        }
    }

    private void setTimeOrNull(PreparedStatement pstmt, int index, String value) throws java.sql.SQLException {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
            pstmt.setNull(index, java.sql.Types.TIME);
        } else {
            pstmt.setTime(index, java.sql.Time.valueOf(value.trim()));
        }
    }

    // --- XML ESPORTATZEKO METODOA ---
    private void esportatuXML() {
        try (Connection conn = Konexioa.getKonexioa()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Errorea: Ezin izan da datu-basearekin konektatu.");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // XML-aren Erro-elementua
            Element rootElement = doc.createElement("EskubaloiDatuak");
            doc.appendChild(rootElement);

            // 1. PERTSONAK
            Element pertsonakNode = doc.createElement("Pertsonak");
            rootElement.appendChild(pertsonakNode);
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM pertsona");
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Element item = doc.createElement("Pertsona");
                    sortuElementua(doc, item, "NANa", rs.getString("NANa"));
                    sortuElementua(doc, item, "Izen_abizena", rs.getString("Izen_abizena"));
                    sortuElementua(doc, item, "Adina", rs.getString("Adina"));
                    sortuElementua(doc, item, "Helbidea", rs.getString("Helbidea"));
                    sortuElementua(doc, item, "Tlfn", rs.getString("Tlfn"));
                    sortuElementua(doc, item, "taldea", rs.getString("taldea"));
                    sortuElementua(doc, item, "Rola", rs.getString("Rola"));
                    pertsonakNode.appendChild(item);
                }
            }

            // 2. JOKALARIAK
            Element jokalariakNode = doc.createElement("Jokalariak");
            rootElement.appendChild(jokalariakNode);
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM jokalaria");
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Element item = doc.createElement("Jokalaria");
                    sortuElementua(doc, item, "NANa", rs.getString("NANa"));
                    sortuElementua(doc, item, "Izen_abizena", rs.getString("Izen_abizena"));
                    sortuElementua(doc, item, "Dortsala", rs.getString("Dortsala"));
                    sortuElementua(doc, item, "Posizioa", rs.getString("Posizioa"));
                    java.sql.Date data = rs.getDate("Jaiotze_data");
                    sortuElementua(doc, item, "Jaiotze_data", data != null ? data.toString() : "");
                    sortuElementua(doc, item, "taldea", rs.getString("taldea"));
                    jokalariakNode.appendChild(item);
                }
            }

            // 3. ENTRENATZAILEAK
            Element entrenatzaileakNode = doc.createElement("Entrenatzaileak");
            rootElement.appendChild(entrenatzaileakNode);
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM entrenatzailea");
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Element item = doc.createElement("Entrenatzailea");
                    sortuElementua(doc, item, "NANa", rs.getString("NANa"));
                    sortuElementua(doc, item, "Izen_abizena", rs.getString("Izen_abizena"));
                    sortuElementua(doc, item, "Titulazioa", rs.getString("Titulazioa"));
                    sortuElementua(doc, item, "taldea", rs.getString("taldea"));
                    entrenatzaileakNode.appendChild(item);
                }
            }

            // 4. EPAILEAK
            Element epaileakNode = doc.createElement("Epaileak");
            rootElement.appendChild(epaileakNode);
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM epailea");
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Element item = doc.createElement("Epailea");
                    sortuElementua(doc, item, "NANa", rs.getString("NANa"));
                    sortuElementua(doc, item, "Izen_abizena", rs.getString("Izen_abizena"));
                    sortuElementua(doc, item, "Maila", rs.getString("Maila"));
                    epaileakNode.appendChild(item);
                }
            }

            // 5. PARTIDUAK
            Element partiduakNode = doc.createElement("Partiduak");
            rootElement.appendChild(partiduakNode);
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM partidua");
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Element item = doc.createElement("Partidua");
                    sortuElementua(doc, item, "id_auto", rs.getString("id_auto"));
                    sortuElementua(doc, item, "kod_partidua", rs.getString("kod_partidua"));
                    sortuElementua(doc, item, "denboraldia", rs.getString("denboraldia"));
                    java.sql.Date data = rs.getDate("Data");
                    sortuElementua(doc, item, "Data", data != null ? data.toString() : "");
                    java.sql.Time ordua = rs.getTime("Ordua");
                    sortuElementua(doc, item, "Ordua", ordua != null ? ordua.toString() : "");
                    sortuElementua(doc, item, "Golak_lokala", rs.getString("Golak_lokala"));
                    sortuElementua(doc, item, "Golak_kanpokoak", rs.getString("Golak_kanpokoak"));
                    sortuElementua(doc, item, "Zelaia", rs.getString("Zelaia"));
                    sortuElementua(doc, item, "Talde_lokala", rs.getString("Talde_lokala"));
                    sortuElementua(doc, item, "Kampoko_taldea", rs.getString("Kampoko_taldea"));
                    sortuElementua(doc, item, "epailea1", rs.getString("epailea1"));
                    sortuElementua(doc, item, "epailea2", rs.getString("epailea2"));
                    partiduakNode.appendChild(item);
                }
            }

            // Fitxategia Gorde
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Gorde XML fitxategia");
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if(!file.getName().toLowerCase().endsWith(".xml")) {
                    file = new File(file.getParentFile(), file.getName() + ".xml");
                }
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
                JOptionPane.showMessageDialog(this, "Datu-base osoa XML fitxategian behar bezala esportatu da!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errorea XML esportatzean: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // --- XML INPORTATZEKO METODOA ---
    private void inportatuXML() {
        try (Connection conn = Konexioa.getKonexioa()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Errorea: Ezin izan da datu-basearekin konektatu.");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Aukeratu inportatzeko XML fitxategia");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(file);
                doc.getDocumentElement().normalize();
                
                try (PreparedStatement psOff = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0")) {
					psOff.execute();
				}

                // 1. INPORTATU PERTSONAK (Lehenik inportatu behar dira jokalari, arbitro eta entrenatzaileak baino lehen)
                NodeList nlPertsona = doc.getElementsByTagName("Pertsona");
                String sqlP = "INSERT INTO pertsona (NANa, Izen_abizena, Adina, Helbidea, Tlfn, taldea, Rola) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                              "ON DUPLICATE KEY UPDATE Izen_abizena=VALUES(Izen_abizena), Adina=VALUES(Adina), Helbidea=VALUES(Helbidea), Tlfn=VALUES(Tlfn), taldea=VALUES(taldea), Rola=VALUES(Rola)";
                try (PreparedStatement ps = conn.prepareStatement(sqlP)) {
                    for (int i = 0; i < nlPertsona.getLength(); i++) {
                        Element e = (Element) nlPertsona.item(i);
                        ps.setString(1, lortuBalioa(e, "NANa"));
                        ps.setString(2, lortuBalioa(e, "Izen_abizena"));
                        setIntOrNull(ps, 3, lortuBalioa(e, "Adina"));
                        ps.setString(4, lortuBalioa(e, "Helbidea"));
                        ps.setString(5, lortuBalioa(e, "Tlfn"));
                        ps.setString(6, lortuBalioa(e, "taldea"));
                        ps.setString(7, lortuBalioa(e, "Rola"));
                        ps.executeUpdate();
                    }
                }

                // 2. INPORTATU JOKALARIAK
                NodeList nlJokalaria = doc.getElementsByTagName("Jokalaria");
                String sqlJ = "INSERT INTO jokalaria (NANa, Izen_abizena, Dortsala, Posizioa, Jaiotze_data, taldea) " +
                              "VALUES (?, ?, ?, ?, ?, ?) " +
                              "ON DUPLICATE KEY UPDATE Izen_abizena=VALUES(Izen_abizena), Dortsala=VALUES(Dortsala), Posizioa=VALUES(Posizioa), Jaiotze_data=VALUES(Jaiotze_data), taldea=VALUES(taldea)";
                try (PreparedStatement ps = conn.prepareStatement(sqlJ)) {
                    for (int i = 0; i < nlJokalaria.getLength(); i++) {
                        Element e = (Element) nlJokalaria.item(i);
                        ps.setString(1, lortuBalioa(e, "NANa"));
                        ps.setString(2, lortuBalioa(e, "Izen_abizena"));
                        setIntOrNull(ps, 3, lortuBalioa(e, "Dortsala"));
                        ps.setString(4, lortuBalioa(e, "Posizioa"));
                        setDateOrNull(ps, 5, lortuBalioa(e, "Jaiotze_data"));
                        ps.setString(6, lortuBalioa(e, "taldea"));
                        ps.executeUpdate();
                    }
                }

                // 3. INPORTATU ENTRENATZAILEAK
                NodeList nlEntrenatzailea = doc.getElementsByTagName("Entrenatzailea");
                String sqlEnt = "INSERT INTO entrenatzailea (NANa, Izen_abizena, Titulazioa, taldea) " +
                                "VALUES (?, ?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE Izen_abizena=VALUES(Izen_abizena), Titulazioa=VALUES(Titulazioa), taldea=VALUES(taldea)";
                try (PreparedStatement ps = conn.prepareStatement(sqlEnt)) {
                    for (int i = 0; i < nlEntrenatzailea.getLength(); i++) {
                        Element e = (Element) nlEntrenatzailea.item(i);
                        ps.setString(1, lortuBalioa(e, "NANa"));
                        ps.setString(2, lortuBalioa(e, "Izen_abizena"));
                        ps.setString(3, lortuBalioa(e, "Titulazioa"));
                        ps.setString(4, lortuBalioa(e, "taldea"));
                        ps.executeUpdate();
                    }
                }

                // 4. INPORTATU EPAILEAK
                NodeList nlEpailea = doc.getElementsByTagName("Epailea");
                String sqlEpa = "INSERT INTO epailea (NANa, Izen_abizena, Maila) " +
                                "VALUES (?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE Izen_abizena=VALUES(Izen_abizena), Maila=VALUES(Maila)";
                try (PreparedStatement ps = conn.prepareStatement(sqlEpa)) {
                    for (int i = 0; i < nlEpailea.getLength(); i++) {
                        Element e = (Element) nlEpailea.item(i);
                        ps.setString(1, lortuBalioa(e, "NANa"));
                        ps.setString(2, lortuBalioa(e, "Izen_abizena"));
                        ps.setString(3, lortuBalioa(e, "Maila"));
                        ps.executeUpdate();
                    }
                }

                // 5. INPORTATU PARTIDUAK
                NodeList nlPartidua = doc.getElementsByTagName("Partidua");
                String sqlPar = "INSERT INTO partidua (id_auto, kod_partidua, denboraldia, Data, Ordua, Golak_lokala, Golak_kanpokoak, Zelaia, Talde_lokala, Kampoko_taldea, epailea1, epailea2) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE kod_partidua=VALUES(kod_partidua), denboraldia=VALUES(denboraldia), Data=VALUES(Data), Ordua=VALUES(Ordua), Golak_lokala=VALUES(Golak_lokala), Golak_kanpokoak=VALUES(Golak_kanpokoak), Zelaia=VALUES(Zelaia), Talde_lokala=VALUES(Talde_lokala), Kampoko_taldea=VALUES(Kampoko_taldea), epailea1=VALUES(epailea1), epailea2=VALUES(epailea2)";
                try (PreparedStatement ps = conn.prepareStatement(sqlPar)) {
                    for (int i = 0; i < nlPartidua.getLength(); i++) {
                        Element e = (Element) nlPartidua.item(i);
                        setIntOrNull(ps, 1, lortuBalioa(e, "id_auto"));
                        ps.setString(2, lortuBalioa(e, "kod_partidua"));
                        ps.setString(3, lortuBalioa(e, "denboraldia"));
                        setDateOrNull(ps, 4, lortuBalioa(e, "Data"));
                        setTimeOrNull(ps, 5, lortuBalioa(e, "Ordua"));
                        setIntOrNull(ps, 6, lortuBalioa(e, "Golak_lokala"));
                        setIntOrNull(ps, 7, lortuBalioa(e, "Golak_kanpokoak"));
                        ps.setString(8, lortuBalioa(e, "Zelaia"));
                        ps.setString(9, lortuBalioa(e, "Talde_lokala"));
                        ps.setString(10, lortuBalioa(e, "Kampoko_taldea"));
                        ps.setString(11, lortuBalioa(e, "epailea1"));
                        ps.setString(12, lortuBalioa(e, "epailea2"));
                        ps.executeUpdate();
                    }
                    
                    try (PreparedStatement psOn = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1")) {
                }
              }
                JOptionPane.showMessageDialog(this, "Datu-base osoa XML fitxategitik behar bezala inportatu da!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errorea XML inportatzean: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}