package Metodoak;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code LoggerUtil} Klasea, LoggerUtil klaseak erabiltzaileari, haren izenari eta gertaeraren data eta orduari buruzko informazioa duten erregistro-sarrerak gordetzeko metodo bat eskaintzen du.
 * 
 */
public class LoggerUtil {

    /**
     * Erabiltzaile-izenarekin, zure izenarekin eta uneko data/orduarekin erregistro bat gordetzen du erregistro-fitxategian.
     * <br>
     * Metodoak erregistro-fitxategia irekitzen du (edo sortzen du, existitzen ez bada) eta lerro berri bat gehitzen du emandako informazioarekin, 
     * <br>
     * data eta orduarekin batera {@code yyyy-MM-dd HH:mm:ss} formatuan.
     * 
     * @param usuario Erregistroan (.log artziboa) erregistratuko den erabiltzaile-izena.
     * @param nombre Saioa hasiko duen erabiltzailearen izen-abizenak.
     * @throws IOException Erregistro-fitxategian idazterakoan errore bat gertatzen bada, errore-mezu bat inprimatzen da kontsolan.
     * 
     */
    public static void guardarLog(String usuario, String nombre) {

        try (FileWriter fw = new FileWriter("Log History/login.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = LocalDateTime.now().format(formato);

            pw.println("Usuario: " + usuario + " | Nombre: " + nombre + " | Fecha: " + fechaHora);

        } catch (IOException e) {
            System.err.println("Error al escribir el LOG: " + e.getMessage());
        }
    }
}
