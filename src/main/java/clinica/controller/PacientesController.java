package clinica.controller;

import clinica.dao.PacienteDao;
import clinica.model.Paciente;
import clinica.view.PacientesFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;
import java.sql.SQLException;
import java.util.List;

public class PacientesController {
    private final PacientesFrame view;
    private final PacienteDao dao = new PacienteDao();

    public PacientesController(PacientesFrame v){
        this.view = v;
        init();
    }

    private void init(){
        view.btnRefrescar.addActionListener(e -> refrescar());
    view.btnNuevo.addActionListener(e -> limpiarFormulario());
    view.btnGuardar.addActionListener(e -> guardar());
        view.btnEliminar.addActionListener(e -> eliminar());

        // Al seleccionar en la tabla, cargar datos en el formulario
        view.table.getSelectionModel().addListSelectionListener(tablaSelectionListener());
        refrescar();
    }

    private void refrescar(){
        try {
            List<Paciente> data = dao.listar();
            DefaultTableModel m = (DefaultTableModel) view.table.getModel();
            m.setRowCount(0);
            for (Paciente p : data){
                m.addRow(new Object[]{p.getId(), p.getNombres(), p.getApellidos(), p.getDpi(), p.getTelefono(), p.getEmail()});
            }
            limpiarFormulario();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, "Error al listar: " + ex.getMessage());
        }
    }

    private void guardar(){
        // Validar requeridos
        String nombres = view.txtNombres.getText().trim();
        String apellidos = view.txtApellidos.getText().trim();
        if (nombres.isEmpty() || apellidos.isEmpty()){
            JOptionPane.showMessageDialog(view, "Nombres y Apellidos son obligatorios");
            return;
        }

        Paciente p = new Paciente();
        p.setNombres(nombres);
        p.setApellidos(apellidos);
        String dpi = view.txtDpi.getText().trim();
        String tel = view.txtTelefono.getText().trim();
        String mail = view.txtEmail.getText().trim();
        p.setDpi(dpi.isEmpty() ? null : dpi);
        p.setTelefono(tel.isEmpty() ? null : tel);
        p.setEmail(mail.isEmpty() ? null : mail);

        String idTxt = view.txtId.getText().trim();
        boolean esEdicion = !idTxt.isEmpty();
        if (esEdicion){
            try {
                p.setId(Integer.parseInt(idTxt));
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(view, "ID inválido");
                return;
            }
        }

        try {
            if (esEdicion){
                dao.actualizar(p);
                JOptionPane.showMessageDialog(view, "Paciente actualizado");
            } else {
                dao.crear(p);
                JOptionPane.showMessageDialog(view, "Paciente creado");
            }
            refrescar();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, (esEdicion?"Error al actualizar: ":"Error al crear: ") + ex.getMessage());
        }
    }

    private void cargarDesdeSeleccion(){
        int row = view.table.getSelectedRow();
        if (row < 0){ 
            JOptionPane.showMessageDialog(view, "Seleccione un paciente");
            return;
        }
        view.txtId.setText(String.valueOf(view.table.getValueAt(row,0)));
        view.txtNombres.setText(String.valueOf(view.table.getValueAt(row,1)));
        view.txtApellidos.setText(String.valueOf(view.table.getValueAt(row,2)));
        Object dpi = view.table.getValueAt(row,3);
        Object tel = view.table.getValueAt(row,4);
        Object mail = view.table.getValueAt(row,5);
        view.txtDpi.setText(dpi == null ? "" : String.valueOf(dpi));
        view.txtTelefono.setText(tel == null ? "" : String.valueOf(tel));
        view.txtEmail.setText(mail == null ? "" : String.valueOf(mail));
    }

    private void eliminar(){
        Integer id = null;
        String idTxt = view.txtId.getText().trim();
        if (!idTxt.isEmpty()){
            try { id = Integer.parseInt(idTxt); } catch (NumberFormatException ignore) {}
        }
        if (id == null){
            int row = view.table.getSelectedRow();
            if (row >= 0){ id = (int) view.table.getValueAt(row,0); }
        }
        if (id == null){
            JOptionPane.showMessageDialog(view, "Seleccione un paciente o cargue uno en el formulario");
            return;
        }
        
        int respuesta = JOptionPane.showConfirmDialog(view, "¿Eliminar paciente ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION){
            try { 
                dao.eliminar(id);
                JOptionPane.showMessageDialog(view, "Paciente eliminado");
                refrescar();
            } catch (SQLException ex){ 
                JOptionPane.showMessageDialog(view, "Error al eliminar: " + ex.getMessage());
            }
        }
    }

    private void limpiarFormulario(){
        view.txtId.setText("");
        view.txtNombres.setText("");
        view.txtApellidos.setText("");
        view.txtDpi.setText("");
        view.txtTelefono.setText("");
        view.txtEmail.setText("");
        view.table.clearSelection();
    }

    private ListSelectionListener tablaSelectionListener(){
        return e -> {
            if (!e.getValueIsAdjusting()){
                int row = view.table.getSelectedRow();
                if (row >= 0){
                    view.txtId.setText(String.valueOf(view.table.getValueAt(row,0)));
                    view.txtNombres.setText(String.valueOf(view.table.getValueAt(row,1)));
                    view.txtApellidos.setText(String.valueOf(view.table.getValueAt(row,2)));
                    Object dpi = view.table.getValueAt(row,3);
                    Object tel = view.table.getValueAt(row,4);
                    Object mail = view.table.getValueAt(row,5);
                    view.txtDpi.setText(dpi == null ? "" : String.valueOf(dpi));
                    view.txtTelefono.setText(tel == null ? "" : String.valueOf(tel));
                    view.txtEmail.setText(mail == null ? "" : String.valueOf(mail));
                } else {
                    // si no hay selección, no hacemos nada; el formulario queda como está
                }
            }
        };
    }
}
