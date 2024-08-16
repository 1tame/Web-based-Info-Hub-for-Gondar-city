package info_hub_management;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/info_hub";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yourpassword";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL query to check login credentials
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ? AND sector = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);

            // Execute the query
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Successful login
                String sector = rs.getString("sector");

                // Redirect based on the sector
                if ("systemAdmin".equals(sector)) {
                    response.sendRedirect("sysAdminHomePage.html");
                } else if ("waterSupplyAdmin".equals(sector)) {
                    response.sendRedirect("Water_Supply_Admin_Dashboard.html");
                } else if ("healthSectorAdmin".equals(sector)) {
                    response.sendRedirect("Health_Sector_Admin_Dashboard.html");
                } else if ("securityAdmin".equals(sector)) {
                    response.sendRedirect("Security_Admin_Dashboard.html");
                } else if ("tourismHotelAdmin".equals(sector)) {
                    response.sendRedirect("Tourism_and_Hotel_Admin_Dashboard.html");
                } 
                else if ("festivalEventAdmin".equals(sector)) {
                    response.sendRedirect("Festival_and_Event_Admin_Dashboard.html");
                } else if ("transportationAdmin".equals(sector)) {
                    response.sendRedirect("Tranportation_Admin_Dashboard.html");
                } 
            } else {
                // Login failed, error message and forward to login page
                request.setAttribute("errorMessage", "Invalid username or password. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.html");
                dispatcher.forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.html");
            dispatcher.forward(request, response);
        } 
        }
    }

