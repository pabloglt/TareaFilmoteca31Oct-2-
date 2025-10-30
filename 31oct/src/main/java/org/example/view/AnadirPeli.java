/**
 * Diálogo (JDialog) utilizado para capturar los datos necesarios para
 * añadir una nueva {@link org.example.model.Pelicula} al sistema.
 * <p>
 * Este diálogo es modal y devuelve el objeto Pelicula creado o {@code null} si
 * la operación fue cancelada.
 *
 * @author [Pablo González de Lema de Torres]
 * @version 1.0
 */
package org.example.view;

import org.example.model.Pelicula;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class AnadirPeli extends JDialog {

    /**
     * Panel contenedor principal de todos los elementos de la interfaz.
     */
    private JPanel contentPane;
    /**
     * Botón para confirmar y guardar la nueva película.
     */
    private JButton btnGuardar;
    /**
     * Botón para cancelar la operación.
     */
    private JButton btnCancelar;
    /**
     * Campo de texto para el título de la película.
     */
    private JTextField tfTitulo;
    /**
     * Campo de texto para el año de lanzamiento.
     */
    private JTextField tfAno;
    /**
     * Campo de texto para el género de la película.
     */
    private JTextField tfGenero;
    /**
     * Campo de texto para el nombre del director.
     */
    private JTextField tfDirector;
    /**
     * Campo de texto para la URL de la imagen/póster.
     */
    private JTextField tfUrl;
    /**
     * Campo de texto para la descripción o sinopsis.
     */
    private JTextField tfDescripcion; // Asumo JTextField para la descripción
    /**
     * Etiqueta para el título.
     */
    private JLabel lblTitulo;
    /**
     * Etiqueta para el año.
     */
    private JLabel lblAno;
    /**
     * Etiqueta para el género.
     */
    private JLabel lblGenero;
    /**
     * Etiqueta para el director.
     */
    private JLabel lblDirector;
    /**
     * Etiqueta para la URL de la imagen.
     */
    private JLabel lblUrl;
    /**
     * Etiqueta para la descripción.
     */
    private JLabel lblDescripcion;

    /**
     * Objeto Pelicula que se crea al guardar. Es {@code null} si la operación
     * se cancela o si aún no se ha guardado.
     */
    private Pelicula pelicula = null;

    /**
     * Constructor principal de la clase. Configura el diálogo, los listeners
     * y muestra el formulario.
     *
     * @param owner La ventana padre ({@code Frame}) que es dueña de este diálogo.
     */
    public AnadirPeli(Frame owner) {
        super(owner, "Añadir Nueva Película", true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnGuardar);

        // Guardar
        btnGuardar.addActionListener(e -> onGuardar());

        //Cancelar
        btnCancelar.addActionListener(e -> onCancel());

        // cierre de ventana y ESCAPE
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { onCancel(); }
        });

        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * Constructor vacío. Utilizado por herramientas de diseño de interfaz (GUI Builders).
     */
    public AnadirPeli() {}

    /**
     * Lógica ejecutada al presionar el botón de Guardar.
     * Valida los campos, crea un objeto {@code Pelicula} con los datos de entrada
     * y cierra el diálogo.
     * El {@code usuarioId} se establece temporalmente a 0 y debe ser asignado
     * por el {@code AppController}.
     */
    private void onGuardar() {
        if (validarCampos()) {
            // Se crea la Pelicula, el ID de usuario lo asigna el Controller
            pelicula = new Pelicula(
                    tfTitulo.getText(),
                    Integer.parseInt(tfAno.getText()),
                    tfDirector.getText(),
                    tfDescripcion.getText(),
                    tfGenero.getText(),
                    tfUrl.getText(),
                    0 // ID de usuario temporal
            );
            dispose();
        }
    }

    /**
     * Lógica ejecutada al presionar el botón de Cancelar o cerrar la ventana.
     * Establece la película a {@code null} para indicar la cancelación y cierra el diálogo.
     */
    private void onCancel() {
        pelicula = null; // Indica que se canceló
        dispose();
    }

    /**
     * Valida que los campos obligatorios (Título, Año, Género, Director)
     * no estén vacíos y que el campo Año sea un número entero válido.
     * Muestra un mensaje de error si la validación falla.
     *
     * @return {@code true} si todos los campos son válidos, {@code false} en caso contrario.
     */
    private boolean validarCampos() {
        if (tfTitulo.getText().trim().isEmpty() || tfAno.getText().trim().isEmpty() ||
                tfGenero.getText().trim().isEmpty() || tfDirector.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título, Año, Género y Director son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(tfAno.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Obtiene el objeto {@code Pelicula} creado por el diálogo.
     * Este método se usa una sola vez, por lo que limpia la referencia interna
     * después de la llamada.
     *
     * @return El objeto {@code Pelicula} si se guardó, o {@code null} si se canceló.
     */
    public Pelicula getPelicula() {
        Pelicula p = this.pelicula;
        this.pelicula = null;
        return p;
    }
}
