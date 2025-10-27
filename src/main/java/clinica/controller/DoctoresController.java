package clinica.controller;

import clinica.dao.DoctorDao;
import clinica.model.Doctor;
import clinica.view.DoctoresFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;
import java.sql.SQLException;
import java.util.List;

public class DoctoresController {
    private final DoctoresFrame view;
    private final DoctorDao dao = new DoctorDao();

    public DoctoresController(DoctoresFrame v){
        this.view = v; init();
    }

    private void init(){
        view.btnRefrescar.addActionListener(e -> refrescar());
    view.btnNuevo.addActionListener(e -> limpiarFormulario());
    view.btnGuardar.addActionListener(e -> guardar());
    view.btnEliminar.addActionListener(e -> eliminar());

        view.table.getSelectionModel().addListSelectionListener(tablaSelectionListener());
        refrescar();
    }

    private void refrescar(){
        try {
            List<Doctor> data = dao.listar();
            DefaultTableModel m = (DefaultTableModel) view.table.getModel();
            m.setRowCount(0);
            for (Doctor d : data){
                m.addRow(new Object[]{d.getId(), d.getNombres(), d.getApellidos(), d.getColegiado(), d.getTelefono(), d.getEmail()});
            }
            limpiarFormulario();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, "Error al listar: " + ex.getMessage());
        }
    }

    private void guardar(){
        String nombres = view.txtNombres.getText().trim();
        String apellidos = view.txtApellidos.getText().trim();
        if (nombres.isEmpty() || apellidos.isEmpty()){
            JOptionPane.showMessageDialog(view, "Nombres y Apellidos son obligatorios");
            return;
        }

        Doctor d = new Doctor();
        d.setNombres(nombres);
        d.setApellidos(apellidos);
        String colegiado = view.txtColegiado.getText().trim();
        String tel = view.txtTelefono.getText().trim();
        String mail = view.txtEmail.getText().trim();
        d.setColegiado(colegiado.isEmpty() ? null : colegiado);
        d.setTelefono(tel.isEmpty() ? null : tel);
        d.setEmail(mail.isEmpty() ? null : mail);

        String idTxt = view.txtId.getText().trim();
        boolean esEdicion = !idTxt.isEmpty();
        if (esEdicion){
            try { d.setId(Integer.parseInt(idTxt)); } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(view, "ID inválido");
                return;
            }
        }

        try {
            if (esEdicion){
                dao.actualizar(d);
                JOptionPane.showMessageDialog(view, "Doctor actualizado");
            } else {
                dao.crear(d);
                JOptionPane.showMessageDialog(view, "Doctor creado");
            }
            refrescar();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, (esEdicion?"Error al actualizar: ":"Error al crear: ") + ex.getMessage());
        }
    }

    private void cargarDesdeSeleccion(){
        int row = view.table.getSelectedRow();
        if (row < 0){ 
            JOptionPane.showMessageDialog(view, "Seleccione un doctor");
            return;
        }
        view.txtId.setText(String.valueOf(view.table.getValueAt(row,0)));
        view.txtNombres.setText(String.valueOf(view.table.getValueAt(row,1)));
        view.txtApellidos.setText(String.valueOf(view.table.getValueAt(row,2)));
        Object colg = view.table.getValueAt(row,3);
        Object tel = view.table.getValueAt(row,4);
        Object mail = view.table.getValueAt(row,5);
        view.txtColegiado.setText(colg == null ? "" : String.valueOf(colg));
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
            JOptionPane.showMessageDialog(view, "Seleccione un doctor o cargue uno en el formulario");
            return;
        }
        
        int respuesta = JOptionPane.showConfirmDialog(view, "¿Eliminar doctor ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION){
            try { 
                dao.eliminar(id);
                JOptionPane.showMessageDialog(view, "Doctor eliminado");
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
        view.txtColegiado.setText("");
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
                    Object colg = view.table.getValueAt(row,3);
                    Object tel = view.table.getValueAt(row,4);
                    Object mail = view.table.getValueAt(row,5);
                    view.txtColegiado.setText(colg == null ? "" : String.valueOf(colg));
                    view.txtTelefono.setText(tel == null ? "" : String.valueOf(tel));
                    view.txtEmail.setText(mail == null ? "" : String.valueOf(mail));
                }
            }
        };
    }
}
