package com.lambton.dao;

import com.lambton.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final String LOGIN_SQL =
      "SELECT role FROM users WHERE email_id = ? AND password = ?";


    public String loginAndGetRole(String email, String password) {
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(LOGIN_SQL)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during login", e);
        }
        return null;
    }
}
