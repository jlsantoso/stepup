<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html> 
<html>  
  <head> 
  	<script src="http://beta.openbadges.org/issuer.js"></script>
	<title>Get a badge!</title>
  </head>
  <body>
  <%
  String username = request.getParameter("username");
  String badgeID = request.getParameter("badgeID");
  
  %> 
  <body onLoad="OpenBadges.issue('http://openbadges-hci.appspot.com/rest/getBadge/<%=username.substring(0, username.indexOf("."))%>/<%=badgeID%>')">
  
  </body>
</html>