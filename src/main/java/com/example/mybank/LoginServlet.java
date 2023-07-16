package com.example.mybank;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://192.168.50.23:3306/bank";
    private static final String DB_USER = "king";
    private static final String DB_PASSWORD = "Kusi@123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate login credentials and interact with the database
        boolean isValid = validateCredentials(username, password);

        // Store the credentials in the database
        try {
            insertUser(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            sendErrorResponse(response, "Error inserting user into the database.");
            return;
        }

        // Redirect after a successful login
        if (isValid) {
            response.sendRedirect(request.getContextPath() + "/dashboard.html");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.html");
        }
    }

    private boolean validateCredentials(String username, String password) {
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ? AND password = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if there is a matching user
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception and return appropriate response or error message
        }
        return false;
    }

    private void insertUser(String username, String password) throws SQLException {
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        }
    }

    private Connection createConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Failed to load MySQL JDBC driver.");
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Error</h1>");
        out.println("<p>" + errorMessage + "</p>");
        out.close();
    }
}
