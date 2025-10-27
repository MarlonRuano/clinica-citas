package clinica.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import clinica.model.Cita;
import clinica.model.Doctor;
import clinica.model.Paciente;
import java.util.List;

public class CitaFormDialog extends JDialog {
    private final JComboBox<String> cmbDoctor = new JComboBox<>();
    private final JComboBox<String> cmbPaciente = new JComboBox<>();
    private final JTextField txtFecha = new JTextField(15);
    private final JTextField txtHora = new JTextField(8);
    private final JTextArea txtMotivo = new JTextArea(3, 20);
    private final JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"PROGRAMADA", "ATENDIDA", "CANCELADA"});
    private boolean aceptado = false;
    
    private List<Doctor> doctores;
    private List<Paciente> pacientes;

    public CitaFormDialog(JFrame parent, String titulo, List<Doctor> doctores, List<Paciente> pacientes) {
        super(parent, titulo, true);
        this.doctores = doctores;
        this.pacientes = pacientes;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Título
        JLabel lblTitulo = new JLabel("📅 Datos de la Cita", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(155, 89, 182));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // Formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Doctor
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Doctor: *"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        cargarDoctores();
        formPanel.add(cmbDoctor, gbc);

        // Paciente
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Paciente: *"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        cargarPacientes();
        formPanel.add(cmbPaciente, gbc);

        // Fecha
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Fecha: *"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        JPanel pFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pFecha.setBackground(Color.WHITE);
        txtFecha.setToolTipText("Formato: YYYY-MM-DD");
        pFecha.add(txtFecha);
        pFecha.add(new JLabel("(YYYY-MM-DD)"));
        formPanel.add(pFecha, gbc);

        // Hora
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Hora: *"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        JPanel pHora = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pHora.setBackground(Color.WHITE);
        txtHora.setToolTipText("Formato: HH:MM");
        pHora.add(txtHora);
        pHora.add(new JLabel("(HH:MM)"));
        formPanel.add(pHora, gbc);

        // Motivo
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Motivo:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);
        JScrollPane scrollMotivo = new JScrollPane(txtMotivo);
        scrollMotivo.setPreferredSize(new Dimension(250, 60));
        formPanel.add(scrollMotivo, gbc);

        // Estado
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(new JLabel("Estado: *"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(cmbEstado, gbc);

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

    private void cargarDoctores() {
        cmbDoctor.removeAllItems();
        for (Doctor d : doctores) {
            cmbDoctor.addItem(d.getId() + " - " + d.nombreCompleto());
        }
    }

    private void cargarPacientes() {
        cmbPaciente.removeAllItems();
        for (Paciente p : pacientes) {
            cmbPaciente.addItem(p.getId() + " - " + p.nombreCompleto());
        }
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
        if (cmbDoctor.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un doctor", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (cmbPaciente.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtFecha.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La fecha es obligatoria", "Validación", JOptionPane.WARNING_MESSAGE);
            txtFecha.requestFocus();
            return false;
        }
        if (txtHora.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La hora es obligatoria", "Validación", JOptionPane.WARNING_MESSAGE);
            txtHora.requestFocus();
            return false;
        }
        try {
            getFechaHora();
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha/hora inválido\nFecha: YYYY-MM-DD\nHora: HH:MM", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void cargarCita(Cita c, int indexDoctor, int indexPaciente) {
        if (indexDoctor >= 0) cmbDoctor.setSelectedIndex(indexDoctor);
        if (indexPaciente >= 0) cmbPaciente.setSelectedIndex(indexPaciente);
        
        DateTimeFormatter fmtFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter fmtHora = DateTimeFormatter.ofPattern("HH:mm");
        txtFecha.setText(c.getFechaHora().format(fmtFecha));
        txtHora.setText(c.getFechaHora().format(fmtHora));
        txtMotivo.setText(c.getMotivo());
        cmbEstado.setSelectedItem(c.getEstado());
    }

    public Cita getCita() {
        Cita c = new Cita();
        String selDoctor = (String) cmbDoctor.getSelectedItem();
        c.setDoctorId(Integer.parseInt(selDoctor.split(" - ")[0]));
        
        String selPaciente = (String) cmbPaciente.getSelectedItem();
        c.setPacienteId(Integer.parseInt(selPaciente.split(" - ")[0]));
        
        c.setFechaHora(getFechaHora());
        c.setMotivo(txtMotivo.getText().trim().isEmpty() ? null : txtMotivo.getText().trim());
        c.setEstado((String) cmbEstado.getSelectedItem());
        return c;
    }

    private LocalDateTime getFechaHora() {
        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();
        return LocalDateTime.parse(fecha + "T" + hora);
    }

    public boolean isAceptado() {
        return aceptado;
    }
}
