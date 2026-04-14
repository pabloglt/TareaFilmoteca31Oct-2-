TareaFilmoteca - Sistema de Gestión de Películas (Swing + CSV)
Este proyecto es una aplicación de escritorio desarrollada en Java para la gestión de una filmoteca personal, utilizando una arquitectura Modelo-Vista-Controlador (MVC) y persistencia de datos en archivos planos.

🛠️ Tecnologías Utilizadas
Java 25: Versión del lenguaje configurada en el pom.xml.

Swing: Framework para la creación de la interfaz gráfica de usuario (GUI).

Maven: Sistema de gestión de dependencias y construcción.

CSV: Formato de persistencia para el almacenamiento de datos.

📂 Estructura del Proyecto
🔵 Lógica y Control (org.example)
Main: Punto de entrada de la aplicación que asegura el arranque de la GUI en el hilo de despacho de eventos (EDT).

controller.AppController: Orquestador principal que gestiona el flujo entre las vistas y la lógica de negocio (login, añadir/eliminar películas, mostrar detalles).

dao: Clases encargadas del acceso a datos (PeliculaDAO y UsuarioDAO) para leer y escribir en los archivos CSV.

model: Definición de las entidades del sistema: Pelicula y Usuario.

🎨 Interfaz de Usuario (org.example.view)
LoginPeli: Pantalla de autenticación de usuarios.

MainPeli: Ventana principal con el listado de películas.

AnadirPeli: Diálogo para el registro de nuevos títulos.

DetallePeli: Vista detallada de la información de una película seleccionada.

🚀 Funcionalidades
Autenticación: Validación de usuarios mediante email y contraseña.

Gestión de Películas (CRUD):

Listado dinámico de películas por usuario.

Añadir nuevas películas asociadas al perfil logueado.

Eliminación de registros con confirmación de seguridad.

Detalle: Visualización completa de los atributos de cada película.

💾 Persistencia de Datos
El sistema utiliza dos archivos en la raíz del proyecto para mantener la información:

usuarios.csv: Almacena las credenciales y datos de los usuarios.

peliculas.csv: Almacena el catálogo de películas vinculado a cada ID de usuario.

📝 Ejecución
Para iniciar la aplicación, ejecute la clase org.example.Main. El controlador iniciará automáticamente la vista de login.
