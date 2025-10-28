# Diagrama de Clases

```mermaid
classDiagram
class Persona {
  +id:int
  +nombres:String
  +apellidos:String
  +telefono:String
  +email:String
}

class Doctor {
  +colegiado:String
}

class Paciente {
  +dpi:String
}

class Usuario {
  +username:String
  +passwordHash:String
  +rol:String
}

class Cita {
  +id:int
  +fechaHora:LocalDateTime
  +motivo:String
  +estado:Estado
}

Persona <|-- Doctor
Persona <|-- Paciente
Doctor "1" --> "0..*" Cita : atiende
Paciente "1" --> "0..*" Cita : solicita
