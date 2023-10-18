package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbConnector;
import database.User;
//import servlets.Util;

/**
 * Servlet implementation class UpdateUserPass
 */
@WebServlet("/UpdateUserPass")
public class UpdateUserPass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserPass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get HTTP Request Parameters
		final String username = request.getParameter("username");
		final String oldPass = request.getParameter("oldPassword");
		final String newPass = request.getParameter("newPassword");
		
		// Check Given Data 
		if (newPass == null || newPass.trim().equals("")) {
			// Inform the user about the problem
			response.getWriter().append("Invalid Password Format!");
			return;
		} 
		try {
			// Find User (if any)
			DbConnector.getInstance().openDbConnection();
			final User user = DbConnector.getInstance().getUser(username, Util.getHash256(oldPass));
			if (user != null) {
				DbConnector.getInstance().updateUserPassword(new User(username, Util.getHash256(newPass)));
				DbConnector.getInstance().closeDbConnection();
				response.getWriter().append("Password Updated Successfully");
				// Redirect User to Login Page
				response.sendRedirect( "Login.html");
			}
			else
			{
				DbConnector.getInstance().closeDbConnection();
				// Inform the user in case of an Error
				final String errMsg = "Error: Wrong username or password";
				response.getWriter().append(errMsg);
				return;
			}
			
			
		
		} catch (Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
			response.getWriter().append(errMsg);
			t.printStackTrace();
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
