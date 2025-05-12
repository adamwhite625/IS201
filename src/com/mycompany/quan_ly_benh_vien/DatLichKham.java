package com.mycompany.quan_ly_benh_vien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatLichKham extends JFrame {
    private JTextField txtMaBN, txtTenBN, txtNgayKham, txtGhiChu;
    private JComboBox<String> cboxBacSi;
    private JButton btnDatLich, btnQuayLai;

    public DatLichKham() {
        setTitle("Đặt Lịch Khám");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Mã bệnh nhân:"));
        txtMaBN = new JTextField();
        txtMaBN.addActionListener(e -> loadPatientName());
        panel.add(txtMaBN);

        panel.add(new JLabel("Tên bệnh nhân:"));
        txtTenBN = new JTextField();
        txtTenBN.setEditable(false);
        panel.add(txtTenBN);

        panel.add(new JLabel("Ngày khám (yyyy-MM-dd):"));
        txtNgayKham = new JTextField();
        panel.add(txtNgayKham);

        panel.add(new JLabel("Ghi chú:"));
        txtGhiChu = new JTextField();
        panel.add(txtGhiChu);

        panel.add(new JLabel("Bác sĩ khám:"));
        cboxBacSi = new JComboBox<>();
        loadDoctors();
        panel.add(cboxBacSi);

        btnDatLich = new JButton("Đặt lịch khám");
        btnDatLich.addActionListener(e -> datLichKham());
        panel.add(btnDatLich);

        btnQuayLai = new JButton("Quay Lại");
        btnQuayLai.addActionListener(e -> {
            this.dispose(); // Đóng cửa sổ hiện tại
            new Reception(); // Quay lại giao diện Reception
        });
        panel.add(btnQuayLai);

        add(panel);
    }

    private void loadPatientName() {
        String maBN = txtMaBN.getText().trim();
        if (maBN.isEmpty()) return;

        try (Connection conn = connect()) {
            String sql = "SELECT HoTen FROM BENHNHAN WHERE MaBN = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maBN);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                txtTenBN.setText(rs.getString("HoTen"));
            } else {
                JOptionPane.showMessageDialog(this, "Bệnh nhân không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtTenBN.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDoctors() {
        try (Connection conn = connect()) {
            String sql = "SELECT MaBS, HoTen FROM BACSI";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                cboxBacSi.addItem(rs.getString("MaBS") + " - " + rs.getString("HoTen"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách bác sĩ: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void datLichKham() {
        String maBN = txtMaBN.getText().trim();
        String ngayKhamStr = txtNgayKham.getText().trim();
        String ghiChu = txtGhiChu.getText().trim();
        String bacSiSelection = (String) cboxBacSi.getSelectedItem();

        if (maBN.isEmpty() || ngayKhamStr.isEmpty() || ghiChu.isEmpty() || bacSiSelection == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date ngayKham;
        try {
            ngayKham = sdf.parse(ngayKhamStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày khám không đúng định dạng (yyyy-MM-dd)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date today = new Date();
        if (ngayKham.before(today)) {
            JOptionPane.showMessageDialog(this, "Ngày khám phải từ hôm nay trở đi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maBS = bacSiSelection.split(" - ")[0];
        String maLich = generateMaLich();

        try (Connection conn = connect()) {
            String sql = "INSERT INTO LICHKHAM (MaLich, MaBN, MaBS, NgayKham, TrangThai, GhiChu) VALUES (?, ?, ?, ?, 'Đã đặt', ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maLich);
            stmt.setString(2, maBN);
            stmt.setString(3, maBS);
            stmt.setString(4, ngayKhamStr);
            stmt.setString(5, ghiChu);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Đặt lịch khám thành công!");
            clearFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Đặt lịch khám không thành công: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateMaLich() {
        try (Connection conn = connect()) {
            String sql = "SELECT MAX(MaLich) FROM LICHKHAM";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String lastMaLich = rs.getString(1);
                if (lastMaLich != null) {
                    int num = Integer.parseInt(lastMaLich.substring(1)) + 1;
                    return String.format("L%04d", num);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi tạo MaLich: " + ex.getMessage());
        }
        return "L0001";
    }

    private void clearFields() {
        txtMaBN.setText("");
        txtTenBN.setText("");
        txtNgayKham.setText("");
        txtGhiChu.setText("");
        cboxBacSi.setSelectedIndex(0);
    }

    private Connection connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/quan_ly_benh_vien";
            String user = "root";
            String password = "";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Kết nối CSDL thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DatLichKham().setVisible(true));
    }
}
