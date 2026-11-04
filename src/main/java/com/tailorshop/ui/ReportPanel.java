package com.tailorshop.ui;

import com.tailorshop.dao.OrderDAO;
import com.tailorshop.utils.Ui;
import javax.swing.*;
import java.awt.*;

public class ReportPanel {
    private final DashboardFrame frame;private final OrderDAO dao=new OrderDAO();
    public ReportPanel(DashboardFrame f){frame=f;}
    public void showPanel(){
        JPanel p=new JPanel(new BorderLayout(12,12));p.setBackground(Ui.BG);p.add(Ui.title("Reports & Analytics"),BorderLayout.NORTH);
        JPanel cards=new JPanel(new GridLayout(2,3,12,12));cards.setOpaque(false);
        try{
            cards.add(Ui.card("All Orders",String.valueOf(dao.count(""))));
            cards.add(Ui.card("Pending",String.valueOf(dao.count("WHERE status='PENDING'"))));
            cards.add(Ui.card("Cutting",String.valueOf(dao.count("WHERE status='CUTTING'"))));
            cards.add(Ui.card("Stitching",String.valueOf(dao.count("WHERE status='STITCHING'"))));
            cards.add(Ui.card("Ready",String.valueOf(dao.count("WHERE status='READY'"))));
            cards.add(Ui.card("Advance Received",String.format("₹ %.2f",dao.revenue())));
        }catch(Exception e){cards.add(Ui.card("Error",e.getMessage()));}
        p.add(cards,BorderLayout.NORTH);
        JTextArea note=new JTextArea("\nProject innovation\n\n• Smart order status workflow: PENDING → CUTTING → STITCHING → READY → DELIVERED\n• Automatic delivery reminders for overdue, today's and tomorrow's orders\n• JDBC + MySQL persistence\n• Customer measurements and payment tracking");
        note.setEditable(false);note.setFont(new Font("Segoe UI",Font.PLAIN,15));note.setBackground(Color.WHITE);note.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));p.add(note,BorderLayout.CENTER);
        frame.content().removeAll();frame.content().add(p);frame.content().revalidate();frame.content().repaint();
    }
}
