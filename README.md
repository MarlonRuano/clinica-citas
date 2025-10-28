Clínica Citas — Java Swing + MySQL (MVC/POO)

Organiza doctores, pacientes y citas médicas en una app de escritorio rápida y sencilla.
Tecnologías: Java 17, Swing, MySQL 8, Maven.

Cambio clave: las credenciales de BD ya no están en el código.
Ahora se leen desde app.properties (archivo externo al .jar, junto a él).

🚀 Demo rápida (3 pasos)

Crea la base e importa el esquema

Abre MySQL Workbench o tu cliente favorito.

Ejecuta el contenido de src/main/resources/sql/schema.sql.

Configura credenciales sin recompilar
Crea un archivo llamado app.properties (ver formato más abajo).

Si ejecutas con java -jar, coloca app.properties junto al .jar.

Si ejecutas desde el IDE, colócalo en la carpeta raíz donde corres el programa.

Inicia la aplicación

Desde IDE (desarrollo): Ejecuta clinica.Main (o tu clase Main).

Empaquetado: mvn -q -DskipTests clean package
Luego: java -jar target/clinica-citas-0.1.0-with-deps.jar

Credenciales de prueba (si importaste el schema.sql tal como está):

Usuario: admin
Contraseña: admin


Si tu instalación usa hash distinto, revisa la tabla usuarios.

🧰 Requisitos

Java 17+

Maven 3.8+

MySQL 8+ en local o remoto

⚙️ Configuración: app.properties

Este archivo vive fuera del .jar y define cómo conectarse a MySQL:

# app.properties
db.url=jdbc:mysql://localhost:3306/clinica?useSSL=false&serverTimezone=UTC
db.user=root
db.pass=


Ubicación:

Ejecución empaquetada: en la misma carpeta donde corres java -jar
(ej. junto a clinica-citas-0.1.0-with-deps.jar).

Ejecución en IDE: en el directorio de trabajo (la carpeta desde la que corres).

Si cambias servidor, puerto o credenciales, solo editas este archivo.
No recompiles Java para cambiar credenciales.

🗄️ Base de datos (esquema + semillas)

El archivo src/main/resources/sql/schema.sql:

Crea las tablas usuarios, doctores, pacientes, citas.

Agrega datos de demo (admin + 2 doctores + 2 pacientes + 1 cita), de forma idempotente (no duplica si ya existen).

Crea índices básicos para citas(doctor_id) y citas(paciente_id).

Si ya tenías la BD, puedes ejecutar solo las inserciones o revisar los INSERT … WHERE NOT EXISTS.

▶️ Cómo ejecutar
Opción A: Desarrollo (IDE / VS Code)

Asegúrate de tener MySQL en ejecución y la BD clinica creada (importa schema.sql).

Coloca app.properties en el directorio raíz desde donde arrancas el proyecto.

Ejecuta la clase Main (por ejemplo, clinica.Main).

Opción B: Empaquetado (JAR)

Compila con dependencias incluidas:

mvn -q -DskipTests clean package


Esto genera: target/clinica-citas-0.1.0-with-deps.jar

Copia app.properties junto al JAR.

Ejecuta:

java -jar clinica-citas-0.1.0-with-deps.jar


(Opcional) Usa el run.bat incluido (ya imprime “Iniciando Clinica Citas…” y lanza el JAR).

🧭 Estructura de carpetas
clinica-citas/
├─ src/
│  └─ main/
│     ├─ java/clinica/
│     │  ├─ config/Db.java
│     │  ├─ controller/ (Login, Pacientes, Doctores, Citas)
│     │  ├─ dao/ (UsuarioDao, PacienteDao, DoctorDao, CitaDao)
│     │  ├─ model/ (Usuario, Paciente, Doctor, Cita)
│     │  └─ view/ (LoginFrame, MainMenuFrame, ...Frames)
│     └─ resources/sql/schema.sql
├─ docs/
│  ├─ guia-instalacion/ (PDF/MD)
│  ├─ manual-tecnico/ (PDF/MD)
│  └─ diagrams/clases.md (Mermaid UML)
├─ app.properties               # (se usa en ejecución local/IDE)
├─ pom.xml
├─ README.md
└─ run.bat                      # (opcional para Windows)

✅ Funcionalidades actuales

Login (usuarios de tabla usuarios)

Gestión de Doctores (CRUD básico, validaciones mínimas)

Gestión de Pacientes (CRUD básico, validaciones mínimas)

Gestión de Citas (crear, reprogramar, cancelar; estados válidos: PROGRAMADA, ATENDIDA, CANCELADA)

Persistencia MySQL mediante DAO, MVC con Swing

Configuración externa por app.properties (sin recompilar)

🧪 Prueba sugerida (rápida)

Iniciar app → Login con admin/admin

Abrir Doctores → verificar 2 doctores de demo (Ana / Luis)

Abrir Pacientes → verificar 2 pacientes (María / Carlos)

Crear una cita nueva o reprogramar la de demo → estado PROGRAMADA

Cambiar estado a ATENDIDA o CANCELADA y refrescar

🆘 Troubleshooting (FAQ)

Communications link failure
MySQL no está levantado o db.url apunta a host/puerto incorrectos.

Access denied for user …
Usuario/clave en app.properties incorrectos o sin permisos.
Prueba esas credenciales en MySQL Workbench.

Unknown database 'clinica'
Falta crear la base y/o ejecutar schema.sql.

FileNotFoundException: app.properties
El archivo no está en el directorio de ejecución.
Al usar java -jar, debe estar junto al JAR.

Login falla
Verifica que exista un usuario en tabla usuarios.
Si cambiaste política de hash, ajusta la contraseña en BD acorde.

📚 Documentación

Guía de instalación: docs/guia-instalacion/

Manual técnico: docs/manual-tecnico/

Diagrama de clases (Mermaid): docs/diagrams/clases.md

📦 Build reproducible

El pom.xml incluye el maven-shade-plugin para generar un fat-jar (*-with-deps.jar).
Esto asegura que tu profesor/compañero puedan ejecutar sin configurar el classpath.

📝 Licencia / Créditos

Proyecto académico. Uso educativo.