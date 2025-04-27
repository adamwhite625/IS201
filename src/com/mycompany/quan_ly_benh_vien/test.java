package com.mycompany.quan_ly_benh_vien;

import javax.swing.*;
import java.awt.*;

public class test extends JFrame {

    public test() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 160, 1950, 870); // Điều chỉnh vị trí và chiều cao để tạo khoảng cách
        panel.setBackground(new Color(109, 164, 170));
        add(panel);

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(0, 0, 1950, 150); // Giữ nguyên chiều cao tiêu đề
        panel1.setBackground(new Color(109, 164, 170));
        add(panel1);

        setSize(1950, 1080);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new test();
    }
}