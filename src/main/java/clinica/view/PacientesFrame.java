package clinica.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PacientesFrame extends JFrame {
    public final JTable table = new JTable();

    // Botones que se agregaron
    public final JButton btnNuevo = new JButton("Nuevo");
    public final JButton btnEliminar = new JButton("Eliminar");
    public final JButton btnRefrescar = new JButton("Refrescar");
    public final JButton btnGuardar = new JButton("Guardar");

    // Campos para llenar datos
    public final JTextField txtId = new JTextField(5);
    public final JTextField txtNombres = new JTextField(15);
    public final JTextField txtApellidos = new JTextField(15);
    public final JTextField txtDpi = new JTextField(12);
    public final JTextField txtTelefono = new JTextField(12);
    public final JTextField txtEmail = new JTextField(18);

    public PacientesFrame() {
        super("Gestión de Pacientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        table.setModel(new DefaultTableModel(new Object[]{"ID","Nombres","Apellidos","DPI","Teléfono","Email"},0){
            @Override public boolean isCellEditable(int r, int c){ return false; }
        });

        // Formulario superior 
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        // Campos de etiquetas y textos
        JLabel lId = new JLabel("ID:"); lId.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lNom = new JLabel("Nombres:"); lNom.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lApe = new JLabel("Apellidos:"); lApe.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lDpi = new JLabel("DPI:"); lDpi.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lTel = new JLabel("Teléfono:"); lTel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lMail = new JLabel("Email:"); lMail.setHorizontalAlignment(SwingConstants.RIGHT);

        txtId.setEditable(false);
        //Orden de los campos en la interfaz
        // Fila 0: ID | Nombres | Apellidos
        gbc.gridy = 0;
        // Columna 0: etiqueta ID
        gbc.gridx = 0; gbc.weightx = 0; formPanel.add(lId, gbc);
        // Columna 1: campo ID
        gbc.gridx = 1; gbc.weightx = 0.2; formPanel.add(txtId, gbc);
        // Columna 2: etiqueta Nombres
        gbc.gridx = 2; gbc.weightx = 0; formPanel.add(lNom, gbc);
        // Columna 3: campo Nombres
        gbc.gridx = 3; gbc.weightx = 0.4; formPanel.add(txtNombres, gbc);
        // Columna 4: etiqueta Apellidos
        gbc.gridx = 4; gbc.weightx = 0; formPanel.add(lApe, gbc);
        // Columna 5: campo Apellidos
        gbc.gridx = 5; gbc.weightx = 0.4; formPanel.add(txtApellidos, gbc);

        // Fila 1: DPI | Teléfono | Email
        gbc.gridy = 1;
        gbc.gridx = 0; gbc.weightx = 0; formPanel.add(lDpi, gbc);
        gbc.gridx = 1; gbc.weightx = 0.2; formPanel.add(txtDpi, gbc);
        gbc.gridx = 2; gbc.weightx = 0; formPanel.add(lTel, gbc);
        gbc.gridx = 3; gbc.weightx = 0.4; formPanel.add(txtTelefono, gbc);
        gbc.gridx = 4; gbc.weightx = 0; formPanel.add(lMail, gbc);
        gbc.gridx = 5; gbc.weightx = 0.4; formPanel.add(txtEmail, gbc);

        // Configuracion de los botoness
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(btnNuevo);
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnRefrescar);

        setLayout(new BorderLayout());
        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());
        north.add(formPanel, BorderLayout.NORTH);
        north.add(buttonPanel, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        setSize(750, 450);
        setLocationRelativeTo(null);
    }
}
