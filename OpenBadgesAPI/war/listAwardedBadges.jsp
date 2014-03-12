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
<%JSONObject result = new JSONObject(RestClient.doGet("http://inquiry.wespot.net/services/api/rest/json/?method=inquiry.users&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&inquiryId="+request.getParameter("context")));
JSONArray users_list = result.getJSONArray("result");
JSONArray badges_array = new JSONArray(PersistanceLayerAwardedBadge.getAwardedBadgeByContext(request.getParameter("context"), "1354615295762", ""));
%>
<%
	
    
    for (int i=0;i<users_list.length();i++){
    	JSONObject user = users_list.getJSONObject(i);
%>

<div data-role="header"> 
	<h1><%=user.getString("name")%></h1>
</div>
<div data-role="body"> 
		<%for (int j=0;j<badges_array.length();j++){
	    	JSONObject badge = badges_array.getJSONObject(j); 
	    	String user_id=user.getString("oauthProvider").toLowerCase()+"_"+user.getString("oauthId").toLowerCase();
	    	if (user_id.toLowerCase().compareTo(badge.getString("username").toLowerCase())==0){
	    	%>
	 			<img width="30px" src="<%=badge.getJSONObject("jsonBadge").getJSONObject("badge").getString("image")%>"/>	
	<%		}
    	}
%>
</div>

<%
    }
%>

</body>
</html>