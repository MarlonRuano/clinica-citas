package clinica.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import clinica.config.Db;
import clinica.model.Paciente;

public class PacienteDao {

    public List<Paciente> listar() throws SQLException {
        String sql = "SELECT id, nombres, apellidos, dpi, telefono, email FROM pacientes ORDER BY id DESC";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Paciente> list = new ArrayList<>();
            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("id"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setDpi(rs.getString("dpi"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("email"));
                list.add(p);
            }
            return list;
        }
    }

    public void crear(Paciente p) throws SQLException {
        String sql = "INSERT INTO pacientes(nombres, apellidos, dpi, telefono, email) VALUES(?,?,?,?,?)";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getDpi());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getEmail());
            ps.executeUpdate();
        }
    }

    public void actualizar(Paciente p) throws SQLException {
        String sql = "UPDATE pacientes SET nombres=?, apellidos=?, dpi=?, telefono=?, email=? WHERE id=?";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getDpi());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getEmail());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM pacientes WHERE id=?";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
