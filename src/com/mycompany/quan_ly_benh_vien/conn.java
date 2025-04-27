/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quan_ly_benh_vien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class conn {

Connection connection;

    public conn() {

        try {
            // Kết nối đến cơ sở dữ liệu
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_ly_benh_vien", "root", "");
            connection.createStatement();
        } catch (SQLException e) {
        }
    }

    public static void main(String[] args) {
        conn c = new conn();
        if (c.connection != null) {
            System.out.println("Kết nối thành công!");
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}



