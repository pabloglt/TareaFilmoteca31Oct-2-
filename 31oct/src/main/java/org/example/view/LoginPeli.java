/**
 * Vista (JFrame) para la pantalla de inicio de sesión de la aplicación.
 * Permite al usuario introducir su email y contraseña y proporciona métodos
 * para que el {@code AppController} interactúe con los campos y el botón.
 *
 * @author [Pablo González de Lema de Torres]
 * @version 1.0
 */
package org.example.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.Dimension;

public class LoginPeli extends JFrame {

    /**
     * Campo de texto para que el usuario introduzca su email.
     */
    private JTextField tfUsuario;
    /**
     * Campo de texto para que el usuario introduzca su contraseña de forma oculta.
     */
    private JPasswordField pfContrasena;
    /**
     * Botón para iniciar la sesión.
     */
    private JButton btnIniciarSesión; // Si es null, el Controller falla
    /**
     * Etiqueta de título o cabecera del formulario de login.
     */
    private JLabel lblLogin;
    /**
     * Etiqueta para el campo de usuario (email).
     */
    private JLabel lblUsuario;
    /**
     * Etiqueta para el campo de contraseña.
     */
    private JLabel lblContrasena;
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel mainPanel;

    /**
     * Constructor de la vista de inicio de sesión.
     * Configura la ventana, el título, la operación de cierre y el tamaño/posición.
     */
    public LoginPeli() {

        if (mainPanel != null) {
            setContentPane(mainPanel);
        }

        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        if (mainPanel != null) {
            mainPanel.setPreferredSize(new Dimension(350, 250));
        }

        pack(); // Ajusta el tamaño

        if (getWidth() < 300 || getHeight() < 200) {
            setSize(350, 250); // Forzar tamaño visible
        }

        setLocationRelativeTo(null);
    }

    // Métodos de Interacción para el controller

    /**
     * Obtiene el texto introducido en el campo de email (usuario).
     * @return El email introducido.
     */
    public String getEmail() { return tfUsuario.getText(); }

    /**
     * Obtiene la contraseña introducida en el campo de contraseña.
     * @return La contraseña como un {@code String}.
     */
    public String getPassword() { return new String(pfContrasena.getPassword()); }

    /**
     * Añade un {@code ActionListener} al botón de Iniciar Sesión.
     * Incluye una comprobación de nulidad para evitar {@code NullPointerException}
     * si el botón no fue inicializado correctamente.
     *
     * @param listener El {@code ActionListener} a adjuntar (normalmente el {@code AppController}).
     */
    public void addLoginListener(ActionListener listener) {
        if (btnIniciarSesión != null) {
            btnIniciarSesión.addActionListener(listener);
        } else {
            System.err.println("Error de inicialización: btnIniciarSesión es nulo. Revisa el enlace del Form.");
        }
    }

    /**
     * Muestra un diálogo emergente con un mensaje de error.
     * @param mensaje El texto del mensaje de error a mostrar.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Limpia el campo de la contraseña.
     */
    public void limpiarFormulario() {
        pfContrasena.setText("");
    }
}
