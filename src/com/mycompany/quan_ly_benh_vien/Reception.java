package com.mycompany.quan_ly_benh_vien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reception extends JFrame {

    public Reception() {
        // Panel chính (nền màu trắng)
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 160, 1950, 870);
        panel.setBackground(Color.WHITE);
        add(panel);

        // Panel tiêu đề (nền màu Sky Blue)
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(0, 0, 1950, 150);
        panel1.setBackground(new Color(135, 206, 235)); // Sky Blue
        add(panel1);

        // Hình ảnh bác sĩ
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/dr.png"));
        Image image = i1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(image);
        JLabel label = new JLabel(i2);
        label.setBounds(1300, 0, 250, 250);
        panel1.add(label);

        // Hình ảnh xe cứu thương
        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("icon/amb.png"));
        Image image1 = i11.getImage().getScaledInstance(300, 100, Image.SCALE_DEFAULT);
        ImageIcon i22 = new ImageIcon(image1);
        JLabel label1 = new JLabel(i22);
        label1.setBounds(1000, 50, 300, 100);
        panel1.add(label1);

        // Các nút chức năng
        JButton btn1 = new JButton("Thêm Bệnh Nhân");
        btn1.setBounds(30, 15, 200, 30);
        btn1.setBackground(new Color(246, 215, 118));
        panel1.add(btn1);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    NewPatient newPatient = new NewPatient();
                    newPatient.setVisible(true); // Hiển thị giao diện thêm bệnh nhân
                });
            }
        });

        JButton btn2 = new JButton("Phòng");
        btn2.setBounds(30, 58, 200, 30);
        btn2.setBackground(new Color(246, 215, 118));
        panel1.add(btn2);

        JButton btn3 = new JButton("Khoa");
        btn3.setBounds(30, 100, 200, 30);
        btn3.setBackground(new Color(246, 215, 118));
        panel1.add(btn3);

        JButton btn4 = new JButton("Thông Tin Nhân Viên");
        btn4.setBounds(270, 15, 200, 30);
        btn4.setBackground(new Color(246, 215, 118));
        panel1.add(btn4);

        JButton btn5 = new JButton("Thông Tin Bệnh Nhân");
        btn5.setBounds(270, 58, 200, 30);
        btn5.setBackground(new Color(246, 215, 118));
        panel1.add(btn5);

        JButton btn6 = new JButton("Xuất Viện");
        btn6.setBounds(270, 100, 200, 30);
        btn6.setBackground(new Color(246, 215, 118));
        panel1.add(btn6);

        JButton btn7 = new JButton("Cập Nhật Bệnh Nhân");
        btn7.setBounds(510, 15, 200, 30);
        btn7.setBackground(new Color(246, 215, 118));
        panel1.add(btn7);

        JButton btn8 = new JButton("Xe Cứu Thương");
        btn8.setBounds(510, 58, 200, 30);
        btn8.setBackground(new Color(246, 215, 118));
        panel1.add(btn8);

        JButton btn9 = new JButton("Tìm Phòng");
        btn9.setBounds(510, 100, 200, 30);
        btn9.setBackground(new Color(246, 215, 118));
        panel1.add(btn9);

        JButton btn10 = new JButton("Đăng Xuất");
        btn10.setBounds(750, 15, 200, 30);
        btn10.setBackground(new Color(246, 215, 118));
        panel1.add(btn10);
        btn10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JButton btnDatLich = new JButton("Đặt Lịch Khám");
        btnDatLich.setBounds(750, 58, 200, 30);
        btnDatLich.setBackground(new Color(246, 215, 118));
        panel1.add(btnDatLich);

        btnDatLich.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new DatLichKham().setVisible(true));
        });

        setSize(1950, 1080);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Reception();
    }
}
