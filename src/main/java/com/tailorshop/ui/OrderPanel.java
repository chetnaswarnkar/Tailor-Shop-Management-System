package com.tailorshop.ui;

import com.tailorshop.dao.OrderDAO;
import com.tailorshop.utils.Ui;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class OrderPanel {
    private final DashboardFrame frame;private final OrderDAO dao=new OrderDAO();
    private final DefaultTableModelAdapter table=new DefaultTableModelAdapter(new String[]{"ID","Customer","Dress","Qty","Order Date","Delivery","Total","Advance","Balance","Status"});
    public OrderPanel(DashboardFrame f){frame=f;}
    public void showPanel(){
        JPanel p=new JPanel(new BorderLayout(12,12));p.setBackground(Ui.BG);
        JPanel top=new JPanel(new BorderLayout(8,8));top.setOpaque(false);JTextField search=new JTextField();JButton go=Ui.button("Search"),add=Ui.button("+ New Order"),status=Ui.button("Update Status");
        top.add(Ui.title("Orders"),BorderLayout.WEST);top.add(search,BorderLayout.CENTER);JPanel b=new JPanel(new FlowLayout(FlowLayout.RIGHT,6,0));b.setOpaque(false);b.add(go);b.add(status);b.add(add);top.add(b,BorderLayout.EAST);p.add(top,BorderLayout.NORTH);
        JTable t=table.table();Ui.styleTable(t);p.add(new JScrollPane(t),BorderLayout.CENTER);
        Runnable load=()->{try{table.clear();dao.list(search.getText().trim()).forEach(table::add);}catch(Exception e){error(e);}};
        go.addActionListener(e->load.run());search.addActionListener(e->load.run());add.addActionListener(e->form(load));
        status.addActionListener(e->{int r=t.getSelectedRow();if(r<0){error(new Exception("Select an order first."));return;}String[] ss={"PENDING","CUTTING","STITCHING","READY","DELIVERED","CANCELLED"};String s=(String)JOptionPane.showInputDialog(frame,"New status:","Update Status",JOptionPane.PLAIN_MESSAGE,null,ss,table.val(r,9));if(s!=null)try{dao.updateStatus(Integer.parseInt(table.val(r,0)),s);load.run();}catch(Exception ex){error(ex);}});
        frame.content().removeAll();frame.content().add(p);frame.content().revalidate();frame.content().repaint();load.run();
    }
    private void form(Runnable load){
        JTextField cid=new JTextField(),dress=new JTextField("Shirt"),qty=new JTextField("1"),od=new JTextField(LocalDate.now().toString()),dd=new JTextField(LocalDate.now().plusDays(7).toString()),total=new JTextField(),adv=new JTextField("0"),notes=new JTextField();
        JPanel f=new JPanel(new GridLayout(0,2,6,6));JComponent[] cs={cid,dress,qty,od,dd,total,adv,notes};String[] ls={"Customer ID *","Dress Type *","Quantity *","Order Date (YYYY-MM-DD)","Delivery Date (YYYY-MM-DD)","Total Amount *","Advance Payment","Instructions"};
        for(int i=0;i<cs.length;i++){f.add(new JLabel(ls[i]));f.add(cs[i]);}
        int r=JOptionPane.showConfirmDialog(frame,f,"Create New Order",JOptionPane.OK_CANCEL_OPTION);
        if(r==JOptionPane.OK_OPTION)try{
            int c=Integer.parseInt(cid.getText().trim()),q=Integer.parseInt(qty.getText().trim());double tot=Double.parseDouble(total.getText().trim()),a=Double.parseDouble(adv.getText().trim());
            if(q<=0||tot<0||a<0||a>tot)throw new IllegalArgumentException("Check quantity and payment amounts.");
            dao.add(c,dress.getText().trim(),q,LocalDate.parse(od.getText().trim()),LocalDate.parse(dd.getText().trim()),tot,a,"PENDING",notes.getText().trim());load.run();
        }catch(Exception e){error(e);}
    }
    private void error(Exception e){JOptionPane.showMessageDialog(frame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}
}
