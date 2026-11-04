package com.tailorshop.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.tailorshop.dao.AuthDAO;
import com.tailorshop.utils.Ui;

public class LoginFrame extends JFrame {

    private final JTextField user = new JTextField();
    private final JPasswordField pass = new JPasswordField();

    public LoginFrame() {

        setTitle("Tailor Shop Management - Login");

        // Bigger window
        setSize(1200, 750);
        setMinimumSize(new Dimension(1000, 650));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main background
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(245, 247, 246));
        root.setBorder(new EmptyBorder(40, 50, 40, 50));

        // Login card
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 226, 223), 1
                ),
                new EmptyBorder(45, 65, 45, 65)
        ));

        card.setPreferredSize(new Dimension(620, 570));

        GridBagConstraints g = new GridBagConstraints();

        g.gridx = 0;
        g.gridwidth = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;

        // =========================
        // Tailor icon
        // =========================

        JLabel logo = new JLabel("✂  🧵", SwingConstants.CENTER);

        logo.setFont(new Font(
                "Segoe UI Symbol",
                Font.PLAIN,
                42
        ));

        logo.setForeground(Ui.ACCENT);

        g.gridy = 0;
        g.insets = new Insets(0, 0, 15, 0);

        card.add(logo, g);


        // =========================
        // Title
        // =========================

        JLabel title = new JLabel(
                "Tailor Shop",
                SwingConstants.CENTER
        );

        title.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                38
        ));

        title.setForeground(Ui.TEXT);

        g.gridy = 1;
        g.insets = new Insets(0, 0, 8, 0);

        card.add(title, g);


        // =========================
        // Subtitle
        // =========================

        JLabel subtitle = new JLabel(
                "Order & Measurement Management",
                SwingConstants.CENTER
        );

        subtitle.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                17
        ));

        subtitle.setForeground(Ui.MUTED);

        g.gridy = 2;
        g.insets = new Insets(0, 0, 20, 0);

        card.add(subtitle, g);


        // =========================
        // Divider
        // =========================

        JLabel divider = new JLabel(
                "────────  ✂  ────────",
                SwingConstants.CENTER
        );

        divider.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                16
        ));

        divider.setForeground(Ui.ACCENT);

        g.gridy = 3;
        g.insets = new Insets(0, 0, 28, 0);

        card.add(divider, g);


        // =========================
        // Username label
        // =========================

        JLabel userLabel = new JLabel("Username");

        userLabel.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                15
        ));

        userLabel.setForeground(Ui.TEXT);

        g.gridy = 4;
        g.insets = new Insets(0, 0, 8, 0);

        card.add(userLabel, g);


        // =========================
        // Username field
        // =========================

        styleField(user);

        user.setToolTipText("Enter your username");

        g.gridy = 5;
        g.insets = new Insets(0, 0, 20, 0);

        card.add(user, g);


        // =========================
        // Password label
        // =========================

        JLabel passLabel = new JLabel("Password");

        passLabel.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                15
        ));

        passLabel.setForeground(Ui.TEXT);

        g.gridy = 6;
        g.insets = new Insets(0, 0, 8, 0);

        card.add(passLabel, g);


        // =========================
        // Password field
        // =========================

        styleField(pass);

        pass.setToolTipText("Enter your password");

        g.gridy = 7;
        g.insets = new Insets(0, 0, 28, 0);

        card.add(pass, g);


        // =========================
        // Login button
        // =========================

        JButton login = Ui.button("LOGIN");

        login.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                17
        ));

        login.setPreferredSize(
                new Dimension(480, 58)
        );

        g.gridy = 8;
        g.insets = new Insets(0, 0, 20, 0);

        card.add(login, g);


        // =========================
        // Demo login
        // =========================

        JLabel hint = new JLabel(
                "Demo login: admin / admin123",
                SwingConstants.CENTER
        );

        hint.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                14
        ));

        hint.setForeground(Ui.MUTED);

        g.gridy = 9;
        g.insets = new Insets(0, 0, 0, 0);

        card.add(hint, g);


        // Add card to screen
        root.add(card);

        setContentPane(root);


        // Login action
        login.addActionListener(e -> doLogin());

        getRootPane().setDefaultButton(login);
    }


    // =========================
    // Field styling
    // =========================

    private void styleField(JTextField field) {

        field.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                16
        ));

        field.setPreferredSize(
                new Dimension(480, 52)
        );

        field.setBackground(Color.WHITE);

        field.setForeground(Ui.TEXT);

        field.setCaretColor(Ui.ACCENT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 201, 198),
                                1
                        ),
                        new EmptyBorder(
                                8, 14, 8, 14
                        )
                )
        );
    }


    // =========================
    // Login
    // =========================

    private void doLogin() {

        try {

            String name = new AuthDAO().login(
                    user.getText().trim(),
                    new String(pass.getPassword())
            );

            if (name != null) {

                dispose();

                new DashboardFrame(name).setVisible(true);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password.",
                        "Login",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}