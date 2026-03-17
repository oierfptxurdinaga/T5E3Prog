package Metodoak;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AutoNANaGenerator {

    /**
     * Genera un DNI (NANa) aleatorio y verifica que no exista en la base de datos.
     */
    public static String generarNANaUnico(Connection conn) throws Exception {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        String nuevoNANa;
        boolean existe;

        do {
            // Generar 8 números aleatorios (entre 10000000 y 99999999)
            int numero = (int) (Math.random() * 90000000) + 10000000;
            char letra = letras.charAt(numero % 23);
            nuevoNANa = String.valueOf(numero) + letra;

            // Verificar si existe en la tabla jokalaria
            String sqlCheck = "SELECT COUNT(*) FROM jokalaria WHERE NANa = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCheck)) {
                pstmt.setString(1, nuevoNANa);
                try (ResultSet rs = pstmt.executeQuery()) {
                    rs.next();
                    existe = rs.getInt(1) > 0;
                }
            }
        } while (existe); // Si existe, el bucle se repite y genera otro

        return nuevoNANa;
    }
}