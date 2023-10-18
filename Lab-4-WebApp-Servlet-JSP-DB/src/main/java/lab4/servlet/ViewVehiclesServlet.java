package lab4.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lab4.db.DbConnector;
import lab4.db.model.Vehicle;
import lab4.db.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/ViewVehiclesServlet")
public class ViewVehiclesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewVehiclesServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
				DbConnector.getInstance().openDbConnection();
				final User user = DbConnector.getInstance().getUser(username, Util.getHash256(password));
				DbConnector.getInstance().closeDbConnection();
				
				// Update Session Data on condition that the User was found
				if (user != null) {
					session.setAttribute("user", user);
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
		final User sessionUser = (User) session.getAttribute("user");
		
		if (sessionUser == null) {
			// Redirect User to Login Page
			response.sendRedirect( "Login.html"); 
		} else {
			final List<Vehicle> vehicleList;
			try {
				// Get ALL vehicles
				DbConnector.getInstance().openDbConnection();
				vehicleList = DbConnector.getInstance().getVehicles();
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
            out.println("<title>Vehicles</title>\n");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/WebApp2.css\" >");
            out.println("</head><body>");
            
            out.print("<p>Hello: " + sessionUser.getUsername() + " <a href=\"LogoutServlet\">Logout</a></p>");
            
			out.print("<h1>View Vehicles</h1>");
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>Type</th>");
			out.println("<th>Image</th>");
			out.println("<th>ID</th>");
			out.println("<th>Name</th>");
			out.println("<th>Description</th>");
			out.println("<th>Action</th>");
			out.println("</tr>");
			for (Vehicle vehicle : vehicleList) {
				out.println("<tr>");
				if (vehicle.getImagePath() != null && !vehicle.getImagePath().trim().equals("")) {
					out.println("<td><img src=\"" + vehicle.getImagePath().trim() + "\" height=\"40\"></td>");
				} else {
					out.println("<td><br></td>");
				}
				
				out.println("<td>" + vehicle.getProductTypeAsString() + "</td>");
				out.println("<td>" + vehicle.getId() + "</td>");
				out.println("<td>" + vehicle.getName() + "</td>");
				out.println("<td>" + vehicle.getDescription() + "</td>");
				out.println("<td><form action=\"PrepareReservation.jsp\" method=\"POST\">"
						+ "<input type=\"hidden\" name=\"vehicleid\" value=\"" + vehicle.getId() + "\">"
						+ "<input type=\"submit\" value=\"Make a Reservation\">"
						+ "</form></td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</body></html>");
			out.println("<a href=\"ViewReservationsServlet\">View My Reservations</a>");

			out.close();
		}
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
