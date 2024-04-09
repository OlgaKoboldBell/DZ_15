package org.example.demo5;

import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "registerServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Підключення до бази даних
        String url = "jdbc:mysql://localhost:3306/quiz";
        String dbUsername = "root";
        String dbPassword = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Вставка нового користувача в таблицю
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            con.close();

            // Встановлення атрибуту для відображення повідомлення про успішну реєстрацію
            request.setAttribute("registrationStatus", "Реєстрація успішно відбулася");
            request.getRequestDispatcher("/index.jsp").forward(request, response); // Перенаправлення на індекс-сторінку
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
