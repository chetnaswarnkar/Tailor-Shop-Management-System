package com.tailorshop.dao;

import com.tailorshop.database.DBConnection;
import java.sql.*;
import java.util.*;

public class MeasurementDAO {
    public void add(int customerId, String dress, String chest, String waist, String shoulder, String sleeve,
                    String length, String neck, String hip, String thigh, String bottom, String notes) throws Exception {
        String sql="INSERT INTO measurements(customer_id,dress_type,chest,waist,shoulder,sleeve,length,neck,hip,thigh,bottom,notes) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1,customerId); ps.setString(2,dress);
            setNum(ps,3,chest); setNum(ps,4,waist); setNum(ps,5,shoulder); setNum(ps,6,sleeve); setNum(ps,7,length);
            setNum(ps,8,neck); setNum(ps,9,hip); setNum(ps,10,thigh); setNum(ps,11,bottom); ps.setString(12,notes); ps.executeUpdate();
        }
    }
    private void setNum(PreparedStatement ps,int i,String s)throws SQLException{
        if(s==null || s.isBlank()){ps.setNull(i,Types.DECIMAL); return;}
        ps.setDouble(i,Double.parseDouble(s));
    }
    public List<Object[]> list() throws Exception{
        List<Object[]> out=new ArrayList<>();
        String sql="SELECT m.measurement_id,c.name,m.dress_type,m.chest,m.waist,m.shoulder,m.sleeve,m.length,m.neck,m.hip,m.thigh,m.bottom,m.measurement_date FROM measurements m JOIN customers c ON m.customer_id=c.customer_id ORDER BY m.measurement_id DESC";
        try(Connection c=DBConnection.getConnection(); Statement st=c.createStatement(); ResultSet rs=st.executeQuery(sql)){
            while(rs.next()) out.add(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getObject(4),rs.getObject(5),rs.getObject(6),rs.getObject(7),rs.getObject(8),rs.getObject(9),rs.getObject(10),rs.getObject(11),rs.getObject(12),rs.getDate(13)});
        }
        return out;
    }
}
