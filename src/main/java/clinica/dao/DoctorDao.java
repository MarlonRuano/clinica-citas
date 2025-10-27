package clinica.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import clinica.config.Db;
import clinica.model.Doctor;

public class DoctorDao {

    public List<Doctor> listar() throws SQLException {
        String sql = "SELECT id, nombres, apellidos, colegiado, telefono, email FROM doctores ORDER BY id DESC";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Doctor> list = new ArrayList<>();
            while (rs.next()) {
                Doctor d = new Doctor();
                d.setId(rs.getInt("id"));
                d.setNombres(rs.getString("nombres"));
                d.setApellidos(rs.getString("apellidos"));
                d.setColegiado(rs.getString("colegiado"));
                d.setTelefono(rs.getString("telefono"));
                d.setEmail(rs.getString("email"));
                list.add(d);
            }
            return list;
        }
    }

    public void crear(Doctor d) throws SQLException {
        String sql = "INSERT INTO doctores(nombres, apellidos, colegiado, telefono, email) VALUES(?,?,?,?,?)";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, d.getNombres());
            ps.setString(2, d.getApellidos());
            ps.setString(3, d.getColegiado());
            ps.setString(4, d.getTelefono());
            ps.setString(5, d.getEmail());
            ps.executeUpdate();
        }
    }

    public void actualizar(Doctor d) throws SQLException {
        String sql = "UPDATE doctores SET nombres=?, apellidos=?, colegiado=?, telefono=?, email=? WHERE id=?";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, d.getNombres());
            ps.setString(2, d.getApellidos());
            ps.setString(3, d.getColegiado());
            ps.setString(4, d.getTelefono());
            ps.setString(5, d.getEmail());
            ps.setInt(6, d.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM doctores WHERE id=?";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
