package info_hub_management;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submitIssue")
public class ReportIssueServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description");
        int reportedBy = Integer.parseInt(request.getParameter("reportedBy"));
        String reportedDate = request.getParameter("reportedDate");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/info_hub", "root", "root");

            String sql = "INSERT INTO issue (description, reportedBy, reportedDate) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, description);
            pstmt.setInt(2, reportedBy);
            pstmt.setDate(3, java.sql.Date.valueOf(reportedDate));

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failed");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().write("failed");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
