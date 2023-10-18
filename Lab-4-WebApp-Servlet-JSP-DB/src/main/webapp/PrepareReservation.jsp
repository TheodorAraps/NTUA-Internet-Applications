<%@page import="lab4.db.model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html><head>
	<meta charset="ISO-8859-1">
	
	<title>PrepareReservation</title>
	
	<!-- Specify the CSS File -->
	<link rel="stylesheet" type="text/css" href="CSS/WebApp.css" >	
	
</head><body>

	<%
	final User sessionUser = (User) session.getAttribute("user");
	if (sessionUser == null || request.getParameter("vehicleid") == null) {
		// Redirect User to Login Page
		response.sendRedirect( "Login.html"); 
	}
	%>

	<form action="MakeReservationServlet" method="POST">
		<input type="hidden" name="userid" value="<%=sessionUser.getId()%>">
		<input type="hidden" name="vehicleid" value="<%=request.getParameter("vehicleid")%>">
		<table class="logintable">
			<tr>
				<th>Start Date:</th>
				<td> <input type="text" name="startdate"> </td>
				<td>Format: YYYY/MM/DD</td>
			</tr>
			<tr>
				<th>End Date:</th>
				<td> <input type="text" name="enddate"> </td>
				<td>Format: YYYY/MM/DD</td>
			</tr>
			<tr>
				<th>Comments:</th>
				<td> <input type="text" name="comments"> </td>
				<td>(Any other useful information)</td>
			</tr>
			<tr>
				<td> <input type="reset" value="Reset" > </td>
				<td colspan="2"> <input type="submit" value="Make Reservation"> </td>
			</tr>
		</table>
	</form>

</body></html>