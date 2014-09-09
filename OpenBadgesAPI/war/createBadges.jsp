<%@ page import="org.be.kuleuven.hci.openbadges.persistanceLayer.*"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONObject"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONArray"%>
<%@ page import="org.be.kuleuven.hci.openbadges.utils.RestClient"%>
<html>
<head>

<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css">
		<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
</head>
<body>
<div data-role="header"> 
<h1>Creating badges... wait some seconds...</h1>
</div>
<%
RestClient.doPostAuth("http://openbadges-wespot.appspot.com/rest/badges/wespot/"+request.getParameter("context"), "", "m4llklqcc0i2mk3usfucqdfcg5");
//RestClient.doPostAuth("http://localhost:8890/rest/badges/wespot/"+request.getParameter("context"), "", "1dhpkotcf3skosvso0mel9veq7");
%>

<script>
var check = function(){
	location.replace('/menu.jsp?context=<%=request.getParameter("context")%>&inquiryserver=dev.inquiry.wespot.net');
}

setTimeout(check, 5000);

</script>
</body>
</html>