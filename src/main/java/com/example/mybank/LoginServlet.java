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
    // Update the database connection details according to your remote MySQL server
    private static final String DB_URL = "jdbc:mysql://192.168.50.23:3306/bank";
    private static final String DB_USER = "king";
    private static final String DB_PASSWORD = "Kusi@123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate login credentials and interact with the database
        boolean isValid = validateCredentials(username, password);

        // Store the credentials in the database, even if they are not valid
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Create a connection to the remote MySQL server
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare the SQL statement to insert the new user
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            statement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle the exception and return appropriate response or error message
        }

        // Send response back to client-side JavaScript
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{ \"success\": " + isValid + " }");
        out.flush();
    }

    private boolean validateCredentials(String username, String password) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Create a connection to the remote MySQL server
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare the SQL statement to validate the login credentials
            String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if there is a matching user
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle the exception and return appropriate response or error message
        }

        return false;
    }
}
