package Metodoak;

import javax.swing.*;
import java.awt.*;

/**
 * Ekipoen fitxako panela sortzeaz arduratzen den klasea.
 * <p>
 * Txartel-lauki bat erakusten du, non txartel bakoitzak bere izena, fundazio-urtea, presidentea eta logotipoa dituen talde bat irudikatzen duen.
 * </p>
 */
public class Panel_Pestaña_Tarjetas_Equipos {

	// =======================
	// MÉTODO PÚBLICO
	// =======================

	/**
	 * Sortu eta konfiguratu ekipoen txartelak dituen panel nagusia.
	 * <p>
	 * Panelak 2 errenkada eta 3 zutabeko {@link GridLayout} bat erabiltzen du, eta
	 * txartelen eta kanpoko ertz baten artean. Txartel bakoitza metodo honen bidez sortzen da:
	 * laguntzailea {@code crearTarjetaEquipo}.
	 * </p>
	 *
	 * @return {@link JPanel} Taldeen Tarjetak dauzka
	 */
	public static JPanel crearPanelEquipos() {

		JPanel panelEquipos = new JPanel();
		panelEquipos.setBackground(new Color(220, 220, 220));
		panelEquipos.setLayout(new GridLayout(2, 3, 15, 15));
		panelEquipos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		panelEquipos.add(
				crearTarjetaEquipo("Aloña Mendi", "1949", "Iñaki", "Imagenes16K/upscalemedia-transformed.png")); //Iñaki Ugarteburu Artola

		panelEquipos.add(crearTarjetaEquipo("Amezti Zarautz", "1964", "Maite", "Imagenes16K/upscalemedia-transformed (1).png")); //Maite Azkue Olaizola

		panelEquipos
				.add(crearTarjetaEquipo("Berango Urduliz", "1976", "Bittor", "Imagenes16K/upscalemedia-transformed (2).png")); //Bittor Arana Goiri

		panelEquipos.add(crearTarjetaEquipo("Irauli Bosteko", "2017", "Amaia", "Imagenes16K/upscalemedia-transformed (4).png")); //Amaia Agirreurreta Elorza

		panelEquipos.add(crearTarjetaEquipo("Kukullaga", "1983", "Mikel", "Imagenes16K/upscalemedia-transformed (3).png")); //Mikel Ituarte Mendizabal

		panelEquipos.add(crearTarjetaEquipo("San Adrian", "1967", "Gorka", "Imagenes16K/upscalemedia-transformed (5).png")); //Gorka Etxeberria Lasa

		return panelEquipos;
	}

	// =======================
	// MÉTODO PRIVADO AUXILIAR
	// =======================

	/**
	 * Sortu txartel grafiko bat ekipo baten informazioa adierazteko.
	 * <p>
	 * Txartelean sartzen dira:
	 * <ul>
	 * <li>Ekipoaren logotipoa</li>
	 * <li>Taldearen izena</li>
	 * <li>Sorrera-urtea</li>
	 * <li>Lehendakariaren izena</li>
	 * </ul>
	 * </p>
	 *
	 * @param nombre: Taldearen izena
	 * @param fundacion: Taldea zortu zen urtea
	 * @param presidente: Presidentearen izena
	 * @param rutaImagen: Argazki ruta
	 * @return {@link JPanel} Tadearen "Tarjeta" errepresentatzen du
	 */
	private static JPanel crearTarjetaEquipo(String nombre, String fundacion,
	                                         String presidente, String rutaImagen) {

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
