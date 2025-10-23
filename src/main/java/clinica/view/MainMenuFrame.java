package clinica.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainMenuFrame extends JFrame {
public MainMenuFrame() {
    super("Clínica - Menú Principal");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(new JLabel("Menú principal (Pacientes / Doctores / Citas)"));
    setSize(420, 220);
    setLocationRelativeTo(null);
    }
}
