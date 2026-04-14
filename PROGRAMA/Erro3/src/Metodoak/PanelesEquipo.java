package Metodoak;

import javax.swing.*;
import java.awt.*;

/**
* Talde baten informazioa erakusten duen txartel grafiko bat sortzen du.
* Txartelak taldearen logotipoa, izena, fundazio urtea eta presidentea bistaratzen ditu.
*/
public class PanelesEquipo {
	
	
	/**
	 *
	 * @param nombre taldearen izena
	 * @param fundacion taldearen fundazio urtea
	 * @param presidente taldearen presidentea
	 * @param rutaImagen taldearen logotipoaren irudiaren fitxategi-bidea
	 * @return taldearen informazioa duen JPanel objektua
	 */
	public static JPanel crearTarjetaEquipo(String nombre, String fundacion, String presidente, String rutaImagen) {
		JPanel tarjeta = new JPanel();
		tarjeta.setBackground(Color.WHITE);
		tarjeta.setLayout(new BorderLayout(10, 10));
		tarjeta.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		// LOGO
		ImageIcon icono = new ImageIcon(rutaImagen);
		Image imagenEscalada = icono.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		JLabel lblImagen = new JLabel(new ImageIcon(imagenEscalada));
		lblImagen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// TEXTO
		JPanel panelTexto = new JPanel(new GridLayout(3, 1));
		panelTexto.setBackground(Color.WHITE);

		JLabel lblNombre = new JLabel("Izena: " + nombre);
		JLabel lblFundacion = new JLabel("Fundazioa: " + fundacion);
		JLabel lblPresidente = new JLabel("Presidentea: " + presidente);

		lblNombre.setFont(new Font("Arial", Font.BOLD, 13));

		panelTexto.add(lblNombre);
		panelTexto.add(lblFundacion);
		panelTexto.add(lblPresidente);

		tarjeta.add(lblImagen, BorderLayout.WEST);
		tarjeta.add(panelTexto, BorderLayout.CENTER);

		return tarjeta;
	}
}
