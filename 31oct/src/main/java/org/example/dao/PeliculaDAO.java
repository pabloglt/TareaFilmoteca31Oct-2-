/**
 * Data Access Object (DAO) para la gestión de objetos {@link org.example.model.Pelicula}.
 * <p>
 * Esta clase implementa la persistencia de los datos de las películas
 * utilizando un archivo de texto plano en formato CSV. Maneja la lectura,
 * escritura y manipulación de registros de películas.
 *
 * @author [Pablo González de Lema de Torres]
 * @version 1.0
 */
package org.example.dao;

import org.example.model.Pelicula;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    /**
     * Nombre del archivo CSV principal donde se almacenan los datos de las películas.
     */
    private String archivo = "peliculas.csv";
    /**
     * Nombre del archivo temporal utilizado durante las operaciones de eliminación
     * para reescribir el contenido excluyendo el registro a borrar.
     */
    private String archivoTemp = "peliculas_temp.csv";

    /**
     * Obtiene la lista de todas las películas asociadas a un ID de usuario específico.
     * Lee el archivo CSV línea por línea y filtra las películas por el ID del usuario
     * almacenado en la octava columna.
     *
     * @param usuarioId El ID del usuario cuyas películas se desean recuperar.
     * @return Una {@code List<Pelicula>} que contiene todas las películas del usuario.
     */
    public List<Pelicula> getPeliculasPorUsuarioId(int usuarioId) {
        List<Pelicula> peliculas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 8) {
                    try {
                        int pUsuarioId = Integer.parseInt(datos[7].trim());
                        if (pUsuarioId == usuarioId) {
                            int pId = Integer.parseInt(datos[0].trim());
                            String pTitulo = datos[1].trim();
                            int pAnio = Integer.parseInt(datos[2].trim());
                            String pDirector = datos[3].trim();
                            String pDescripcion = datos[4].trim();
                            String pGenero = datos[5].trim();
                            String pImagen = datos[6].trim();

                            peliculas.add(new Pelicula(pId, pTitulo, pAnio, pDirector, pDescripcion, pGenero, pImagen, pUsuarioId));
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error al parsear datos de película: " + e.getMessage() + " en línea: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de películas: " + e.getMessage());
        }
        return peliculas;
    }

    /**
     * Obtiene una película específica buscando por su ID único.
     *
     * @param peliculaId El ID de la película que se desea recuperar.
     * @return El objeto {@code Pelicula} si se encuentra, o {@code null} en caso contrario.
     */
    public Pelicula getPeliculaPorId(int peliculaId) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 8) {
                    try {
                        int pId = Integer.parseInt(datos[0].trim());
                        if (pId == peliculaId) {
                            String pTitulo = datos[1].trim();
                            int pAnio = Integer.parseInt(datos[2].trim());
                            String pDirector = datos[3].trim();
                            String pDescripcion = datos[4].trim();
                            String pGenero = datos[5].trim();
                            String pImagen = datos[6].trim();
                            int pUsuarioId = Integer.parseInt(datos[7].trim());
                            return new Pelicula(pId, pTitulo, pAnio, pDirector, pDescripcion, pGenero, pImagen, pUsuarioId);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error al parsear ID en getPeliculaPorId: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de películas: " + e.getMessage());
        }
        return null;
    }

    /**
     * Añade una nueva película al archivo CSV.
     * <p>
     * Antes de escribir, asigna un nuevo ID autoincremental a la película
     * llamando a {@link #getSiguienteId()}.
     *
     * @param pelicula El objeto {@code Pelicula} que se desea añadir.
     */
    public void anadirPelicula(Pelicula pelicula) {
        int nuevoId = getSiguienteId();
        pelicula.setId(nuevoId);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) { // true para modo append
            String linea = String.join(",",
                    String.valueOf(pelicula.getId()),
                    pelicula.getTitulo(),
                    String.valueOf(pelicula.getAnio()),
                    pelicula.getDirector(),
                    pelicula.getDescripcion(),
                    pelicula.getGenero(),
                    pelicula.getImagenUrl(),
                    String.valueOf(pelicula.getUsuarioId())
            );
            bw.newLine();
            bw.write(linea);
        } catch (IOException e) {
            System.err.println("Error al añadir película: " + e.getMessage());
        }
    }

    /**
     * Elimina una película del archivo CSV basándose en su ID.
     * <p>
     * Este método realiza una reescritura del archivo: lee el original y copia
     * todas las líneas excepto la que coincide con el ID de la película a eliminar
     * a un archivo temporal. Luego, reemplaza el archivo original con el temporal.
     *
     * @param peliculaId El ID de la película que se desea eliminar.
     */
    public void eliminarPelicula(int peliculaId) {
        File original = new File(archivo);
        File temp = new File(archivoTemp);

        try (BufferedReader br = new BufferedReader(new FileReader(original));
             BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length > 0) {
                    try {
                        int idActual = Integer.parseInt(datos[0].trim());
                        if (idActual != peliculaId) {
                            bw.write(linea);
                            bw.newLine();
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar líneas mal formadas
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar película (IO): " + e.getMessage());
            return;
        }

        // Reemplazar el archivo original con el temporal
        if (!original.delete()) {
            System.err.println("No se pudo borrar el archivo original.");
            return;
        }
        if (!temp.renameTo(original)) {
            System.err.println("No se pudo renombrar el archivo temporal.");
        }
    }

    // Helper para obtener el siguiente ID disponible
    /**
     * Calcula y devuelve el siguiente ID disponible para una nueva película.
     * Esto se determina encontrando el valor máximo de ID en el archivo CSV
     * y sumándole uno.
     *
     * @return El siguiente ID entero disponible.
     */
    private int getSiguienteId() {
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length > 0) {
                    try {
                        int id = Integer.parseInt(datos[0].trim());
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al calcular el siguiente ID: " + e.getMessage());
        }
        return maxId + 1;
    }
}