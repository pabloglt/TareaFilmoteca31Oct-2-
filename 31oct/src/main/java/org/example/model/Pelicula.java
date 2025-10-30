/**
 * Clase de modelo que representa una Película.
 * Contiene todos los atributos que definen una película y los métodos para acceder
 * y modificar sus propiedades (getters y setters).
 *
 * @author [Pablo González de Lema de Torres]
 * @version 1.0
 */
package org.example.model;

public class Pelicula {
    /**
     * ID único de la película. Es generado por el DAO al guardar.
     */
    private int id;
    /**
     * Título de la película.
     */
    private String titulo;
    /**
     * Año de lanzamiento de la película.
     */
    private int anio;
    /**
     * Nombre del director de la película.
     */
    private String director;
    /**
     * Descripción o sinopsis breve de la película.
     */
    private String descripcion;
    /**
     * Género al que pertenece la película (e.g., "Drama", "Ciencia Ficción").
     */
    private String genero;
    /**
     * URL de la imagen o póster de la película.
     */
    private String imagenUrl;
    /**
     * ID del usuario al que pertenece esta película (quién la añadió).
     */
    private int usuarioId;

    /**
     * Constructor completo utilizado principalmente para cargar una película
     * desde la capa de persistencia (DAO/CSV), donde el ID ya es conocido.
     *
     * @param id El ID único de la película.
     * @param titulo El título de la película.
     * @param anio El año de lanzamiento.
     * @param director El director.
     * @param descripcion La descripción.
     * @param genero El género.
     * @param imagenUrl La URL de la imagen.
     * @param usuarioId El ID del usuario propietario.
     */
    public Pelicula(int id, String titulo, int anio, String director, String descripcion, String genero, String imagenUrl, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.anio = anio;
        this.director = director;
        this.descripcion = descripcion;
        this.genero = genero;
        this.imagenUrl = imagenUrl;
        this.usuarioId = usuarioId;
    }

    /**
     * Constructor utilizado para crear un nuevo objeto Pelicula antes de añadirlo
     * a la base de datos, donde el ID aún no ha sido generado.
     * El ID se establecerá posteriormente en el DAO.
     *
     * @param titulo El título de la película.
     * @param anio El año de lanzamiento.
     * @param director El director.
     * @param descripcion La descripción.
     * @param genero El género.
     * @param imagenUrl La URL de la imagen.
     * @param usuarioId El ID del usuario propietario.
     */
    public Pelicula(String titulo, int anio, String director, String descripcion, String genero, String imagenUrl, int usuarioId) {
        this.titulo = titulo;
        this.anio = anio;
        this.director = director;
        this.descripcion = descripcion;
        this.genero = genero;
        this.imagenUrl = imagenUrl;
        this.usuarioId = usuarioId;
    }


    /**
     * Obtiene el ID único de la película.
     * @return El ID de la película.
     */
    public int getId() { return id; }
    /**
     * Obtiene el título de la película.
     * @return El título.
     */
    public String getTitulo() { return titulo; }
    /**
     * Obtiene el año de lanzamiento de la película.
     * @return El año de lanzamiento.
     */
    public int getAnio() { return anio; }
    /**
     * Obtiene el nombre del director.
     * @return El director.
     */
    public String getDirector() { return director; }
    /**
     * Obtiene la descripción de la película.
     * @return La descripción.
     */
    public String getDescripcion() { return descripcion; }
    /**
     * Obtiene el género de la película.
     * @return El género.
     */
    public String getGenero() { return genero; }
    /**
     * Obtiene la URL de la imagen.
     * @return La URL de la imagen.
     */
    public String getImagenUrl() { return imagenUrl; }
    /**
     * Obtiene el ID del usuario propietario de la película.
     * @return El ID del usuario.
     */
    public int getUsuarioId() { return usuarioId; }

    // Setters  para el DAO/Controller
    /**
     * Establece el ID de la película. Usado por el DAO después de la inserción.
     * @param id El nuevo ID de la película.
     */
    public void setId(int id) { this.id = id; }
    /**
     * Establece el ID del usuario propietario de la película. Usado por el Controller.
     * @param usuarioId El ID del usuario.
     */
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
