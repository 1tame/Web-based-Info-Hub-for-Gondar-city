package info_hub_management;


import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@MultipartConfig
@WebServlet("/HealthSectorInfoServlet")
public class HealthSectorInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String contact = request.getParameter("contact");
        String operatingHours = request.getParameter("operatingHours");
        Part imagePart = request.getPart("image");

        // Saving the image file to the server and getting its path
        String imageFileName = extractFileName(imagePart);
        String savePath = "C:\\uploaded_images" + File.separator + imageFileName;
        //File fileSaveDir = new File(savePath);
        imagePart.write(savePath);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/info_hub", "root", "password");
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "INSERT INTO healthsectorinfo (title, description, location, contact, operatingHours, image) VALUES (?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, contact);
            preparedStatement.setString(5, operatingHours);
            preparedStatement.setString(6, savePath);

            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                response.getWriter().println("Health Sector Information inserted successfully.");
            } else {
                response.getWriter().println("Failed to insert Health Sector Information.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error occurred.");
        }
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
