package Metodoak;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil_Arb {

    public static void guardarLogArbitro(String accion, String detalles) {
        
        // 1. Crear el directorio si no existe (Tu código)
        File directorio = new File("Log History");
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        // 2. Preparar la escritura en el archivo logInArb.log (Tu código)
        try (FileWriter fw = new FileWriter("Log History/logInArb.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            // 3. Formatear la fecha y hora actual
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = ahora.format(formato);

            // 4. Escribir la línea en el archivo
            pw.println("[" + fechaHora + "] EKINTZA: " + accion + " | XEHETASUNAK: " + detalles);

        } catch (IOException e) {
            System.err.println("Errorea epailearen log-a gordetzean: " + e.getMessage());
        }
    }
}