package Metodoak;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DNI edo NAN (NANa) ausazko bat sortzen duen eta datu-basean ez dela existitzen 
 * ziurtatzen duen klase laguntzailea.
 * 
 * @author T5
 * @version 1.0
 * @since 1.0
 * 
 */   
public class AutoNANaGenerator {
	
	/**
	 *  Ausazko NAN berri bat sortzen du (8 zenbaki eta letra bat) eta 
     * 'jokalaria' taulan jadanik ez dagoela egiaztatzen du.
     * Existitzen bada, prozesua errepikatzen du NAN esklusibo bat lortu arte.
     *
     * @param conn Datu-basearekin ezarritako konexioa ({@link Connection}).
     * @return Sortutako eta datu-basean esklusiboa den NAN katea (String).
     * @throws Exception SQL kontsultak egiterakoan arazorik baldin badago erroreak kudeatzeko.
     */
    public static String generarNANaUnico(Connection conn) throws Exception {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        String nuevoNANa;
        boolean existe;

        do {
            int numero = (int) (Math.random() * 90000000) + 10000000;
            char letra = letras.charAt(numero % 23);
            nuevoNANa = String.valueOf(numero) + letra;

            String sqlCheck = "SELECT COUNT(*) FROM jokalaria WHERE NANa = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCheck)) {
                pstmt.setString(1, nuevoNANa);
                try (ResultSet rs = pstmt.executeQuery()) {
                    rs.next();
                    existe = rs.getInt(1) > 0;
                }
            }
        } while (existe);

        return nuevoNANa;
    }
}