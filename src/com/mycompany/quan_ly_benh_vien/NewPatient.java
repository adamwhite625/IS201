package com.mycompany.quan_ly_benh_vien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewPatient extends JFrame {

    private JTextField txtMaBN, txtHoTen, txtNgaySinh, txtSDT, txtDiaChi, txtEmail, txtCCCD;
    private JRadioButton rbNam, rbNu, rbKhac;
    private JButton btnThem, btnQuayLai;

    public NewPatient() {
        setTitle("Thêm Bệnh Nhân");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thêm các thành phần giao diện
        panel.add(new JLabel("Mã bệnh nhân:"));
        txtMaBN = new JTextField();
        panel.add(txtMaBN);

        panel.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField();
        panel.add(txtHoTen);

        panel.add(new JLabel("Ngày sinh (yyyy-MM-dd):"));
        txtNgaySinh = new JTextField();
        panel.add(txtNgaySinh);

        panel.add(new JLabel("Số điện thoại:"));
        txtSDT = new JTextField();
        panel.add(txtSDT);

        panel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        panel.add(txtDiaChi);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("CCCD:"));
        txtCCCD = new JTextField();
        panel.add(txtCCCD);

        panel.add(new JLabel("Giới tính:"));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbNam = new JRadioButton("Nam");
        rbNu = new JRadioButton("Nữ");
        rbKhac = new JRadioButton("Khác");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbNam);
        genderGroup.add(rbNu);
        genderGroup.add(rbKhac);
        genderPanel.add(rbNam);
        genderPanel.add(rbNu);
        genderPanel.add(rbKhac);
        panel.add(genderPanel);

        btnThem = new JButton("Thêm");
        btnQuayLai = new JButton("Quay Lại");
        panel.add(btnThem);
        panel.add(btnQuayLai);

        add(panel); // Thêm panel vào JFrame

        // Xử lý sự kiện nút "Thêm"
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maBN = txtMaBN.getText().trim();
                String hoTen = txtHoTen.getText().trim();
                String ngaySinh = txtNgaySinh.getText().trim();
                String sdt = txtSDT.getText().trim();
                String diaChi = txtDiaChi.getText().trim();
                String email = txtEmail.getText().trim();
                String cccd = txtCCCD.getText().trim();
                String gioiTinh = rbNam.isSelected() ? "Nam" : rbNu.isSelected() ? "Nữ" : "Khác";

                if (maBN.isEmpty() || hoTen.isEmpty() || ngaySinh.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || email.isEmpty() || cccd.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                themBenhNhan(maBN, hoTen, ngaySinh, gioiTinh, sdt, diaChi, email, cccd);
            }
        });

        // Xử lý sự kiện nút "Quay Lại"
        btnQuayLai.addActionListener(e -> setVisible(false));
    }

    private static Connection connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/quan_ly_benh_vien";
            String user = "root";
            String password = "";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kết nối CSDL thất bại!");
            return null;
        }
    }

    private static void themBenhNhan(String maBN, String hoTen, String ngaySinh, String gioiTinh, String sdt, String diaChi, String email, String cccd) {
        Connection conn = connect();
        if (conn != null) {
            try {
                // Kiểm tra xem mã bệnh nhân đã tồn tại hay chưa
                String checkSql = "SELECT COUNT(*) FROM BENHNHAN WHERE MaBN = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, maBN);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, "Mã bệnh nhân đã tồn tại!");
                        return;
                    }
                }

                // Thêm bệnh nhân mới
                String sql = "INSERT INTO BENHNHAN (MaBN, HoTen, NgaySinh, GioiTinh, SDT, DiaChi, Email, CCCD) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, maBN);
                    pstmt.setString(2, hoTen);
                    pstmt.setString(3, ngaySinh);
                    pstmt.setString(4, gioiTinh);
                    pstmt.setString(5, sdt);
                    pstmt.setString(6, diaChi);
                    pstmt.setString(7, email);
                    pstmt.setString(8, cccd);

                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Thêm bệnh nhân thành công!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm bệnh nhân không thành công!");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm bệnh nhân!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NewPatient().setVisible(true);
        });

        JButton btn1 = new JButton("Open New Patient");
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewPatient newPatient = new NewPatient();
                newPatient.setVisible(true); // Hiển thị giao diện thêm bệnh nhân
            }
        });
    }
}
