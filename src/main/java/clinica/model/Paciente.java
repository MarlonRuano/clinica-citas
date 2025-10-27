package clinica.model;

public class Paciente extends Persona {
    private String dpi;

    public String getDpi() { return dpi; }
    public void setDpi(String dpi) { this.dpi = dpi; }

    @Override
    public String mostrarInfo() {
        return "Paciente: " + nombreCompleto() + (dpi != null ? " (DPI: " + dpi + ")" : "");
    }
}
