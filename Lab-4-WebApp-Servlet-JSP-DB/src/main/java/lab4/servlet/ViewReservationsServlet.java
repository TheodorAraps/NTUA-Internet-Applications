package lab4.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab4.db.DbConnector;
import lab4.db.model.User;
import lab4.db.model.Reservation;


@WebServlet("/ViewReservationsServlet")
public class ViewReservationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ViewReservationsServlet() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// Get Session Data
		final HttpSession session = request.getSession();
		
		// Get User Data from Session
		final User sessionUser = (User) session.getAttribute("user");
		
		if (sessionUser == null) {
			// Redirect User to Login Page
			response.sendRedirect( "Login.html"); 
		} else {
			final List<Reservation> resList;
			try {
				// Get ALL vehicles
				DbConnector.getInstance().openDbConnection();
				resList = DbConnector.getInstance().getReservations(sessionUser.getId());
				DbConnector.getInstance().closeDbConnection();
			} catch (Throwable t) {
				// Inform the user in case of an Error
				final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
				response.getWriter().append(errMsg);
				t.printStackTrace();
				return;
			}
			
			response.setContentType("text/html; charset=UTF-8");
			final PrintWriter out = response.getWriter();
			
			out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<title>My Reservations</title>\n");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/WebApp2.css\" >");
            out.println("</head><body>");
            
            out.print("<p>Hello: " + sessionUser.getUsername() + " <a href=\"LogoutServlet\">Logout</a></p>");
            
			out.print("<h1>My Reservations</h1>");
			
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>UserID</th>");
			out.println("<th>VehicleID</th>");
			out.println("<th>START</th>");
			out.println("<th>END</th>");
			out.println("<th>Comments</th>");
			out.println("</tr>");
			
			final String dateFormat = "yyyy/MM/dd";
			final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			
			for (Reservation res : resList) {
				out.println("<tr>");

				out.println("<td>" + res.getUserId() + "</td>");
				out.println("<td>" + res.getProductId() + "</td>");
				out.println("<td>" + sdf.format(res.getStartDate()) + "</td>");
				out.println("<td>" + sdf.format(res.getEndDate()) + "</td>");
				out.println("<td>" + res.getComments() + "</td>");
				
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</body></html>");
			
			out.close();
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
