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

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/dev";
    private static final String DB_USERNAME = "dev";
    private static final String DB_PASSWORD = "Kusi@123";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Show the login form
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Login</h1>");
        out.println("<form action=\"/login\" method=\"post\">");
        out.println("<input type=\"text\" name=\"username\" placeholder=\"Username\">");
        out.println("<input type=\"password\" name=\"password\" placeholder=\"Password\">");
        out.println("<input type=\"submit\" value=\"Login\">");
        out.println("</form>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the username and password from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check the username and password against the database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // The username and password are correct, so redirect to the dashboard page
                response.sendRedirect(request.getContextPath() + "/dashboard.html");
            } else {
                // The username and password are incorrect, so display an error message
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<h1>Login failed</h1>");
                out.println("The username and password are incorrect. Please try again.");
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
