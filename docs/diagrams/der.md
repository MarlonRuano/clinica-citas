erDiagram
  USUARIOS {
    int id PK
    varchar username
    varchar password_hash
    enum rol
  }
  DOCTORES {
    int id PK
    varchar nombres
    varchar apellidos
    varchar colegiado
    varchar telefono
    varchar email
  }
  PACIENTES {
    int id PK
    varchar nombres
    varchar apellidos
    varchar dpi
    varchar telefono
    varchar email
  }
  CITAS {
    int id PK
    int doctor_id FK
    int paciente_id FK
    datetime fecha_hora
    varchar motivo
    enum estado
  }

  DOCTORES ||--o{ CITAS : "atiende"
  PACIENTES ||--o{ CITAS : "solicita"
