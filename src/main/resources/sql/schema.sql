
CREATE DATABASE IF NOT EXISTS clinica
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE clinica;

-- -------------------- USUARIOS ----------------------------
CREATE TABLE IF NOT EXISTS usuarios (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password_hash VARCHAR(255) NOT NULL,
rol ENUM('ADMIN','RECEPCION') DEFAULT 'RECEPCION'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------- DOCTORES ----------------------------
CREATE TABLE IF NOT EXISTS doctores (
id INT AUTO_INCREMENT PRIMARY KEY,
nombres   VARCHAR(80) NOT NULL,
apellidos VARCHAR(80) NOT NULL,
colegiado VARCHAR(30),
telefono  VARCHAR(20),
email     VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------- PACIENTES ---------------------------
CREATE TABLE IF NOT EXISTS pacientes (
id INT AUTO_INCREMENT PRIMARY KEY,
nombres   VARCHAR(80) NOT NULL,
apellidos VARCHAR(80) NOT NULL,
dpi       VARCHAR(20),
telefono  VARCHAR(20),
email     VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------- CITAS -----------------------------
CREATE TABLE IF NOT EXISTS citas (
id INT AUTO_INCREMENT PRIMARY KEY,
doctor_id   INT NOT NULL,
paciente_id INT NOT NULL,
fecha_hora  DATETIME NOT NULL,
motivo      VARCHAR(200),
estado ENUM('PROGRAMADA','ATENDIDA','CANCELADA') DEFAULT 'PROGRAMADA',
CONSTRAINT fk_citas_doctor   FOREIGN KEY (doctor_id)   REFERENCES doctores(id),
CONSTRAINT fk_citas_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- (Opcionales) índices para FK
CREATE INDEX IF NOT EXISTS idx_citas_doctor   ON citas(doctor_id);
CREATE INDEX IF NOT EXISTS idx_citas_paciente ON citas(paciente_id);

-- ======================== INICIO DEMO ========================
START TRANSACTION;

-- 1) Usuario admin
INSERT INTO usuarios (username, password_hash, rol)
SELECT 'admin', 'admin', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE username='admin');

-- 2) Doctores (evita duplicados por email)
INSERT INTO doctores (nombres, apellidos, colegiado, telefono, email)
SELECT 'Ana', 'López', 'COL-123', '55551111', 'ana@demo.com'
WHERE NOT EXISTS (SELECT 1 FROM doctores WHERE email='ana@demo.com');

INSERT INTO doctores (nombres, apellidos, colegiado, telefono, email)
SELECT 'Luis', 'Pérez', 'COL-456', '55552222', 'luis@demo.com'
WHERE NOT EXISTS (SELECT 1 FROM doctores WHERE email='luis@demo.com');

-- 3) Pacientes (evita duplicados por email)
INSERT INTO pacientes (nombres, apellidos, dpi, telefono, email)
SELECT 'María', 'García', '1002003000101', '55553333', 'maria@demo.com'
WHERE NOT EXISTS (SELECT 1 FROM pacientes WHERE email='maria@demo.com');

INSERT INTO pacientes (nombres, apellidos, dpi, telefono, email)
SELECT 'Carlos', 'Ruiz', '2003004000202', '55554444', 'carlos@demo.com'
WHERE NOT EXISTS (SELECT 1 FROM pacientes WHERE email='carlos@demo.com');

-- 4) Cita de ejemplo (mañana) evitando duplicado del mismo par y fecha
INSERT INTO citas (doctor_id, paciente_id, fecha_hora, motivo, estado)
SELECT d.id, p.id, NOW() + INTERVAL 1 DAY, 'Chequeo general', 'PROGRAMADA'
FROM doctores d
JOIN pacientes p
WHERE d.email='ana@demo.com' AND p.email='maria@demo.com'
    AND NOT EXISTS (
    SELECT 1 FROM citas
    WHERE doctor_id = d.id
    AND paciente_id = p.id
    AND DATE(fecha_hora) = DATE(NOW() + INTERVAL 1 DAY)
    )
LIMIT 1;

COMMIT;
-- ======================== FIN DEMO ========================
