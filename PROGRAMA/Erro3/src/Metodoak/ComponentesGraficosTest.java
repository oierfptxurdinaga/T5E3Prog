package Metodoak;
import Metodoak.Cerrar_Sesion;
import Metodoak.Panel_Pestaña_Tarjetas_Equipos;
import Metodoak.PanelesEquipo;
import Metodoak.Traspaso_Y_Confirmacion;
import Metodoak.Panel_Pestaña_Entrenadores;

import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ComponentesGraficosTest {

    @Test
    public void testCrearTarjetaEquipo() {

        JPanel tarjeta = PanelesEquipo.crearTarjetaEquipo("Aloña Mendi", "1949", "Iñaki", "foto.png");

        assertNotNull(tarjeta, "El panel de la tarjeta no debería ser nulo.");
        assertTrue(tarjeta.getLayout() instanceof BorderLayout, "El layout debe ser BorderLayout.");
        assertEquals(Color.WHITE, tarjeta.getBackground(), "El fondo de la tarjeta debe ser blanco.");
        

        assertEquals(2, tarjeta.getComponentCount(), "La tarjeta debería contener 2 componentes principales.");
    }

    @Test
    public void testCrearPanelEquipos() {

        JPanel panelEquipos = Panel_Pestaña_Tarjetas_Equipos.crearPanelEquipos();
        
        assertNotNull(panelEquipos);
        assertTrue(panelEquipos.getLayout() instanceof GridLayout, "El panel debe usar GridLayout.");
        assertEquals(6, panelEquipos.getComponentCount(), "Deberían haberse añadido 6 tarjetas de equipos.");
    }

    @Test
    public void testCrearPanelCerrarSesion() {
        JFrame mockFrame = new JFrame();
        JPanel panel = Cerrar_Sesion.crearPanel(mockFrame);

        assertNotNull(panel);
        assertTrue(panel.getLayout() instanceof GridBagLayout, "Debe usar GridBagLayout.");
        assertEquals(1, panel.getComponentCount(), "El panel debe contener exactamente un botón.");
        assertTrue(panel.getComponent(0) instanceof JButton, "El componente debe ser un botón de cerrar sesión.");
    }
    
    @Test
    public void testCrearPanelTraspaso() {

        JPanel panelTraspaso = Traspaso_Y_Confirmacion.crearPanelTraspaso();
        assertNotNull(panelTraspaso, "El panel de traspaso no debe ser nulo.");
    }
    
    @Test
    public void testCrearPanelEntrenadores() {

        try {
            JPanel panelEntrenadores = Panel_Pestaña_Entrenadores.crearPanelDeLosEntrenadores();
            assertNotNull(panelEntrenadores);
        } catch (Exception e) {
            fail("No debería lanzar excepción al construir el panel de entrenadores, incluso sin BD.");
        }
    }
}