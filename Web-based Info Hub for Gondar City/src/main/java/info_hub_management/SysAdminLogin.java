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

/**
 * Servlet implementation class SysAdminLogin
 */
@WebServlet("/SysAdminLogin")
public class SysAdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SysAdminLogin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uname = request.getParameter("username");
        String pwd = request.getParameter("password");

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/info_hub", "root", "root");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM admin WHERE username = ? and password = ?");

            ps.setString(1, uname);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                System.out.println("Redirecting to sysAdmin_page.jsp");
                response.sendRedirect("sysAdmin_page.jsp");
            } 
            else {
                System.out.println("Incorrect username or password");
                response.getWriter().write("Incorrect username or password");
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
		
		
	}


