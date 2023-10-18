<%@page import="database.Task"%>
<%@page import="database.User"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html><head>
	<meta charset="ISO-8859-1">
	
	<title>Record Test</title>
	
	<!-- Specify the CSS File 
	<link rel="stylesheet" type="text/css" href="CSS/adminPageStyle.css" >	-->
	
</head><body>
	
	<%
	final User sessionUser = (User) session.getAttribute("user");
	final String taskID = request.getParameter("taskID");
	final String statusID = request.getParameter("statusID");
	if (sessionUser == null || taskID == null || statusID == null) {
		// Redirect User to Login Page
		response.sendRedirect( "Login.html"); 
	}
	%>
	<p>Hello: <%= sessionUser.getUsername() %>  <a href="LogoutServlet">Logout</a></p>
	<h1>Update Task</h1>
	<form id="recordForm"action="UpdateTaskServlet" method="GET">
		<input type="hidden" name="taskID" value="<%= taskID %>" >
		<div id="select">
			<select name="statusID" id="statusID" required>		    <!--type="text" placeholder="Select Method of Test" <option disabled selected>Select Method of Test</option>    value-->
			<% if (Integer.parseInt(statusID) == 1) { %>
		    	<option value="1" selected>ASSIGNED</option>
		        <option value="2">PENDING</option>
		        <option value="3">COMPLETED</option>
			
			<%} else if (Integer.parseInt(statusID) == 2) { %>
				<option value="1">ASSIGNED</option>
		        <option value="2" selected>PENDING</option>
		        <option value="3">COMPLETED</option>
		     
		    <%} else if (Integer.parseInt(statusID) == 3) { %>
				<option value="1">ASSIGNED</option>
		        <option value="2">PENDING</option>
		        <option value="3" selected>COMPLETED</option>
		    <% } %>
	       	</select>
		</div>
		<input type="submit" name="Update" value="Update">
	</form>

</body></html>