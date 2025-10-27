package clinica.view;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    public MainMenuFrame() {
        super("Menú Principal - Clínica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnPacientes = new JButton("Pacientes");
        JButton btnDoctores = new JButton("Doctores");
        JButton btnCitas = new JButton("Citas");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnPacientes);
        panel.add(btnDoctores);
        panel.add(btnCitas);
        panel.add(btnSalir);

        add(panel);
        setSize(400, 250);
        setLocationRelativeTo(null);

        btnPacientes.addActionListener(e -> {
            clinica.view.PacientesFrame f = new clinica.view.PacientesFrame();
            new clinica.controller.PacientesController(f);
            f.setVisible(true);
        });
        btnDoctores.addActionListener(e -> {
            clinica.view.DoctoresFrame f = new clinica.view.DoctoresFrame();
            new clinica.controller.DoctoresController(f);
            f.setVisible(true);
        });
        btnCitas.addActionListener(e -> {
            clinica.view.CitasFrame f = new clinica.view.CitasFrame();
            new clinica.controller.CitasController(f);
            f.setVisible(true);
        });
        btnSalir.addActionListener(e -> System.exit(0));
    }
}
