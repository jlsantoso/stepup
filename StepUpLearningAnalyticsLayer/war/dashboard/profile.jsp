<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipalImpl"%>

<%
	String principalName =  request.getRemoteUser();

	session.setAttribute("isActive", true);
	session.setAttribute("principalName", principalName);

	AttributePrincipalImpl principal = (AttributePrincipalImpl) request
			.getUserPrincipal();

	Map<String, Object> attributes = principal.getAttributes();

	Iterator attributeNames = attributes.keySet().iterator();
%>

<html>
<head/>
<body>



	<div class="page-header">
		<h1>Profile Details</h1>
	</div>
	<h2><%=principalName%></h2>

	<table
		class="table table-striped table table-bordered table-hover table-condensed">
		<caption>Principal Attribute List</caption>
		<thead>
			<tr>
				<th>NAME</th>
				<th>VALUE</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (; attributeNames.hasNext();) {
					out.println("<tr><th>");
					String attributeName = (String) attributeNames.next();
					out.println(attributeName);
					out.println("</th><td>");
					Object attributeValue = attributes.get(attributeName);
					out.println(attributeValue);
					out.println("</td></tr>");
				}
			%>
		</tbody>
	</table>


</body></html>