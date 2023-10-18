

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LabDate
 */
@WebServlet("/LabDate")
public class LabDate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LabDate() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get Lab exercise (id)
		final int labID = Integer.parseInt(request.getParameter("sub"));
		// Examine id
		final String checkDate = checkDate(labID);
		// Return Examination Outcome
		response.getWriter().write(checkDate);
	}
	
	private String checkDate (int i) {
		if (i == 1) return "06/04/2022";
		else if (i == 2) return "Not specified yet !";
		else if (i == 3) return "Not specified yet !";
		else return "Not specified yet !";
	}
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
