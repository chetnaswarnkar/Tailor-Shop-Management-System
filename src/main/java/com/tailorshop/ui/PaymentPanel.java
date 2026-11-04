package com.tailorshop.ui;

import com.tailorshop.dao.PaymentDAO;
import com.tailorshop.utils.Ui;
import javax.swing.*;
import java.awt.*;

public class PaymentPanel {
    private final DashboardFrame frame;private final PaymentDAO dao=new PaymentDAO();
    private final DefaultTableModelAdapter table=new DefaultTableModelAdapter(new String[]{"Payment ID","Order ID","Customer","Amount","Date","Method"});
    public PaymentPanel(DashboardFrame f){frame=f;}
    public void showPanel(){
        JPanel p=new JPanel(new BorderLayout(12,12));p.setBackground(Ui.BG);JButton add=Ui.button("+ Add Payment");
        JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);top.add(Ui.title("Payments"),BorderLayout.WEST);top.add(add,BorderLayout.EAST);p.add(top,BorderLayout.NORTH);
        JTable t=table.table();Ui.styleTable(t);p.add(new JScrollPane(t),BorderLayout.CENTER);add.addActionListener(e->form());
        frame.content().removeAll();frame.content().add(p);frame.content().revalidate();frame.content().repaint();load();
    }
    private void load(){try{table.clear();dao.list().forEach(table::add);}catch(Exception e){error(e);}}
    private void form(){
        JTextField oid=new JTextField(),amount=new JTextField();JComboBox<String> method=new JComboBox<>(new String[]{"Cash","UPI","Card","Other"});
        JPanel f=new JPanel(new GridLayout(0,2,8,8));f.add(new JLabel("Order ID *"));f.add(oid);f.add(new JLabel("Amount *"));f.add(amount);f.add(new JLabel("Method"));f.add(method);
        if(JOptionPane.showConfirmDialog(frame,f,"Add Payment",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)try{
            double a=Double.parseDouble(amount.getText().trim());if(a<=0)throw new IllegalArgumentException("Amount must be greater than zero.");
            dao.add(Integer.parseInt(oid.getText().trim()),a,(String)method.getSelectedItem());load();
        }catch(Exception e){error(e);}
    }
    private void error(Exception e){JOptionPane.showMessageDialog(frame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}
}
