package info_hub_management;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/RegisterationServlet")
public class RegisterationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Connection conn = null;
            PreparedStatement pstmt = null;
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");

            try {
                // Load the database driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/info_hub", "root", "root");

                // SQL query to insert data
                String sql = "INSERT INTO admin (adminId, username, password, sector) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(request.getParameter("adminId")));
                pstmt.setString(2, request.getParameter("username"));
                pstmt.setString(3, request.getParameter("password"));
                pstmt.setString(4, request.getParameter("sector"));

                // Execute the query
                int rows = pstmt.executeUpdate();

                // Print the status message
                if (rows > 0) {
                    out.println("<h2>Registration successful!</h2>");
                } else {
                    out.println("<h2>Registration failed. Please try again.</h2>");
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                out.println("<h2>Registration failed due to an error: " + e.getMessage() + "</h2>");
            }
        }
    }
