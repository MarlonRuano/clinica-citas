package clinica.model;

public class Doctor extends Persona {
    private String colegiado;

    public String getColegiado() { return colegiado; }
    public void setColegiado(String colegiado) { this.colegiado = colegiado; }

    @Override
    public String mostrarInfo() {
        return "Doctor: " + nombreCompleto() + (colegiado != null ? " (Colegiado: " + colegiado + ")" : "");
    }
}
