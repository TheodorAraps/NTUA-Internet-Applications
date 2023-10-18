package servlets;

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

import database.DbConnector;
import database.Task;
import database.User;


@WebServlet("/ViewTasks")
public class ViewTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ViewTasks() {
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
				DbConnector.getInstance().openDbConnection();
				final User user = DbConnector.getInstance().examineUser(username, password);
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
			// Regular User
			if (sessionUser.getRoleID() == 2) {
				final List<Task> taskList;
				
				try {
					// Get ALL vehicles
					DbConnector.getInstance().openDbConnection();
					taskList = DbConnector.getInstance().getTasks(sessionUser.getUserID());
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
	            out.println("<title>MyTasks</title>\n");
	      
	            out.println("</head><body>");
	            
	            out.print("<p>Hello Regular User (Role ID = 2): " + sessionUser.getUsername() + " <a href=\"LogoutServlet\">Logout</a></p>");
	            
				out.print("<h1>My Tasks</h1>");
				out.println("<table>");
				out.println("<tr>");
				out.println("<th>Task ID</th>");
				out.println("<th>Title</th>");
				out.println("<th>Description</th>");
				out.println("<th>Status ID</th>");
				out.println("<th>Date Updated</th>");
				out.println("<th>Update</th>");
				out.println("</tr>");
				for (Task cpc : taskList) {
					out.println("<tr>");
					
					out.println("<td>" + cpc.getTaskID() + "</td>");
					out.println("<td>" + cpc.getTitle() + "</td>");
					out.println("<td>" + cpc.getDesc() + "</td>");
					out.println("<td>" + cpc.getStatusID() + "</td>");
					
					final String dateFormat = "dd/MM/yyyy";
					final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
					final long dateS = cpc.getDate().getTime();
					final String newDateF = sdf.format(dateS);
					
					out.println("<td>" + newDateF + "</td>");
					
					out.println("<td><form action=\"UpdateTask.jsp\" method=\"GET\">"
							+ "<input type=\"hidden\" name=\"taskID\" value=\"" + cpc.getTaskID() + "\">"
							+ "<input type=\"hidden\" name=\"statusID\" value=\"" + cpc.getStatusID() + "\">"
							+ "<input type=\"submit\" value=\"updateTask\">"
							+ "</form></td>");
				}
				out.println("</table>");
				out.println("</body></html>");
	
				out.close();
			}
			else {
//				final List<Task> taskList;
//				
//				try {
//					// Get ALL Tasks
//					DbConnector.getInstance().openDbConnection();
//					taskList = DbConnector.getInstance().getTasks(sessionUser.getUserID());
//					DbConnector.getInstance().closeDbConnection();
//				} catch (Throwable t) {
//					// Inform the user in case of an Error
//					final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
//					response.getWriter().append(errMsg);
//					t.printStackTrace();
//					return;
//				}
				
				
				response.setContentType("text/html; charset=UTF-8");
				final PrintWriter out = response.getWriter();
				
				out.println("<!DOCTYPE html>");
	            out.println("<html><head>");
	            out.println("<title>MyTasks</title>\n");
	           
	            out.println("</head><body>");
	            
	            out.print("<p>Hello Admin (Role ID = 1): " + sessionUser.getUsername() + " <a href=\"LogoutServlet\">Logout</a></p>");
	            
				out.print("<h1>My Tasks</h1>");
//				out.println("<table>");
//				out.println("<tr>");
//				out.println("<th>Task ID</th>");
//				out.println("<th>Title</th>");
//				out.println("<th>Description</th>");
//				out.println("<th>Status ID</th>");
//				out.println("<th>Date Updated</th>");
//				out.println("</tr>");
//				for (Task cpc : taskList) {
//					out.println("<tr>");
//					
//					out.println("<td>" + cpc.getTaskID() + "</td>");
//					out.println("<td>" + cpc.getTitle() + "</td>");
//					out.println("<td>" + cpc.getDesc() + "</td>");
//					out.println("<td>" + cpc.getStatusID() + "</td>");
//					out.println("<td>" + cpc.getDate() + "</td>");
//				}
//				out.println("</table>");
				out.println("</body></html>");
				//out.println("<p><a href=\"UpdatePassword\">Change Password</a></p>");
	
				out.close();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
