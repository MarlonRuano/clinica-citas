package clinica.view;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
public final JTextField txtUser = new JTextField(14);
public final JPasswordField txtPass = new JPasswordField(14);
public final JButton btnEntrar = new JButton("Entrar");

public LoginFrame() {
    super("Login");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(6, 6, 6, 6);

    gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_END;
    panel.add(new JLabel("Usuario:"), gbc);

    gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_START;
    panel.add(txtUser, gbc);

    gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END;
    panel.add(new JLabel("Contraseña:"), gbc);

    gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_START;
    panel.add(txtPass, gbc);

    gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
    panel.add(btnEntrar, gbc);

    setContentPane(panel);
    pack();
    setLocationRelativeTo(null);
    }
}
