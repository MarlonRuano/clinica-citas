package clinica.view;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenuFrame extends JFrame {

public MainMenuFrame() {
    super("Menú Principal - Clínica");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 250);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new GridLayout(2, 2, 12, 12));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JButton btnPacientes = new JButton("Pacientes");
    JButton btnDoctores  = new JButton("Doctores");
    JButton btnCitas     = new JButton("Citas");
    JButton btnSalir     = new JButton("Salir");

    // Accesibilidad / UX
    btnPacientes.setMnemonic(KeyEvent.VK_P); // Alt+P
    btnDoctores.setMnemonic(KeyEvent.VK_D);  // Alt+D
    btnCitas.setMnemonic(KeyEvent.VK_C);     // Alt+C
    btnSalir.setMnemonic(KeyEvent.VK_S);     // Alt+S

    btnPacientes.setToolTipText("Abrir módulo de Pacientes");
    btnDoctores.setToolTipText("Abrir módulo de Doctores");
    btnCitas.setToolTipText("Abrir módulo de Citas");
    btnSalir.setToolTipText("Cerrar la aplicación");

    panel.add(btnPacientes);
    panel.add(btnDoctores);
    panel.add(btnCitas);
    panel.add(btnSalir);

    setContentPane(panel);

    // Abrir Pacientes
    btnPacientes.addActionListener(e -> {
    PacientesFrame f = new PacientesFrame();
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    @SuppressWarnings("unused")
    clinica.controller.PacientesController c = new clinica.controller.PacientesController(f);
    f.setVisible(true);
    });

    // Abrir Doctores
    btnDoctores.addActionListener(e -> {
    DoctoresFrame f = new DoctoresFrame();
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    @SuppressWarnings("unused")
    clinica.controller.DoctoresController c = new clinica.controller.DoctoresController(f);
    f.setVisible(true);
    });

    // Abrir Citas
    btnCitas.addActionListener(e -> {
    CitasFrame f = new CitasFrame();
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    @SuppressWarnings("unused")
    clinica.controller.CitasController c = new clinica.controller.CitasController(f);
    f.setVisible(true);
    });

    // Salir
    btnSalir.addActionListener(e -> {
    dispose();
    System.exit(0);
    });
    }
}
