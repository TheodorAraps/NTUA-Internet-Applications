package lab4.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab4.db.DbConnector;
import lab4.db.model.User;

/**
 * Servlet implementation class UserRegistrationServlet
 */
@WebServlet("/CreateUserAccountServlet")
public class CreateUserAccountServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserAccountServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get HTTP Request Parameters
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		final String password2 = request.getParameter("password2");
		
		// Check Given Data 
		if (password == null || password.trim().equals("") || !password.equals(password2)) {
			// Inform the user about the problem
			response.getWriter().append("Invalid Data - Password #1 and #2 are different ! No user account created !");
			return;
		} 
		
		try {
			// Create User Account
			final DbConnector db = DbConnector.getInstance();
			db.openDbConnection();
			db.insertUser(new User(username, Util.getHash256(password)));
			db.closeDbConnection();
			
			// Redirect User to Login Page
			response.sendRedirect( "Login.html");
			
		} catch (Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Creating User Account Problem ... " + t.getMessage() + " Ask system administrators for details !";
			response.getWriter().append(errMsg);
			t.printStackTrace();
		}
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
