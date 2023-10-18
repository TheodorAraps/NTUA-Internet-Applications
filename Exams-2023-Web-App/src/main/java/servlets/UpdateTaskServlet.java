 package servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DbConnector;
import database.User;


/**
 * Servlet implementation class addTestRecordServlet
 */
@WebServlet("/UpdateTaskServlet")
public class UpdateTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UpdateTaskServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get User Data from Session
		final HttpSession session = request.getSession();
		final User sessionUser = (User) session.getAttribute("user");
				
		
		// Get HTTP Request Parameters
		final String taskIDstr = request.getParameter("taskID");
		final String statusIDstr = request.getParameter("statusID");

		if (sessionUser == null || taskIDstr == null || statusIDstr == null) {
			// Redirect User to Login Page
			response.sendRedirect( "Login.html"); 
		}
		
		
		try {
			// Process Data
			final Integer taskID = Integer.parseInt(taskIDstr);
			final Integer statusID = Integer.parseInt(statusIDstr);

			// Store Record
			final DbConnector db = DbConnector.getInstance();
			db.openDbConnection();
			db.updateTaskStatus(taskID, statusID);
			db.closeDbConnection();
			response.setContentType("text/html");
			response.getWriter().append("DB Updated Succesfully ! <a href=\"ViewTasks\">Go to ViewTasks page.</a>");
			
		} catch (Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Storing Record Problem ... { " + t.getMessage() + " } Ask system administrators for details !";
			response.getWriter().append(errMsg);
			t.printStackTrace();
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
