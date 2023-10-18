package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Game;
import java.util.List;
import database.ExamDbConnector;

/**
 * Servlet implementation class InsertDB
 */
@WebServlet("/InsertDB")
public class InsertDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public InsertDB() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get User Data from Session
		final HttpSession session = request.getSession();
		final String sessionUser = (String) session.getAttribute("user");
				
		
		// Get HTTP Request Parameters
		if (sessionUser == null) {
			// Redirect User to Login Page
			response.sendRedirect( "Login.html"); 
		}
		else
		{
		
			final String gameDateStr = request.getParameter("datestr");
			final String team1Str = request.getParameter("team1id");
			final String team1SCStr = request.getParameter("team1score");
			final String team2Str = request.getParameter("team2id");
			final String team2SCStr = request.getParameter("team2score");
			List<Game> gamesList = null;

			try {
				// Process Data
				final String dateFormat = "dd-MM-yyyy";
				
				// Date to java.sql.Date
				final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
				final Date gameDate = sdf.parse(gameDateStr);
				final java.sql.Date gameDateF = new java.sql.Date(gameDate.getTime());  
				
				final Integer team1 = Integer.parseInt(team1Str);
				final Integer team1SC = Integer.parseInt(team1SCStr);
				final Integer team2 = Integer.parseInt(team2Str);
				final Integer team2SC = Integer.parseInt(team2SCStr);
				
				// Store Record
				final ExamDbConnector db = new ExamDbConnector();
				db.openDbConnection();
				gamesList = db.getGamesList();
				db.closeDbConnection();
				
				// Get All Records
				db.openDbConnection();
				db.recordData(gameDateF, team1, team1SC, team2, team2SC);
				db.closeDbConnection();
				
			} catch (Throwable t) {
				// Inform the user in case of an Error
				final String errMsg = "Storing Record Problem ... { " + t.getMessage() + " } Ask system administrators for details !";
				response.getWriter().append(errMsg);
				t.printStackTrace();
			}
			
			response.setContentType("text/html; charset=UTF-8");
			final PrintWriter out = response.getWriter();
			
			out.println("<!DOCTYPE html>");
	        out.println("<html><head>");
	        out.println("<title>All Recorded Games</title>\n");
	        //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/adminPageStyle.css\" >");
	        out.println("</head><body>");
	        
	       // out.print("<p>Hello: " + sessionUser.getUsername() + " <a href=\"LogoutServlet\">Logout</a></p>");
	        
			out.print("<h1>All Recorded Games</h1>");
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>Team 1</th>");
			out.println("<th>Team 2</th>");
			out.println("<th>Team 1 Score</th>");
			out.println("<th>Team 2 Score</th>");
			
			out.println("</tr>");
			for (Game game : gamesList) {
				out.println("<tr>");
				
				out.println("<td>" + game.getName1() + "</td>");
				out.println("<td>" + game.getName2() + "</td>");
				out.println("<td>" + game.getTeam1SC() + "</td>");
				out.println("<td>" + game.getTeam2SC() + "</td>");
			}
			out.println("</table>");
			out.println("</body></html>");
			out.println("<p><a href=\"InsertData.html\">Insert more Records</a></p>");
	
			out.close();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
