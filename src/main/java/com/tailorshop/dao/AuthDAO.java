package com.tailorshop.dao;

import com.tailorshop.database.DBConnection;
import java.sql.*;

public class AuthDAO {
    public String login(String username, String password) throws Exception {
        String sql = "SELECT full_name FROM users WHERE username=? AND password=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("full_name") : null;
            }
        }
    }
}
