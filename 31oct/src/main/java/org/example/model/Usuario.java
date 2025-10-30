/**
 * Clase de modelo que representa un Usuario del sistema.
 * Contiene información esencial para la autenticación y la identificación.
 *
 * @author [Pablo González de Lema de Torres]
 * @version 1.0
 */
package org.example.model;

public class Usuario {
    /**
     * ID único del usuario. Utilizado como clave de referencia en la base de datos o CSV.
     */
    private int id;
    /**
     * Correo electrónico del usuario, utilizado como nombre de usuario para el login.
     */
    private String email;
    /**
     * Contraseña del usuario.
     */
    private String password;

    /**
     * Constructor para crear una instancia completa de Usuario.
     * Utilizado principalmente por el DAO después de recuperar los datos.
     *
     * @param id El ID único asignado al usuario.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    public Usuario(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }


    /**
     * Obtiene el ID único del usuario.
     * @return El ID del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }
}
