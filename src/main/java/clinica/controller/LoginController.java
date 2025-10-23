package clinica.controller;

import clinica.dao.UsuarioDao;
import clinica.model.Usuario;
import clinica.view.LoginFrame;
import clinica.view.MainMenuFrame;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class LoginController {
private final LoginFrame view;
private final UsuarioDao usuarioDao;

public LoginController(LoginFrame view) {
    this.view = view;
    this.usuarioDao = new UsuarioDao();
    init();
    }

private void init() {
    view.btnEntrar.addActionListener(e -> onEntrar());
    view.getRootPane().setDefaultButton(view.btnEntrar);
    }

private void onEntrar() {
    String u = view.txtUser.getText().trim();
    String p = new String(view.txtPass.getPassword());

    if (u.isEmpty() || p.isEmpty()) {
    JOptionPane.showMessageDialog(view, "Usuario y contraseña requeridos.");
    return;
    }

    try {
    Usuario user = usuarioDao.autenticar(u, p);
    if (user != null) {
        JOptionPane.showMessageDialog(view, "Bienvenido, " + user.getUsername());
        view.dispose();
        new MainMenuFrame().setVisible(true);
    } else {
        JOptionPane.showMessageDialog(view, "Credenciales inválidas.");
    }
    } catch (SQLException ex) {
    JOptionPane.showMessageDialog(view, "Error de base de datos: " + ex.getMessage());
    }
}
}
