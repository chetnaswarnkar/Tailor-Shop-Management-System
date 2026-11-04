package com.tailorshop.dao;

import com.tailorshop.database.DBConnection;
import java.sql.*;
import java.util.*;

public class CustomerDAO {
    public void add(String name, String mobile, String address, String email) throws Exception {
        String sql = "INSERT INTO customers(name,mobile,address,email) VALUES(?,?,?,?)";
        try (Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1,name); ps.setString(2,mobile); ps.setString(3,address); ps.setString(4,email); ps.executeUpdate();
        }
    }

    public void update(int id, String name, String mobile, String address, String email) throws Exception {
        String sql="UPDATE customers SET name=?,mobile=?,address=?,email=? WHERE customer_id=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,name); ps.setString(2,mobile); ps.setString(3,address); ps.setString(4,email); ps.setInt(5,id); ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement("DELETE FROM customers WHERE customer_id=?")){
            ps.setInt(1,id); ps.executeUpdate();
        }
    }

    public List<Object[]> list(String search) throws Exception {
        List<Object[]> out=new ArrayList<>();
        String sql="SELECT customer_id,name,mobile,address,email,registration_date FROM customers WHERE name LIKE ? OR mobile LIKE ? ORDER BY customer_id DESC";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            String s="%"+search+"%"; ps.setString(1,s); ps.setString(2,s);
            try(ResultSet rs=ps.executeQuery()){ while(rs.next()) out.add(new Object[]{
                rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6)
            });}
        }
        return out;
    }
}
