package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.ExamDbConnector;
//import servlets.Util;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get Session Data
		final HttpSession session = request.getSession();
		
		// Check User Data if being necessary
		if (session.getAttribute("user") == null) {
			// Get Request Parameters
			final String username = request.getParameter("username");
			final String password = request.getParameter("password");
			
			try {
				// Find User (if any)
				final ExamDbConnector db = new ExamDbConnector();
				db.openDbConnection();
				final boolean exists = db.examineUser(username, Util.getHash256(password));
				db.closeDbConnection();
				
				// Update Session Data on condition that the User was found
				if (exists) {
					session.setAttribute("user", username);
				}
			
			} catch (Throwable t) {
				// Inform the user in case of an Error
				final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
				response.getWriter().append(errMsg);
				t.printStackTrace();
				return;
			}
		}
		
		// Get User Data from Session
		final String sessionUser = (String) session.getAttribute("user");
		
		if (sessionUser == null) {
			// Redirect User to Login Page
			response.sendRedirect("Login.html"); 
			
		} else {
			// Redirect User to Login Page
			response.sendRedirect("InsertData.html");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
