package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DbConnector;
import database.User;
import database.Cases;

/**
 * Servlet implementation class addTestRecordServlet
 */
@WebServlet("/addTestRecordServlet")
public class addTestRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public addTestRecordServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get User Data from Session
		final HttpSession session = request.getSession();
		final User sessionUser = (User) session.getAttribute("user");
				
		
		// Get HTTP Request Parameters
		final String useridstr = request.getParameter("userid");
		if (sessionUser == null || useridstr == null) {
			// Redirect User to Login Page
			response.sendRedirect( "Login.html"); 
		}
		
		final String diagMethodIDstr = request.getParameter("test-method");
		final String diagDatestr = request.getParameter("diagDate");
		final String diagLocstr = request.getParameter("diagLoc");
		final String diagRepUIDstr = request.getParameter("diagRepUID");
		
		try {
			// Process Data
			final Integer userid = Integer.parseInt(useridstr);
			final Integer diagMethodID = Integer.parseInt(diagMethodIDstr);
			final String dateFormat = "yyyy/MM/dd";
			final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			final Date diagDate = sdf.parse(diagDatestr);

			// Store Record
			final DbConnector db = DbConnector.getInstance();
			db.openDbConnection();
			if (diagMethodID == 1) 
			{
				db.insertCase(new Cases(null, userid, diagDate, diagMethodID, null, null));
			}
			else if (diagMethodID == 2)
			{
				if (diagLocstr.length() > 1)
				{
					db.insertCase(new Cases(null, userid, diagDate, diagMethodID, diagLocstr, null));
					response.setContentType("text/html");
					response.getWriter().append("Record Added Succesfully ! <a href=\"Login\">Go to main page.</a>");

				}
				else 
				{
					final String errMsg = "You must fill the Diagnosis Location field for the Rapid Test !";
					response.getWriter().append(errMsg);
				}
			}
			else if (diagMethodID == 3)
			{
				if (diagRepUIDstr.length() > 1)
				{
					db.insertCase(new Cases(null, userid, diagDate, diagMethodID, null, diagRepUIDstr));
					response.setContentType("text/html");
					response.getWriter().append("Record Added Succesfully ! <a href=\"Login\">Go to main page.</a>");
				}
				else 
				{
					final String errMsg = "You must fill the Diagnosis Report UID field for the PCR Test !";
					response.getWriter().append(errMsg);
				}
				
			}
			db.closeDbConnection();
			
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
