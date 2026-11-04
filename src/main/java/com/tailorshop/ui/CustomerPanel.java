package com.tailorshop.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.tailorshop.dao.CustomerDAO;
import com.tailorshop.utils.Ui;

public class CustomerPanel {

    private final DashboardFrame frame;
    private final CustomerDAO dao = new CustomerDAO();

    private final DefaultTableModelAdapter table =
            new DefaultTableModelAdapter(
                    new String[]{"ID", "Name", "Mobile", "Address", "Email", "Registered"}
            );

    public CustomerPanel(DashboardFrame f) {
        frame = f;
    }

    public void showPanel() {

        JPanel p = new JPanel(new BorderLayout(12, 12));
        p.setBackground(Ui.BG);

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.setOpaque(false);

        JLabel title = Ui.title("Customers");

        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(250, 38));

        JButton go = Ui.button("Search");
        JButton add = Ui.button("+ New Customer");

        JPanel left = new JPanel(new BorderLayout(6, 6));
        left.setOpaque(false);

        left.add(search, BorderLayout.CENTER);
        left.add(go, BorderLayout.EAST);

        top.add(title, BorderLayout.WEST);
        top.add(left, BorderLayout.CENTER);
        top.add(add, BorderLayout.EAST);

        p.add(top, BorderLayout.NORTH);

        JTable t = table.table();
        Ui.styleTable(t);

        JScrollPane scroll = new JScrollPane(t);
        p.add(scroll, BorderLayout.CENTER);

        Runnable load = () -> {
            try {
                table.clear();

                dao.list(search.getText().trim())
                   .forEach(table::add);

            } catch (Exception e) {
                error(e);
            }
        };

        go.addActionListener(e -> load.run());

        search.addActionListener(e -> load.run());

        add.addActionListener(e -> form(null, load));

        t.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                if (e.getClickCount() == 2 &&
                    t.getSelectedRow() >= 0) {

                    form(t.getSelectedRow(), load);
                }
            }
        });

        frame.content().removeAll();
        frame.content().add(p);
        frame.content().revalidate();
        frame.content().repaint();

        load.run();
    }

    private void form(Integer row, Runnable load) {

        JTextField name = new JTextField();
        JTextField mobile = new JTextField();
        JTextField address = new JTextField();
        JTextField email = new JTextField();

        JPanel f = new JPanel(new GridLayout(0, 2, 8, 8));
        f.setPreferredSize(new Dimension(420, 190));

        f.add(new JLabel("Name *"));
        f.add(name);

        f.add(new JLabel("Mobile *"));
        f.add(mobile);

        f.add(new JLabel("Address"));
        f.add(address);

        f.add(new JLabel("Email"));
        f.add(email);

        if (row != null) {
            name.setText(table.val(row, 1));
            mobile.setText(table.val(row, 2));
            address.setText(table.val(row, 3));
            email.setText(table.val(row, 4));
        }

        int result = JOptionPane.showConfirmDialog(
                frame,
                f,
                row == null ? "Add Customer" : "Edit Customer",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            try {

                if (name.getText().isBlank() ||
                    mobile.getText().isBlank()) {

                    throw new IllegalArgumentException(
                            "Name and mobile are required."
                    );
                }

                if (row == null) {

                    dao.add(
                            name.getText().trim(),
                            mobile.getText().trim(),
                            address.getText().trim(),
                            email.getText().trim()
                    );

                } else {

                    dao.update(
                            Integer.parseInt(table.val(row, 0)),
                            name.getText().trim(),
                            mobile.getText().trim(),
                            address.getText().trim(),
                            email.getText().trim()
                    );
                }

                load.run();

            } catch (Exception e) {
                error(e);
            }
        }
    }

    private void error(Exception e) {

        JOptionPane.showMessageDialog(
                frame,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}