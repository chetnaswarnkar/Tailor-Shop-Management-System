package com.tailorshop.ui;

import com.tailorshop.dao.OrderDAO;
import com.tailorshop.utils.Ui;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame {
    private final JPanel content=new JPanel(new BorderLayout(12,12));
    private final OrderDAO orders=new OrderDAO();
    private final String adminName;

    public DashboardFrame(String adminName){
        this.adminName=adminName;
        setTitle("Tailor Shop Management System");
        setSize(1180,720); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE);
        content.setBackground(Ui.BG); content.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));
        add(sidebar(),BorderLayout.WEST); add(content,BorderLayout.CENTER);
        showDashboard();
    }

    private JPanel sidebar(){
        JPanel p=new JPanel();
        p.setBackground(Ui.NAV); p.setPreferredSize(new Dimension(210,0));
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        JLabel brand=new JLabel("<html><b>TAILOR SHOP</b><br>Management System</html>");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Segoe UI",Font.BOLD,17));
        brand.setBorder(BorderFactory.createEmptyBorder(25,18,25,10));
        p.add(brand);
        addNav(p,"Dashboard",e->showDashboard());
        addNav(p,"Customers",e->showCustomers());
        addNav(p,"Measurements",e->showMeasurements());
        addNav(p,"Orders",e->showOrders());
        addNav(p,"Payments",e->showPayments());
        addNav(p,"Deliveries",e->showDeliveries());
        addNav(p,"Reports",e->showReports());
        p.add(Box.createVerticalGlue());
        JLabel who=new JLabel("<html>Admin<br>"+adminName+"</html>");
        who.setForeground(Color.LIGHT_GRAY); who.setBorder(BorderFactory.createEmptyBorder(10,18,10,10)); p.add(who);
        addNav(p,"Logout",e->{dispose();new LoginFrame().setVisible(true);});
        return p;
    }

    private void addNav(JPanel p,String text,java.awt.event.ActionListener a){
        JButton b=new JButton(text); b.setAlignmentX(Component.LEFT_ALIGNMENT); b.setMaximumSize(new Dimension(210,44));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setForeground(Color.WHITE);
        b.setBackground(Ui.NAV);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10,18,10,10));
        b.setFocusPainted(false);
        b.addChangeListener(e -> {
            if (b.getModel().isRollover()) b.setBackground(Ui.NAV_HOVER);
            else b.setBackground(Ui.NAV);
        });
        b.addActionListener(a); p.add(b);
    }

    private void base(String title){
        content.removeAll();
        JPanel head=new JPanel(new BorderLayout()); head.setOpaque(false);
        head.add(Ui.title(title),BorderLayout.WEST);
        JButton refresh=Ui.button("Refresh"); refresh.addActionListener(e->{showDashboard();}); head.add(refresh,BorderLayout.EAST);
        content.add(head,BorderLayout.NORTH);
    }

    private void showDashboard(){
        base("Dashboard");
        JPanel body=new JPanel(new BorderLayout(12,12)); body.setOpaque(false);
        JPanel cards=new JPanel(new GridLayout(1,5,10,10)); cards.setOpaque(false);
        try{
            cards.add(Ui.card("Customers",String.valueOf(customerCount())));
            cards.add(Ui.card("Total Orders",String.valueOf(orders.count(""))));
            cards.add(Ui.card("Pending",String.valueOf(orders.count("WHERE status='PENDING'"))));
            cards.add(Ui.card("Ready",String.valueOf(orders.count("WHERE status='READY'"))));
            cards.add(Ui.card("Received",String.format("₹ %.0f",orders.revenue())));
        }catch(Exception ex){ cards.add(Ui.card("DB","Error")); }
        body.add(cards,BorderLayout.NORTH);

        JPanel rem=new JPanel(new BorderLayout(8,8)); rem.setBackground(Color.WHITE); rem.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(225,226,232)),BorderFactory.createEmptyBorder(12,12,12,12)));
        JLabel rt=new JLabel("Delivery Reminders"); rt.setFont(new Font("Segoe UI",Font.BOLD,17)); rem.add(rt,BorderLayout.NORTH);
        DefaultListModel<String> model=new DefaultListModel<>();
        try{ List<String> rs=orders.reminders(); if(rs.isEmpty())model.addElement("No urgent delivery reminders."); else rs.forEach(model::addElement); }
        catch(Exception ex){model.addElement("Unable to load reminders: "+ex.getMessage());}
        rem.add(new JScrollPane(new JList<>(model)),BorderLayout.CENTER);
        body.add(rem,BorderLayout.CENTER);
        content.add(body,BorderLayout.CENTER); content.revalidate(); content.repaint();
    }

    private int customerCount()throws Exception{
        try(var c=com.tailorshop.database.DBConnection.getConnection();
            var st=c.createStatement();var rs=st.executeQuery("SELECT COUNT(*) FROM customers")){rs.next();return rs.getInt(1);}
    }

    private void showCustomers(){ new CustomerPanel(this).showPanel(); }
    private void showMeasurements(){ new MeasurementPanel(this).showPanel(); }
    private void showOrders(){ new OrderPanel(this).showPanel(); }
    private void showPayments(){ new PaymentPanel(this).showPanel(); }
    private void showDeliveries(){ new DeliveryPanel(this).showPanel(); }
    private void showReports(){ new ReportPanel(this).showPanel(); }
    public JPanel content(){return content;}
    public void refreshDashboard(){showDashboard();}
}
