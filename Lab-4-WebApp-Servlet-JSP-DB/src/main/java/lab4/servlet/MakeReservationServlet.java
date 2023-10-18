package lab4.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab4.db.DbConnector;
import lab4.db.model.Reservation;
import lab4.db.model.User;

/**
 * Servlet implementation class MakeReservationServlet
 */
@WebServlet("/MakeReservationServlet")
public class MakeReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeReservationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get User Data from Session
		final HttpSession session = request.getSession();
		final User sessionUser = (User) session.getAttribute("user");
				
		if (sessionUser == null) {
			// Redirect User to Login Page
			response.sendRedirect( "Login.html"); 
		}
		
		// Get HTTP Request Parameters
		final String useridstr = request.getParameter("userid");
		final String vehicleidstr = request.getParameter("vehicleid");
		final String startdateStr = request.getParameter("startdate");
		final String enddateStr = request.getParameter("enddate");
		final String comments = request.getParameter("comments");
		
		try {
			// Process Data
			final Integer userid = Integer.parseInt(useridstr);
			final Integer vehicleid = Integer.parseInt(vehicleidstr);
			final String dateFormat = "yyyy/MM/dd";
			final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			final Date startdate = sdf.parse(startdateStr);
			final Date enddate = sdf.parse(enddateStr);
			
			// Store Reservation
			final DbConnector db = DbConnector.getInstance();
			db.openDbConnection();
			db.insertReservation(new Reservation(userid, vehicleid, startdate, enddate, comments));
			db.closeDbConnection();
			
			response.setContentType("text/html");
			response.getWriter().append("Reservation Successfully made ! <a href=\"ViewVehiclesServlet\">Go to main page.</a>");
			
		} catch (Throwable t) {
			// Inform the user in case of an Error
			final String errMsg = "Storing Reservation Problem ... " + t.getMessage() + " Ask system administrators for details !";
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
