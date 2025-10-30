/**
 * Vista (JFrame) principal de la aplicación.
 * Muestra la colección de películas del usuario logueado en una tabla y
 * proporciona botones para gestionar las películas (añadir, eliminar, ver detalle)
 * y cerrar sesión.
 *
 * @author [Pablo González de Lema de Torres]
 * @version 1.0
 */
package org.example.view;

import org.example.model.Pelicula;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Frame;
import java.awt.Dimension;

public class MainPeli extends JFrame {

    /**
     * Botón para cerrar la sesión actual y volver a la pantalla de login.
     */
    private JButton btnCerrarSesion;
    /**
     * La tabla principal que muestra la lista de películas.
     */
    private JTable table1; // La JTable principal
    /**
     * Botón para abrir el diálogo de añadir una nueva película.
     */
    private JButton btnAnadir;
    /**
     * Botón para eliminar la película seleccionada de la tabla.
     */
    private JButton btnEliminar;
    /**
     * Botón para mostrar la vista de detalle de la película seleccionada.
     */
    private JButton btnDetalle; // Usado para Ver Detalle
    /**
     * Etiqueta para mostrar un mensaje de bienvenida al usuario logueado.
     */
    private JLabel lblBienvenida;
    /**
     * Panel principal que contiene todos los componentes de la vista.
     */
    private JPanel mainPanel;

    /**
     * Modelo de datos para la JTable, utilizado para gestionar las filas y columnas.
     */
    private DefaultTableModel tableModel;

    /**
     * Constructor de la vista principal.
     * Configura el {@code DefaultTableModel} con las columnas necesarias,
     * oculta la columna de ID y establece el tamaño y la posición de la ventana.
     */
    public MainPeli() {

        if (mainPanel != null) {
            setContentPane(mainPanel);
        }

        // Configuración inicial del JTable
        String[] columnas = {"ID", "Título", "Año", "Género"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 0) ? Integer.class : super.getColumnClass(columnIndex);
            }
        };

        table1.setModel(tableModel);

        // Ocultar la columna ID (necesario para la lógica del Controller)
        table1.getColumnModel().getColumn(0).setMinWidth(0);
        table1.getColumnModel().getColumn(0).setMaxWidth(0);
        table1.getColumnModel().getColumn(0).setPreferredWidth(0);

        setTitle("Colección de Películas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Asegurar tamaño visible y centrado
        pack();
        if (getWidth() < 600 || getHeight() < 400) {
            setSize(800, 600);
        }
        setLocationRelativeTo(null);
    }

    //Métodos de Interacción por el Controller

    /**
     * Establece el texto de bienvenida en la etiqueta correspondiente.
     * @param email El email del usuario logueado para mostrar en el mensaje.
     */
    public void setBienvenida(String email) { lblBienvenida.setText("Bienvenido, " + email); }

    /**
     * Carga la lista de películas en la JTable, reemplazando cualquier contenido anterior.
     * @param peliculas La {@code List} de objetos {@code Pelicula} a mostrar.
     */
    public void mostrarPeliculas(List<Pelicula> peliculas) {
        tableModel.setRowCount(0);
        for (Pelicula p : peliculas) {
            Object[] fila = { p.getId(), p.getTitulo(), p.getAnio(), p.getGenero() };
            tableModel.addRow(fila);
        }
    }

    /**
     * Obtiene el ID de la película actualmente seleccionada en la tabla.
     * @return El ID de la película seleccionada, o -1 si no hay ninguna fila seleccionada.
     */
    public int getSelectedPeliculaId() {
        int filaSeleccionada = table1.getSelectedRow();
        return (filaSeleccionada != -1) ? (int) tableModel.getValueAt(filaSeleccionada, 0) : -1;
    }

    // Listeners para los botones
    /**
     * Añade un {@code ActionListener} al botón de Añadir.
     * @param listener El listener a adjuntar.
     */
    public void addAnadirListener(ActionListener listener) { btnAnadir.addActionListener(listener); }
    /**
     * Añade un {@code ActionListener} al botón de Eliminar.
     * @param listener El listener a adjuntar.
     */
    public void addEliminarListener(ActionListener listener) { btnEliminar.addActionListener(listener); }
    /**
     * Añade un {@code ActionListener} al botón de Ver Detalle.
     * @param listener El listener a adjuntar.
     */
    public void addVerDetalleListener(ActionListener listener) { btnDetalle.addActionListener(listener); }
    /**
     * Añade un {@code ActionListener} al botón de Cerrar Sesión.
     * @param listener El listener a adjuntar.
     */
    public void addCerrarSesionListener(ActionListener listener) { btnCerrarSesion.addActionListener(listener); }

    // Diálogos
    /**
     * Muestra un diálogo de mensaje simple.
     * @param mensaje El texto del mensaje.
     * @param titulo El título del diálogo.
     * @param tipo El tipo de mensaje (por ejemplo, {@code JOptionPane.ERROR_MESSAGE}).
     */
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
    /**
     * Muestra un diálogo de confirmación de Sí/No.
     * @param mensaje El texto de la pregunta de confirmación.
     * @param titulo El título del diálogo.
     * @return El resultado de la confirmación (por ejemplo, {@code JOptionPane.YES_OPTION}).
     */
    public int mostrarConfirmacion(String mensaje, String titulo) {
        return JOptionPane.showConfirmDialog(this, mensaje, titulo, JOptionPane.YES_NO_OPTION);
    }
    /**
     * Devuelve una referencia a sí misma como {@code Frame}. Útil para
     * pasarla como ventana dueña a diálogos modales.
     * @return Esta instancia de {@code JFrame} como {@code Frame}.
     */
    public Frame getFrame() { return this; }
}
