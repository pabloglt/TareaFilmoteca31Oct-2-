/**
 * Clase principal que contiene el método {@code main}.
 *
 * Es el punto de entrada de la aplicación y es responsable de asegurar que
 * la inicialización de la interfaz de usuario de Swing se realice
 * de manera segura en el Event Dispatch Thread (EDT) a través de
 * {@code SwingUtilities.invokeLater}.
 *
 * @author [Pablo González de Lema de Torres]
 * @version 1.0
 */
package org.example;

import org.example.controller.AppController;
import javax.swing.SwingUtilities;

public class Main {

    /**
     * El método principal (main) de la aplicación.
     * Crea e inicia el {@code AppController}, asegurando que toda la lógica de la
     * interfaz de usuario de Swing se ejecute correctamente en el EDT.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Para la GUI de Swing se ejecute
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppController controller = new AppController();
                controller.iniciar();
            }
        });
    }
}
