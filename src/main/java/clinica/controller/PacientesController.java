package clinica.controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import clinica.dao.PacienteDao;
import clinica.model.Paciente;
import clinica.view.PacientesFrame;

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

        view.table.getSelectionModel().addListSelectionListener(tablaSelectionListener());

        refrescar();
    }

    private void refrescar(){
        try {
            List<Paciente> data = dao.listar();
            DefaultTableModel m = (DefaultTableModel) view.table.getModel();
            m.setRowCount(0);
            for (Paciente p : data){
                m.addRow(new Object[]{
                    p.getId(), p.getNombres(), p.getApellidos(),
                    p.getDpi(), p.getTelefono(), p.getEmail()
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
        // Requeridos mínimos
        String nombres = view.txtNombres.getText().trim();
        String apellidos = view.txtApellidos.getText().trim();
        if (nombres.isEmpty() || apellidos.isEmpty()){
            JOptionPane.showMessageDialog(view, "Nombres y Apellidos son obligatorios");
            return;
        }

        // Validaciones mínimas adicionales
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

        Paciente p = new Paciente();
        p.setNombres(nombres);
        p.setApellidos(apellidos);

        String dpi = view.txtDpi.getText().trim();
        p.setDpi(dpi.isEmpty() ? null : dpi);
        p.setTelefono(telV.isEmpty() ? null : telV);
        p.setEmail(emailV.isEmpty() ? null : emailV);

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
            JOptionPane.showMessageDialog(view, (esEdicion ? "Error al actualizar: " : "Error al crear: ") + ex.getMessage());
        }
    }

    private void cargarDesdeSeleccion(){
        int row = view.table.getSelectedRow();
        if (row < 0) return;

        Object id   = view.table.getValueAt(row,0);
        Object nom  = view.table.getValueAt(row,1);
        Object ape  = view.table.getValueAt(row,2);
        Object dpi  = view.table.getValueAt(row,3);
        Object tel  = view.table.getValueAt(row,4);
        Object mail = view.table.getValueAt(row,5);

        view.txtId.setText(id   == null ? "" : String.valueOf(id));
        view.txtNombres.setText(nom == null ? "" : String.valueOf(nom));
        view.txtApellidos.setText(ape == null ? "" : String.valueOf(ape));
        view.txtDpi.setText(dpi == null ? "" : String.valueOf(dpi));
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
        Integer id = parseNullableInt(view.txtId.getText());
        if (id == null) {
            int row = view.table.getSelectedRow();
            if (row >= 0) {
                try { id = toInt(view.table.getValueAt(row, 0)); }
                catch (NumberFormatException ignore) { /* seguirá nulo */ }
            }
        }
        if (id == null){
            JOptionPane.showMessageDialog(view, "Seleccione un paciente o cargue uno en el formulario");
            return;
        }

        int r = JOptionPane.showConfirmDialog(
            view, "¿Eliminar paciente ID " + id + "?", "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
        if (r != JOptionPane.YES_OPTION) return;

        try {
            dao.eliminar(id);
            JOptionPane.showMessageDialog(view, "Paciente eliminado");
            refrescar();
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(view, "Error al eliminar: " + ex.getMessage());
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
                cargarDesdeSeleccion();
            }
        };
    }
}
