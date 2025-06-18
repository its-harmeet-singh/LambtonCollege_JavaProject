package com.lambton.dao;

import com.lambton.model.Diagnosis;
import com.lambton.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiagnosisDAO {
    private static final String INSERT_SQL =
      "INSERT INTO diagnoses (patient_id, doctor_id, diagnosis, date_diagnosed, notes) "
    + "VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_PATIENT =
      "SELECT * FROM diagnoses WHERE patient_id = ? ORDER BY date_diagnosed ASC";

    public void insert(Diagnosis d) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, d.getPatientId());
            if (d.getDoctorId() != null) ps.setInt(2, d.getDoctorId());
            else ps.setNull(2, Types.INTEGER);
            ps.setString(3, d.getDiagnosis());
            ps.setDate(4, Date.valueOf(d.getDateDiagnosed()));
            ps.setString(5, d.getNotes());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) d.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting diagnosis", e);
        }
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
                      rs.getObject("doctor_id") != null ? rs.getInt("doctor_id") : null,
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
