package com.lambton.dao;

import com.lambton.model.Prescription;
import com.lambton.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {
    private static final String INSERT_SQL =
      "INSERT INTO prescriptions (patient_id, doctor_id, medicine, dosage, date_issued, instructions) "
    + "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_PATIENT =
      "SELECT * FROM prescriptions WHERE patient_id = ? ORDER BY date_issued ASC";
    // (You can add SELECT_ALL, SELECT_BY_ID, UPDATE, DELETE as needed)

    public void insert(Prescription p) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getPatientId());
            if (p.getDoctorId() != null) ps.setInt(2, p.getDoctorId());
            else ps.setNull(2, Types.INTEGER);
            ps.setString(3, p.getMedicine());
            ps.setString(4, p.getDosage());
            ps.setDate(5, Date.valueOf(p.getDateIssued()));
            ps.setString(6, p.getInstructions());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting prescription", e);
        }
    }

    public List<Prescription> getByPatientId(int patientId) {
        List<Prescription> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_PATIENT)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Prescription(
                      rs.getInt("id"),
                      rs.getInt("patient_id"),
                      rs.getObject("doctor_id") != null ? rs.getInt("doctor_id") : null,
                      rs.getString("medicine"),
                      rs.getString("dosage"),
                      rs.getDate("date_issued").toLocalDate(),
                      rs.getString("instructions")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching prescriptions", e);
        }
        return list;
    }
}
