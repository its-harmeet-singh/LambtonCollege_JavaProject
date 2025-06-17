package main.com.lambton.dao;

import main.com.lambton.model.Doctor;
import main.com.lambton.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    private static final String INSERT = 
        "INSERT INTO doctors (name,specialty,phone,email) VALUES (?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM doctors";
    private static final String SELECT_BY_ID = "SELECT * FROM doctors WHERE id=?";
    private static final String UPDATE = 
        "UPDATE doctors SET name=?,specialty=?,phone=?,email=? WHERE id=?";
    private static final String DELETE = "DELETE FROM doctors WHERE id=?";

    public void insertDoctor(Doctor d) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getSpecialty());
            ps.setString(3, d.getPhone());
            ps.setString(4, d.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) d.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Doctor(
                  rs.getInt("id"),
                  rs.getString("name"),
                  rs.getString("specialty"),
                  rs.getString("phone"),
                  rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Doctor getDoctorById(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                      rs.getInt("id"),
                      rs.getString("name"),
                      rs.getString("specialty"),
                      rs.getString("phone"),
                      rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateDoctor(Doctor d) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getSpecialty());
            ps.setString(3, d.getPhone());
            ps.setString(4, d.getEmail());
            ps.setInt(5, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDoctor(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
