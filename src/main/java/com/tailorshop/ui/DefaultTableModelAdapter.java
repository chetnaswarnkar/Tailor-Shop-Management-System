package com.tailorshop.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DefaultTableModelAdapter {
    private final DefaultTableModel model;
    private final JTable table;

    public DefaultTableModelAdapter(String[] columns){
        model=new DefaultTableModel(columns,0){@Override public boolean isCellEditable(int r,int c){return false;}};
        table=new JTable(model);
    }
    public JTable table(){return table;}
    public void clear(){model.setRowCount(0);}
    public void add(Object[] row){model.addRow(row);}
    public String val(int row,int col){Object v=model.getValueAt(row,col);return v==null?"":v.toString();}
}
