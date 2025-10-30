/**
 * Data Access Object (DAO) para la gestión de objetos {@link org.example.model.Usuario}.
 * <p>
 * Implementa la lógica para acceder y verificar las credenciales de los usuarios
 * utilizando un archivo de texto plano en formato CSV.
 *
 * @author [Pablo González de Lema de Torres]
 * @version 1.0
 */
package org.example.dao;

import org.example.model.Usuario;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UsuarioDAO {

    /**
     * Nombre del archivo CSV principal donde se almacenan los datos de los usuarios.
     * El formato esperado de cada línea es: ID,EMAIL,PASSWORD.
     */
    private String archivo = "usuarios.csv";

    /**
     * Intenta autenticar a un usuario con el email y la contraseña proporcionados.
     * Lee el archivo CSV línea por línea y verifica si alguna línea coincide con
     * ambas credenciales.
     *
     * @param email El email del usuario que intenta iniciar sesión.
     * @param password La contraseña del usuario.
     * @return El objeto {@code Usuario} si las credenciales son válidas, o {@code null}
     * si el usuario no es encontrado o ocurre un error de lectura/parseo.
     */
    public Usuario login(String email, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    String emailCSV = datos[1].trim();
                    String passCSV = datos[2].trim();

                    if (emailCSV.equals(email) && passCSV.equals(password)) {
                        int id = Integer.parseInt(datos[0].trim());
                        return new Usuario(id, emailCSV, passCSV);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de usuarios: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear ID de usuario: " + e.getMessage());
        }
        return null; // Devuelve null si no se encuentra nada o hay error
    }
}
