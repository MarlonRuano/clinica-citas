package clinica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import clinica.config.Db;
import clinica.model.Usuario;

public class UsuarioDao {
public Usuario autenticar(String username, String password) throws SQLException {
    String sql = "SELECT id, username, rol FROM usuarios WHERE username=? AND password_hash=?";
    try (Connection cn = Db.get();
    PreparedStatement ps = cn.prepareStatement(sql)) {
    ps.setString(1, username);
    ps.setString(2, password);
    try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setRol(rs.getString("rol"));
            return u;
        }
        }
    }
    return null;
    }
}
