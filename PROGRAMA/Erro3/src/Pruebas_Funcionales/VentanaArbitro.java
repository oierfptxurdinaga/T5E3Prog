package Pruebas_Funcionales;

import javax.swing.*;
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

import java.awt.*;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import Metodoak.Cerrar_Sesion;
import Metodoak.Panel_Pestaña_Tarjetas_Equipos;
import Metodoak.Pestaña_Resultados;
import Metodoak.Konexioa;
import Metodoak.LoggerUtil_Arb;

public class VentanaArbitro extends JFrame {

    // Diccionario para relacionar equipos con sus respectivos campos
    private HashMap<String, String> camposPorEquipo;
	private Node doc;

    public VentanaArbitro() {
        setTitle("Panel del Arbitro");
        setIconImage(new ImageIcon("Imagenes16K/LOGONarb.png").getImage());
        setSize(850, 600); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicializar relación equipo -> campo (Basado en tu base de datos)
        camposPorEquipo = new HashMap<>();
        camposPorEquipo.put("Aloña Mendi", "Zubikoa Kiroldegia");
        camposPorEquipo.put("Amezti Zarautz", "Aritzbatalde Kiroldegia");
        camposPorEquipo.put("Berango Urduliz", "Polideportivo Berango");
        camposPorEquipo.put("Irauli Bosteko", "Polideportivo Benta Berri");
        camposPorEquipo.put("Kukullaga", "Polideportivo Municipal Etxebarri");
        camposPorEquipo.put("San Adrian", "Polideportivo Rekalde");

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Orri Nagusia", crearPanelPrincipal());
        pestañas.addTab("Taldeak Ikusi", Panel_Pestaña_Tarjetas_Equipos.crearPanelEquipos());
        pestañas.addTab("Emaitzak", new Pestaña_Resultados());
        
        pestañas.addTab("XML Kudeaketa", crearPanelXMLArb());
        
        JPanel panelCerrarSesion = Cerrar_Sesion.crearPanel(this);
        pestañas.addTab("Saioa Itxi", panelCerrarSesion);

        getContentPane().add(pestañas);
    }
    
    private Vector<String> obtenerEpaileak() {
        Vector<String> epaileak = new Vector<>();
        epaileak.add(""); // Opción vacía por si solo hay un árbitro o ninguno
        try (Connection conn = Konexioa.getKonexioa()) {
            if (conn != null) {
                String sql = "SELECT Izen_abizena FROM epailea";
                try (PreparedStatement pstmt = conn.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        epaileak.add(rs.getString("Izen_abizena"));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Errorea epaileak kargatzean: " + e.getMessage());
            // Opciones de respaldo que coinciden con los que tienes en tu fichero .sql
            epaileak.add("Jon Urkidi Artetxe");
            epaileak.add("Ane Mendizabal Goti");
            epaileak.add("Kepa Arrate Zubia");
            epaileak.add("Maider Elorriaga Laka");
            epaileak.add("Unai Goikoetxea Lasa");
            epaileak.add("Itziar Agirre Beloki");
        }
        return epaileak;
    }

    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // ------------------ FILA 1: Denboraldia y Jardunaldia ------------------
        JLabel lblTemporada = new JLabel("Denboraldia:");
        lblTemporada.setBounds(50, 20, 100, 25);
        panel.add(lblTemporada);

        String[] temporadas = {"2024/25", "2025/26"};
        JComboBox<String> comboTemporada = new JComboBox<>(temporadas);
        comboTemporada.setBounds(150, 20, 150, 25);
        panel.add(comboTemporada);

        JLabel lblJornada = new JLabel("Jardunaldia:");
        lblJornada.setBounds(400, 20, 100, 25);
        panel.add(lblJornada);

        Integer[] jornadas = new Integer[38];
        for (int i = 0; i < 38; i++) jornadas[i] = i + 1;
        JComboBox<Integer> comboJornada = new JComboBox<>(jornadas);
        comboJornada.setBounds(500, 20, 100, 25);
        panel.add(comboJornada);

        // ------------------ FILA 2: Data y Ordua ------------------
        JLabel lblData = new JLabel("Data (YYYY-MM-DD):");
        lblData.setBounds(50, 70, 150, 25);
        panel.add(lblData);
        
        JTextField txtData = new JTextField();
        txtData.setBounds(200, 70, 100, 25);
        panel.add(txtData);

        JLabel lblOrdua = new JLabel("Ordua (HH:MM):");
        lblOrdua.setBounds(400, 70, 100, 25);
        panel.add(lblOrdua);
        
        JTextField txtOrdua = new JTextField();
        txtOrdua.setBounds(500, 70, 100, 25);
        panel.add(txtOrdua);

        // ------------------ FILA 3: Equipos y Goles ------------------
        String[] equipos = {"Aloña Mendi", "Amezti Zarautz", "Berango Urduliz", "Irauli Bosteko", "Kukullaga", "San Adrian"};

        JLabel lblLocal = new JLabel("Talde Lokala:");
        lblLocal.setBounds(50, 120, 100, 25);
        panel.add(lblLocal);

        JComboBox<String> comboLocal = new JComboBox<>(equipos);
        comboLocal.setBounds(150, 120, 150, 25);
        panel.add(comboLocal);

        JTextField txtGolesLocal = new JTextField();
        txtGolesLocal.setBounds(320, 120, 50, 25);
        panel.add(txtGolesLocal);

        JLabel lblVisitante = new JLabel("Kanpoko Taldea:");
        lblVisitante.setBounds(400, 120, 100, 25);
        panel.add(lblVisitante);

        JComboBox<String> comboVisitante = new JComboBox<>(equipos);
        comboVisitante.setBounds(500, 120, 150, 25);
        panel.add(comboVisitante);

        JTextField txtGolesVisitante = new JTextField();
        txtGolesVisitante.setBounds(670, 120, 50, 25);
        panel.add(txtGolesVisitante);

        // ------------------ FILA 4: Zelaia (Campo) ------------------
        JLabel lblZelaia = new JLabel("Zelaia:");
        lblZelaia.setBounds(50, 170, 100, 25);
        panel.add(lblZelaia);
        
        // Obtener campos únicos para el JComboBox
        String[] campos = new HashSet<>(camposPorEquipo.values()).toArray(new String[0]);
        JComboBox<String> comboZelaia = new JComboBox<>(campos);
        comboZelaia.setEditable(true); // ¡Permite al usuario cambiarlo o escribir uno nuevo!
        comboZelaia.setBounds(150, 170, 220, 25);
        panel.add(comboZelaia);

        // Listener: Cambiar campo automáticamente al elegir Talde Lokala
        comboLocal.addActionListener(e -> {
            String equipoLocal = (String) comboLocal.getSelectedItem();
            if (camposPorEquipo.containsKey(equipoLocal)) {
                comboZelaia.setSelectedItem(camposPorEquipo.get(equipoLocal));
            }
        });
        // Configuración inicial del campo según el primer equipo local
        comboZelaia.setSelectedItem(camposPorEquipo.get(comboLocal.getSelectedItem()));

        // ------------------ FILA 5: Árbitros ------------------
        Vector<String> listaEpaileak = obtenerEpaileak();
        
        JLabel lblEpailea1 = new JLabel("Epailea 1:");
        lblEpailea1.setBounds(50, 220, 100, 25);
        panel.add(lblEpailea1);
        
        JComboBox<String> comboEpailea1 = new JComboBox<>(listaEpaileak);
        comboEpailea1.setBounds(150, 220, 150, 25);
        panel.add(comboEpailea1);

        JLabel lblEpailea2 = new JLabel("Epailea 2:");
        lblEpailea2.setBounds(400, 220, 100, 25);
        panel.add(lblEpailea2);
        
        JComboBox<String> comboEpailea2 = new JComboBox<>(listaEpaileak);
        comboEpailea2.setBounds(500, 220, 150, 25);
        panel.add(comboEpailea2);
        
        // ------------------ BOTÓN GUARDAR ------------------
        JButton btnGuardar = new JButton("Gorde Emaitza");
        btnGuardar.setBounds(300, 300, 200, 40);
        panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            String temporada = (String) comboTemporada.getSelectedItem();
            int jornada = (int) comboJornada.getSelectedItem();
            String local = (String) comboLocal.getSelectedItem();
            String visitante = (String) comboVisitante.getSelectedItem();
            String data = txtData.getText().trim();
            String ordua = txtOrdua.getText().trim();
            String zelaia = (String) comboZelaia.getSelectedItem();
            String epailea1 = comboEpailea1.getSelectedItem() != null ? comboEpailea1.getSelectedItem().toString() : "";
            String epailea2 = comboEpailea2.getSelectedItem() != null ? comboEpailea2.getSelectedItem().toString() : "";

            if (local.equals(visitante)) {
                JOptionPane.showMessageDialog(panel, "Talde bera ezin da bi aldiz aukeratu.");
                return;
            }

            try {
                int golesLocal = Integer.parseInt(txtGolesLocal.getText());
                int golesVisitante = Integer.parseInt(txtGolesVisitante.getText());

                try (Connection conn = Konexioa.getKonexioa()) {
                    if (conn == null) return;

                    // Formato (Ej: "2024/25" -> "24-25") y Código Partido (Ej: "J 1 - 24-25")
                    String temporadaBD = temporada.substring(2).replace("/", "-"); 
                    String kodPartidua = "J " + jornada + " - " + temporadaBD;

                    // 1. Insertar todos los parámetros en la tabla
                    String sql = "INSERT INTO partidua (kod_partidua, denboraldia, Data, Ordua, Golak_lokala, Golak_kanpokoak, Zelaia, Talde_lokala, Kampoko_taldea, epailea1, epailea2) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                                 "ON DUPLICATE KEY UPDATE Golak_lokala = ?, Golak_kanpokoak = ?";
                    
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, kodPartidua);
                    pstmt.setString(2, temporadaBD);
                    
                    // Manejo seguro de Fecha
                    if (data.isEmpty()) pstmt.setNull(3, java.sql.Types.DATE);
                    else pstmt.setDate(3, java.sql.Date.valueOf(data));
                    
                    // Manejo seguro de Hora (Añade :00 si solo se pone HH:MM)
                    if (ordua.isEmpty()) {
                        pstmt.setNull(4, java.sql.Types.TIME);
                    } else {
                        if (ordua.length() == 5) ordua += ":00";
                        pstmt.setTime(4, java.sql.Time.valueOf(ordua));
                    }

                    pstmt.setInt(5, golesLocal);
                    pstmt.setInt(6, golesVisitante);
                    pstmt.setString(7, zelaia);
                    pstmt.setString(8, local);
                    pstmt.setString(9, visitante);
                    pstmt.setString(10, epailea1);
                    pstmt.setString(11, epailea2);
                    pstmt.setInt(12, golesLocal);
                    pstmt.setInt(13, golesVisitante);
                    
                    pstmt.executeUpdate();

                    // 2. Llamar al Procedimiento 
                    CallableStatement cstmt = conn.prepareCall("{CALL ActualizarClasificacion(?)}");
                    cstmt.setString(1, temporadaBD);
                    cstmt.execute();

                    JOptionPane.showMessageDialog(panel, "Behar bezala gorde da emaitza eta sailkapena eguneratu da.");
                    
                    String LogDetallesPartido = String.format("Kod: %s | %s %d - %d %s | \nZelaia: %s | Epaileak: %s, %s", 
                            kodPartidua, local, golesLocal, golesVisitante, visitante, zelaia, epailea1, epailea2);
                    LoggerUtil_Arb.guardarLogArbitro("EMAITZA GORDE", LogDetallesPartido);
                    
                    txtGolesLocal.setText("");
                    txtGolesVisitante.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Errorea datu-basean gordetzean: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Golak zenbakiak izan behar dira.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(panel, "Data edo Ordua formatua ez da zuzena. (Data: YYYY-MM-DD, Ordua: HH:MM)");
            }
        });

        return panel;
    }
    

private JPanel crearPanelXMLArb() {
    JPanel panel = new JPanel();

    JButton btnExportar = new JButton("Datuak XML-ra Esportatu");
    btnExportar.setBounds(254, 30, 147, 21);
    JButton btnImportar = new JButton("Datuak XML-tik Inportatu");
    btnImportar.setBounds(431, 30, 145, 21);

    // Botoiei ekintzak gehitu
    btnExportar.addActionListener(e -> esportatuXMLArb());
    btnImportar.addActionListener(e -> inportatuXMLArb());
    panel.setLayout(null);

    panel.add(btnExportar);
    panel.add(btnImportar);
    
    JLabel lblNewLabel = new JLabel("Bakarrik Partiduak Esportatu eta importatuko dira");
    lblNewLabel.setBounds(254, 200, 322, 13);
    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
    panel.add(lblNewLabel);

    return panel; 
}

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
private void esportatuXMLArb() {
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

//--- XML INPORTATZEKO METODOA ---
private void inportatuXMLArb() {
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
         
         // 1. VACIAR LA TABLA ANTES DE IMPORTAR PARA UNA RESTAURACIÓN LIMPIA
         try (PreparedStatement psDelete = conn.prepareStatement("DELETE FROM partidua")) {
             psDelete.executeUpdate();
         }
         
         // 2. INPORTATU PARTIDUAK
         NodeList nlPartidua = doc.getElementsByTagName("Partidua");
         String sqlPar = "INSERT INTO partidua (id_auto, kod_partidua, denboraldia, Data, Ordua, Golak_lokala, Golak_kanpokoak, Zelaia, Talde_lokala, Kampoko_taldea, epailea1, epailea2) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
         
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
         }
         
         try (PreparedStatement psOn = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1")) {
             psOn.execute(); 
         }
         
         // 3. ACTUALIZAR LAS CLASIFICACIONES DESPUÉS DE LA IMPORTACIÓN
         try (CallableStatement cstmt1 = conn.prepareCall("{CALL ActualizarClasificacion('24-25')}");
              CallableStatement cstmt2 = conn.prepareCall("{CALL ActualizarClasificacion('25-26')}")) {
             cstmt1.execute();
             cstmt2.execute();
         }
         
         JOptionPane.showMessageDialog(this, "Datu-base osoa XML fitxategitik behar bezala inportatu da!");
     }
 } catch (Exception ex) {
     JOptionPane.showMessageDialog(this, "Errorea XML inportatzean: " + ex.getMessage());
     ex.printStackTrace();
 }
}
}