package com.lambton.dao;

import com.lambton.model.Diagnosis;
import com.lambton.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosisDAO {
    private static final String INSERT_SQL =
      "INSERT INTO diagnoses "
    + "(patient_id, doctor_id, appointment_id, diagnosis, date_diagnosed, notes) "
    + "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
      "UPDATE diagnoses SET "
    + "patient_id=?, doctor_id=?, appointment_id=?, diagnosis=?, date_diagnosed=?, notes=? "
    + "WHERE id=?";

    private static final String SELECT_BY_APPOINTMENT =
      "SELECT * FROM diagnoses WHERE appointment_id = ?";

    private static final String SELECT_BY_PATIENT =
      "SELECT * FROM diagnoses WHERE patient_id = ? ORDER BY date_diagnosed ASC";

    public void insert(Diagnosis d) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, d.getPatientId());
            if (d.getDoctorId() != null)        ps.setInt(2, d.getDoctorId());
            else                                 ps.setNull(2, Types.INTEGER);

            if (d.getAppointmentId() != null)   ps.setInt(3, d.getAppointmentId());
            else                                 ps.setNull(3, Types.INTEGER);

            ps.setString(4, d.getDiagnosis());
            ps.setDate(5, Date.valueOf(d.getDateDiagnosed()));
            ps.setString(6, d.getNotes());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) d.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting diagnosis", e);
        }
    }

    /** New update method **/
    public void update(Diagnosis d) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE_SQL)) {
            ps.setInt(1, d.getPatientId());
            if (d.getDoctorId() != null)      ps.setInt(2, d.getDoctorId());
            else                               ps.setNull(2, Types.INTEGER);

            if (d.getAppointmentId() != null) ps.setInt(3, d.getAppointmentId());
            else                               ps.setNull(3, Types.INTEGER);

            ps.setString(4, d.getDiagnosis());
            ps.setDate(5, Date.valueOf(d.getDateDiagnosed()));
            ps.setString(6, d.getNotes());
            ps.setInt(7, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating diagnosis", e);
        }
    }

    public List<Diagnosis> getByAppointmentId(int appointmentId) {
        List<Diagnosis> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_APPOINTMENT)) {
            ps.setInt(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Diagnosis(
                      rs.getInt("id"),
                      rs.getInt("patient_id"),
                      rs.getObject("doctor_id")      != null ? rs.getInt("doctor_id")      : null,
                      rs.getObject("appointment_id") != null ? rs.getInt("appointment_id") : null,
                      rs.getString("diagnosis"),
                      rs.getDate("date_diagnosed").toLocalDate(),
                      rs.getString("notes")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching diagnoses by appointment", e);
        }
        return list;
    }

    public List<Diagnosis> getByPatientId(int patientId) {
        List<Diagnosis> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_PATIENT)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Diagnosis(
                      rs.getInt("id"),
                      rs.getInt("patient_id"),
                      rs.getObject("doctor_id")      != null ? rs.getInt("doctor_id")      : null,
                      rs.getObject("appointment_id") != null ? rs.getInt("appointment_id") : null,
                      rs.getString("diagnosis"),
                      rs.getDate("date_diagnosed").toLocalDate(),
                      rs.getString("notes")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching diagnoses", e);
        }
        return list;
    }
}
