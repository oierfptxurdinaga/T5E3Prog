package Metodoak;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code LoggerUtil} Klasea, erabiltzaileari, haren izenari eta gertaeraren data eta orduari buruzko informazioa duten erregistro-sarrerak (log-ak) gordetzeko metodoak eskaintzen ditu.
 * <br>
 * Orain, saio-hasiera arrakastatsuak eta saiakera okerrak bereizteko gaitasuna du.
 */
public class LoggerUtil {

    /**
     * Erabiltzaile-izenarekin, haren izenarekin eta uneko data/orduarekin erregistro bat gordetzen du erregistro-fitxategian saioa ONGI hasi denean.
     * <br>
     * Metodoak erregistro-fitxategia irekitzen du (edo sortzen du, existitzen ez bada) eta lerro berri bat gehitzen du emandako informazioarekin, 
     * <br>
     * data eta orduarekin batera {@code yyyy-MM-dd HH:mm:ss} formatuan.
     * * @param usuario Erregistroan (.log artxiboa) erregistratuko den erabiltzaile-izena.
     * @param nombre Saioa hasiko duen erabiltzailearen izen-abizenak.
     */
    public static void guardarLog(String usuario, String nombre) {
        
        File directorio = new File("Log History");
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        try (FileWriter fw = new FileWriter("Log History/login.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = LocalDateTime.now().format(formato);

            String logEntry = String.format("[%s] ✅ ONARTUA: %-15s | IZENA: %s \n------------------------------------------------------------------", 
                                            fechaHora, usuario, nombre);
            pw.println(logEntry);

        } catch (IOException e) {
            System.err.println("Error al escribir el LOG: " + e.getMessage());
        }
    }

    /**
     * Saioa hasteko saiakera OKERRA denean erregistro bat gordetzen du erregistro-fitxategian.
     * <br>
     * Metodoak saiakera egin duen erabiltzaile-izena, sartutako pasahitza, errorearen zergatia eta 
     * uneko data eta ordua ({@code yyyy-MM-dd HH:mm:ss} formatuan) idazten ditu.
     * * @param usuario Saioa hasten saiatu den erabiltzaile-izena.
     * @param password Sartzen saiatu den pasahitza.
     * @param motivo Errorearen deskribapena edo zergatia (adibidez, "Erabiltzaile edo pasahitz okerra").
     */
    public static void registrarFallo(String usuario, String password, String motivo) {
        
        File directorio = new File("Log History");
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        try (FileWriter fw = new FileWriter("Log History/login.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = LocalDateTime.now().format(formato);

            String logEntry = String.format("[%s] ❌ ERROREA: %-25s \nERABILTZAILEA: %-15s | PASAHITZA: %s \n------------------------------------------------------------------", 
                                            fechaHora, motivo, usuario, password);
            pw.println(logEntry);

        } catch (IOException e) {
            System.err.println("Error al escribir el LOG: " + e.getMessage());
        }
    }
}