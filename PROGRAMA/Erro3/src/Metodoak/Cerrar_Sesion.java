package Metodoak;

import javax.swing.*;

import Pruebas_Funcionales.LogIn;
import Pruebas_Funcionales.VentanaArbitro;

import java.awt.*;

/**
 * Saioa ixteko funtzionaltasuna duen panel bat sortzeaz arduratzen den klasea.
 * <p>
 * Panel honek ikusmen-animazioak dituen botoi bat dauka, sagua igarotzean erreakzionatzen dutenak (hover), pixkanaka haren tamaina, kolorea eta iturria aldatuz. 
 * <br>
 * Botoia sakatzean, berrespena eskatuko zaio erabiltzaileari uneko saioa itxi aurretik.
 * </p>
 * <p>
 * Saioaren itxiera baieztatzen bada, saioa hasteko leihoa irekiko da, eta panela inbokatzeko erabiltzen den leiho nagusia itxiko da.
 * </p>
 *
 * @author T4
 * @version 1.0
 * @since 1.0
 */
public class Cerrar_Sesion {

	/**
	 * Saioa ixteko botoi bat duen {@link JPanel} bat sortu eta itzultzen du.
	 * <p>
	 * Botoiak ikusizko animazioak jasotzen ditu sagua gainetik pasatzean, tamaina, hondoko kolore, testu-kolore eta iturri-tamainako trantsizio leunak eginez {@link Timer} baten bidez.
	 * </p>
	 * <p>
	 * Botoian klik egitean, berresteko elkarrizketa-koadro bat agertuko da.
	 * <br>
	 * Erabiltzaileak onartzen badu, saioa hasteko leihoa irekiko da ({@link Pruebas_Funcionales.LogIn}) eta leiho gurasoa ixten da.

	 * </p>
	 *
	 * @param ventanaPadre JFrame nagusia, panel hau inbokatzeko erabiltzen dena. 
	 * <br>
	 * Erreferentzia gisa erabiltzen da berrespen-elkarrizketa erakusteko eta saioa amaitzean leihoa ixteko.
	 * 
	 * @return JPanel honek saioa ixteko botoia dauka animazioekin eta baieztapen integratuaren logikarekin.
	 */
	public static JPanel crearPanel(JFrame ventanaPadre) {
		JPanel panel = new JPanel(new GridBagLayout());

		JButton btnCerrarSesion = new JButton("Saioa Amaitu");
		btnCerrarSesion.setFocusPainted(false);
		btnCerrarSesion.setOpaque(true);
		btnCerrarSesion.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		btnCerrarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		Dimension sizeNormal = new Dimension(160, 23);
		Dimension sizeGrande = new Dimension(320, 33);

		int fontNormal = 14;
		int fontGrande = 28;

		Color colorNormal = new Color(230, 230, 230);
		Color colorHover = Color.RED;

		btnCerrarSesion.setPreferredSize(sizeNormal);
		btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, fontNormal));
		btnCerrarSesion.setBackground(colorNormal);
		btnCerrarSesion.setForeground(Color.BLACK);

		final int delay = 15;
		final float velocidad = 0.08f;

		final float[] progreso = { 0.0f };
		final boolean[] hover = { false };

		Timer timer = new Timer(delay, e -> {

			if (hover[0]) {
				progreso[0] = Math.min(1f, progreso[0] + velocidad);
			} else {
				progreso[0] = Math.max(0f, progreso[0] - velocidad);
			}

			float t = progreso[0];

			int width = (int) (sizeNormal.width + (sizeGrande.width - sizeNormal.width) * t);
			int height = (int) (sizeNormal.height + (sizeGrande.height - sizeNormal.height) * t);
			int fontSize = (int) (fontNormal + (fontGrande - fontNormal) * t);

			int r = (int) (colorNormal.getRed() + (colorHover.getRed() - colorNormal.getRed()) * t);
			int g = (int) (colorNormal.getGreen() + (colorHover.getGreen() - colorNormal.getGreen()) * t);
			int b = (int) (colorNormal.getBlue() + (colorHover.getBlue() - colorNormal.getBlue()) * t);

			int text = (int) (255 * t);

			btnCerrarSesion.setPreferredSize(new Dimension(width, height));
			btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, fontSize));
			btnCerrarSesion.setBackground(new Color(r, g, b));
			btnCerrarSesion.setForeground(new Color(text, text, text));

			panel.revalidate();
			panel.repaint();

			if ((!hover[0] && progreso[0] == 0f) || (hover[0] && progreso[0] == 1f)) {
				((Timer) e.getSource()).stop();
			}
		});

		btnCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				hover[0] = true;
				if (!timer.isRunning())
					timer.start();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				hover[0] = false;
				if (!timer.isRunning())
					timer.start();
			}
		});

		btnCerrarSesion.addActionListener(e -> {
			int opcion = JOptionPane.showConfirmDialog(
					ventanaPadre,
					"¿Seguru saioa itxi nahi duzula?",
					"Berretsi saioaren itxiera",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE
			);

			if (opcion == JOptionPane.YES_OPTION) {
				
				if (ventanaPadre instanceof VentanaArbitro) {
					LoggerUtil_Arb.guardarLogArbitro("SESIOA ITXI", "Epaileak saioa amaitu du");
				}
				new LogIn().setVisible(true);
				ventanaPadre.dispose();
			}
		});

		panel.add(btnCerrarSesion);
		return panel;
	}
}
