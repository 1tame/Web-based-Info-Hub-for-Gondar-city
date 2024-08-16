package info_hub_management;

import java.io.File;
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
import javax.servlet.http.Part;

/**
 * Servlet implementation class EventFestivalInfoServlet
 */
@WebServlet("/EventFestivalInfoServlet")
public class EventFestivalInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String contact = request.getParameter("contact");
        String operatingHours = request.getParameter("operatingHours");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String organizer = request.getParameter("organizer");
        String ticketInformation = request.getParameter("ticketInformation");
        Part imagePart = request.getPart("image");

        // Saving the image file to the server and getting its path
        String imageFileName = extractFileName(imagePart);
        String savePath = "C:\\uploaded_images" + File.separator + imageFileName;
       // File fileSaveDir = new File(savePath);
        imagePart.write(savePath);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/info_hub", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "INSERT INTO eventfestivalinformation (title, description, location, contact, operatingHours, startDate, endDate, organizer, ticketInformation, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, contact);
            preparedStatement.setString(5, operatingHours);
            preparedStatement.setString(6, startDate);
            preparedStatement.setString(7, endDate);
            preparedStatement.setString(8, organizer);
            preparedStatement.setString(9, ticketInformation);
            preparedStatement.setString(10, savePath);

            int row = preparedStatement.executeUpdate();
            
            if (row > 0) {
                response.getWriter().println("Event Festival Information inserted successfully.");
            } else {
                response.getWriter().println("Failed to insert Event Festival Information.");
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