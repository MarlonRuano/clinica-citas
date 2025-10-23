package clinica;

import javax.swing.SwingUtilities;

import clinica.controller.LoginController;
import clinica.view.LoginFrame;

public class Main {
public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        LoginFrame lf = new LoginFrame();
        @SuppressWarnings("unused")
        LoginController controller = new LoginController(lf);
        lf.setVisible(true);
    });
    }
}
