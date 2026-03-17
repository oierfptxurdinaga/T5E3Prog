package Metodoak;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code LoggerUtil} Klasea, erabiltzaileari, haren izenari eta gertaeraren data eta orduari buruzko informazioa duten erregistro-sarrerak (log-ak) gordetzeko metodoak eskaintzen ditu.
 * <br>
 * Orain, saio-hasiera arrakastatsuak eta saiakera okerrak bereizteko gaitasuna du, baita ezkutuko erabiltzaileak zentsuratzeko gaitasuna ere.
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
        
        // Zentsura aplikatzen dugu idatzi aurretik
        if (debeCensurar(usuario)) {
            usuario = "[##########]";
            nombre = "[######]";
        }

        File directorio = new File("Log History");
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        try (FileWriter fw = new FileWriter("Log History/login.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = LocalDateTime.now().format(formato);

            String logEntry = String.format("[%s] ✅ ONARTUA: %-15s| IZENA: %s \n-------------------------------------------------------------------", 
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

        // Zentsura aplikatzen dugu idatzi aurretik
        if (debeCensurar(usuario)) {
            usuario = "[##########]";
        } else {
            // Gainerako erabiltzaileentzat
            usuario = "[NORMAL USER]";
        }

        File directorio = new File("Log History");
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        try (FileWriter fw = new FileWriter("Log History/login.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = LocalDateTime.now().format(formato);

            String passwordSeguro = "[PARAMETRO INDISPONIBLE]";

            String logEntry = String.format(
                    "[%s] ❌ ERROREA: %-25s \nERABILTZAILEA: %-15s| PASAHITZA: %s \n-------------------------------------------------------------------",
                    fechaHora, motivo, usuario, passwordSeguro);

            pw.println(logEntry);

        } catch (IOException e) {
            System.err.println("Error al escribir el LOG: " + e.getMessage());
        }

	}

    /**
     * Emandako testuak ezkutuko helburu-erabiltzailearekin %33ko edo gehiagoko
     * antzekotasuna duen egiaztatzen du. Base64 kodifikazioa erabiltzen du
     * jatorrizko izena kodean ez erakusteko.
     * * @param input Egiaztatu beharreko testua (sartutako erabiltzaile-izena).
     * @return {@code true} antzekotasuna %33koa edo handiagoa bada, bestela {@code false}.
     */
    private static boolean debeCensurar(String input) {
        if (input == null || input.isEmpty()) return false;
        
        // "TESTSystem" codificado en Base64 para ocultarlo en el código fuente
        String objetivoOculto = "VEVTVFN5c3RlbQ=="; 
        String objetivo = new String(java.util.Base64.getDecoder().decode(objetivoOculto));
        
        double similitud = calcularSimilitud(input, objetivo);
        return similitud >= 0.33; // 33% o más de similitud
    }

    /**
     * Bi testuren arteko antzekotasun portzentajea kalkulatzen du 0.0 eta 1.0 arteko balio gisa.
     * * @param s1 Lehenengo testua.
     * @param s2 Bigarren testua.
     * @return Antzekotasunaren ratioa (1.0 guztiz berdina bada, 0.0 guztiz ezberdina bada).
     */
    private static double calcularSimilitud(String s1, String s2) {
        int distancia = calcularDistanciaLevenshtein(s1, s2);
        int maxLen = Math.max(s1.length(), s2.length());
        if (maxLen == 0) return 1.0;
        return 1.0 - ((double) distancia / maxLen);
    }

    /**
     * Levenshtein distantziaren algoritmoa aplikatzen du bi kateren artean.
     * Distantzia horrek adierazten du zenbat aldaketa (txertatze, ezabatze edo ordezkatze)
     * behar diren kate bat bestea bihurtzeko.
     * * @param s1 Lehenengo katea.
     * @param s2 Bigarren katea.
     * @return Bi kateen arteko aldaketa kopuru minimoa.
     */
    private static int calcularDistanciaLevenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int costoSustitucion = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + costoSustitucion, 
                               Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
}