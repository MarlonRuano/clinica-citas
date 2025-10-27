package clinica.view;

import javax.swing.*;
import java.awt.*;
import clinica.model.Paciente;
 
public class PacienteFormDialog extends JDialog {
    private final JTextField txtNombres = new JTextField(20);
    private final JTextField txtApellidos = new JTextField(20);
    private final JTextField txtDpi = new JTextField(20);
    private final JTextField txtTelefono = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private boolean aceptado = false;

    public PacienteFormDialog(JFrame parent, String titulo) {
        super(parent, titulo, true);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Título
        JLabel lblTitulo = new JLabel("👤 Datos del Paciente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(46, 204, 113));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // Formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombres
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Nombres: *"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtNombres, gbc);

        // Apellidos
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Apellidos: *"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtApellidos, gbc);

        // DPI
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("DPI:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtDpi, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtTelefono, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(txtEmail, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnAceptar = new JButton("✓ Aceptar");
        JButton btnCancelar = new JButton("✗ Cancelar");
        
        estilizarBoton(btnAceptar, new Color(46, 204, 113));
        estilizarBoton(btnCancelar, new Color(231, 76, 60));
        
        btnAceptar.addActionListener(e -> {
            if (validar()) {
                aceptado = true;
                dispose();
            }
        });
        btnCancelar.addActionListener(e -> dispose());

        buttonPanel.add(btnAceptar);
        buttonPanel.add(btnCancelar);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 35));
    }

    private boolean validar() {
        if (txtNombres.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombres.requestFocus();
            return false;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los apellidos son obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }
        return true;
    }

    public void cargarPaciente(Paciente p) {
        txtNombres.setText(p.getNombres());
        txtApellidos.setText(p.getApellidos());
        txtDpi.setText(p.getDpi());
        txtTelefono.setText(p.getTelefono());
        txtEmail.setText(p.getEmail());
    }

    public Paciente getPaciente() {
        Paciente p = new Paciente();
        p.setNombres(txtNombres.getText().trim());
        p.setApellidos(txtApellidos.getText().trim());
        p.setDpi(txtDpi.getText().trim().isEmpty() ? null : txtDpi.getText().trim());
        p.setTelefono(txtTelefono.getText().trim().isEmpty() ? null : txtTelefono.getText().trim());
        p.setEmail(txtEmail.getText().trim().isEmpty() ? null : txtEmail.getText().trim());
        return p;
    }

    public boolean isAceptado() {
        return aceptado;
    }
}
