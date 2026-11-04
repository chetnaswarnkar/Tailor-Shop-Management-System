package com.tailorshop.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class Ui {
    public static final Color NAV = new Color(31, 48, 68);
    public static final Color NAV_HOVER = new Color(47, 67, 91);
    public static final Color ACCENT = new Color(24, 119, 101);
    public static final Color ACCENT_HOVER = new Color(20, 99, 84);
    public static final Color BG = new Color(244, 247, 249);
    public static final Color TEXT = new Color(30, 41, 59);
    public static final Color MUTED = new Color(100, 116, 139);

    private Ui() {}

    public static JButton button(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(ACCENT);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setBorder(new EmptyBorder(10, 16, 10, 16));
        b.setMargin(new Insets(10, 16, 10, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addChangeListener(e -> b.setBackground(b.getModel().isRollover() ? ACCENT_HOVER : ACCENT));
        return b;
    }

    public static JLabel title(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 24));
        l.setForeground(TEXT);
        l.setBorder(new EmptyBorder(0, 0, 4, 0));
        return l;
    }

    public static JPanel card(String title, String value) {
        JPanel p = new JPanel(new BorderLayout(8, 4));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 226, 232)),
                new EmptyBorder(14, 16, 14, 16)
        ));
        JLabel t = new JLabel(title);
        t.setForeground(new Color(100, 101, 110));
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JLabel v = new JLabel(value);
        v.setForeground(TEXT);
        v.setFont(new Font("Segoe UI", Font.BOLD, 23));
        p.add(t, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);
        return p;
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(220, 239, 234));
        table.setSelectionForeground(TEXT);
        table.setGridColor(new Color(226, 232, 240));
        table.setShowGrid(true);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(NAV);
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean selected,
                                                           boolean focused, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(t, value, false, false, row, column);
                label.setOpaque(true);
                label.setBackground(NAV);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(new EmptyBorder(0, 10, 0, 10));
                return label;
            }
        });

        // Give every column enough room for its heading/content. A horizontal
        // scrollbar will appear for wide tables such as Measurements.
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            int width = 95;
            String name = table.getColumnModel().getColumn(i).getHeaderValue().toString().toLowerCase();
            if (name.equals("id")) width = 60;
            else if (name.contains("customer")) width = 105;
            else if (name.contains("address")) width = 180;
            else if (name.contains("email")) width = 170;
            else if (name.contains("dress")) width = 115;
            else if (name.contains("date")) width = 115;
            else if (name.contains("status")) width = 115;
            else if (name.contains("amount") || name.contains("total") || name.contains("advance") || name.contains("balance")) width = 110;
            else if (name.equals("notes") || name.contains("instructions")) width = 180;
            else if (name.equals("chest") || name.equals("shoulder") || name.equals("sleeve") || name.equals("length") || name.equals("waist") || name.equals("neck") || name.equals("hip") || name.equals("thigh") || name.equals("bottom")) width = 90;
            table.getColumnModel().getColumn(i).setPreferredWidth(width);
            table.getColumnModel().getColumn(i).setMinWidth(Math.min(width, 60));
        }
    }
}
