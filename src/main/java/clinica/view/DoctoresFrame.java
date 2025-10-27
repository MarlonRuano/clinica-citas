package clinica.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DoctoresFrame extends JFrame {
    public final JTable table = new JTable();
    // Botones de acciones
    public final JButton btnNuevo = new JButton("Nuevo");
    public final JButton btnEliminar = new JButton("Eliminar");
    public final JButton btnRefrescar = new JButton("Refrescar");
    public final JButton btnGuardar = new JButton("Guardar");

    // Campos del formulario (arriba)
    public final JTextField txtId = new JTextField(5);
    public final JTextField txtNombres = new JTextField(15);
    public final JTextField txtApellidos = new JTextField(15);
    public final JTextField txtColegiado = new JTextField(12);
    public final JTextField txtTelefono = new JTextField(12);
    public final JTextField txtEmail = new JTextField(18);

    public DoctoresFrame() {
        super("Gestión de Doctores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table.setModel(new DefaultTableModel(new Object[]{"ID","Nombres","Apellidos","Colegiado","Teléfono","Email"},0){
            @Override public boolean isCellEditable(int r, int c){ return false; }
        });

        // Formulario superior (alineado y parejo)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,6,4,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        JLabel lId = new JLabel("ID:"); lId.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lNom = new JLabel("Nombres:"); lNom.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lApe = new JLabel("Apellidos:"); lApe.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lCol = new JLabel("Colegiado:"); lCol.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lTel = new JLabel("Teléfono:"); lTel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lMail = new JLabel("Email:"); lMail.setHorizontalAlignment(SwingConstants.RIGHT);

        txtId.setEditable(false);

        // Fila 0: ID | Nombres | Apellidos
        gbc.gridy = 0;
        gbc.gridx = 0; gbc.weightx = 0; formPanel.add(lId, gbc);
        gbc.gridx = 1; gbc.weightx = 0.2; formPanel.add(txtId, gbc);
        gbc.gridx = 2; gbc.weightx = 0; formPanel.add(lNom, gbc);
        gbc.gridx = 3; gbc.weightx = 0.4; formPanel.add(txtNombres, gbc);
        gbc.gridx = 4; gbc.weightx = 0; formPanel.add(lApe, gbc);
        gbc.gridx = 5; gbc.weightx = 0.4; formPanel.add(txtApellidos, gbc);

        // Fila 1: Colegiado | Teléfono | Email
        gbc.gridy = 1;
        gbc.gridx = 0; gbc.weightx = 0; formPanel.add(lCol, gbc);
        gbc.gridx = 1; gbc.weightx = 0.2; formPanel.add(txtColegiado, gbc);
        gbc.gridx = 2; gbc.weightx = 0; formPanel.add(lTel, gbc);
        gbc.gridx = 3; gbc.weightx = 0.4; formPanel.add(txtTelefono, gbc);
        gbc.gridx = 4; gbc.weightx = 0; formPanel.add(lMail, gbc);
        gbc.gridx = 5; gbc.weightx = 0.4; formPanel.add(txtEmail, gbc);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(btnNuevo);
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnRefrescar);

        setLayout(new BorderLayout());
        JPanel north = new JPanel(new BorderLayout());
        north.add(formPanel, BorderLayout.NORTH);
        north.add(buttonPanel, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        setSize(750, 450);
        setLocationRelativeTo(null);
    }
}
