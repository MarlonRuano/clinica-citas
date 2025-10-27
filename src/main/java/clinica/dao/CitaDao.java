package clinica.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import clinica.config.Db;
import clinica.model.Cita;

public class CitaDao {

    public List<Cita> listar() throws SQLException {
        String sql = "SELECT id, doctor_id, paciente_id, fecha_hora, motivo, estado FROM citas ORDER BY fecha_hora DESC";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Cita> list = new ArrayList<>();
            while (rs.next()) {
                Cita c = new Cita();
                c.setId(rs.getInt("id"));
                c.setDoctorId(rs.getInt("doctor_id"));
                c.setPacienteId(rs.getInt("paciente_id"));
                Timestamp ts = rs.getTimestamp("fecha_hora");
                if (ts != null) {
                    c.setFechaHora(LocalDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault()));
                }
                c.setMotivo(rs.getString("motivo"));
                c.setEstado(rs.getString("estado"));
                list.add(c);
            }
            return list;
        }
    }

    public void crear(Cita c) throws SQLException {
        String sql = "INSERT INTO citas(doctor_id, paciente_id, fecha_hora, motivo, estado) VALUES(?,?,?,?,?)";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, c.getDoctorId());
            ps.setInt(2, c.getPacienteId());
            ps.setTimestamp(3, Timestamp.valueOf(c.getFechaHora()));
            ps.setString(4, c.getMotivo());
            ps.setString(5, c.getEstado());
            ps.executeUpdate();
        }
    }

    public void actualizar(Cita c) throws SQLException {
        String sql = "UPDATE citas SET doctor_id=?, paciente_id=?, fecha_hora=?, motivo=?, estado=? WHERE id=?";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, c.getDoctorId());
            ps.setInt(2, c.getPacienteId());
            ps.setTimestamp(3, Timestamp.valueOf(c.getFechaHora()));
            ps.setString(4, c.getMotivo());
            ps.setString(5, c.getEstado());
            ps.setInt(6, c.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM citas WHERE id=?";
        try (Connection cn = Db.get(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
