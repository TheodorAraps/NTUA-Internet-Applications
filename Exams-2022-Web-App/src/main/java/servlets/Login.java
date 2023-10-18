package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DbConnector;
import database.User;
import database.CountPerCategory;
import database.Cases;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
			response.sendRedirect("Login.html"); 
			
		} else {
			
			if (sessionUser.getRoleID() == 2) {

				final List<CountPerCategory> CountPerCategList;
				try {
					// Get ALL objects {Test Name, Count}
					DbConnector.getInstance().openDbConnection();
					CountPerCategList = DbConnector.getInstance().getAdminInfo();
					DbConnector.getInstance().closeDbConnection();
				} catch (Throwable t) {
					// Inform the user in case of an Error
					final String errMsg = "Error ... " + t.getMessage();
					response.getWriter().append(errMsg);
					t.printStackTrace();
					return;
				}
				
				response.setContentType("text/html; charset=UTF-8");
				final PrintWriter out = response.getWriter();
				
				out.println("<!DOCTYPE html>");
	            out.println("<html><head>");
	            out.println("<title>Admin Page</title>\n");
	            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/adminPageStyle.css\" >");
	            out.println("</head><body>");
	            
	            out.print("<p>Hello: " + sessionUser.getUsername() + " <a href=\"LogoutServlet\">Logout</a></p>");
	            
				out.print("<h1>Total Number of Diagnosis Test Methods Per Category</h1>");
				out.println("<table>");
				out.println("<tr>");
				out.println("<th>Diagnosis Test Method</th>");
				out.println("<th>Total Number of Tests</th>");
				out.println("</tr>");
				for (CountPerCategory cpc : CountPerCategList) {
					out.println("<tr>");
					
					out.println("<td>" + cpc.getName() + "</td>");
					out.println("<td>" + cpc.getCount() + "</td>");
				}
				out.println("</table>");
				out.println("</body></html>");
				out.println("<p><a href=\"UpdatePassword\">Change Password</a></p>");
	
				out.close();
			}
			else
			{
				final List<Cases> CasesList;
				try {
					// Get ALL vehicles
					DbConnector.getInstance().openDbConnection();
					CasesList = DbConnector.getInstance().getCases(sessionUser.getId());
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
	            out.println("<title>My Tests</title>\n");
	            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/adminPageStyle.css\" >");
	            out.println("</head><body>");
	            
	            out.print("<p>Hello: " + sessionUser.getUsername() + " <a href=\"LogoutServlet\">Logout</a></p>");
	            
				out.print("<h1>The List of your Tests</h1>");
				out.println("<table>");
				out.println("<tr>");
				out.println("<th>Date</th>");
				out.println("<th>Location</th>");
				out.println("<th>Type of Test</th>");
				out.println("</tr>");
				for (Cases Case : CasesList) {
					out.println("<tr>");
									
					out.println("<td>" + Case.getDiagnosisDate() + "</td>");
					out.println("<td>" + Case.getDiagnosisLocation() + "</td>");
					out.println("<td>" + Case.getName() + "</td>");
				}
				
				out.println("</table>");
				out.println("<br>");
				out.println("<form action=\"RecordStatement.jsp\" method=\"POST\">"
						+ "<input type=\"hidden\" name=\"userID\" value=\"" + sessionUser.getId() + "\">"
						+ "<input type=\"submit\" value=\"Record New Test\">"
						+ "</form>");
				out.println("</body></html>");
				out.println("<p><a href=\"UpdatePassword\">Change Password</a></p>");

				out.close();
			}
		}
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
