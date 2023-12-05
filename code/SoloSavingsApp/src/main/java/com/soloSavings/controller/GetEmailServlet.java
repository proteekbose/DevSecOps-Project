package com.soloSavings.controller;


import jakarta.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/solosavings/GetEmailServlet")
public class GetEmailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("hi");
    /*    String username = request.getParameter("username");

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String jdbcUrl = "jdbc:mysql://localhost:3306/your_database";
            String dbUsername = "your_username";
            String dbPassword = "your_password";
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Query the database
            String sql = "SELECT email FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String email = resultSet.getString("email");
                    PrintWriter out = response.getWriter();
                    out.print(email);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }

            // Close the database connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }*/
    }
}
