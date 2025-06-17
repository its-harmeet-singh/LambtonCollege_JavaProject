package com.lambton.dao;

import com.lambton.model.Billing;
import com.lambton.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {
    private static final String INSERT = 
      "INSERT INTO billing (patient_id,amount,billing_date,description) VALUES (?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM billing";
    private static final String SELECT_BY_ID = 
      "SELECT * FROM billing WHERE id=?";
    private static final String UPDATE = 
      "UPDATE billing SET patient_id=?,amount=?,billing_date=?,description=? WHERE id=?";
    private static final String DELETE = "DELETE FROM billing WHERE id=?";

    public void insertBilling(Billing b) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, b.getPatientId());
            ps.setDouble(2, b.getAmount());
            ps.setDate(3, Date.valueOf(b.getBillingDate()));
            ps.setString(4, b.getDescription());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) b.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Billing> getAllBilling() {
        List<Billing> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Billing(
                  rs.getInt("id"),
                  rs.getInt("patient_id"),
                  rs.getDouble("amount"),
                  rs.getDate("billing_date").toLocalDate(),
                  rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Billing getBillingById(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Billing(
                      rs.getInt("id"),
                      rs.getInt("patient_id"),
                      rs.getDouble("amount"),
                      rs.getDate("billing_date").toLocalDate(),
                      rs.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateBilling(Billing b) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setInt(1, b.getPatientId());
            ps.setDouble(2, b.getAmount());
            ps.setDate(3, Date.valueOf(b.getBillingDate()));
            ps.setString(4, b.getDescription());
            ps.setInt(5, b.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBilling(int id) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
