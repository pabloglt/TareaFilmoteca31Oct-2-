/**
 * Diálogo (JDialog) utilizado para mostrar los detalles completos de una
 * {@link org.example.model.Pelicula} específica en modo de solo lectura.
 *
 * @author [Pablo González de Lema de Torres]
 * @version 1.0
 */
package org.example.view;

import org.example.model.Pelicula;
import javax.swing.*;
import java.awt.*;

public class DetallePeli extends JDialog {

    /**
     * Panel contenedor principal del diálogo.
     */
    private JPanel contentPane;
    /**
     * Área de texto utilizada para mostrar la descripción detallada de la película.
     */
    private JTextArea textArea1; // Usado para la descripción
    /**
     * Etiqueta para mostrar el título de la película.
     */
    private JLabel lblTitulo;
    /**
     * Etiqueta que combina el director y el año de lanzamiento.
     */
    private JLabel lblDirectorYAno;
    /**
     * Etiqueta para mostrar el género de la película.
     */
    private JLabel lblGenero;
    /**
     * Etiqueta para el título de la descripción.
     */
    private JLabel lblDescripcion;
    /**
     * Referencia al panel contenedor principal. (Nota: Es probable que esta variable {@code contenPane}
     * sea un duplicado de {@code contentPane} en el diseño de la GUI).
     */
    private JPanel contenPane;
    /**
     * Etiqueta para mostrar la URL de la imagen.
     */
    private JLabel lblURL;

    /**
     * Constructor principal utilizado por el {@code AppController}.
     * Inicializa y muestra el diálogo con la información de la película proporcionada.
     *
     * @param owner La ventana padre ({@code Frame}) que es dueña de este diálogo.
     * @param pelicula La {@code Pelicula} cuyos detalles se van a mostrar.
     */
    public DetallePeli(Frame owner, Pelicula pelicula) {
        super(owner, "Detalle de: " + pelicula.getTitulo(), true);
        setContentPane(contentPane);
        setModal(true);

        lblTitulo.setText(pelicula.getTitulo());
        lblDirectorYAno.setText("Dirigida por " + pelicula.getDirector() + " (" + pelicula.getAnio() + ")");
        lblGenero.setText("Género: " + pelicula.getGenero());
        textArea1.setText(pelicula.getDescripcion());
        textArea1.setEditable(false);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        // Hace que el JTextArea se vea como un JLabel
        textArea1.setBackground(UIManager.getColor("Label.background"));
        lblURL.setText(pelicula.getImagenUrl());


        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    /**
     * Constructor vacío. Utilizado por herramientas de diseño de interfaz (GUI Builders).
     */
    public DetallePeli() {} // Constructor vacío para el diseñador
}
