/*package Pruebas_Funcionales;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import Metodoak.LoggerUtil;

public class LogIn extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public LogIn() {
        setTitle("Login de Usuario");
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

        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(100, 120, 130, 30);
        getContentPane().add(btnLogin);

        btnLogin.addActionListener(e -> validarLogin());
    }

    private void validarLogin() {
        String usuarioIngresado = txtUsuario.getText().trim();
        String passIngresada = new String(txtPassword.getPassword()).trim();

        if (usuarioIngresado.isEmpty() || passIngresada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ezin dira eremu hutsak utzi.");
            return;
        }

        try {
            if (!Files.exists(Paths.get("datos.dat"))) {
                JOptionPane.showMessageDialog(this, "Ez da datos.dat fitxategia aurkitu.");
                return;
            }

            List<String> lineas = Files.readAllLines(Paths.get("datos.dat"));
            boolean autenticado = false;

            for (String linea : lineas) {
                String[] datos = linea.split(";");
                if (datos.length < 8) continue;

                if (usuarioIngresado.equals(datos[5]) && passIngresada.equals(datos[6])) {
                    autenticado = true;
                    String rol = datos[7]; 
                    
                    LoggerUtil.guardarLog(datos[5], datos[1]);

                    if (rol.equalsIgnoreCase("ERABILTZAILEA")) {
                        new VentanaUsuarios().setVisible(true);
                    } else if (rol.equalsIgnoreCase("ADMIN")) {
                        new VentanaAdministrador().setVisible(true);
                    } else if (rol.equalsIgnoreCase("ARBITROA")) {
                        new VentanaArbitro().setVisible(true);
                    }
                    this.dispose();
                    return;
                }
            }

            if (!autenticado) {
                JOptionPane.showMessageDialog(this, "Erabiltzaile-izen edo pasahitz okerra.");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Ezin izan da datos.dat fitxategia irakurri");
        }
    }

    public static void main(String[] args) {
        new LogIn().setVisible(true);
    }
}*/



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////






/*import java.awt.Image;
import javax.swing.ImageIcon;

ImageIcon icon = new ImageIcon("Imagenes16K/NexoDev.png");
Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
setIconImage(img);*/

/*.getScaledInstance(48, 48, Image.SCALE_SMOOTH);*/







package Pruebas_Funcionales;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
//import java.awt.Toolkit;

import Metodoak.LoggerUtil;
import Metodoak.VerificacionDeDatos;
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

		JButton btnLogin = new JButton("Zartu");
		btnLogin.setBounds(110, 120, 120, 30);
		getContentPane().add(btnLogin);

		btnLogin.addActionListener(e -> verificarDatos());

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
