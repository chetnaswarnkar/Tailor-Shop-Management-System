package com.tailorshop.dao;

import com.tailorshop.database.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class OrderDAO {
    public void add(int customerId, String dress, int qty, LocalDate orderDate, LocalDate deliveryDate,
                    double total, double advance, String status, String notes) throws Exception {
        String sql="INSERT INTO orders(customer_id,dress_type,quantity,order_date,delivery_date,total_amount,advance_payment,remaining_amount,status,special_instructions) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,customerId); ps.setString(2,dress); ps.setInt(3,qty);
            ps.setDate(4,java.sql.Date.valueOf(orderDate)); ps.setDate(5,java.sql.Date.valueOf(deliveryDate));
            ps.setDouble(6,total); ps.setDouble(7,advance); ps.setDouble(8,total-advance);
            ps.setString(9,status); ps.setString(10,notes); ps.executeUpdate();
        }
    }

    public void updateStatus(int id, String status) throws Exception {
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement("UPDATE orders SET status=? WHERE order_id=?")){
            ps.setString(1,status); ps.setInt(2,id); ps.executeUpdate();
        }
    }

    public List<Object[]> list(String filter) throws Exception {
        List<Object[]> out=new ArrayList<>();
        String sql="SELECT o.order_id,c.name,o.dress_type,o.quantity,o.order_date,o.delivery_date,o.total_amount,o.advance_payment,o.remaining_amount,o.status " +
                   "FROM orders o JOIN customers c ON o.customer_id=c.customer_id " +
                   "WHERE o.status LIKE ? OR c.name LIKE ? OR o.dress_type LIKE ? ORDER BY o.order_id DESC";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            String s="%"+filter+"%"; ps.setString(1,s); ps.setString(2,s); ps.setString(3,s);
            try(ResultSet rs=ps.executeQuery()){while(rs.next())out.add(new Object[]{
                rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getDate(6),
                rs.getDouble(7),rs.getDouble(8),rs.getDouble(9),rs.getString(10)
            });}
        }
        return out;
    }

    public int count(String where) throws Exception {
        String sql="SELECT COUNT(*) FROM orders "+where;
        try(Connection c=DBConnection.getConnection(); Statement st=c.createStatement(); ResultSet rs=st.executeQuery(sql)){
            rs.next(); return rs.getInt(1);
        }
    }

    public double revenue() throws Exception {
        try(Connection c=DBConnection.getConnection(); Statement st=c.createStatement();
            ResultSet rs=st.executeQuery("SELECT COALESCE(SUM(advance_payment),0) FROM orders")){
            rs.next(); return rs.getDouble(1);
        }
    }

    public List<String> reminders() throws Exception {
        List<String> r=new ArrayList<>();
        String sql="SELECT order_id,delivery_date,status FROM orders WHERE status <> 'DELIVERED' AND status <> 'CANCELLED' AND delivery_date <= DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY) ORDER BY delivery_date";
        try(Connection c=DBConnection.getConnection(); Statement st=c.createStatement(); ResultSet rs=st.executeQuery(sql)){
            LocalDate today=LocalDate.now();
            while(rs.next()){
                int id=rs.getInt(1); LocalDate d=rs.getDate(2).toLocalDate(); String status=rs.getString(3);
                if(d.isBefore(today)) r.add("OVERDUE: Order #"+id+" was due on "+d+" ("+status+")");
                else if(d.equals(today)) r.add("DUE TODAY: Order #"+id+" is due today ("+status+")");
                else r.add("DUE TOMORROW: Order #"+id+" is due tomorrow ("+status+")");
            }
        }
        return r;
    }
}
