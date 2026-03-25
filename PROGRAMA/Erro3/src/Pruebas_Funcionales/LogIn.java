package Pruebas_Funcionales;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.Image;
import java.io.*;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.Toolkit;

import Metodoak.LoggerUtil;
import Metodoak.VerificacionDeDatos;
import Metodoak.LoggerUtil_Arb;
import java.awt.Font;
import java.awt.Color;
//import Prueba_De_Pojos.Pertsona;

public class LogIn extends JFrame {

	private JTextField txtUsuario;
	private JPasswordField txtPassword;

	// Aspecto grafico.
	public LogIn() {

		setTitle("Login de Usuario");
		setIconImage(new ImageIcon("Imagenes16K/LOGON.png").getImage());
		setSize(350, 220);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(20, 30, 100, 20);
		getContentPane().add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(120, 30, 180, 20);
		getContentPane().add(txtUsuario);

		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(20, 70, 100, 20);
		getContentPane().add(lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(120, 70, 180, 20);
		getContentPane().add(txtPassword);

		JButton btnLogin = new JButton("Sartu");
		btnLogin.setBounds(110, 120, 120, 30);
		getContentPane().add(btnLogin);

		btnLogin.addActionListener(e -> verificarDatos());
		
		ImageIcon iconoOriginal = new ImageIcon("Imagenes16K/EEF16K.png");
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); //SCALE_SMOOTH para mejor calidad | SCALE_FAST para rendimiento
		ImageIcon icono = new ImageIcon(imagenEscalada);

		JLabel lblImagen = new JLabel(icono);
		lblImagen.setFont(new Font("Consolas", Font.PLAIN, 10));
		lblImagen.setBounds(10, 100, 80, 80);
		getContentPane().add(lblImagen);

		getContentPane().add(lblImagen);

		ImageIcon iconoOriginal2 = new ImageIcon("Imagenes16K/NexoDev4K.png");
		Image imagenEscalada2 = iconoOriginal2.getImage().getScaledInstance(96, 64, Image.SCALE_SMOOTH); //SCALE_SMOOTH para mejor calidad | SCALE_FAST para rendimiento
		ImageIcon icono2 = new ImageIcon(imagenEscalada2);

		JLabel lblImagen2 = new JLabel(icono2);
		lblImagen2.setFont(new Font("Consolas", Font.PLAIN, 10));
		lblImagen2.setBounds(225, 100, 96, 64);
		getContentPane().add(lblImagen2);

		getContentPane().add(lblImagen2);
		
		JLabel lblRealDate = new JLabel("Fecha Y Hora");
		lblRealDate.setBounds(225, 168, 111, 12);
		getContentPane().add(lblRealDate);
		
		JLabel lblNewLabel = new JLabel("👍🏻");
		lblNewLabel.setForeground(new Color(247, 247, 247));
		lblNewLabel.setBounds(120, 160, 44, 12);
		getContentPane().add(lblNewLabel);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		
		/* Dato curioso sobre los formatos de fecha y hora de Timer:
		 * 
		 * yyyy: año completo (2026)
		 * YYYY: año de la semana (2025, porque la semana 1 de 2026 empieza en diciembre de 2025)
		 * mm: minutos (00-59)
		 * MM: meses (01-12)
		 * dd: día del mes (01-31)
		 * DD: día del año (001-365)
		 * hh: hora en formato 12 horas (01-12)
		 * HH: hora en formato 24 horas (00-23)
		 * ss: segundos (00-59)
		 * SS: milisegundos (000-999)
		 * sss: fracción de segundo (0.000-0.999) - aunque no es común usarlo, se refiere a la parte decimal de los segundos
		 * SSS: microsegundos (000000-999999)
		 * ssss: fracción de segundo con microsegundos (0.000000-0.999999) - aunque no es común usarlo, se refiere a la parte decimal de los segundos incluyendo microsegundos
		 * SSSS: nanosegundos (000000000-999999999)
		 * 
		 * CONCLUSIÓN: Para mostrar la fecha y hora correctamente, se debe usar "yyyy/MM/dd HH:mm:ss" en lugar de "YYYY/MM/DD hh:mm:ss".
		 */
		
		Timer timer = new Timer(1000, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
			lblRealDate.setText(dtf.format(java.time.LocalDateTime.now()));
		    }
		});
		
		timer.start();
		setVisible(true);
	}

	// Con esto verifica si los datos ingresados coinciden con los que deberia de
	// ser
	private void verificarDatos() {

		String usuarioIngresado = txtUsuario.getText();
		String passIngresada = new String(txtPassword.getPassword());

		if (!VerificacionDeDatos.verificarDatos(usuarioIngresado, passIngresada)) {
			return;
		}

		// continuar con el login...

		// =========================================================
		// 🔥 MODO ADMINISTRADOR 🔥
		// =========================================================

		if (usuarioIngresado.equals("NotchAdmin")) {

			// 🔄 Siempre limpiar campos
			txtUsuario.setText("");
			txtPassword.setText("");

			if (!passIngresada.equals("AWSD")) { //OLD PASS: 17 de mayo de 2009
				//LoggerUtil.guardarLog("O5 USER", "###");
				JOptionPane.showMessageDialog(this, "Administratzailearen pasahitza okerra da.");
				return;
			}

			String codigo = JOptionPane.showInputDialog(this, "Sarbide mugatua\\nSartu egiaztapen-kodea:");

			if (codigo != null && codigo.equals("AWSD")) { //OLD CODE: /op permit Admin1
				new VentanaAdministrador().setVisible(true);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Kode okerra.");
			}
			return;
		}

		// =========================================================
		// ⚽ MODO ARBITRO ⚽
		// =========================================================

		if (usuarioIngresado.equals("User-Arb")) {

			// 🔄 Siempre limpiar campos
			txtUsuario.setText("");
			txtPassword.setText("");

			if (!passIngresada.equals("AWSD")) { //OLD PASS: 787b
				//LoggerUtil.guardarLog("Epaile bat", "ARBITROA");
				JOptionPane.showMessageDialog(this, "Epailearen pasahitz okerra.");
				return;
			}

			String codigo = JOptionPane.showInputDialog(this, "Sarbide mugatua\\nSartu egiaztapen-kodea:");

			if (codigo != null && codigo.equals("AWSD")) { //OLD CODE: LeMans 1991
				
				LoggerUtil_Arb.guardarLogArbitro("SESIOA HASI", "Epailea sisteman dago");
				
				new VentanaArbitro().setVisible(true);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Kode okerra.");
			}
			return;
		} 

		// =========================================================
		// 👤 USUARIO NORMAL 👤
		// =========================================================
		try {
			List<String> lineas = Files.readAllLines(Paths.get("datos.dat"));

			for (String linea : lineas) {

				String[] datos = linea.split(";");
				if (datos.length < 8) continue;

				// Comprobamos si coincide usuario (posición 5) y contraseña (posición 6)
				if (usuarioIngresado.equals(datos[5]) && passIngresada.equals(datos[6])) {

					// 📝 SE REGISTRA EL ÉXITO DEL USUARIO NORMAL
					LoggerUtil.guardarLog(datos[5], datos[1]);

					VentanaUsuarios ventana = new VentanaUsuarios();
					ventana.setDatosUsuario(datos); // Pasamos los datos del usuario
					ventana.setVisible(true);
					this.dispose(); // Cerramos el login
					return;
				}
			}

			// 🔥 AQUÍ SE REGISTRA EL FALLO DE USUARIO NORMAL / DESCONOCIDO 🔥
			// Si llega aquí, significa que no es Admin, no es Árbitro, y no acertó en datos.dat
			LoggerUtil.registrarFallo(usuarioIngresado, passIngresada, "Erabiltzaile edo pasahitz okerra");
			JOptionPane.showMessageDialog(this, "Erabiltzaile-izen edo pasahitz okerra.");

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Ezin izan da datos.dat fitxategia irakurri");
		}
	}

	public static void main(String[] args) {
		new LogIn().setVisible(true);
	}
}

// =========================================================
// 🔥 MODO ADMINISTRADOR 🔥
// =========================================================
// =========================================================
// ⚽ MODO ARBITRO ⚽
// =========================================================
// =========================================================
// 👤 USUARIO NORMAL 👤
// =========================================================
