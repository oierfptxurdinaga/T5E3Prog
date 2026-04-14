package Metodoak;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Epaileen ekintzak eta gertakariak testu-fitxategi batean (log) erregistratzeko 
 * erabiltzen den tresna edo klase laguntzailea.
 */
public class LoggerUtil_Arb {
	
	/**
     * Epaile batek burututako ekintza bat eta honen xehetasunak gordetzen ditu 
     * "Log History/logInArb.log" fitxategian. Idatzitako lerro bakoitzak uneko 
     * data eta ordua jasoko ditu automatikoki.
     *
     * @param accion Epaileak burutu duen ekintzaren izena edo deskribapen laburra.
     * @param detalles Ekintza horri buruzko xehetasun edo informazio gehigarria.
     */
    public static void guardarLogArbitro(String accion, String detalles) {
        
        File directorio = new File("Log History");
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        try (FileWriter fw = new FileWriter("Log History/logInArb.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = ahora.format(formato);

            pw.println("[" + fechaHora + "] EKINTZA: " + accion + " | XEHETASUNAK: " + detalles);

        } catch (IOException e) {
            System.err.println("Errorea epailearen log-a gordetzean: " + e.getMessage());
        }
    }
}