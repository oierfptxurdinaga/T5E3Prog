package Metodoak;
import Metodoak.VerificacionDeDatos;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VerificacionDeDatosTest {

    @Test
    public void testVerificarDatos_Validos() {
        // Datos correctos
        assertTrue(VerificacionDeDatos.verificarDatos("usuario1", "pass123"), 
            "Debería devolver true para datos válidos.");
    }

    @Test
    public void testVerificarDatos_Nulos() {
        // Comprobación de nulos
        assertFalse(VerificacionDeDatos.verificarDatos(null, "pass123"), 
            "Debería devolver false si el usuario es null.");
        assertFalse(VerificacionDeDatos.verificarDatos("usuario1", null), 
            "Debería devolver false si la contraseña es null.");
        assertFalse(VerificacionDeDatos.verificarDatos(null, null), 
            "Debería devolver false si ambos son null.");
    }

    @Test
    public void testVerificarDatos_Vacios() {
        // Comprobación de cadenas vacías o espacios
        assertFalse(VerificacionDeDatos.verificarDatos("", "pass123"), 
            "Debería devolver false si el usuario está vacío.");
        assertFalse(VerificacionDeDatos.verificarDatos("usuario1", "   "), 
            "Debería devolver false si la contraseña son solo espacios.");
    }
}