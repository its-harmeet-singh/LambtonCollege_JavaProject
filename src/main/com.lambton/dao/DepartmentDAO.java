package main.com.lambton.dao;

import com.lambton.model.Department;
import com.lambton.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private static final String INSERT = 
        "INSERT INTO departments (name,location) VALUES (?,?)";
    private static final String SELECT_ALL = "SELECT * FROM departments";
    private static final String SELECT_BY_ID = 
        "SELECT * FROM departments WHERE id=?";
    private static final String UPDATE = 
        "UPDATE departments SET name=?,location=? WHERE id=?";
    private static final String DELETE = "DELETE FROM departments WHERE id=?";

    public void insertDepartment(Department d) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getLocation());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) d.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Department(
                  rs.getInt("id"),
                  rs.getString("name"),
                  rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Department getDepartmentById(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Department(
                      rs.getInt("id"),
                      rs.getString("name"),
                      rs.getString("location")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateDepartment(Department d) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getLocation());
            ps.setInt(3, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDepartment(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
