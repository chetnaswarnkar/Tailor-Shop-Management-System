package com.tailorshop.ui;

import com.tailorshop.dao.MeasurementDAO;
import com.tailorshop.utils.Ui;
import javax.swing.*;
import java.awt.*;

public class MeasurementPanel {
    private final DashboardFrame frame; private final MeasurementDAO dao=new MeasurementDAO();
    private final DefaultTableModelAdapter table=new DefaultTableModelAdapter(new String[]{"ID","Customer","Dress","Chest","Waist","Shoulder","Sleeve","Length","Neck","Hip","Thigh","Bottom","Date"});
    public MeasurementPanel(DashboardFrame f){frame=f;}
    public void showPanel(){
        JPanel p=new JPanel(new BorderLayout(12,12));p.setBackground(Ui.BG);
        JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);JButton add=Ui.button("+ Add Measurement");top.add(Ui.title("Measurements"),BorderLayout.WEST);top.add(add,BorderLayout.EAST);p.add(top,BorderLayout.NORTH);
        JTable t=table.table();Ui.styleTable(t);JScrollPane scroll=new JScrollPane(t);scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);p.add(scroll,BorderLayout.CENTER);
        add.addActionListener(e->form());
        frame.content().removeAll();frame.content().add(p);frame.content().revalidate();frame.content().repaint();load();
    }
    private void load(){try{table.clear();dao.list().forEach(table::add);}catch(Exception e){error(e);}}
    private void form(){
        JTextField cid=new JTextField(),dress=new JTextField("Shirt"),ch=new JTextField(),wa=new JTextField(),sh=new JTextField(),sl=new JTextField(),le=new JTextField(),ne=new JTextField(),hi=new JTextField(),th=new JTextField(),bo=new JTextField(),notes=new JTextField();
        JPanel f=new JPanel(new GridLayout(0,2,6,6));
        JComponent[] cs={cid,dress,ch,wa,sh,sl,le,ne,hi,th,bo,notes};String[] labels={"Customer ID *","Dress Type *","Chest","Waist","Shoulder","Sleeve","Length","Neck","Hip","Thigh","Bottom","Notes"};
        for(int i=0;i<cs.length;i++){f.add(new JLabel(labels[i]));f.add(cs[i]);}
        int r=JOptionPane.showConfirmDialog(frame,new JScrollPane(f),"Add Measurement",JOptionPane.OK_CANCEL_OPTION);
        if(r==JOptionPane.OK_OPTION)try{dao.add(Integer.parseInt(cid.getText().trim()),dress.getText().trim(),ch.getText(),wa.getText(),sh.getText(),sl.getText(),le.getText(),ne.getText(),hi.getText(),th.getText(),bo.getText(),notes.getText());load();}catch(Exception e){error(e);}
    }
    private void error(Exception e){JOptionPane.showMessageDialog(frame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}
}
