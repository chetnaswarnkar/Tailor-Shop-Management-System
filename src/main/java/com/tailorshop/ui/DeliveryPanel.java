package com.tailorshop.ui;

import com.tailorshop.dao.OrderDAO;
import com.tailorshop.utils.Ui;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeliveryPanel {
    private final DashboardFrame frame;private final OrderDAO dao=new OrderDAO();
    public DeliveryPanel(DashboardFrame f){frame=f;}
    public void showPanel(){
        JPanel p=new JPanel(new BorderLayout(12,12));p.setBackground(Ui.BG);p.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        JLabel title=Ui.title("Delivery Tracker");p.add(title,BorderLayout.NORTH);
        DefaultListModel<String> m=new DefaultListModel<>();
        try{List<String> r=dao.reminders();if(r.isEmpty())m.addElement("No urgent deliveries.");else r.forEach(m::addElement);}catch(Exception e){m.addElement(e.getMessage());}
        JList<String> list=new JList<>(m);list.setFont(new Font("Segoe UI",Font.PLAIN,15));list.setFixedCellHeight(42);p.add(new JScrollPane(list),BorderLayout.CENTER);
        JLabel info=new JLabel("Smart reminders: overdue, due today, and due tomorrow. Update order status from Orders.");
        info.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));p.add(info,BorderLayout.SOUTH);
        frame.content().removeAll();frame.content().add(p);frame.content().revalidate();frame.content().repaint();
    }
}
