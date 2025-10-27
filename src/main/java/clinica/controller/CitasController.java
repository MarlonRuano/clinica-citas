package clinica.controller;

import clinica.dao.CitaDao;
import clinica.model.Cita;
import clinica.view.CitasFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class CitasController {
    private final CitasFrame view;
    private final CitaDao dao = new CitaDao();

    public CitasController(CitasFrame v){ this.view = v; init(); }

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
                m.addRow(new Object[]{c.getId(), c.getDoctorId(), c.getPacienteId(), c.getFechaHora(), c.getMotivo(), c.getEstado()});
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, "Error al listar: " + ex.getMessage());
        }
    }

    private void crear(){
        Integer doc = inputInt("Doctor ID:"); if (doc==null) return;
        Integer pac = inputInt("Paciente ID:"); if (pac==null) return;
        String fecha = input("Fecha y hora (YYYY-MM-DDTHH:MM):"); if (fecha==null) return;
        String motivo = input("Motivo (opcional):");
        Cita c = new Cita();
        c.setDoctorId(doc);
        c.setPacienteId(pac);
        try { c.setFechaHora(LocalDateTime.parse(fecha)); }
        catch (Exception ex) { JOptionPane.showMessageDialog(view, "Formato de fecha inválido"); return; }
        c.setMotivo(motivo);
        c.setEstado("PROGRAMADA");
        try { dao.crear(c); refrescar(); } catch (SQLException ex){ JOptionPane.showMessageDialog(view, "Error al crear: " + ex.getMessage()); }
    }

    private void reprogramar(){
        int row = view.table.getSelectedRow();
        if (row < 0){ JOptionPane.showMessageDialog(view, "Seleccione una cita"); return; }
        Cita c = new Cita();
        c.setId((int)view.table.getValueAt(row,0));
        c.setDoctorId((Integer)view.table.getValueAt(row,1));
        c.setPacienteId((Integer)view.table.getValueAt(row,2));
        String fecha = input("Nueva fecha (YYYY-MM-DDTHH:MM):", String.valueOf(view.table.getValueAt(row,3)));
        if (fecha==null) return;
        try { c.setFechaHora(LocalDateTime.parse(fecha)); }
        catch (Exception ex) { JOptionPane.showMessageDialog(view, "Formato de fecha inválido"); return; }
        c.setMotivo(input("Motivo:", (String)view.table.getValueAt(row,4)));
        c.setEstado(input("Estado (PROGRAMADA/ATENDIDA/CANCELADA):", (String)view.table.getValueAt(row,5)));
        try { dao.actualizar(c); refrescar(); } catch (SQLException ex){ JOptionPane.showMessageDialog(view, "Error al actualizar: " + ex.getMessage()); }
    }

    private void cancelar(){
        int row = view.table.getSelectedRow();
        if (row < 0){ JOptionPane.showMessageDialog(view, "Seleccione una cita"); return; }
        int id = (int) view.table.getValueAt(row,0);
        if (JOptionPane.showConfirmDialog(view, "¿Cancelar cita ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            try { dao.eliminar(id); refrescar(); } catch (SQLException ex){ JOptionPane.showMessageDialog(view, "Error al cancelar: " + ex.getMessage()); }
        }
    }

    private String input(String label){
        String v = JOptionPane.showInputDialog(view, label);
        return v != null && v.isBlank() ? null : v;
    }
    private String input(String label, String def){
        String v = (String)JOptionPane.showInputDialog(view, label, "Editar", JOptionPane.PLAIN_MESSAGE, null, null, def);
        return v != null && v.isBlank() ? null : v;
    }
    private Integer inputInt(String label){
        String s = input(label);
        if (s == null) return null;
        try { return Integer.parseInt(s); } catch (Exception e){ JOptionPane.showMessageDialog(view, "Número inválido"); return null; }
    }
}
