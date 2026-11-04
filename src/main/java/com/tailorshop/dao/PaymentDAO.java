package com.tailorshop.dao;

import com.tailorshop.database.DBConnection;
import java.sql.*;
import java.util.*;

public class PaymentDAO {
    public void add(int orderId,double amount,String method)throws Exception{
        try(Connection c=DBConnection.getConnection()){
            c.setAutoCommit(false);
            try(PreparedStatement ps=c.prepareStatement("INSERT INTO payments(order_id,amount,payment_method) VALUES(?,?,?)")){
                ps.setInt(1,orderId); ps.setDouble(2,amount); ps.setString(3,method); ps.executeUpdate();
            }
            try(PreparedStatement ps=c.prepareStatement("UPDATE orders SET advance_payment=advance_payment+?, remaining_amount=GREATEST(total_amount-advance_payment,0) WHERE order_id=?")){
                ps.setDouble(1,amount); ps.setInt(2,orderId); ps.executeUpdate();
            }
            c.commit();
        }
    }
    public List<Object[]> list()throws Exception{
        List<Object[]> out=new ArrayList<>();
        String sql="SELECT p.payment_id,p.order_id,c.name,p.amount,p.payment_date,p.payment_method FROM payments p JOIN orders o ON p.order_id=o.order_id JOIN customers c ON o.customer_id=c.customer_id ORDER BY p.payment_id DESC";
        try(Connection c=DBConnection.getConnection(); Statement st=c.createStatement(); ResultSet rs=st.executeQuery(sql)){
            while(rs.next()) out.add(new Object[]{rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getDouble(4),rs.getDate(5),rs.getString(6)});
        }
        return out;
    }
}
