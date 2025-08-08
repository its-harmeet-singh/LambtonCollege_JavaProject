package com.lambton.dao;

import com.lambton.model.Patient;
import com.lambton.model.PatientSearchRow;
import com.lambton.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    private static final String INSERT = "INSERT INTO patients (name, age, gender, address) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM patients";
    private static final String SELECT_BY_ID = "SELECT * FROM patients WHERE id = ?";
    private static final String UPDATE = "UPDATE patients SET name=?, age=?, gender=?, address=? WHERE id=?";
    private static final String DELETE = "DELETE FROM patients WHERE id=?";

    public void insertPatient(Patient p) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getAge());
            stmt.setString(3, p.getGender());
            stmt.setString(4, p.getAddress());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next())
                    p.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("address")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Patient getPatientById(int id) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Patient(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("address"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updatePatient(Patient p) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getAge());
            stmt.setString(3, p.getGender());
            stmt.setString(4, p.getAddress());
            stmt.setInt(5, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePatient(int id) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Patient getByEmail(String email) {
        String sql = "SELECT * FROM patients WHERE email_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Patient(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("address"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching patient by email", e);
        }
        return null;
    }

    public List<PatientSearchRow> searchPatients(String nameLike, LocalDate date, Integer doctorId) {
        List<PatientSearchRow> out = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT p.id AS patient_id, p.name AS patient_name, p.email_id AS phone, " +
            "       a.id AS appointment_id, " +                      
            "       a.appointment_time AS appointment_date, " +
            "       a.doctor_id, d.name AS doctor_name " +
            "FROM patients p " +
            "LEFT JOIN appointments a ON a.patient_id = p.id " +
            "LEFT JOIN doctors d ON d.id = a.doctor_id " +
            "WHERE 1=1 "
        );


        // dynamic filters
        if (nameLike != null && !nameLike.isBlank()) {
            sql.append(" AND LOWER(p.name) LIKE ? ");
        }
        if (date != null) {
            sql.append(" AND DATE(a.appointment_time) = ? ");
        }
        if (doctorId != null) {
            sql.append(" AND a.doctor_id = ? ");
        }

        sql.append(" ORDER BY a.appointment_time IS NULL, a.appointment_time DESC, p.name ASC ");

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int idx = 1;
            if (nameLike != null && !nameLike.isBlank()) {
                ps.setString(idx++, "%" + nameLike.toLowerCase().trim() + "%");
            }
            if (date != null) {
                ps.setDate(idx++, Date.valueOf(date));
            }
            if (doctorId != null) {
                ps.setInt(idx++, doctorId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PatientSearchRow row = new PatientSearchRow();
                    row.setPatientId(rs.getInt("patient_id"));
                    row.setPatientName(rs.getString("patient_name"));
                    row.setPhone(rs.getString("phone"));
                    Date dsql = rs.getDate("appointment_date");
                    row.setAppointmentDate(dsql == null ? null : dsql.toLocalDate());
                    int did = rs.getInt("doctor_id");
                    row.setAppointmentId(rs.getInt("appointment_id"));
                    row.setDoctorId(rs.wasNull() ? null : did);
                    row.setDoctorName(rs.getString("doctor_name"));
                    out.add(row);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("searchPatients failed", e);
        }
        return out;
    }
}
