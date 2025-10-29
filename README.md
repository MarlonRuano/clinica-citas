# Proyecto Final: Clínica de Citas (clinica-citas)

Aplicación de escritorio para gestionar Doctores, Pacientes y Citas médicas.

Hecha con Java 17, Swing, MySQL 8 y Maven. Empaquetada en JAR ejecutable con configuración de conexión externalizada.

## Funcionalidades

* **Login de demostración:** `admin` / `admin`.
* **CRUD de Doctores:** Gestión completa de médicos.
* **CRUD de Pacientes:** Gestión completa de pacientes.
* **Citas:** Programar, reprogramar y cancelar citas.
* **Validaciones:** Campos requeridos, emails, etc.
* **Semillas de datos:** Demo lista para usar al instante.

## Requisitos

* **Java 17+** (ejecuta `java -version` para verificar).
* **MySQL 8+** corriendo en `localhost:3306`.
* **Cliente MySQL** (como Workbench) para ejecutar el script inicial.

## Base de datos (Paso 1: Crear y Sembrar)

Abre MySQL Workbench (o tu cliente) y ejecuta el script:

`src/main/resources/sql/schema.sql`

Esto creará la base de datos `clinica` y cargará los datos de demo (doctores, pacientes y el usuario admin).

## Cómo ejecutar (Paso 2: Configurar y Correr)

Para correr la aplicación, usa el paquete de "release" que contiene el `run.bat`, el `.jar` y el `app.properties` en la misma carpeta.

1.  **Configura la conexión:** Abre el archivo `app.properties` con cualquier editor de texto.
2.  **Edita las credenciales:** Cambia el `db.user` y `db.pass` para que coincidan con tu configuración local de MySQL.

    ```properties
    db.url=jdbc:mysql://localhost:3306/clinica?useSSL=false&serverTimezone=UTC
    db.user=root
    db.pass=AQUI_VA_TU_CONTRASENA_DE_MYSQL
    ```
3.  **Ejecuta:**
    * **Windows:** Haz doble clic en `run.bat`.
    * **Mac/Linux:** Abre una terminal y ejecuta `java -jar clinica-citas-0.1.0-shaded.jar`.

## Acceso de prueba

Una vez que la app inicie, usa las credenciales de demo:

* **Usuario:** `admin`
* **Contraseña:** `admin`

## Flujo de prueba recomendado (end-to-end)

1.  Login con `admin` / `admin`.
2.  **Pacientes** → verás María y Carlos. Prueba crear, editar y eliminar.
3.  **Doctores** → verás Ana y Luis. Prueba el CRUD.
4.  **Citas** → crea una cita PROGRAMADA, luego repográmala o cancélela.

## Solución de problemas

* **"Error fatal: No se pudo leer 'app.properties'"**: El archivo .jar no encuentra el archivo de configuración. Asegúrate de que `app.properties` esté en la misma carpeta que el `.jar`.
* **"Access denied for user..."**: Usuario/clave de MySQL incorrectos. Revisa y corrige `app.properties`.
* **"Communications link failure"**: MySQL está apagado o la URL es incorrecta. Revisa que MySQL esté corriendo y que `db.url` en `app.properties` sea correcto.