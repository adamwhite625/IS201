package com.mycompany.quan_ly_benh_vien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    JTextField textField;
    JPasswordField jPasswordField;
    JButton b1, b2;

    public Login() {
        setSize(900, 400); // Tăng kích thước cửa sổ
        setLocation(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLabel = new JLabel("Username");
        nameLabel.setBounds(50, 50, 150, 30);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        add(nameLabel);

        textField = new JTextField();
        textField.setBounds(200, 50, 250, 30);
        textField.setFont(new Font("Tahoma", Font.BOLD, 16));
        textField.setBackground(new Color(176, 224, 230)); // Sky Blue
        textField.setForeground(Color.BLACK);
        add(textField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 120, 150, 30);
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        passwordLabel.setForeground(Color.WHITE);
        add(passwordLabel);

        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(200, 120, 250, 30);
        jPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        jPasswordField.setBackground(new Color(176, 224, 230)); // Sky Blue
        jPasswordField.setForeground(Color.BLACK);
        add(jPasswordField);

        b1 = new JButton("Login");
        b1.setBounds(50, 200, 150, 40);
        b1.setFont(new Font("Serif", Font.BOLD, 18));
        b1.setBackground(new Color(255, 165, 0)); // Orange
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Cancel");
        b2.setBounds(250, 200, 150, 40);
        b2.setFont(new Font("Serif", Font.BOLD, 18));
        b2.setBackground(new Color(255, 165, 0)); // Orange
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        add(b2);

        // Thêm hình ảnh login.png vào phía bên phải
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBounds(500, 50, 300, 250);
        add(imageLabel);

        getContentPane().setBackground(new Color(135, 206, 235)); // Sky Blue
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                conn c = new conn();
                String user = textField.getText();
                String pass = new String(jPasswordField.getPassword());

                String q = "SELECT * FROM login WHERE ID = ? AND PW = ?";
                PreparedStatement ps = c.connection.prepareStatement(q);
                ps.setString(1, user);
                ps.setString(2, pass);
                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    new Reception();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == b2) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Login login = new Login();
    }
}
