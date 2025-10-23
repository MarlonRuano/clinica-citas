## 🏥 ¿De qué va este proyecto?

**Clínica Citas** es una **aplicación de escritorio** hecha en **Java (Swing)** que permite a una clínica **organizar sus citas médicas** de manera sencilla y ordenada.  
El sistema guarda la información en **MySQL**, aplicando **POO** y el patrón **MVC** para un código claro y mantenible.

---
## 1) Requisitos (qué instalar)
-  instalacion de herramientas
-  winget install -e --id EclipseAdoptium.Temurin.17.JDK
-  winget install -e --id Apache.Maven
# (Opcional) MySQL + Workbench suelen instalarse con su instalador oficial.

### Lenguaje/Build
- **Java JDK 17** (válido 8+; recomendado 17)
- **Maven 3.8+** (para compilar y empaquetar)

### Base de datos
- **MySQL 8+** (o MariaDB 10.6+)
- (Opcional) **MySQL Workbench** para ejecutar el script SQL

### IDE
- **NetBeans 12+**
- **VS Code** con *Extension Pack for Java*

### Dependencias del proyecto (pom.xml)
- **mysql-connector-j 8.0.33** (conector JDBC de MySQL)


## ✨ ¿Qué puedo hacer con la app?

- 🔐 **Iniciar sesión** (Login) para entrar de forma segura.
- 👤 **Gestionar Pacientes**: crear, ver, actualizar y eliminar (CRUD).
- 🩺 **Gestionar Doctores**: crear, ver, actualizar y eliminar (CRUD).
- 📅 **Agendar Citas**: asignar paciente + doctor + fecha/hora, editar o cancelar.
- ✅ **Validaciones básicas** en formularios (campos requeridos y formato simple).
- 🧭 **Navegación** mediante **ventanas con Swing** (menú principal + módulos).

> Objetivo académico: cumplir los requisitos del PDF (GUI, MySQL CRUD, Login, **POO con clase abstracta + subclases + polimorfismo**, **MVC**, validaciones y documentación).

---

## 🧩 Módulos principales

- **Login** 🔐  
- **Pacientes** 👤 (CRUD)
- **Doctores** 🩺 (CRUD)
- **Citas** 📅 (crear / reprogramar / cancelar)

---

## 🧠 Arquitectura y conceptos clave

- **MVC**:  
  `view/` (ventanas Swing) · `controller/` (lógica de interacción) · `dao/` (JDBC y consultas) · `model/` (entidades).
- **POO**:  
  `Persona` (**clase abstracta**) ⇒ `Doctor` y `Paciente` (**subclases**) con **polimorfismo** (p.ej., `mostrarInfo()`).
- **Base de datos relacional (MySQL)**:  
  Tablas `usuarios`, `pacientes`, `doctores`, `citas` con **llaves foráneas**.
- **Validaciones**:  
  Campos obligatorios y formatos simples (ej. email básico y fecha/hora válida).

---

## 🛠️ Tecnologías

- **Java SE 8+** (recomendado **17**)
- **Swing** (GUI)
- **MySQL/MariaDB** + **mysql-connector-j**
- **Maven** (build)
- IDE: **NetBeans 12+** (entrega) / **VS Code** (desarrollo)

---

## 🚀 Flujo típico de uso

1. El usuario **inicia sesión**.
2. Desde el **menú principal**, abre **Pacientes** o **Doctores** para crear registros.
3. En **Citas**, selecciona **Doctor + Paciente + Fecha/Hora** y guarda.
4. Puede **editar** o **cancelar** citas cuando sea necesario.





