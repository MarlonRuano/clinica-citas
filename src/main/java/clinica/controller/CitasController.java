package clinica.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import clinica.dao.CitaDao;
import clinica.model.Cita;
import clinica.view.CitasFrame;

public class CitasController {
    private final CitasFrame view;
    private final CitaDao dao = new CitaDao();

    public CitasController(CitasFrame v){
        this.view = v;
        init();
    }

    private void init(){
        view.btnRefrescar.addActionListener(e -> refrescar());
        view.btnNueva.addActionListener(e -> crear());
        view.btnEditar.addActionListener(e -> reprogramar());
        view.btnCancelar.addActionListener(e -> cancelar());
        refrescar();
    }

    private void refrescar(){
        try {
            List<Cita> data = dao.listar();
            DefaultTableModel m = (DefaultTableModel) view.table.getModel();
            m.setRowCount(0);
            for (Cita c : data){
                m.addRow(new Object[]{
                    c.getId(), c.getDoctorId(), c.getPacienteId(),
                    c.getFechaHora(), c.getMotivo(), c.getEstado()
                });
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, "Error al listar: " + ex.getMessage());
        }
    }

    // ---------- Utilidades ----------
    private boolean isValidEstado(String s) {
        return s != null && (s.equals("PROGRAMADA") || s.equals("ATENDIDA") || s.equals("CANCELADA"));
    }
    private LocalDateTime parseFecha(String raw) {
        if (raw == null) return null;
        raw = raw.trim();
        if (raw.isEmpty()) return null;
        String norm = raw.replace(' ', 'T'); // acepta "YYYY-MM-DD HH:MM" o "YYYY-MM-DDTHH:MM"
        try {
            return LocalDateTime.parse(norm);
        } catch (DateTimeParseException ex) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd['T']HH:mm");
                return LocalDateTime.parse(norm, f);
            } catch (DateTimeParseException ex2) {
                return null;
            }
        }
    }
    private int toInt(Object o) {
        if (o == null) throw new NumberFormatException("null");
        if (o instanceof Number n) return n.intValue();
        return Integer.parseInt(String.valueOf(o));
    }

    private void crear(){
        Integer doc = inputInt("Doctor ID:");                     if (doc == null) return;
        Integer pac = inputInt("Paciente ID:");                   if (pac == null) return;
        String fechaStr = input("Fecha y hora (YYYY-MM-DDTHH:MM):"); if (fechaStr == null) return;

        LocalDateTime fh = parseFecha(fechaStr);
        if (fh == null) { JOptionPane.showMessageDialog(view, "Formato de fecha inválido"); return; }

        String motivo = input("Motivo (opcional):");

        if (doc <= 0 || pac <= 0) {
            JOptionPane.showMessageDialog(view, "IDs de Doctor/Paciente deben ser positivos.");
            return;
        }

        Cita c = new Cita();
        c.setDoctorId(doc);
        c.setPacienteId(pac);
        c.setFechaHora(fh);
        c.setMotivo(motivo);
        c.setEstado("PROGRAMADA");

        try {
            dao.crear(c);
            JOptionPane.showMessageDialog(view, "Cita creada");
            refrescar();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, "Error al crear: " + ex.getMessage());
        }
    }

    private void reprogramar(){
        int row = view.table.getSelectedRow();
        if (row < 0){ JOptionPane.showMessageDialog(view, "Seleccione una cita"); return; }

        Cita c = new Cita();
        try {
            c.setId(toInt(view.table.getValueAt(row, 0)));
            c.setDoctorId(toInt(view.table.getValueAt(row, 1)));
            c.setPacienteId(toInt(view.table.getValueAt(row, 2)));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "ID inválido en la fila seleccionada");
            return;
        }

        String fechaDefault = String.valueOf(view.table.getValueAt(row, 3));
        String fechaStr = input("Nueva fecha (YYYY-MM-DDTHH:MM):", fechaDefault);
        if (fechaStr == null) return;

        LocalDateTime fh = parseFecha(fechaStr);
        if (fh == null) { JOptionPane.showMessageDialog(view, "Formato de fecha inválido"); return; }

        String motivo = input("Motivo:", String.valueOf(view.table.getValueAt(row, 4)));
        String estIn  = input("Estado (PROGRAMADA/ATENDIDA/CANCELADA):",
        String.valueOf(view.table.getValueAt(row, 5)));
        if (estIn == null) return;

        String estado = estIn.trim().toUpperCase();
        if (!isValidEstado(estado)) {
            JOptionPane.showMessageDialog(view, "Estado inválido.");
            return;
        }

        c.setFechaHora(fh);
        c.setMotivo(motivo);
        c.setEstado(estado);

        try {
            dao.actualizar(c);
            JOptionPane.showMessageDialog(view, "Cita actualizada");
            refrescar();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void cancelar(){
        int row = view.table.getSelectedRow();
        if (row < 0){ JOptionPane.showMessageDialog(view, "Seleccione una cita"); return; }

        int id;
        try { id = toInt(view.table.getValueAt(row, 0)); }
        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(view, "ID inválido"); return; }

        int confirm = JOptionPane.showConfirmDialog(
            view, "¿Cancelar cita ID " + id + "?", "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            dao.eliminar(id);
            JOptionPane.showMessageDialog(view, "Cita cancelada");
            refrescar();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, "Error al cancelar: " + ex.getMessage());
        }
    }
    private String input(String label){
        String v = JOptionPane.showInputDialog(view, label);
        return v != null && v.isBlank() ? null : v;
    }
    private String input(String label, String def){
        String v = (String) JOptionPane.showInputDialog(
            view, label, "Editar",
            JOptionPane.PLAIN_MESSAGE, null, null, def
        );
        return v != null && v.isBlank() ? null : v;
    }
    private Integer inputInt(String label){
        String s = input(label);
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(view, "Número inválido");
            return null;
        }
    }
}
