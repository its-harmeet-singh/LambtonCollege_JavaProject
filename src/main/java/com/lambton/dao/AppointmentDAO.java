package com.lambton.dao;

import com.lambton.model.Appointment;
import com.lambton.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private static final String INSERT =
      "INSERT INTO appointments (patient_id,doctor_id,appointment_time,reason) VALUES (?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM appointments";
    private static final String SELECT_BY_ID = "SELECT * FROM appointments WHERE id=?";
    private static final String UPDATE =
      "UPDATE appointments SET patient_id=?,doctor_id=?,appointment_time=?,reason=? WHERE id=?";
    private static final String DELETE = "DELETE FROM appointments WHERE id=?";

    public void insertAppointment(Appointment a) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getPatientId());
            ps.setInt(2, a.getDoctorId());
            ps.setTimestamp(3, Timestamp.valueOf(a.getAppointmentTime()));
            ps.setString(4, a.getReason());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) a.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Appointment(
                  rs.getInt("id"),
                  rs.getInt("patient_id"),
                  rs.getInt("doctor_id"),
                  rs.getTimestamp("appointment_time").toLocalDateTime(), 
                  rs.getString("reason")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Appointment getAppointmentById(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Appointment(
                      rs.getInt("id"),
                      rs.getInt("patient_id"),
                      rs.getInt("doctor_id"),
                      rs.getTimestamp("appointment_time").toLocalDateTime(),
                      rs.getString("reason")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateAppointment(Appointment a) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setInt(1, a.getPatientId());
            ps.setInt(2, a.getDoctorId());
            ps.setTimestamp(3, Timestamp.valueOf(a.getAppointmentTime()));
            ps.setString(4, a.getReason());
            ps.setInt(5, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAppointment(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // AppointmentDAO.java
    public void insertForPatient(int patientId, int doctorId,
                                java.time.LocalDate date,
                                java.time.LocalTime time,
                                String reason) throws SQLException {

        String sql = "INSERT INTO appointments " +
                    "(patient_id, doctor_id, appointment_time, reason) " +
                    "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);

            java.time.LocalDateTime dt = java.time.LocalDateTime.of(date, time);
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(dt));

            ps.setString(4, (reason == null ? "" : reason.trim()));
            ps.executeUpdate();
        }
    }

}
