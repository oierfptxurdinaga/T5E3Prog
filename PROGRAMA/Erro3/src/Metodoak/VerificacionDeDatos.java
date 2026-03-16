package Metodoak;

import javax.swing.JOptionPane;

/**
 * {@code VerificacionDeDatos} Klasea, erabiltzaile-izenaren eta pasahitzaren eremuak hutsik edo nuluak ez daudela egiaztatzeko metodo bat eskaintzen du.
 */
public class VerificacionDeDatos {

    /**
     * Egiaztatu emandako erabiltzaile-izen eta pasahitzaren parametroak baliozkoak diren. 
     * <br>
     * Funtzio honek ziurtatzen du ez erabiltzaile-izena ez pasahitza ez direla kate nuluak edo hutsak, eta errore-mezu bat bistaratuko du baldintza horietako bat betetzen ez bada.
     * @param usuario Egiaztatu beharreko erabiltzaile-izena.
     * @param password Egiaztatuko den pasahitza.
     * @return {@code true} bi parametroak ez badira nuluak eta ez badira hutsak;
     *         {@code false} parametroren bat nulua edo hutsa bada.
     * @throws IllegalArgumentException Parametroren bat nulua edo hutsik badago, errore-mezu bat bistaratuko da leiho gainerakor batean.
     *
     */
    public static boolean verificarDatos(String usuario, String password) {

        if (usuario == null || password == null || usuario.trim().isEmpty() || password.trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Ezin dira eremu hutsak egon.");
            return false;
        }

        return true;
    }
}
