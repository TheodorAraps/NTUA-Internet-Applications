<%@page import="database.Cases"%>
<%@page import="database.User"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html><head>
	<meta charset="ISO-8859-1">
	
	<title>Record Test</title>
	
	<!-- Specify the CSS File -->
	<link rel="stylesheet" type="text/css" href="CSS/adminPageStyle.css" >	
	
</head><body>
	
	<%
	final User sessionUser = (User) session.getAttribute("user");
	if (sessionUser == null || request.getParameter("userID") == null) {
		// Redirect User to Login Page
		response.sendRedirect( "Login.html"); 
	}
	%>
	<p>Hello: <%= sessionUser.getUsername() %>  <a href="LogoutServlet">Logout</a></p>
	<h1>Add Record of of COVID Test</h1>
	<form id="recordForm"action="addTestRecordServlet" method="POST">
		<input type="hidden" name="userid" value="<%=sessionUser.getId()%>">
		<div id="select">
			<select name="test-method" id="dropdown" required>		    <!--type="text" placeholder="Select Method of Test"-->
	        <option disabled selected>Select Method of Test</option>    <!--value-->
     		<option value="1">SELF Test</option>
	        <option value="2">Rapid Test</option>
	        <option value="3">PCR Test</option>
	       	</select>
		</div>
		<table class="RecordInfo">
			<tr>
				<th>Diagnosis Date:</th>
				<td> <input type="text" name="diagDate" required> </td>
				<td>Format: YYYY/MM/DD</td>
			</tr>
			<tr>
				<th>Diagnosis Location:</th>
				<td> <input type="text" name="diagLoc"> </td>
				<td>(Required only for Rapid Test)</td>
			</tr>
			<tr>
				<th>Diagnosis Report UID:</th>
				<td> <input type="text" name="diagRepUID"> </td>
				<td>(Required only for PCR Test)</td>
			</tr>
			<tr>
				<td> <input id="reset" type="reset" value="Reset" > </td>
				<td colspan="2"> <input type="submit" value="Add Record"> </td>
			</tr>
		</table>
	</form>

</body></html>