package Metodoak;

import Metodoak.Konexioa;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class KonexioaTest {

    @Test
    public void testGetKonexioaNoLanzaExcepcion() {
        // Como no podemos garantizar que el entorno donde corra JUnit tenga MySQL activo en el puerto 3306,
        // simplemente validaremos que el método maneja correctamente su propio try-catch.
        Connection conn = null;
        try {
            conn = Konexioa.getKonexioa();
            // Si la base de datos está encendida y configurada, será distinta de null.
            // Si está apagada, el método captura el error, muestra un JOptionPane y devuelve null.
        } catch (Exception e) {
            fail("El método getKonexioa() no debería propagar excepciones, debería manejarlas internamente.");
        }
    }
}