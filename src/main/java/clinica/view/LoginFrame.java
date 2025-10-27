package clinica.view;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public final JTextField txtUser = new JTextField(15);
    public final JPasswordField txtPass = new JPasswordField(15);
    public final JButton btnEntrar = new JButton("Entrar");

    public LoginFrame() {
        super("Login - Clínica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        panel.add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        panel.add(txtPass, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(btnEntrar, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }
}
