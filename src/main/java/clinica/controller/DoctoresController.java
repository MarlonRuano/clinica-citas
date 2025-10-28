package clinica.controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clinica.dao.DoctorDao;
import clinica.model.Doctor;
import clinica.view.DoctoresFrame;

public class DoctoresController {
    private final DoctoresFrame view;
    private final DoctorDao dao = new DoctorDao();

    public DoctoresController(DoctoresFrame v){
        this.view = v;
        init();
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
                m.addRow(new Object[]{
                    d.getId(), d.getNombres(), d.getApellidos(),
                    d.getColegiado(), d.getTelefono(), d.getEmail()
                });
            }
            limpiarFormulario();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, "Error al listar: " + ex.getMessage());
        }
    }

    // ---- Validaciones básicas ----
    private boolean isDigits(String s) { return s != null && s.matches("\\d+"); }
    private boolean isEmailBasic(String s) { return s != null && s.contains("@"); }

    private void guardar(){
        String nombres = view.txtNombres.getText().trim();
        String apellidos = view.txtApellidos.getText().trim();
        if (nombres.isEmpty() || apellidos.isEmpty()){
            JOptionPane.showMessageDialog(view, "Nombres y Apellidos son obligatorios");
            return;
        }

        String emailV = view.txtEmail.getText().trim();
        if (!emailV.isEmpty() && !isEmailBasic(emailV)) {
            JOptionPane.showMessageDialog(view, "Email inválido (debe contener @).");
            return;
        }
        String telV = view.txtTelefono.getText().trim();
        if (!telV.isEmpty() && !isDigits(telV)) {
            JOptionPane.showMessageDialog(view, "Teléfono inválido (solo dígitos).");
            return;
        }

        Doctor d = new Doctor();
        d.setNombres(nombres);
        d.setApellidos(apellidos);
        String colegiado = view.txtColegiado.getText().trim();
        d.setColegiado(colegiado.isEmpty() ? null : colegiado);
        d.setTelefono(telV.isEmpty() ? null : telV);
        d.setEmail(emailV.isEmpty() ? null : emailV);

        String idTxt = view.txtId.getText().trim();
        boolean esEdicion = !idTxt.isEmpty();
        if (esEdicion){
            try {
                d.setId(Integer.parseInt(idTxt));
            } catch (NumberFormatException ex){
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
            JOptionPane.showMessageDialog(view, (esEdicion ? "Error al actualizar: " : "Error al crear: ") + ex.getMessage());
        }
    }

    private void cargarDesdeSeleccion(){
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        Object id   = view.table.getValueAt(row,0);
        Object nom  = view.table.getValueAt(row,1);
        Object ape  = view.table.getValueAt(row,2);
        Object colg = view.table.getValueAt(row,3);
        Object tel  = view.table.getValueAt(row,4);
        Object mail = view.table.getValueAt(row,5);

        view.txtId.setText(id   == null ? "" : String.valueOf(id));
        view.txtNombres.setText(nom == null ? "" : String.valueOf(nom));
        view.txtApellidos.setText(ape == null ? "" : String.valueOf(ape));
        view.txtColegiado.setText(colg == null ? "" : String.valueOf(colg));
        view.txtTelefono.setText(tel == null ? "" : String.valueOf(tel));
        view.txtEmail.setText(mail == null ? "" : String.valueOf(mail));
    }
    private Integer parseNullableInt(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        try { return Integer.valueOf(s); } catch (NumberFormatException e) { return null; }
    }

    private int toInt(Object o) {
        if (o == null) throw new NumberFormatException("null");
        if (o instanceof Number n) return n.intValue();
        return Integer.parseInt(String.valueOf(o));
    }

    private void eliminar(){
        // 1) Intentar obtener ID del formulario
        Integer id = parseNullableInt(view.txtId.getText());

        // 2) Si no hay, tomarlo de la fila seleccionada
        if (id == null) {
            int row = view.table.getSelectedRow();
            if (row >= 0) {
                try { id = toInt(view.table.getValueAt(row, 0)); }
                catch (NumberFormatException ignore) { /* seguirá nulo */ }
            }
        }

        // 3) Validar
        if (id == null){
            JOptionPane.showMessageDialog(view, "Seleccione un doctor o cargue uno en el formulario");
            return;
        }

        // 4) Confirmar y eliminar
        int r = JOptionPane.showConfirmDialog(
            view, "¿Eliminar doctor ID " + id + "?", "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
        if (r != JOptionPane.YES_OPTION) return;

        try {
            dao.eliminar(id);
            JOptionPane.showMessageDialog(view, "Doctor eliminado");
            refrescar();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, "Error al eliminar: " + ex.getMessage());
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
                cargarDesdeSeleccion();
            }
        };
    }
}
