/**
 * Clase principal del controlador de la aplicación.
 * Gestiona el flujo de la aplicación, las interacciones entre las vistas (LoginPeli, MainPeli, etc.)
 * y la lógica de negocio a través de los DAOs (Data Access Objects).
 * Sirve como el punto de inicio de la aplicación y maneja las operaciones de usuario
 * como login, logout, y la gestión de películas (añadir, eliminar, ver detalle).
 *
 * @author [Pablo González de Lema de Torres]
 * @version 1.0
 */
package org.example.controller;

import org.example.dao.PeliculaDAO;
import org.example.dao.UsuarioDAO;
import org.example.model.Pelicula;
import org.example.model.Usuario;
import org.example.view.AnadirPeli;
import org.example.view.DetallePeli;
import org.example.view.LoginPeli;
import org.example.view.MainPeli;

import javax.swing.*;
import java.util.List;


public class AppController {

    /**
     * Vista de la pantalla de inicio de sesión.
     */
    private LoginPeli loginView;
    /**
     * Vista de la pantalla principal de la aplicación.
     */
    private MainPeli mainView;


    /**
     * Objeto de acceso a datos para la gestión de usuarios.
     */
    private UsuarioDAO usuarioDAO;
    /**
     * Objeto de acceso a datos para la gestión de películas.
     */
    private PeliculaDAO peliculaDAO;


    /**
     * El usuario que ha iniciado sesión actualmente en la aplicación.
     * Es 'null' si no hay un usuario logueado.
     */
    private Usuario usuarioLogueado;

    /**
     * Constructor de la clase AppController.
     * Inicializa los DAOs y la vista de login, y configura el listener para el
     * botón de inicio de sesión.
     */
    public AppController() {
        // Inicializar DAOs
        this.usuarioDAO = new UsuarioDAO();
        this.peliculaDAO = new PeliculaDAO();

        // Inicializar Vistas, MainView después del login
        this.loginView = new LoginPeli();

        // Listener del botón de login
        this.loginView.addLoginListener(e -> handleLogin());
    }

    /**
     * Inicia la aplicación haciendo visible la pantalla de login.
     * Asegura que la inicialización de la interfaz de usuario se haga en
     * el Event Dispatch Thread (EDT).
     */
    public void iniciar() {
        SwingUtilities.invokeLater(() -> loginView.setVisible(true));
    }

    // Manejadores de Eventos

    /**
     * Maneja el evento de intento de inicio de sesión.
     * Recupera el email y la contraseña de la vista de login,
     * intenta autenticar al usuario y, si es exitoso, muestra la vista principal.
     * Si falla, muestra un mensaje de error.
     */
    private void handleLogin() {
        String email = loginView.getEmail();
        String password = loginView.getPassword();

        usuarioLogueado = usuarioDAO.login(email, password);

        if (usuarioLogueado != null) {
            loginView.setVisible(false);
            mostrarMainView();
        } else {
            loginView.mostrarError("Email o contraseña incorrectos");
        }
        loginView.limpiarFormulario();
    }

    /**
     * Maneja el evento de cerrar sesión.
     * Limpia el usuario logueado, cierra la vista principal (si existe)
     * y vuelve a mostrar la vista de login.
     */
    private void handleCerrarSesion() {
        usuarioLogueado = null;
        if (mainView != null) {
            mainView.dispose();
            mainView = null;
        }
        loginView.setVisible(true);
    }

    /**
     * Maneja el evento de añadir una nueva película.
     * Abre el diálogo de añadir película. Si se proporciona una película
     * válida, le asigna el ID del usuario logueado, la guarda en la base de datos
     * y recarga la lista de películas en la vista principal.
     */
    private void handleAnadirPelicula() {
        //pasar el Frame como dueño del diálogo
        AnadirPeli dialog = new AnadirPeli(mainView.getFrame());
        dialog.setVisible(true);

        Pelicula nuevaPelicula = dialog.getPelicula();

        if (nuevaPelicula != null) {
            // Se da el ID del usuario logueado
            nuevaPelicula.setUsuarioId(usuarioLogueado.getId());
            peliculaDAO.anadirPelicula(nuevaPelicula);
            cargarPeliculasUsuario();
        }
    }

    /**
     * Maneja el evento de eliminar una película.
     * Obtiene el ID de la película seleccionada en la vista principal.
     * Pide confirmación al usuario y, si confirma, elimina la película de la
     * base de datos y recarga la lista.
     */
    private void handleEliminarPelicula() {
        int peliculaId = mainView.getSelectedPeliculaId();
        if (peliculaId == -1) {
            mainView.mostrarMensaje("Por favor, selecciona una película para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = mainView.mostrarConfirmacion("¿Estás seguro de que quieres eliminar esta película?", "Confirmar Eliminación");

        if (confirm == JOptionPane.YES_OPTION) {
            peliculaDAO.eliminarPelicula(peliculaId);
            cargarPeliculasUsuario();
        }
    }

    /**
     * Maneja el evento de ver el detalle de una película.
     * Obtiene el ID de la película seleccionada, busca la película en la base
     * de datos y, si la encuentra, muestra la vista de detalle.
     */
    private void handleVerDetalle() {
        int peliculaId = mainView.getSelectedPeliculaId();
        if (peliculaId == -1) {
            mainView.mostrarMensaje("Por favor, selecciona una película para ver el detalle.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pelicula pelicula = peliculaDAO.getPeliculaPorId(peliculaId);

        if (pelicula != null) {
            // Crear y mostrar la vista de detalle
            new DetallePeli(mainView.getFrame(), pelicula);
        } else {
            mainView.mostrarMensaje("No se pudo encontrar la película seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Métodos de Lógica Interna

    /**
     * Inicializa y muestra la vista principal (MainPeli).
     * Configura todos los listeners para los botones de la vista principal
     * (Añadir, Eliminar, Ver Detalle, Cerrar Sesión),
     * establece el saludo de bienvenida y carga las películas del usuario logueado.
     */
    private void mostrarMainView() {
        mainView = new MainPeli();

        // Configurar listeners
        mainView.addAnadirListener(e -> handleAnadirPelicula());
        mainView.addEliminarListener(e -> handleEliminarPelicula());
        mainView.addVerDetalleListener(e -> handleVerDetalle());
        mainView.addCerrarSesionListener(e -> handleCerrarSesion());

        // Personalizar y cargar datos
        mainView.setBienvenida(usuarioLogueado.getEmail());
        cargarPeliculasUsuario();

        mainView.setVisible(true);
    }

    /**
     * Obtiene la lista de películas asociadas al usuario logueado desde el DAO
     * y las muestra en la vista principal.
     */
    private void cargarPeliculasUsuario() {
        List<Pelicula> peliculas = peliculaDAO.getPeliculasPorUsuarioId(usuarioLogueado.getId());
        mainView.mostrarPeliculas(peliculas);
    }
}